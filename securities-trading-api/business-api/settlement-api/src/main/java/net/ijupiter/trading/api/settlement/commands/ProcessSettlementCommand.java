package net.ijupiter.trading.api.settlement.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

/**
 * 处理清算命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ProcessSettlementCommand {
    /**
     * 清算编号
     */
    private String settlementCode;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
}