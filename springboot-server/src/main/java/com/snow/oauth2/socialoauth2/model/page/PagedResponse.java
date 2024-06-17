package com.snow.oauth2.socialoauth2.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;


    public static class PageConverter {
        public static <T> PagedResponse<T> toCustomPage(Page<T> page) {
            return new PagedResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
        }
    }

}
