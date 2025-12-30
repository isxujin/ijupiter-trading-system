package net.ijupiter.trading.core.query.handlers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.CustomerFundingBalanceDTO;
import net.ijupiter.trading.api.query.queries.CustomerFundingBalanceQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.ijupiter.trading.core.query.services.QueryDomainService;

import java.util.List;

/**
 * 客户资金账户余额查询处理器
 */
@Slf4j
@Component
public class CustomerFundingBalanceQueryHandler {
    
    @Autowired
    private QueryDomainService queryDomainService;
    
    @QueryHandler
    public List<CustomerFundingBalanceDTO> handle(CustomerFundingBalanceQuery query) {
        log.info("处理客户资金账户余额查询: customerId={}", query.getCustomerId());
        return queryDomainService.queryCustomerFundingBalance(query);
    }
}