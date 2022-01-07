package com.darkjeff.i18n.movies.resource.mapper;

import com.darkjeff.i18n.movies.model.Movie;
import com.darkjeff.i18n.movies.resource.dto.MovieDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface MovieDTOMapper {
    MovieDTO toDTO(final Movie source);
    List<MovieDTO> toDTO(final List<Movie> source);
}
