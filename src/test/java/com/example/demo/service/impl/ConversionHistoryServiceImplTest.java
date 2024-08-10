package com.example.demo.service.impl;

import com.example.demo.base.AbstractBaseServiceTest;
import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConversionHistoryFilterRequest;
import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.mapper.ConvertEntityToConvertMapper;
import com.example.demo.model.pagination.CustomPaging;
import com.example.demo.repository.ConvertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test class for {@link ConversionHistoryServiceImpl}.
 *
 * This class contains test cases to verify the behavior of the
 * {@link ConversionHistoryServiceImpl} class, specifically focusing on
 * the {@link ConversionHistoryServiceImpl#getConversionHistory(ConversionHistoryFilterRequest)}
 * method.
 */
class ConversionHistoryServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private ConversionHistoryServiceImpl conversionHistoryService;

    @Mock
    private ConvertRepository convertRepository;

    private final ConvertEntityToConvertMapper convertEntityToConvertMapper = ConvertEntityToConvertMapper.initialize();

    @Test
    @DisplayName("Given ConversionHistoryFilterRequest - When Get Conversion History - Then Return Correct Page of Conversion Data")
    void givenConversionHistoryFilterRequest_whenGetConversionHistory_thenReturnCorrectPageOfConversionData() {

        // Given
        LocalDateTime now = LocalDateTime.now();

        ConversionHistoryFilterRequest conversionHistoryFilterRequest = ConversionHistoryFilterRequest.builder()
                .transactionId("tx123")
                .date(LocalDate.now())
                .pagination(new CustomPaging(1, 10))
                .build();

        Pageable pageable = conversionHistoryFilterRequest.toPageable();

        ConvertEntity convertEntity = ConvertEntity.builder()
                .transactionId("tx123")
                .amount(BigDecimal.valueOf(100.0))
                .fromCurrency("USD")
                .toCurrency("EUR")
                .convertedAmount(BigDecimal.valueOf(85.0))
                .createdAt(now)
                .build();

        Page<ConvertEntity> entityPage = new PageImpl<>(Collections.singletonList(convertEntity),
                PageRequest.of(0, 10), 1);

        List<Convert> convertList = entityPage.getContent().stream()
                .map(convertEntityToConvertMapper::map)
                .toList();

        Page<Convert> expected = new PageImpl<>(convertList, pageable, entityPage.getTotalElements());


        // When
        when(convertRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(entityPage);


        Page<Convert> result = conversionHistoryService.getConversionHistory(conversionHistoryFilterRequest);

        // Then
        assertNotNull(result);
        assertEquals(expected.getTotalElements(), result.getTotalElements());
        assertEquals(expected.getContent().get(0).getTo(), result.getContent().get(0).getTo());
        assertEquals(expected.getContent().get(0).getFrom(), result.getContent().get(0).getFrom());
        assertEquals(expected.getContent().get(0).getConvertedAmount(), result.getContent().get(0).getConvertedAmount());
        assertEquals(expected.getContent().get(0).getTransactionId(), result.getContent().get(0).getTransactionId());
        assertEquals(expected.getContent().get(0).getAmount(), result.getContent().get(0).getAmount());

        // Verify
        verify(convertRepository).findAll(any(Specification.class), any(Pageable.class));

    }

}