package com.devtiro.database.mappers.impl;

import com.devtiro.database.domain.FilmAdaptationEntity;
import com.devtiro.database.domain.dto.FilmAdaptationDto;
import com.devtiro.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FilmAdaptationMapper implements Mapper<FilmAdaptationEntity, FilmAdaptationDto> {

    private ModelMapper modelMapper;

    public FilmAdaptationMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public FilmAdaptationDto mapTo(FilmAdaptationEntity filmAdaptationEntity) {
        return modelMapper.map(filmAdaptationEntity, FilmAdaptationDto.class);
    }

    @Override
    public FilmAdaptationEntity mapFrom(FilmAdaptationDto filmAdaptationDto) {
        return modelMapper.map(filmAdaptationDto, FilmAdaptationEntity.class);
    }
}
