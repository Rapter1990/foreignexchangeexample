package com.example.demo.model.mapper;

import com.example.demo.model.Convert;
import com.example.demo.model.dto.response.ConvertResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link Convert} and {@link ConvertResponse}.
 */
@Mapper
public interface ConvertToConvertResponseMapper extends BaseMapper<Convert, ConvertResponse> {

    /**
     * Maps a {@link Convert} object to a {@link ConvertResponse} object.
     *
     * @param source The {@link Convert} object to map.
     * @return A {@link ConvertResponse} object mapped from the {@link Convert}.
     */
    @Override
    ConvertResponse map(Convert source);

    /**
     * Initializes and returns an instance of {@code ConvertToConvertResponseMapper}.
     *
     * @return An initialized {@code ConvertToConvertResponseMapper} instance.
     */
    static ConvertToConvertResponseMapper initialize() {
        return Mappers.getMapper(ConvertToConvertResponseMapper.class);
    }
}

