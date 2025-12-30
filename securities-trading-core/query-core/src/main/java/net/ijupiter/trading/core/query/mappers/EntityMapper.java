package net.ijupiter.trading.core.query.mappers;

import net.ijupiter.trading.api.query.dtos.*;
import net.ijupiter.trading.core.query.entities.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询实体与DTO之间的映射转换器
 */
@Component
public class EntityMapper {

    // CustomerQueryEntity 转换为 CustomerFinancialSummaryDTO 中的客户信息部分
    public void mapToCustomerFinancialSummaryDTO(CustomerQueryEntity entity, CustomerFinancialSummaryDTO dto) {
        if (entity != null) {
            dto.setCustomerId(entity.getId());
            dto.setCustomerName(entity.getName());
            dto.setCustomerType(Integer.parseInt(entity.getType()));
            dto.setCustomerStatus(entity.getStatus());
            dto.setRegistrationTime(entity.getCreateTime());
        }
    }

    // FundingAccountQueryEntity 转换为 CustomerFinancialSummaryDTO.FundingAccountSummaryDTO
    public CustomerFinancialSummaryDTO.FundingAccountSummaryDTO mapToFundingAccountSummaryDTO(
            FundingAccountQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerFinancialSummaryDTO.FundingAccountSummaryDTO dto = 
                new CustomerFinancialSummaryDTO.FundingAccountSummaryDTO();
        dto.setAccountId(entity.getId());
        // DTO中使用accountNumber而不是accountNo
        dto.setAccountNumber(entity.getAccountNo());
        // DTO中accountType是String类型，需要转换
        dto.setAccountType(String.valueOf(entity.getAccountType()));
        dto.setAccountName(entity.getAccountName());
        dto.setBalance(entity.getBalance());
        dto.setFrozenAmount(entity.getFrozenAmount());
        dto.setStatus(entity.getStatus());
        dto.setCreateTime(entity.getCreateTime());
        return dto;
    }

    // SecuritiesAccountQueryEntity 转换为 CustomerFinancialSummaryDTO.SecuritiesAccountSummaryDTO
    public CustomerFinancialSummaryDTO.SecuritiesAccountSummaryDTO mapToSecuritiesAccountSummaryDTO(
            SecuritiesAccountQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerFinancialSummaryDTO.SecuritiesAccountSummaryDTO dto = 
                new CustomerFinancialSummaryDTO.SecuritiesAccountSummaryDTO();
        dto.setAccountId(entity.getId());
        // DTO中使用accountNumber而不是accountNo
        dto.setAccountNumber(entity.getAccountNo());
        // DTO中使用securitiesCode而不是securitiesType
        dto.setSecuritiesCode(entity.getSecuritiesType());
        dto.setAccountName(entity.getAccountName());
        dto.setStatus(entity.getStatus());
        dto.setCreateTime(entity.getCreateTime());
        return dto;
    }

    // TransactionQueryEntity 转换为 CustomerTransactionSummaryDTO
    public CustomerTransactionSummaryDTO mapToTransactionSummaryDTO(TransactionQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerTransactionSummaryDTO dto = new CustomerTransactionSummaryDTO();
        dto.setTransactionId(entity.getId());
        // DTO中使用transactionCode而不是transactionNo
        dto.setTransactionCode(entity.getTransactionNo());
        dto.setCustomerId(entity.getCustomerId());
        // DTO中没有fundingAccountId字段，跳过
        // DTO中使用accountId而不是securitiesAccountId
        dto.setAccountId(entity.getSecuritiesAccountId());
        dto.setTransactionType(Integer.parseInt(entity.getTransactionType()));
        dto.setSecurityCode(entity.getSecurityCode());
        dto.setSecurityName(entity.getSecurityName());
        // DTO中使用price而不是transactionPrice
        dto.setPrice(entity.getTransactionPrice());
        // DTO中使用quantity而不是transactionVolume
        dto.setQuantity(new BigDecimal(entity.getTransactionVolume()));
        // DTO中使用amount而不是transactionAmount
        dto.setAmount(entity.getTransactionAmount());
        // DTO中使用commission而不是transactionFee
        dto.setCommission(entity.getTransactionFee());
        dto.setStatus(entity.getStatus());
        dto.setTransactionTime(entity.getTransactionTime());
        return dto;
    }

