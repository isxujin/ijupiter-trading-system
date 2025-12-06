package net.ijupiter.trading.web.common.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页返回结果
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends Result<List<T>> {
    
    private static final int SUCCESS_CODE = 0;
    private static final int FAIL_CODE = -1;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    public PageResult() {
        super();
    }

    public PageResult(int pageNum, int pageSize, long total, List<T> data) {
        super(0, "操作成功", data);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = (int) Math.ceil((double) total / pageSize);
        this.hasNext = pageNum < pages;
        this.hasPrevious = pageNum > 1;
    }

    /**
     * 成功返回分页数据
     */
    public static <T> PageResult<T> success(int pageNum, int pageSize, long total, List<T> data) {
        return new PageResult<>(pageNum, pageSize, total, data);
    }

    /**
     * 失败返回
     */
    public static <T> PageResult<T> failPage(String message) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(FAIL_CODE);
        pageResult.setMessage(message);
        pageResult.setData(null);
        return pageResult;
    }

    /**
     * 失败返回码和消息
     */
    public static <T> PageResult<T> failPage(int code, String message) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(code);
        pageResult.setMessage(message);
        pageResult.setData(null);
        return pageResult;
    }
}