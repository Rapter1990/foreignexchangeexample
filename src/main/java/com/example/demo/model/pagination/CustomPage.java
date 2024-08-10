package com.example.demo.model.pagination;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Represents a custom pagination response containing details about the paginated data.
 *
 * @param <T> The type of the elements contained in the page.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomPage<T> {

    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElementCount;

    private Integer totalPageCount;

    /**
     * Creates a {@link CustomPage} instance from a {@link List} of domain models and a {@link Page} object.
     *
     * @param <C> The type of the elements in the custom page.
     * @param <X> The type of the elements in the original page.
     * @param domainModels The list of items to be included in the custom page.
     * @param page The original page object containing pagination information.
     * @return A new {@link CustomPage} instance populated with the provided data.
     */
    public static <C, X> CustomPage<C> of(final List<C> domainModels, final Page<X> page) {
        return CustomPage.<C>builder()
                .content(domainModels)
                .pageNumber(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalPageCount(page.getTotalPages())
                .totalElementCount(page.getTotalElements())
                .build();
    }

}
