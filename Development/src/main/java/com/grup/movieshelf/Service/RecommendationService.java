package com.grup.movieshelf.Service;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Movies.TitleGenre;
import com.grup.movieshelf.JPA.Entity.Sessions.Recommendation;
import com.grup.movieshelf.JPA.Entity.Sessions.RecommendationInput;
import com.grup.movieshelf.JPA.Entity.Sessions.RecommendationResult;
import com.grup.movieshelf.JPA.Repository.TitleGenreRepository;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@Service
public class RecommendationService {

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    TitleGenreRepository titleGenreRepository;

    public RecommendationResult getRecommendations(RecommendationInput input) {

        RecommendationResult result = new RecommendationResult();

        /***********************************
         * FILTER BY RELEASE YEAR
         ***********************************/

        ArrayList<Integer> releaseYears = new ArrayList<>();
        for(Title title : input.getTitles()) {
            releaseYears.add(title.getYear());
        }
        Collections.sort(releaseYears);
        // technically this isn't the median when the array is even in size, but it doesn't matter
        int medianYear = releaseYears.get(releaseYears.size() / 2);

        int minYear = medianYear - 7;
        int maxYear = medianYear + 7;

        /**
         ASSIGN POINTS:

         +3 if title's release year is < 3 years from the median year
         +2 if title's release year is < 5 years from the median year
         +1 if title's release year is < 7 years from the median year

         NOTE: all titles with release years >= 7 years are discarded
         **/

        for(Title title : titleRepository.getAllByYearIsBetween(minYear, maxYear)) {
            int score;
            if(Math.abs(medianYear - title.getYear()) < 3) {
                score = 3;
            } else if(Math.abs(medianYear - title.getYear()) < 5) {
                score = 2;
            } else {
                score = 1;
            }
            result.getRecommendations().add(new Recommendation(title, score));
        }

        /***********************************
         * FILTER BY GENRE
         ***********************************/

        // top 3 genres
        String genre1 = null;
        String genre2 = null;
        String genre3 = null;

        // GET MOST POPULAR GENRES
        HashMap<String, Integer> genres = new HashMap<>();
        for(Title title : input.getTitles()) {
            for(TitleGenre titleGenre : titleGenreRepository.getAllByTitleId(title.getTitleId())) {
                String genre = titleGenre.getGenre();
                if(!genres.containsKey(genre)) {
                    genres.put(genre, 1);
                } else {
                    genres.put(genre, genres.get(genre) + 1);
                }
            }
        }

        int genreCount1 = 0;
        int genreCount2 = 0;
        int genreCount3 = 0;

        for(String genre : genres.keySet()) {
            int count = genres.get(genre);
            if(count > genreCount1) {
                // reorder
                genreCount3 = genreCount2;
                genre3 = genre2;
                genreCount2 = genreCount1;
                genre2 = genre1;
                genreCount1 = count;
                genre1 = genre;
            } else if(count > genreCount2) {
                // reorder
                genreCount3 = genreCount2;
                genre3 = genre2;
                genreCount2 = count;
                genre2 = genre;
            } else if(count > genreCount3) {
                // reorder
                genreCount3 = count;
                genre3 = genre;
            }
        }

        /**
            ASSIGN POINTS:

            +3 if one of title's genres matches #1 most popular
            +2 if one of title's genres matches #2 most popular
            +1 if one of title's genres matches #3 most popular

            NOTE: a title can receive points from multiple matchings
                ex. if #1 genre is "Comedy" and #2 genre is "Family",
                    a title matching both genres would receive +5 points
         **/
        for(Recommendation recommendation : result.getRecommendations()) {
            for(TitleGenre titleGenre : titleGenreRepository.getAllByTitleId(recommendation.getTitle().getTitleId())) {
                if(titleGenre.getGenre().equals(genre1)) {
                    recommendation.setScore(recommendation.getScore() + 3);
                } else if(titleGenre.getGenre().equals(genre2)) {
                    recommendation.setScore(recommendation.getScore() + 2);
                } else if(titleGenre.getGenre().equals(genre3)) {
                    recommendation.setScore(recommendation.getScore() + 1);
                }
            }

        }

        return result;
    }
}
