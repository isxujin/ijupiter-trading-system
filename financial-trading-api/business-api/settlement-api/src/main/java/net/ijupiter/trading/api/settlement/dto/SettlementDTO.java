package net.ijupiter.trading.api.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.settlement.enums.SettlementStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 结算数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementDTO {

    private String settlementId;
    private LocalDate settlementDate;
    private SettlementStatus status;
    private LocalDateTime startTime;
    private LocalDateTime completedTime;
    private String errorMessage;
    
    /**
     * 是否处于待处理状态
     * 
     * @return 是否待处理
     */
    public boolean isPending() {
        return SettlementStatus.PENDING.equals(status);
    }

    /**
     * 是否处于处理中状态
     * 
     * @return 是否处理中
     */
    public boolean isInProgress() {
        return SettlementStatus.IN_PROGRESS.equals(status);
    }

    /**
     * 是否已完成
     * 
     * @return 是否已完成
     */
    public boolean isCompleted() {
        return SettlementStatus.COMPLETED.equals(status);
    }

    /**
     * 是否已失败
     * 
     * @return 是否已失败
     */
    public boolean isFailed() {
        return SettlementStatus.FAILED.equals(status);
    }

    /**
     * 是否已取消
     * 
     * @return 是否已取消
     */
    public boolean isCancelled() {
        return SettlementStatus.CANCELLED.equals(status);
    }

    /**
     * 计算结算耗时（分钟）
     * 
     * @return 结算耗时（分钟）
     */
    public Long getDurationMinutes() {
        if (startTime == null || completedTime == null) {
            return null;
        }
        
        return java.time.Duration.between(startTime, completedTime).toMinutes();
    }
}