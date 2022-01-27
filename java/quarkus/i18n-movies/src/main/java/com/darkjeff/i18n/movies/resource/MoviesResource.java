package com.darkjeff.i18n.movies.resource;

import com.darkjeff.i18n.movies.service.MovieService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/movies")
public class MoviesResource {

    @Inject
    MovieService movieService;

    @GET
    public Response getAll(@HeaderParam(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        return Response.ok(
            movieService.getAll(language)
        ).build();
    }

}
