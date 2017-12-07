package com.grup.movieshelf.Service;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Movies.TitleGenre;
import com.grup.movieshelf.JPA.Entity.Movies.TitleRating;
import com.grup.movieshelf.JPA.Entity.Sessions.*;
import com.grup.movieshelf.JPA.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    TitleGenreRepository titleGenreRepository;

    @Autowired
    TitleRatingRepository titleRatingRepository;

    @Autowired
    UserSuggestionRepository userSuggestionRepository;

    @Autowired
    SessionResultRepository sessionResultRepository;

    public RecommendationResult doSessionRecommend(Integer sessionId){
        RecommendationInput input = new RecommendationInput();

        for (UserSuggestion suggestion : userSuggestionRepository.getAllBySessionId(sessionId)){
            input.getTitles().add(titleRepository.getByTitleId(suggestion.getTitleId()));
        }
        RecommendationResult result = getRecommendations(input);
        for (Recommendation recommendation : result.getRecommendations()){
            sessionResultRepository.save(new SessionResult(sessionId,recommendation.getTitle().getTitleId()));
        }
        return result;
    }

    public RecommendationResult getRecommendations(RecommendationInput input) {
        RecommendationResult result = new RecommendationResult();

        /***********************************
         * INITIAL FILTER: RELEASE YEAR
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

        System.out.println("MEDIAN YEAR FOUND: " + medianYear);

        /**
         ASSIGN POINTS:

         +3 if title's release year is < 3 years from the median year
         +2 if title's release year is < 5 years from the median year
         +1 if title's release year is < 7 years from the median year

         NOTE: all titles with release years >= 7 years are discarded
         **/

        for(Title title : titleRepository.findByYearBetween(minYear, maxYear)) {
            if(!input.getTitles().contains(title)) {
                int score;
                if (Math.abs(medianYear - title.getYear()) < 3) {
                    score = 3;
                } else if (Math.abs(medianYear - title.getYear()) < 5) {
                    score = 2;
                } else {
                    score = 1;
                }
                result.getRecommendations().add(new Recommendation(title, score));
            }
        }

        /*********************************************
         * SECOND FILTER: FAME/INFAMY (NUM OF RATINGS)
         *********************************************/

        ArrayList<Integer> numOfRatings = new ArrayList<>();
        for(Title title : input.getTitles()) {
            numOfRatings.add(titleRatingRepository.findByTitleId(title.getTitleId()).getNumVotes());
        }
        Collections.sort(numOfRatings);
        // technically this isn't the median when the array is even in size, but it doesn't matter
        int medianNumOfVotes = numOfRatings.get(numOfRatings.size() / 2);
        int upperThreshold;
        int lowerThreshold;

        int top0 = 2000000; // threshold at which all titles are below
        int top100 = 570000; // TOP 100 WELL-KNOWN MOVIES
        int top200 = 426000; // TOP 200 WELL-KNOWN MOVIES
        int top500 = 245000; // TOP 500 WELL-KNOWN MOVIES
        int top1000 = 146500; // TOP 1000 WELL-KNOWN MOVIES
        int top5000 = 18500; // TOP 5000 WELL-KNOWN MOVIES
        int top10000 = 5120; // TOP 10000 WELL-KNOWN MOVIES
        int top25000 = 910; // TOP 25000 WELL-KNOWN MOVIES

        System.out.println("MEDIAN NUM OF VOTES: " + medianNumOfVotes);

        if(medianNumOfVotes > top100) {
            lowerThreshold = top200;
            upperThreshold = top0;
        } else if(medianNumOfVotes > top200) {
            lowerThreshold = top500;
            upperThreshold = top0;
        } else if(medianNumOfVotes > top500) {
            lowerThreshold = top500;
            upperThreshold = top100;
        } else if(medianNumOfVotes > top1000) {
            lowerThreshold = top1000;
            upperThreshold = top200;
        } else if(medianNumOfVotes > top5000) {
            lowerThreshold = top5000;
            upperThreshold = top1000;
        } else if(medianNumOfVotes > top10000) {
            lowerThreshold = top10000;
            upperThreshold = top5000;
        } else {
            lowerThreshold = top25000;
            upperThreshold = top10000;
        }

        List<Recommendation> recommendations = new ArrayList<>();
        List<TitleRating> titleRatings = titleRatingRepository.findByNumVotesBetween(lowerThreshold, upperThreshold);
        List<String> titleIds = new ArrayList<>();
        for(TitleRating titleRating : titleRatings) {
            titleIds.add(titleRating.getTitleId());
        }
        for(Recommendation recommendation : result.getRecommendations()) {
            if(titleIds.contains(recommendation.getTitle().getTitleId())) {
                recommendations.add(recommendation);
            }
        }
        result.setRecommendations(recommendations);

        /***********************************
         * THIRD FILTER: GENRE
         ***********************************/

        // GET COUNT OF GENRES
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

        // top 3 genres
        String genre1 = null;
        String genre2 = null;

        int genreCount1 = 0;
        int genreCount2 = 0;

        for(String genre : genres.keySet()) {
            int count = genres.get(genre);
            if(count > genreCount1) {
                // reorder
                genreCount2 = genreCount1;
                genre2 = genre1;
                genreCount1 = count;
                genre1 = genre;
            } else if(count > genreCount2) {
                // reorder
                genreCount2 = count;
                genre2 = genre;
            }
        }

        /**
            ASSIGN POINTS:

            +5 if one of title's genres matches #1 most popular
            +2 if one of title's genres matches #2 most popular

            NOTE: a title can receive points from multiple matchings
                ex. if #1 genre is "Comedy" and #2 genre is "Family",
                    a title matching both genres would receive +8 points
         **/

        System.out.println("TOP 2 GENRES: " + genre1 + ", " + genre2);

        List<String> genre1List = new ArrayList<>();
        for(TitleGenre titleGenre : titleGenreRepository.getAllByGenre(genre1)) {
            genre1List.add(titleGenre.getTitleId());
        }
        List<String> genre2List = new ArrayList<>();
        for(TitleGenre titleGenre : titleGenreRepository.getAllByGenre(genre2)) {
            genre2List.add(titleGenre.getTitleId());
        }

        for(Recommendation recommendation : result.getRecommendations()) {
            String titleId = recommendation.getTitle().getTitleId();
            if(genre1List.contains(titleId)) {
                recommendation.setScore(recommendation.getScore() + 5);
            } else if(genre2List.contains(titleId)) {
                recommendation.setScore(recommendation.getScore() + 2);
            }
        }

        /***********************************
         * FOURTH FILTER: AVERAGE RATING
         ***********************************/

        ArrayList<Float> avgRatings = new ArrayList<>();
        for(Title title : input.getTitles()) {
            avgRatings.add(titleRatingRepository.findByTitleId(title.getTitleId()).getAverageRating());
        }
        Collections.sort(avgRatings);
        // technically this isn't the median when the array is even in size, but it doesn't matter
        float medianAvgRating = avgRatings.get(avgRatings.size() / 2);

        System.out.println("MEDIAN AVG RATING: " + medianAvgRating);

        List<String> avgRating1List = new ArrayList<>();
        for(TitleRating titleRating : titleRatingRepository.findByAverageRatingBetween(medianAvgRating - (float) 0.5, medianAvgRating + (float) 0.5)) {
            avgRating1List.add(titleRating.getTitleId());
        }
        List<String> avgRating2List = new ArrayList<>();
        for(TitleRating titleRating : titleRatingRepository.findByAverageRatingBetween(medianAvgRating - 1, medianAvgRating + 1)) {
            avgRating2List.add(titleRating.getTitleId());
        }
        List<String> avgRating3List = new ArrayList<>();
        for(TitleRating titleRating : titleRatingRepository.findByAverageRatingBetween(medianAvgRating - (float) 1.5, medianAvgRating + (float) 1.5)) {
            avgRating3List.add(titleRating.getTitleId());
        }

        /**
         ASSIGN POINTS:

         +3 if title's avg rating is within 0.5 of the median avg rating
         +2 if title's avg rating is within 1.0 of the median avg rating
         +1 if title's avg rating is within 1.5 of the median avg rating
         **/

        for(Recommendation recommendation : result.getRecommendations()) {
            String titleId = recommendation.getTitle().getTitleId();
            if(avgRating1List.contains(titleId)) {
                recommendation.setScore(recommendation.getScore() + 3);
            } else if(avgRating2List.contains(titleId)) {
                recommendation.setScore(recommendation.getScore() + 2);
            } else if(avgRating3List.contains(titleId)) {
                recommendation.setScore(recommendation.getScore() + 1);
            }
        }

        // shuffle, sort by score, and slice results
        Collections.shuffle(result.getRecommendations());
        Collections.sort(result.getRecommendations());
        if(result.getRecommendations().size() > 20) {
            result.getRecommendations().subList(20, result.getRecommendations().size()).clear();
        }

        System.out.println("RECOMMENDATIONS:");
        for(Recommendation recommendation : result.getRecommendations()) {
            System.out.println(recommendation.getTitle().getTitleName() + " " + recommendation.getScore());
        }

        return result;
    }
}
