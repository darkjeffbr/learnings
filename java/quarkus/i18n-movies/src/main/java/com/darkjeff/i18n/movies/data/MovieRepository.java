package com.darkjeff.i18n.movies.data;

import static java.util.Objects.isNull;

import com.darkjeff.i18n.movies.model.Movie;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * TODO: Add tests, add JPA meta model (hibernate-jpamodelgen)
 */
@ApplicationScoped
public class MovieRepository {

    @ConfigProperty(name = "i18n.movies.defaultLanguage", defaultValue = "en")
    private String defaultLanguage;

    private EntityManager em;
    private MovieMapper movieMapper;

    public MovieRepository(EntityManager em, MovieMapper movieMapper) {
        this.em = em;
        this.movieMapper = movieMapper;
    }

    public List<Movie> getAll(String language) {

        String searchLanguage = getSearchLanguage(language);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MovieTranslationEntity> cq = cb.createQuery(MovieTranslationEntity.class);
        Root<MovieTranslationEntity> fromTranslations = cq.from(MovieTranslationEntity.class);
        Join<MovieTranslationEntity, LanguageEntity> fromLanguage = fromTranslations.join("language");

        ParameterExpression<String> languageCodeParam = cb.parameter(String.class);
        cq.where(cb.equal(fromLanguage.get("code"), languageCodeParam));

        TypedQuery<MovieTranslationEntity> query = em.createQuery(cq);
        query.setParameter(languageCodeParam, searchLanguage);

        List<MovieTranslationEntity> movieTranslations = query.getResultList();

        return movieTranslations.stream().map(movieMapper::toMovie).collect(Collectors.toList());
    }

    private String getSearchLanguage(String language) {
        if(isNull(language)) {
            return defaultLanguage;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LanguageEntity> cq = cb.createQuery(LanguageEntity.class);
        Root<LanguageEntity> root = cq.from(LanguageEntity.class);

        cq.select(root).where(cb.equal(root.get("code"), language));

        TypedQuery<LanguageEntity> query = em.createQuery(cq);

        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            return defaultLanguage;
        }
        return language;
    }

}
