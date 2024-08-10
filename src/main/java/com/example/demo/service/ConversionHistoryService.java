package com.example.demo.service;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConversionHistoryFilterRequest;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing conversion history.
 */
public interface ConversionHistoryService {

    /**
     * Retrieves a paginated list of conversion history records based on the provided filter criteria.
     *
     * @param conversionHistoryFilterRequest An {@link ConversionHistoryFilterRequest} containing filter criteria.
     * @return A {@link Page} of {@link Convert} objects representing the conversion history.
     */
    Page<Convert> getConversionHistory(final ConversionHistoryFilterRequest conversionHistoryFilterRequest);

}
