package com.example.demo.service.impl;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConversionHistoryFilterRequest;
import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.mapper.ConvertEntityToConvertMapper;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.service.ConversionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link ConversionHistoryService} interface.
 * Provides functionality for retrieving and paginating conversion history records.
 * Caches the results to improve performance and reduce redundant database queries.
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "exchanges")
public class ConversionHistoryServiceImpl implements ConversionHistoryService {

    private final ConvertRepository convertRepository;

    private final ConvertEntityToConvertMapper convertEntityToConvertMapper = ConvertEntityToConvertMapper.initialize();

    /**
     * Retrieves a paginated list of currency conversion records based on the specified filter criteria.
     * The method applies the filter criteria to query the repository and fetch the relevant conversion records.
     * It then maps the retrieved entities to {@link Convert} objects and returns them in a paginated format.
     *
     * @param conversionHistoryFilterRequest A {@link ConversionHistoryFilterRequest} object containing the filter criteria and pagination details.
     * @return A {@link Page} of {@link Convert} objects representing the paginated conversion history.
     */
    @Override
    @Cacheable(key = "'ConversionHistoryCache::' + #conversionHistoryFilterRequest.transactionId + '-' + #conversionHistoryFilterRequest.date")
    public Page<Convert> getConversionHistory(ConversionHistoryFilterRequest conversionHistoryFilterRequest) {
        Specification<ConvertEntity> spec = conversionHistoryFilterRequest.toSpecification();
        Pageable pageable = conversionHistoryFilterRequest.toPageable();
        Page<ConvertEntity> entityPage = convertRepository.findAll(spec, pageable);
        List<Convert> convertList = entityPage.getContent().stream()
                .map(convertEntityToConvertMapper::map)
                .toList();

        return new PageImpl<>(convertList, pageable, entityPage.getTotalElements());
    }

}
