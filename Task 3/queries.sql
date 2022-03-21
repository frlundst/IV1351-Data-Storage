-- "Show the number of lessons given per month during a specified year. It shall be possible to retrieve the total number of 
-- lessons per month (just one number per month) and the specific number of individual lessons, group lessons and ensembles (three numbers per month)."
CREATE VIEW view1 AS SELECT EXTRACT(month FROM time_start) AS month, COUNT(*) as lessons, 
COUNT(CASE type_of_lesson WHEN 'Individual' THEN 1 END) as individual,
COUNT(CASE type_of_lesson WHEN 'Group' THEN 1 END) as group,
COUNT(CASE type_of_lesson WHEN 'Ensamble' THEN 1 END) as ensamble

FROM lesson 
WHERE EXTRACT(year FROM time_start) = 2022
GROUP BY EXTRACT(month FROM time_start);

-- "The same as above, but retrieve the average number of lessons per month during the entire year, instead of the total 
--  for each month."
CREATE VIEW view2 AS SELECT type_of_lesson as LessonType, COUNT(type_of_lesson) as numberOfLessons
FROM lesson
WHERE EXTRACT(year FROM time_start) = 2022
GROUP BY LessonType

UNION ALL

SELECT 'AVG' type_of_lesson, COUNT(type_of_lesson)/12.
FROM lesson
WHERE EXTRACT(year FROM time_start) = 2022

--"List all instructors who has given more than a specific number of lessons during the current month. Sum all lessons, independent of type,
--and sort the result by the number of given lessons. This query will be used to find instructors risking to work too much, and will be executed daily.""
EXPLAIN ANALYZE CREATE VIEW view3 AS
SELECT instructor_id as Instructor,
COUNT(type_of_lesson) as TotalLessons
FROM lesson

WHERE EXTRACT(month FROM time_start) = EXTRACT(month FROM now())
GROUP BY Instructor HAVING COUNT(type_of_lesson)>1;

--List all ensembles held during the next week, sorted by music genre and weekday. For each ensemble tell whether it's full booked, has 1-2 seats left 
--or has more seats left. Hint: you might want to use a CASE statement in your query to produce the desired output.
EXPLAIN ANALYZE CREATE MATERIALIZED VIEW mv AS SELECT
to_char(time_start, 'Day') AS Weekday,
genre AS Genre,
max_nr_slots AS Seats,
amount_of_students AS Amount,
CASE
	WHEN amount_of_students = CAST(max_nr_slots AS INTEGER) THEN 'Full booked'
	WHEN amount_of_students > CAST(max_nr_slots AS INTEGER) - 3 THEN 'One or two seats left'
	ELSE 'Many seats left'
END AS Booked
FROM lesson
WHERE type_of_lesson='Ensamble' AND EXTRACT(week from time_start) = EXTRACT(week from now()) + 1
GROUP BY Weekday, Genre, Seats, Booked, Amount;