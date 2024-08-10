package com.example.demo.service;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConversionHistoryFilterRequest;
import org.springframework.data.domain.Page;

public interface ConversionHistoryService {

    Page<Convert> getConversionHistory(final ConversionHistoryFilterRequest conversionHistoryFilterRequest);

}
