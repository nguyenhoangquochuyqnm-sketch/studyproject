package com.doan.cv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultPagination<T> {
    T result;
    Meta meta;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta{
        private int page;
        private int pageSize;
        private int totalPages;
        private long totalElements;
    }
}