    // FundingTransactionQueryEntity 转换为 CustomerFundingTransactionSummaryDTO
    public CustomerFundingTransactionSummaryDTO mapToFundingTransactionSummaryDTO(
            FundingTransactionQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerFundingTransactionSummaryDTO dto = new CustomerFundingTransactionSummaryDTO();
        dto.setTransactionId(entity.getId());
        // DTO中没有transactionNo字段，跳过
        dto.setCustomerId(entity.getCustomerId());
        // DTO中使用accountId而不是fundingAccountId
        dto.setAccountId(entity.getFundingAccountId());
        // DTO中transactionType是Integer类型，需要转换
        dto.setTransactionType(Integer.parseInt(entity.getTransactionType()));
        // DTO中使用amount而不是transactionAmount
        dto.setAmount(entity.getTransactionAmount());
        // DTO中没有transactionChannel字段，跳过
        // DTO中使用description而不是transactionDescription
        dto.setDescription(entity.getTransactionDescription());
        dto.setStatus(entity.getStatus());
        dto.setTransactionTime(entity.getTransactionTime());
        return dto;
    }

    // FundingAccountQueryEntity 转换为 CustomerFundingBalanceDTO
    public CustomerFundingBalanceDTO mapToFundingBalanceDTO(FundingAccountQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerFundingBalanceDTO dto = new CustomerFundingBalanceDTO();
        dto.setAccountId(entity.getId());
        // DTO中使用accountNumber而不是accountNo
        dto.setAccountNumber(entity.getAccountNo());
        dto.setCustomerId(entity.getCustomerId());
        // DTO中accountType是String类型，需要转换
        dto.setAccountType(String.valueOf(entity.getAccountType()));
        dto.setAccountName(entity.getAccountName());
        // DTO中使用currentBalance而不是balance
        dto.setCurrentBalance(entity.getBalance());
        // DTO中使用frozenBalance而不是frozenAmount
        dto.setFrozenBalance(entity.getFrozenAmount());
        dto.setStatus(entity.getStatus());
        dto.setUpdateTime(entity.getUpdateTime());
        return dto;
    }

    // SecuritiesPositionQueryEntity 转换为 CustomerSecuritiesPositionDTO
    public CustomerSecuritiesPositionDTO mapToSecuritiesPositionDTO(SecuritiesPositionQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerSecuritiesPositionDTO dto = new CustomerSecuritiesPositionDTO();
        dto.setPositionId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        // DTO中使用accountId而不是securitiesAccountId
        dto.setAccountId(entity.getSecuritiesAccountId());
        dto.setSecurityCode(entity.getSecurityCode());
        dto.setSecurityName(entity.getSecurityName());
        // DTO中使用positionQuantity而不是positionVolume，需要转换为BigDecimal
        dto.setPositionQuantity(new BigDecimal(entity.getPositionVolume()));
        // DTO中使用availableQuantity而不是availableVolume，需要转换为BigDecimal
        dto.setAvailableQuantity(new BigDecimal(entity.getAvailableVolume()));
        // DTO中使用frozenQuantity而不是frozenVolume，需要转换为BigDecimal
        dto.setFrozenQuantity(new BigDecimal(entity.getFrozenVolume()));
        // DTO中使用costPrice而不是averagePrice
        dto.setCostPrice(entity.getAveragePrice());
        // DTO中使用currentPrice而不是marketPrice
        dto.setCurrentPrice(entity.getMarketPrice());
        dto.setMarketValue(entity.getMarketValue());
        // DTO中使用costAmount而不是costValue
        dto.setCostAmount(entity.getCostValue());
        // DTO中使用profitLossAmount而不是profitLoss
        dto.setProfitLossAmount(entity.getProfitLoss());
        // DTO中使用profitLossRate而不是profitLossRatio
        dto.setProfitLossRate(entity.getProfitLossRatio());
        // DTO中没有tradeDate字段，跳过
        return dto;
    }

