package com.example.demo.model.pagination;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a custom pagination configuration used for specifying the page number and page size.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CustomPaging {

    @Min(value = 1, message = "Page number must be bigger than 0")
    private Integer pageNumber;

    @Min(value = 1, message = "Page size must be bigger than 0")
    private Integer pageSize;

    /**
     * Gets the page number adjusted to a 0-based index used by many pagination implementations.
     *
     * @return The current page number adjusted to a 0-based index.
     */
    public Integer getPageNumber() {
        return pageNumber - 1;
    }

}

