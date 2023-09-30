package com.vannguyen.jobseeker.payload;

import lombok.Data;

import java.util.List;

@Data
public class Paginate<T> {
    private List<T> data;
    private int pageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean last;

}
