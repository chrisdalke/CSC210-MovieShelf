INSERT INTO chrisdal_csc210_final.titles (tconst, runtime_minutes, primary_title, start_year)
SELECT tconst, runtimeMinutes, primaryTitle, startYear
FROM chrisdal_csc210.Titles;