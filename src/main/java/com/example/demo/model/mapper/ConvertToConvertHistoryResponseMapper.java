package com.example.demo.model.mapper;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.response.ConvertHistoryResponse;
import com.example.demo.model.entity.ConvertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link Convert} and {@link ConvertHistoryResponse}.
 */
@Mapper
public interface ConvertToConvertHistoryResponseMapper extends BaseMapper<Convert, ConvertHistoryResponse> {

    /**
     * Maps a {@link Convert} object to a {@link ConvertHistoryResponse} object.
     *
     * @param source The {@link Convert} object to map.
     * @return A {@link ConvertHistoryResponse} object mapped from the {@link Convert}.
     */
    @Override
    ConvertHistoryResponse map(Convert source);

    /**
     * Initializes and returns an instance of {@code ConvertEntityToConvertMapper}.
     *
     * @return An initialized {@code ConvertEntityToConvertMapper} instance.
     */
    static ConvertToConvertHistoryResponseMapper initialize() {
        return Mappers.getMapper(ConvertToConvertHistoryResponseMapper.class);
    }

}
