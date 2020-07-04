package cn.lefer.tomu.view;

import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/4
 * @Description : 分页封装
 */
public class Page<T> {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private List<T> data;

    private Page(Builder<T> builder) {
        this.pageNum = builder.pageNum;
        this.pageSize = builder.pageSize;
        this.startRow = (pageNum - 1) * pageSize + 1;
        this.endRow = pageNum * pageSize;
        this.total = builder.total;
        this.pages = (int) Math.ceil(builder.total * 1.0 / builder.pageSize);
        this.data = builder.data;
    }

    public static class Builder<T> {
        private int pageNum;
        private int pageSize;
        private long total;
        private List<T> data;

        public Builder<T> pageNum(int pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder<T> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> total(long total) {
            this.total = total;
            return this;
        }

        public Builder<T> data(List<T> data) {
            this.data = data;
            return this;
        }

        public Page<T> build() {
            return new Page<T>(this);
        }
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
