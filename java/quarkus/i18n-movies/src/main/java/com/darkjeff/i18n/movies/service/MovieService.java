package com.darkjeff.i18n.movies.service;

import com.darkjeff.i18n.movies.data.MovieRepository;
import com.darkjeff.i18n.movies.model.LocalizedMovie;
import com.darkjeff.i18n.movies.model.Movie;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MovieService {

    @Inject
    MovieRepository repository;

    @Inject
    LocalizationService localizationService;

    public List<LocalizedMovie> getAll(String language) {
        List<Movie> toLocalize = repository.getAll(language);
        return localizationService.localize(toLocalize);
    }

}
