package net.ijupiter.trading.core.query.handlers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.CustomerFinancialSummaryDTO;
import net.ijupiter.trading.api.query.queries.CustomerFinancialSummaryQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.ijupiter.trading.core.query.services.QueryDomainService;

/**
 * 客户综合信息查询处理器
 */
@Slf4j
@Component
public class CustomerFinancialSummaryQueryHandler {
    
    @Autowired
    private QueryDomainService queryDomainService;
    
    @QueryHandler
    public CustomerFinancialSummaryDTO handle(CustomerFinancialSummaryQuery query) {
        log.info("处理客户综合信息查询: customerId={}", query.getCustomerId());
        return queryDomainService.queryCustomerFinancialSummary(query);
    }
}