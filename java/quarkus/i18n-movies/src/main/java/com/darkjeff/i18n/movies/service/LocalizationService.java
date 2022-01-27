package com.darkjeff.i18n.movies.service;

import com.darkjeff.i18n.movies.model.LocalizedMovie;
import com.darkjeff.i18n.movies.model.Movie;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalizationService {

    public List<LocalizedMovie> localize(List<Movie> toLocalize) {
        return List.of();
    }

}
