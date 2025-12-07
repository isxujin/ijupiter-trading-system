package net.ijupiter.trading.api.query.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询分页结果
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPageResult<T> {
    
    /**
     * 数据列表
     */
    private List<T> data;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 当前页码
     */
    private Integer page;
    
    /**
     * 每页大小
     */
    private Integer size;
    
    /**
     * 总页数
     */
    private Integer totalPages;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNext;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;
    
    /**
     * 是否为第一页
     */
    private Boolean isFirst;
    
    /**
     * 是否为最后一页
     */
    private Boolean isLast;
    
    /**
     * 创建空结果
     */
    public static <T> QueryPageResult<T> empty() {
        return QueryPageResult.<T>builder()
                .data(List.of())
                .total(0L)
                .page(1)
                .size(10)
                .totalPages(0)
                .hasNext(false)
                .hasPrevious(false)
                .isFirst(true)
                .isLast(true)
                .build();
    }
    
    /**
     * 创建成功结果
     */
    public static <T> QueryPageResult<T> of(List<T> data, Long total, Integer page, Integer size) {
        if (data == null) {
            data = List.of();
        }
        
        Long totalPages = (total + size - 1) / size;
        
        return QueryPageResult.<T>builder()
                .data(data)
                .total(total)
                .page(page)
                .size(size)
                .totalPages(totalPages.intValue())
                .hasNext(page < totalPages)
                .hasPrevious(page > 1)
                .isFirst(page.equals(1))
                .isLast(page.equals(totalPages))
                .build();
    }
}