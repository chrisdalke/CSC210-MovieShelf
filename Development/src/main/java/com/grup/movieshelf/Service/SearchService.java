///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.User;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/////////////////////////////////////////////////////////////
// Search Service
// Performs searches.
/////////////////////////////////////////////////////////////

@Repository
@Transactional
@Service
public class SearchService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Title> searchTitles(String text) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
 
        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Title.class).get();

        Query query = queryBuilder.keyword()
                .fuzzy()
                .onFields("titleName", "associatedPeople.name")
                .matching(text)
                .createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Title.class);
        jpaQuery.setMaxResults(30);

        @SuppressWarnings("unchecked")
        List<Title> titles = jpaQuery.getResultList();

        return titles;
    }

    public List<User> searchUsers(String text) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(User.class).get();

        Query query = queryBuilder.keyword()
                .fuzzy()
                .onFields("username")
                .matching(text)
                .createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, User.class);
        jpaQuery.setMaxResults(30);

        @SuppressWarnings("unchecked")
        List<User> users = jpaQuery.getResultList();

        return users;
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////