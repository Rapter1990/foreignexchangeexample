package com.example.demo.model.dto.request;

import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.pagination.CustomPaging;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a request to filter conversion history records.
 * This class encapsulates the criteria for filtering conversion history records, including
 * transaction IDs, dates, and pagination details. It also provides methods to convert
 * the filter request into a {@link Pageable} object for pagination and a {@link Specification}
 * for querying the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionHistoryFilterRequest {

    private String transactionId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private CustomPaging pagination;

    /**
     * Converts the filter request into a {@link Pageable} object for pagination.
     * This method uses the pagination details from the request to create a {@link PageRequest}
     * object, which is used for retrieving a specific page of results.
     *
     * @return a {@link Pageable} object representing the pagination information
     */
    public Pageable toPageable() {
        return PageRequest.of(
                pagination.getPageNumber(),
                pagination.getPageSize()
        );
    }

    /**
     * Converts the filter request into a {@link Specification} for querying the database.
     * This method creates a {@link Specification} that filters {@link ConvertEntity} records
     * based on the specified transaction ID and date. The resulting specification is used
     * to build queries with criteria for filtering records.
     *
     * @return a {@link Specification} for filtering {@link ConvertEntity} records
     */
    public Specification<ConvertEntity> toSpecification() {
        return (root, query, criteriaBuilder) -> {
            Specification<ConvertEntity> spec = Specification.where(null);

            if (transactionId != null && !transactionId.isEmpty()) {
                spec = spec.and((rootTransactionId, queryTransactionId, criteriaBuilderTransactionId) ->
                        criteriaBuilderTransactionId.equal(rootTransactionId.get("transactionId"), transactionId));
            }

            if (date != null) {
                // Assuming the entity has a field named `transactionDate` that stores the date
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
                spec = spec.and((rootDate, queryDate, criteriaBuilderDate) ->
                        criteriaBuilderDate.between(rootDate.get("createdAt"), startOfDay, endOfDay));
            }

            return spec.toPredicate(root, query, criteriaBuilder);
        };
    }

}
