package com.example.demo.service.impl;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.request.ConversionHistoryFilterRequest;
import com.example.demo.model.entity.ConvertEntity;
import com.example.demo.model.mapper.ConvertEntityToConvertMapper;
import com.example.demo.repository.ConvertRepository;
import com.example.demo.service.ConversionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversionHistoryServiceImpl implements ConversionHistoryService {

    private final ConvertRepository convertRepository;

    private final ConvertEntityToConvertMapper convertEntityToConvertMapper = ConvertEntityToConvertMapper.initialize();

    @Override
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
