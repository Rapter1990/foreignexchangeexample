package com.example.demo.model.dto.request;

import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.pagination.CustomPaging;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConversionHistoryFilterRequestTest {

    private static final String TRANSACTION_ID = "12345";
    private static final LocalDate DATE = LocalDate.of(2024, 8, 10);
    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 10;

    private ConversionHistoryFilterRequest filterRequest;

    @BeforeEach
    public void setUp() {
        CustomPaging paging = new CustomPaging(PAGE_NUMBER, PAGE_SIZE);
        filterRequest = new ConversionHistoryFilterRequest(
                TRANSACTION_ID,
                DATE,
                paging
        );
    }

    @Test
    public void testToPageable() {
        // Act
        Pageable pageable = filterRequest.toPageable();

        // Assert
        assertEquals(PAGE_NUMBER-1, pageable.getPageNumber());
        assertEquals(PAGE_SIZE, pageable.getPageSize());
    }

    @Test
    public void testToSpecification_WithTransactionId() {
        // Arrange
        ConversionHistoryFilterRequest filterRequestWithTransactionId = new ConversionHistoryFilterRequest(
                TRANSACTION_ID,
                null,
                null
        );
        Specification<ConvertEntity> spec = filterRequestWithTransactionId.toSpecification();

        // Mock CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ConvertEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ConvertEntity> root = mock(Root.class);

        // Act
        spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Assert
        // Verify that criteriaBuilder.equal was called with appropriate parameters
        verify(criteriaBuilder).equal(any(), eq(TRANSACTION_ID));
    }

    @Test
    public void testToSpecification_WithDate() {
        // Arrange
        ConversionHistoryFilterRequest filterRequestWithDate = new ConversionHistoryFilterRequest(
                null,
                DATE,
                null
        );
        Specification<ConvertEntity> spec = filterRequestWithDate.toSpecification();

        // Mock CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ConvertEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ConvertEntity> root = mock(Root.class);

        LocalDateTime startOfDay = DATE.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // Act
        spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Assert
        // Verify that criteriaBuilder.between was called with appropriate parameters
        verify(criteriaBuilder).between(any(), eq(startOfDay), eq(endOfDay));
    }

    @Test
    public void testToSpecification_WithTransactionIdAndDate() {
        // Arrange
        ConversionHistoryFilterRequest filterRequestWithTransactionIdAndDate = new ConversionHistoryFilterRequest(
                TRANSACTION_ID,
                DATE,
                null
        );
        Specification<ConvertEntity> spec = filterRequestWithTransactionIdAndDate.toSpecification();

        // Mock CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<ConvertEntity> criteriaQuery = mock(CriteriaQuery.class);
        Root<ConvertEntity> root = mock(Root.class);

        LocalDateTime startOfDay = DATE.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // Act
        spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Assert
        // Verify that criteriaBuilder.equal and criteriaBuilder.between were called with appropriate parameters
        verify(criteriaBuilder).equal(any(), eq(TRANSACTION_ID));
        verify(criteriaBuilder).between(any(), eq(startOfDay), eq(endOfDay));
    }

}