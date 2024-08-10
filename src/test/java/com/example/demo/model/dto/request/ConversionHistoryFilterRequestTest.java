package com.example.demo.model.dto.request;

import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.pagination.CustomPaging;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link ConversionHistoryFilterRequest}.
 * This class contains test cases to validate the behavior of the
 * {@link ConversionHistoryFilterRequest} class, specifically its methods
 * for converting to {@link Pageable} and {@link Specification} instances.
 */
class ConversionHistoryFilterRequestTest {

    private static final String TRANSACTION_ID = "12345";
    private static final LocalDate DATE = LocalDate.of(2024, 8, 10);
    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 10;

    private ConversionHistoryFilterRequest filterRequest;

    @BeforeEach
    void setUp() {
        CustomPaging paging = new CustomPaging(PAGE_NUMBER, PAGE_SIZE);
        filterRequest = new ConversionHistoryFilterRequest(
                TRANSACTION_ID,
                DATE,
                paging
        );
    }

    @Test
    @DisplayName("Given Filter Request - When Convert to Pageable - Then Correct Page Number and Size")
    void givenFilterRequest_whenConvertToPageable_thenCorrectPageNumberAndSize() {

        // Given, When and Then
        Pageable pageable = filterRequest.toPageable();

        assertEquals(PAGE_NUMBER-1, pageable.getPageNumber());
        assertEquals(PAGE_SIZE, pageable.getPageSize());

    }

    @Test
    @DisplayName("Given Filter Request with Transaction ID - When Convert to Specification - Then Use Transaction ID in Predicate")
    void givenFilterRequestWithTransactionId_whenConvertToSpecification_thenUseTransactionIdInPredicate() {

        // Given
        ConversionHistoryFilterRequest filterRequestWithTransactionId = new ConversionHistoryFilterRequest(
                TRANSACTION_ID,
                null,
                null
        );

        // Mock CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ConvertEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ConvertEntity> root = mock(Root.class);

        // When
        Specification<ConvertEntity> spec = filterRequestWithTransactionId.toSpecification();

        // Then
        spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Verify
        verify(criteriaBuilder).equal(any(), eq(TRANSACTION_ID));

    }

    @Test
    @DisplayName("Given Filter Request with Date - When Convert to Specification - Then Use Date Range in Predicate")
    void givenFilterRequestWithDate_whenConvertToSpecification_thenUseDateRangeInPredicate() {

        // Given
        ConversionHistoryFilterRequest filterRequestWithDate = new ConversionHistoryFilterRequest(
                null,
                DATE,
                null
        );

        // Mock CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ConvertEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ConvertEntity> root = mock(Root.class);

        LocalDateTime startOfDay = DATE.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // When
        Specification<ConvertEntity> spec = filterRequestWithDate.toSpecification();

        // Then
        spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Verify
        verify(criteriaBuilder).between(any(), eq(startOfDay), eq(endOfDay));

    }

    @Test
    @DisplayName("Given Filter Request with Transaction ID and Date - When Convert to Specification - Then Use Both in Predicate")
    void givenFilterRequestWithTransactionIdAndDate_whenConvertToSpecification_thenUseBothInPredicate() {

        // Given
        ConversionHistoryFilterRequest filterRequestWithTransactionIdAndDate = new ConversionHistoryFilterRequest(
                TRANSACTION_ID,
                DATE,
                null
        );

        // Mock CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ConvertEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ConvertEntity> root = mock(Root.class);

        LocalDateTime startOfDay = DATE.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // When
        Specification<ConvertEntity> spec = filterRequestWithTransactionIdAndDate.toSpecification();

        // Then
        spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Verify
        verify(criteriaBuilder).equal(any(), eq(TRANSACTION_ID));
        verify(criteriaBuilder).between(any(), eq(startOfDay), eq(endOfDay));

    }

}