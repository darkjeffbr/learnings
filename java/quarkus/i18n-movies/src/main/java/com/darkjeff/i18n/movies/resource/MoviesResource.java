package com.darkjeff.i18n.movies.resource;

import com.darkjeff.i18n.movies.data.MovieRepository;
import com.darkjeff.i18n.movies.resource.mapper.MovieDTOMapper;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/movies")
public class MoviesResource {

    @Inject
    MovieRepository repository;

    @Inject
    MovieDTOMapper mapper;

    @GET
    public Response getAll(@HeaderParam(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return Response.ok(
            mapper.toDTO(
                repository.getAll(language)
            )
        ).build();
    }

}
