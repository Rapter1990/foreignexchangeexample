package com.example.demo.model.mapper;

import com.example.demo.model.Convert;
import com.example.demo.model.entity.ConvertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


/**
 * Mapper interface for converting between {@link ConvertEntity} and {@link Convert}.
 */
@Mapper
public interface ConvertEntityToConvertMapper extends BaseMapper<ConvertEntity, Convert> {

    /**
     * Maps a {@link ConvertEntity} object to a {@link Convert} object.
     *
     * @param source The {@link ConvertEntity} object to map.
     * @return A {@link Convert} object mapped from the {@link ConvertEntity}.
     */
    @Override
    @Mapping(source = "toCurrency", target = "to")
    @Mapping(source = "fromCurrency", target = "from")
    Convert map(ConvertEntity source);

    /**
     * Initializes and returns an instance of {@code ConvertEntityToConvertMapper}.
     *
     * @return An initialized {@code ConvertEntityToConvertMapper} instance.
     */
    static ConvertEntityToConvertMapper initialize() {
        return Mappers.getMapper(ConvertEntityToConvertMapper.class);
    }

}
