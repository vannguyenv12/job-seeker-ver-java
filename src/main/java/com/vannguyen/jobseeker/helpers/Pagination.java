package com.vannguyen.jobseeker.helpers;

import com.vannguyen.jobseeker.payload.Paginate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Pagination<T> {
     public Paginate<T> build(List<T> dtoList, Page<?> page) {
        Paginate<T> paginate = new Paginate<>();
        paginate.setData(dtoList);
        paginate.setPageNo(page.getNumber());
        paginate.setPageSize(page.getSize());
        paginate.setTotalElements(paginate.getTotalElements());
        paginate.setTotalPages(page.getTotalPages());
        paginate.setLast(page.isLast());
        return paginate;
    }
}
