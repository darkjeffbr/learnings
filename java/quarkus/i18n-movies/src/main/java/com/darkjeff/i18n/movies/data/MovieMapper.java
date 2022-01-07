package com.darkjeff.i18n.movies.data;

import com.darkjeff.i18n.movies.model.Movie;
import org.javamoney.moneta.Money;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface MovieMapper {
    @Mapping(target = "release", source = "movie.release")
    @Mapping(target = "views", source = "movie.views")
    Movie toMovie(MovieTranslationEntity source);

    @AfterMapping
    default void setMoney(MovieTranslationEntity source, @MappingTarget Movie movie){
        movie.setProductionCost(Money.of(source.getCost(), source.getCurrency()));
    }

}
