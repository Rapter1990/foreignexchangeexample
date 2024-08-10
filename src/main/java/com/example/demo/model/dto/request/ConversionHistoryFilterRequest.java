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

    public Pageable toPageable() {
        return PageRequest.of(
                pagination.getPageNumber(),
                pagination.getPageSize()
        );
    }

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
