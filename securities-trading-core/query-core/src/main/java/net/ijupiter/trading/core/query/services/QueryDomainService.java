package net.ijupiter.trading.core.query.services;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.*;
import net.ijupiter.trading.api.query.queries.*;
import net.ijupiter.trading.api.query.services.QueryService;
import net.ijupiter.trading.core.query.entities.*;
import net.ijupiter.trading.core.query.mappers.EntityMapper;
import net.ijupiter.trading.core.query.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询领域服务实现
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class QueryDomainService implements QueryService {
    
    @Autowired
    private CustomerQueryRepository customerQueryRepository;
    
    @Autowired
    private FundingAccountQueryRepository fundingAccountQueryRepository;
    
    @Autowired
    private SecuritiesAccountQueryRepository securitiesAccountQueryRepository;
    
    @Autowired
    private TransactionQueryRepository transactionQueryRepository;
    
    @Autowired
    private FundingTransactionQueryRepository fundingTransactionQueryRepository;
    
    @Autowired
    private SecuritiesPositionQueryRepository securitiesPositionQueryRepository;
    
    @Autowired
    private DailySecuritiesProfitQueryRepository dailySecuritiesProfitQueryRepository;
    
    @Autowired
    private EntityMapper entityMapper;
    
    @Override
    public CustomerFinancialSummaryDTO queryCustomerFinancialSummary(CustomerFinancialSummaryQuery query) {
        CustomerFinancialSummaryDTO summary = new CustomerFinancialSummaryDTO();
        
        // 查询客户基础信息
        CustomerQueryEntity customerInfo = customerQueryRepository.findById(query.getCustomerId())
                .orElseThrow(() -> new RuntimeException("客户不存在: " + query.getCustomerId()));
        
        // 使用EntityMapper转换客户信息
        entityMapper.mapToCustomerFinancialSummaryDTO(customerInfo, summary);
        
        // 查询资金账户信息
        if (query.getIncludeFundingAccounts()) {
            List<FundingAccountQueryEntity> fundingAccountEntities = 
                    fundingAccountQueryRepository.findByCustomerId(query.getCustomerId());
            
            List<CustomerFinancialSummaryDTO.FundingAccountSummaryDTO> fundingAccounts = 
                    fundingAccountEntities.stream()
                    .map(entityMapper::mapToFundingAccountSummaryDTO)
                    .collect(Collectors.toList());
            
            summary.setFundingAccounts(fundingAccounts);
            
            // 计算资金总余额
            BigDecimal totalFundingBalance = fundingAccounts.stream()
                    .map(CustomerFinancialSummaryDTO.FundingAccountSummaryDTO::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            summary.setTotalFundingBalance(totalFundingBalance);
        }
        
        // 查询证券账户信息
        if (query.getIncludeSecuritiesAccounts()) {
            List<SecuritiesAccountQueryEntity> securitiesAccountEntities = 
                    securitiesAccountQueryRepository.findByCustomerId(query.getCustomerId());
            
            List<CustomerFinancialSummaryDTO.SecuritiesAccountSummaryDTO> securitiesAccounts = 
                    securitiesAccountEntities.stream()
                    .map(entityMapper::mapToSecuritiesAccountSummaryDTO)
                    .collect(Collectors.toList());
            
            summary.setSecuritiesAccounts(securitiesAccounts);
        }
        
        // 计算证券持仓总市值
        BigDecimal totalSecuritiesValue = securitiesPositionQueryRepository
                .findByCustomerId(query.getCustomerId()).stream()
                .map(position -> position.getMarketPrice().multiply(BigDecimal.valueOf(position.getPositionVolume())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        summary.setTotalSecuritiesValue(totalSecuritiesValue);
        
        // 计算客户总资产
        BigDecimal totalAssets = (summary.getTotalFundingBalance() != null ? 
                summary.getTotalFundingBalance() : BigDecimal.ZERO)
                .add(totalSecuritiesValue);
        summary.setTotalAssets(totalAssets);
        
        return summary;
    }
    
    @Override
    public Page<CustomerTransactionSummaryDTO> queryCustomerTransactionSummary(CustomerTransactionSummaryQuery query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        
        Page<TransactionQueryEntity> results = transactionQueryRepository.findCustomerTransactionsWithFilters(
                query.getCustomerId(), 
                query.getSecurityCode(),
                query.getTransactionType(),
                query.getStatus(),
                query.getStartTime(),
                query.getEndTime(),
                pageable);
        
        return entityMapper.mapToTransactionSummaryDTOPage(results);
    }
    
    @Override
    public Page<CustomerFundingTransactionSummaryDTO> queryCustomerFundingTransactionSummary(CustomerFundingTransactionSummaryQuery query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        
        Page<FundingTransactionQueryEntity> results = fundingTransactionQueryRepository.findCustomerFundingTransactionsWithFilters(
                query.getCustomerId(),
                query.getFundingAccountId(),
                query.getTransactionType(),
                query.getStatus(),
                query.getStartTime(),
                query.getEndTime(),
                pageable);
        
        return entityMapper.mapToFundingTransactionSummaryDTOPage(results);
    }
    
    @Override
    public List<CustomerFundingBalanceDTO> queryCustomerFundingBalance(CustomerFundingBalanceQuery query) {
        List<FundingAccountQueryEntity> fundingAccounts = fundingAccountQueryRepository.findByCustomerIdAndFilters(
                query.getCustomerId(),
                query.getAccountId(),
                query.getAccountType(),
                query.getStatus());
        
        return entityMapper.mapToFundingBalanceDTOList(fundingAccounts);
    }
    
    @Override
    public Page<CustomerSecuritiesPositionDTO> queryCustomerSecuritiesPosition(CustomerSecuritiesPositionQuery query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        
        Page<SecuritiesPositionQueryEntity> results = securitiesPositionQueryRepository.findCustomerSecuritiesPositionsWithFilters(
                query.getCustomerId(),
                query.getSecurityCode(),
                query.getSecuritiesAccountId(),
                pageable);
        
        return entityMapper.mapToSecuritiesPositionDTOPage(results);
    }
    
    @Override
    public Page<CustomerDailySecuritiesProfitDTO> queryCustomerDailySecuritiesProfit(CustomerDailySecuritiesProfitQuery query) {
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize());
        
        Page<DailySecuritiesProfitQueryEntity> results = dailySecuritiesProfitQueryRepository.findCustomerDailySecuritiesProfitWithFilters(
                query.getCustomerId(),
                query.getSecurityCode(),
                query.getStartDate(),
                query.getEndDate(),
                pageable);
        
        return entityMapper.mapToDailySecuritiesProfitDTOPage(results);
    }
    
    /**
     * 计算账户变化额
     */
    private void calculateAccountChanges(CustomerFinancialSummaryDTO.FundingAccountSummaryDTO account, 
                                    LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return;
        }
        
        // 计算日变化
        LocalDateTime dayStart = startTime.toLocalDate().atStartOfDay();
        LocalDateTime dayEnd = endTime.toLocalDate().atTime(23, 59, 59);
        // 这里应该调用repository计算实际的变化额
        // 简化实现，实际应通过数据库查询
    }
    
    /**
     * 计算账户变化额
     */
    private void calculateAccountChanges(CustomerFundingBalanceDTO account, 
                                    LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return;
        }
        
        // 计算日变化
        LocalDateTime dayStart = startTime.toLocalDate().atStartOfDay();
        LocalDateTime dayEnd = endTime.toLocalDate().atTime(23, 59, 59);
        // 这里应该调用repository计算实际的变化额
        // 简化实现，实际应通过数据库查询
    }
    
    /**
     * 计算持仓盈亏
     */
    private void calculatePositionProfit(CustomerSecuritiesPositionDTO position) {
        // 市值
        BigDecimal marketValue = position.getCurrentPrice().multiply(position.getPositionQuantity());
        position.setMarketValue(marketValue);
        
        // 成本金额
        BigDecimal costAmount = position.getCostPrice().multiply(position.getPositionQuantity());
        position.setCostAmount(costAmount);
        
        // 盈亏金额
        BigDecimal profitLossAmount = marketValue.subtract(costAmount);
        position.setProfitLossAmount(profitLossAmount);
        
        // 盈亏率
        if (costAmount.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal profitLossRate = profitLossAmount.divide(costAmount, 4, BigDecimal.ROUND_HALF_UP);
            position.setProfitLossRate(profitLossRate);
        }
    }
    
    
}