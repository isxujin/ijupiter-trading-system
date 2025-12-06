package net.ijupiter.trading.core.customer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.customer.commands.*;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.dtos.FundAccountBasicInfoDTO;
import net.ijupiter.trading.api.customer.dtos.FundAccountBalanceDTO;
import net.ijupiter.trading.api.customer.dtos.TradingAccountBasicInfoDTO;
import net.ijupiter.trading.api.customer.dtos.TradingAccountPositionDTO;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import net.ijupiter.trading.api.customer.services.CustomerService;
import net.ijupiter.trading.core.customer.entities.CustomerEntity;
import net.ijupiter.trading.core.customer.entities.TradingAccountBasicInfoEntity;
import net.ijupiter.trading.core.customer.entities.FundAccountBasicInfoEntity;
import net.ijupiter.trading.core.customer.repositories.CustomerRepository;
    
import net.ijupiter.trading.core.customer.repositories.FundAccountBasicInfoRepository;
import net.ijupiter.trading.core.customer.repositories.FundAccountBalanceRepository;
import net.ijupiter.trading.core.customer.repositories.TradingAccountBasicInfoRepository;
import net.ijupiter.trading.core.customer.repositories.TradingAccountPositionRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 客户服务实现
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CommandGateway commandGateway;
    private final CustomerRepository customerRepository;
    
    private final FundAccountBasicInfoRepository fundAccountBasicInfoRepository;
    private final FundAccountBalanceRepository fundAccountBalanceRepository;
    private final TradingAccountBasicInfoRepository tradingAccountBasicInfoRepository;
    private final TradingAccountPositionRepository tradingAccountPositionRepository;

    // 客户管理方法实现
    @Override
    @Transactional
    public String createCustomer(CreateCustomerCommand command) {
        log.info("创建客户: {}", command);
        
        // 参数校验
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        Assert.hasText(command.getCustomerCode(), "客户编号不能为空");
        Assert.hasText(command.getCustomerName(), "客户名称不能为空");
        Assert.hasText(command.getIdCardNumber(), "身份证号不能为空");
        Assert.hasText(command.getPhoneNumber(), "手机号不能为空");
        Assert.hasText(command.getRiskLevel(), "风险等级不能为空");

        // 检查身份证号是否已存在
        if (customerRepository.existsByIdCardNumber(command.getIdCardNumber())) {
            throw new IllegalArgumentException("身份证号已存在: " + command.getIdCardNumber());
        }

        // 检查手机号是否已存在
        if (customerRepository.existsByPhoneNumber(command.getPhoneNumber())) {
            throw new IllegalArgumentException("手机号已存在: " + command.getPhoneNumber());
        }

        // 检查客户编号是否已存在
        if (customerRepository.existsByCustomerCode(command.getCustomerCode())) {
            throw new IllegalArgumentException("客户编号已存在: " + command.getCustomerCode());
        }

        // 发送命令创建客户
        commandGateway.send(command);
        
        return command.getCustomerId();
    }

    @Override
    @Transactional
    public void updateCustomer(UpdateCustomerCommand command) {
        log.info("更新客户信息: {}", command);
        
        // 参数校验
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        
        // 检查客户是否存在
        Optional<CustomerEntity> customerOptional = customerRepository.findById(command.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("客户不存在: " + command.getCustomerId());
        }

        CustomerEntity customer = customerOptional.get();
        
        // 检查客户状态，已注销的客户不能更新
        if (CustomerStatus.CANCELLED.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("已注销的客户不能更新信息");
        }

        // 发送命令更新客户
        commandGateway.send(command);
    }

    @Override
    @Transactional
    public void freezeCustomer(FreezeCustomerCommand command) {
        log.info("冻结客户: {}", command);
        
        // 参数校验
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        Assert.hasText(command.getReason(), "冻结原因不能为空");
        
        // 检查客户是否存在
        Optional<CustomerEntity> customerOptional = customerRepository.findById(command.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("客户不存在: " + command.getCustomerId());
        }

        CustomerEntity customer = customerOptional.get();
        
        // 检查客户状态
        if (CustomerStatus.CANCELLED.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("已注销的客户不能冻结");
        }
        
        if (CustomerStatus.FROZEN.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("客户已经处于冻结状态");
        }

        // 发送命令冻结客户
        commandGateway.send(command);
    }

    @Override
    @Transactional
    public void unfreezeCustomer(UnfreezeCustomerCommand command) {
        log.info("解冻客户: {}", command);
        
        // 参数校验
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        Assert.hasText(command.getReason(), "解冻原因不能为空");
        
        // 检查客户是否存在
        Optional<CustomerEntity> customerOptional = customerRepository.findById(command.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("客户不存在: " + command.getCustomerId());
        }

        CustomerEntity customer = customerOptional.get();
        
        // 检查客户状态
        if (!CustomerStatus.FROZEN.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("只有冻结状态的客户才能解冻");
        }

        // 发送命令解冻客户
        commandGateway.send(command);
    }

    @Override
    @Transactional
    public void cancelCustomer(CancelCustomerCommand command) {
        log.info("注销客户: {}", command);
        
        // 参数校验
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        Assert.hasText(command.getReason(), "注销原因不能为空");
        
        // 检查客户是否存在
        Optional<CustomerEntity> customerOptional = customerRepository.findById(command.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("客户不存在: " + command.getCustomerId());
        }

        CustomerEntity customer = customerOptional.get();
        
        // 检查客户状态
        if (CustomerStatus.CANCELLED.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("客户已经处于注销状态");
        }

        // TODO: 检查客户是否有关联的未结算订单或资产

        // 发送命令注销客户
        commandGateway.send(command);
    }

    @Override
    @Transactional
    public boolean login(String customerId) {
        log.info("客户登录: {}", customerId);
        
        // 参数校验
        Assert.hasText(customerId, "客户ID不能为空");
        
        // 检查客户是否存在
        Optional<CustomerEntity> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("客户不存在: " + customerId);
        }

        CustomerEntity customer = customerOptional.get();
        
        // 检查客户状态
        if (CustomerStatus.CANCELLED.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("已注销的客户不能登录");
        }
        
        if (CustomerStatus.FROZEN.equals(customer.getCustomerStatus())) {
            throw new IllegalStateException("冻结状态的客户不能登录");
        }

        // 更新最后登录时间
        customer.setLastLoginTime(LocalDateTime.now());
        customerRepository.save(customer);

        return true;
    }

    @Override
    @Transactional
    public void logout(String customerId) {
        log.info("客户登出: {}", customerId);
        
        // 参数校验
        Assert.hasText(customerId, "客户ID不能为空");
        
        // 登出可以不做任何操作，或者可以记录登出时间
        // 这里仅做参数校验，不做其他操作
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getCustomerById(String customerId) {
        log.debug("根据客户ID查询客户: {}", customerId);
        
        return customerRepository.findById(customerId)
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getCustomerStatus());
                    // 手动映射时间字段，因为Entity中是closeDate/freezeDate，DTO中是closeDate/freezeDate
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    dto.setFreezeDate(entity.getFreezeDate());
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDTO> getCustomerByCode(String customerCode) {
        log.debug("根据客户编号查询客户: {}", customerCode);
        
        return customerRepository.findByCustomerCode(customerCode)
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getCustomerStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    dto.setFreezeDate(entity.getFreezeDate());
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomersByStatus(CustomerStatus status) {
        log.debug("根据客户状态查询客户: {}", status);
        
        return customerRepository.findByCustomerStatus(status)
                .stream()
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getCustomerStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    dto.setFreezeDate(entity.getFreezeDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        log.debug("查询所有客户");
        
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getCustomerStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    dto.setFreezeDate(entity.getFreezeDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 交易账户管理方法实现
    @Override
    @Transactional
    public String createTradingAccount(CreateTradingAccountCommand command) {
        log.info("创建交易账户: {}", command);
        
        // 参数校验
        Assert.hasText(command.getAccountId(), "账户ID不能为空");
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        Assert.hasText(command.getAccountName(), "账户名称不能为空");
        Assert.hasText(command.getExchangeCode(), "交易所代码不能为空");
        Assert.hasText(command.getExchangeName(), "交易所名称不能为空");
        Assert.hasText(command.getExchangeAccountNumber(), "交易所账号不能为空");
        Assert.hasText(command.getTradingProduct(), "交易产品品种不能为空");
        Assert.notNull(command.getAccountType(), "账户类型不能为空");

        // 检查客户是否存在
        if (!customerRepository.existsById(command.getCustomerId())) {
            throw new IllegalArgumentException("客户不存在: " + command.getCustomerId());
        }

        // 检查客户是否已有相同类型的交易账户
        if (tradingAccountBasicInfoRepository.existsByCustomerIdAndAccountType(command.getCustomerId(), command.getAccountType())) {
            throw new IllegalArgumentException("客户已存在相同类型的交易账户");
        }

        // 发送命令创建交易账户
        commandGateway.send(command);
        
        return command.getAccountId();
    }

    @Override
    @Transactional
    public void closeTradingAccount(String accountId, String reason) {
        log.info("注销交易账户: {}, 原因: {}", accountId, reason);
        
        // 参数校验
        Assert.hasText(accountId, "账户ID不能为空");
        Assert.hasText(reason, "注销原因不能为空");
        
        // 检查交易账户是否存在
        Optional<TradingAccountBasicInfoEntity> accountOptional = tradingAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("交易账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 检查账户是否有未结算订单
        // TODO: 检查账户是否有资产

        // TODO: 发送关闭交易账户命令
    }

    @Override
    @Transactional
    public void freezeTradingAccount(String accountId, String reason) {
        log.info("冻结交易账户: {}, 原因: {}", accountId, reason);
        
        // 参数校验
        Assert.hasText(accountId, "账户ID不能为空");
        Assert.hasText(reason, "冻结原因不能为空");
        
        // 检查交易账户是否存在
        Optional<TradingAccountBasicInfoEntity> accountOptional = tradingAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("交易账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 发送冻结交易账户命令
    }

    @Override
    @Transactional
    public void unfreezeTradingAccount(String accountId, String reason) {
        log.info("解冻交易账户: {}, 原因: {}", accountId, reason);
        
        // 参数校验
        Assert.hasText(accountId, "账户ID不能为空");
        Assert.hasText(reason, "解冻原因不能为空");
        
        // 检查交易账户是否存在
        Optional<TradingAccountBasicInfoEntity> accountOptional = tradingAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("交易账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 发送解冻交易账户命令
    }

    @Override
    @Transactional
    public void updateExchangeAccount(String accountId, String exchangeAccountNumber, 
                                    String tradingPassword, String fundPassword,
                                    String apiKey, String apiSecret) {
        log.info("更新交易所账号信息: 账户ID={}, 交易所账号={}", accountId, exchangeAccountNumber);
        
        // 参数校验
        Assert.hasText(accountId, "交易账户ID不能为空");
        Assert.hasText(exchangeAccountNumber, "交易所账号不能为空");
        
        // 检查交易账户是否存在
        Optional<TradingAccountBasicInfoEntity> accountOptional = tradingAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("交易账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 加密密码和API密钥
        // TODO: 发送更新交易所账号命令
    }

    // Removed legacy full TradingAccountDTO methods. Use basic-info / position methods above.

    // 交易账户基本信息和持仓信息拆分查询方法实现
    @Override
    @Transactional(readOnly = true)
    public Optional<TradingAccountBasicInfoDTO> getTradingAccountBasicInfoById(String accountId) {
        log.debug("根据交易账户ID查询交易账户基本信息: {}", accountId);
        
        return tradingAccountBasicInfoRepository.findByAccountId(accountId)
                .map(entity -> {
                    TradingAccountBasicInfoDTO dto = new TradingAccountBasicInfoDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getAccountStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TradingAccountBasicInfoDTO> getTradingAccountBasicInfoByCustomerId(String customerId) {
        log.debug("根据客户ID查询交易账户基本信息列表: {}", customerId);
        
        return tradingAccountBasicInfoRepository.findByCustomerId(customerId)
                .stream()
                .map(entity -> {
                    TradingAccountBasicInfoDTO dto = new TradingAccountBasicInfoDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getAccountStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TradingAccountPositionDTO> getTradingAccountPositionById(String accountId) {
        log.debug("根据交易账户ID查询交易账户持仓信息: {}", accountId);

        return tradingAccountPositionRepository.findByAccountId(accountId)
                .map(entity -> {
                    TradingAccountPositionDTO dto = new TradingAccountPositionDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TradingAccountPositionDTO> getTradingAccountPositionByCustomerId(String customerId) {
        log.debug("根据客户ID查询交易账户持仓信息列表: {}", customerId);
        
        return tradingAccountPositionRepository.findByCustomerId(customerId)
                .stream()
                .map(entity -> {
                    TradingAccountPositionDTO dto = new TradingAccountPositionDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 资金账户管理方法实现
    @Override
    @Transactional
    public String createFundAccount(CreateFundAccountCommand command) {
        log.info("创建资金账户: {}", command);
        
        // 参数校验
        Assert.hasText(command.getAccountId(), "账户ID不能为空");
        Assert.hasText(command.getCustomerId(), "客户ID不能为空");
        Assert.hasText(command.getAccountName(), "账户名称不能为空");
        Assert.hasText(command.getBankCardNumber(), "银行卡号不能为空");
        Assert.hasText(command.getBankCode(), "银行代码不能为空");
        Assert.hasText(command.getBankName(), "银行名称不能为空");
        Assert.hasText(command.getHolderName(), "持卡人姓名不能为空");
        Assert.hasText(command.getCurrency(), "币种不能为空");
        Assert.notNull(command.getAccountType(), "账户类型不能为空");

        // 检查客户是否存在
        if (!customerRepository.existsById(command.getCustomerId())) {
            throw new IllegalArgumentException("客户不存在: " + command.getCustomerId());
        }

        // 检查客户是否已有相同类型的资金账户
        if (fundAccountBasicInfoRepository.existsByCustomerIdAndAccountType(command.getCustomerId(), command.getAccountType())) {
            throw new IllegalArgumentException("客户已存在相同类型的资金账户");
        }

        // 发送命令创建资金账户
        commandGateway.send(command);
        
        return command.getAccountId();
    }

    @Override
    @Transactional
    public void closeFundAccount(String accountId, String reason) {
        log.info("注销资金账户: {}, 原因: {}", accountId, reason);
        
        // 参数校验
        Assert.hasText(accountId, "账户ID不能为空");
        Assert.hasText(reason, "注销原因不能为空");
        
        // 检查资金账户是否存在
        Optional<FundAccountBasicInfoEntity> accountOptional = fundAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("资金账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 检查账户是否有余额

        // TODO: 发送关闭资金账户命令
    }

    @Override
    @Transactional
    public void freezeFundAccount(String accountId, String reason) {
        log.info("冻结资金账户: {}, 原因: {}", accountId, reason);
        
        // 参数校验
        Assert.hasText(accountId, "账户ID不能为空");
        Assert.hasText(reason, "冻结原因不能为空");
        
        // 检查资金账户是否存在
        Optional<FundAccountBasicInfoEntity> accountOptional = fundAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("资金账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 发送冻结资金账户命令
    }

    @Override
    @Transactional
    public void unfreezeFundAccount(String accountId, String reason) {
        log.info("解冻资金账户: {}, 原因: {}", accountId, reason);
        
        // 参数校验
        Assert.hasText(accountId, "账户ID不能为空");
        Assert.hasText(reason, "解冻原因不能为空");
        
        // 检查资金账户是否存在
        Optional<FundAccountBasicInfoEntity> accountOptional = fundAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("资金账户不存在: " + accountId);
        }

        accountOptional.get();
        
        // TODO: 发送解冻资金账户命令
    }

    @Override
    @Transactional
    public void updateBankCard(String accountId, String bankCardNumber, String bankCode,
                              String bankName, String holderName) {
        log.info("更新银行卡信息: 账户ID={}, 银行卡号={}", accountId, bankCardNumber);
        
        // 参数校验
        Assert.hasText(accountId, "资金账户ID不能为空");
        Assert.hasText(bankCardNumber, "银行卡号不能为空");
        Assert.hasText(bankCode, "银行代码不能为空");
        Assert.hasText(bankName, "银行名称不能为空");
        Assert.hasText(holderName, "持卡人姓名不能为空");
        
        // 检查资金账户是否存在
        Optional<FundAccountBasicInfoEntity> accountOptional = fundAccountBasicInfoRepository.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            throw new IllegalArgumentException("资金账户不存在: " + accountId);
        }

        accountOptional.get();

        // TODO: 发送更新银行卡命令
    }

    // Removed legacy full FundAccountDTO methods. Use basic-info / balance methods above.

    // 资金账户基本信息和余额信息拆分查询方法实现
    @Override
    @Transactional(readOnly = true)
    public Optional<FundAccountBasicInfoDTO> getFundAccountBasicInfoById(String accountId) {
        log.debug("根据资金账户ID查询资金账户基本信息: {}", accountId);
        
        return fundAccountBasicInfoRepository.findByAccountId(accountId)
                .map(entity -> {
                    FundAccountBasicInfoDTO dto = new FundAccountBasicInfoDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getAccountStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FundAccountBasicInfoDTO> getFundAccountBasicInfoByCustomerId(String customerId) {
        log.debug("根据客户ID查询资金账户基本信息列表: {}", customerId);
        
        return fundAccountBasicInfoRepository.findByCustomerId(customerId)
                .stream()
                .map(entity -> {
                    FundAccountBasicInfoDTO dto = new FundAccountBasicInfoDTO();
                    BeanUtils.copyProperties(entity, dto);
                    dto.setStatus(entity.getAccountStatus());
                    // 手动映射时间字段
                    dto.setOpenDate(entity.getOpenDate());
                    dto.setCloseDate(entity.getCloseDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FundAccountBalanceDTO> getFundAccountBalanceById(String accountId) {
        log.debug("根据资金账户ID查询资金账户余额信息: {}", accountId);
        
        return fundAccountBalanceRepository.findByAccountId(accountId)
                .map(entity -> {
                    FundAccountBalanceDTO dto = new FundAccountBalanceDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FundAccountBalanceDTO> getFundAccountBalanceByCustomerId(String customerId) {
        log.debug("根据客户ID查询资金账户余额信息列表: {}", customerId);
        
        return fundAccountBalanceRepository.findByCustomerId(customerId)
                .stream()
                .map(entity -> {
                    FundAccountBalanceDTO dto = new FundAccountBalanceDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}