    // DailySecuritiesProfitQueryEntity 转换为 CustomerDailySecuritiesProfitDTO
    public CustomerDailySecuritiesProfitDTO mapToDailySecuritiesProfitDTO(DailySecuritiesProfitQueryEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CustomerDailySecuritiesProfitDTO dto = new CustomerDailySecuritiesProfitDTO();
        dto.setRecordId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        // DTO中没有securitiesAccountId字段，跳过
        dto.setSecurityCode(entity.getSecurityCode());
        dto.setSecurityName(entity.getSecurityName());
        // DTO中使用positionQuantity而不是positionVolume，需要转换为BigDecimal
        dto.setPositionQuantity(new BigDecimal(entity.getPositionVolume()));
        // DTO中使用openingPrice而不是openPrice
        dto.setOpeningPrice(entity.getOpenPrice());
        // DTO中使用closingPrice而不是closePrice
        dto.setClosingPrice(entity.getClosePrice());
        // DTO中有tradeDate字段
        dto.setTradeDate(entity.getTradeDate());
        // DTO中没有marketValueStart/marketValueEnd字段，跳过
        // DTO中使用dailyProfitLossAmount而不是dailyProfitLoss
        dto.setDailyProfitLossAmount(entity.getDailyProfitLoss());
        // DTO中使用dailyProfitLossRate而不是dailyProfitLossRatio
        dto.setDailyProfitLossRate(entity.getDailyProfitLossRatio());
        // DTO中使用totalProfitLossAmount而不是cumulativeProfitLoss
        dto.setTotalProfitLossAmount(entity.getCumulativeProfitLoss());
        // DTO中使用totalProfitLossRate而不是cumulativeProfitLossRatio
        dto.setTotalProfitLossRate(entity.getCumulativeProfitLossRatio());
        return dto;
    }

    // List<TransactionQueryEntity> 转换为 Page<CustomerTransactionSummaryDTO>
    public org.springframework.data.domain.Page<CustomerTransactionSummaryDTO> mapToTransactionSummaryDTOPage(
            org.springframework.data.domain.Page<TransactionQueryEntity> entityPage) {
        return entityPage.map(this::mapToTransactionSummaryDTO);
    }

    // List<FundingTransactionQueryEntity> 转换为 Page<CustomerFundingTransactionSummaryDTO>
    public org.springframework.data.domain.Page<CustomerFundingTransactionSummaryDTO> mapToFundingTransactionSummaryDTOPage(
            org.springframework.data.domain.Page<FundingTransactionQueryEntity> entityPage) {
        return entityPage.map(this::mapToFundingTransactionSummaryDTO);
    }

    // List<SecuritiesPositionQueryEntity> 转换为 Page<CustomerSecuritiesPositionDTO>
    public org.springframework.data.domain.Page<CustomerSecuritiesPositionDTO> mapToSecuritiesPositionDTOPage(
            org.springframework.data.domain.Page<SecuritiesPositionQueryEntity> entityPage) {
        return entityPage.map(this::mapToSecuritiesPositionDTO);
    }

    // List<DailySecuritiesProfitQueryEntity> 转换为 Page<CustomerDailySecuritiesProfitDTO>
    public org.springframework.data.domain.Page<CustomerDailySecuritiesProfitDTO> mapToDailySecuritiesProfitDTOPage(
            org.springframework.data.domain.Page<DailySecuritiesProfitQueryEntity> entityPage) {
        return entityPage.map(this::mapToDailySecuritiesProfitDTO);
    }

    // List<FundingAccountQueryEntity> 转换为 List<CustomerFundingBalanceDTO>
    public List<CustomerFundingBalanceDTO> mapToFundingBalanceDTOList(List<FundingAccountQueryEntity> entityList) {
        return entityList.stream()
                .map(this::mapToFundingBalanceDTO)
                .collect(Collectors.toList());
    }

    // List<SecuritiesPositionQueryEntity> 转换为 List<CustomerSecuritiesPositionDTO>
    public List<CustomerSecuritiesPositionDTO> mapToSecuritiesPositionDTOList(List<SecuritiesPositionQueryEntity> entityList) {
        return entityList.stream()
                .map(this::mapToSecuritiesPositionDTO)
                .collect(Collectors.toList());
    }
}