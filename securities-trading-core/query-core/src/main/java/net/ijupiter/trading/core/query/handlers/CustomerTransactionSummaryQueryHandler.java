package net.ijupiter.trading.core.query.handlers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.CustomerTransactionSummaryDTO;
import net.ijupiter.trading.api.query.queries.CustomerTransactionSummaryQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import net.ijupiter.trading.core.query.services.QueryDomainService;

/**
 * 客户交易流水查询处理器
 */
@Slf4j
@Component
public class CustomerTransactionSummaryQueryHandler {
    
    @Autowired
    private QueryDomainService queryDomainService;
    
    @QueryHandler
    public Page<CustomerTransactionSummaryDTO> handle(CustomerTransactionSummaryQuery query) {
        log.info("处理客户交易流水查询: customerId={}, page={}, size={}", 
            query.getCustomerId(), query.getPage(), query.getSize());
        return queryDomainService.queryCustomerTransactionSummary(query);
    }
}