package net.ijupiter.trading.core.query.handlers;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.query.dtos.CustomerDailySecuritiesProfitDTO;
import net.ijupiter.trading.api.query.queries.CustomerDailySecuritiesProfitQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import net.ijupiter.trading.core.query.services.QueryDomainService;

/**
 * 客户每日证券收益信息查询处理器
 */
@Slf4j
@Component
public class CustomerDailySecuritiesProfitQueryHandler {
    
    @Autowired
    private QueryDomainService queryDomainService;
    
    @QueryHandler
    public Page<CustomerDailySecuritiesProfitDTO> handle(CustomerDailySecuritiesProfitQuery query) {
        log.info("处理客户每日证券收益信息查询: customerId={}, startDate={}, endDate={}", 
            query.getCustomerId(), query.getStartDate(), query.getEndDate());
        return queryDomainService.queryCustomerDailySecuritiesProfit(query);
    }
}