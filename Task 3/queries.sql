-- "Show the number of lessons given per month during a specified year. It shall be possible to retrieve the total number of 
-- lessons per month (just one number per month) and the specific number of individual lessons, group lessons and ensembles (three numbers per month)."
SELECT EXTRACT(month FROM time_start) AS month, COUNT(*) as lessons, 
COUNT(CASE type_of_lesson WHEN 'Individual' THEN 1 END) as individual,
COUNT(CASE type_of_lesson WHEN 'Group' THEN 1 END) as group,
COUNT(CASE type_of_lesson WHEN 'Ensamble' THEN 1 END) as ensamble

FROM lesson 
WHERE EXTRACT(year FROM time_start) = 2022
GROUP BY EXTRACT(month FROM time_start);
-- "The same as above, but retrieve the average number of lessons per month during the entire year, instead of the total 
--  for each month."
SELECT type_of_lesson as LessonType, COUNT(type_of_lesson) as numberOfLessons
FROM lesson
WHERE EXTRACT(year FROM time_start) = 2022
GROUP BY LessonType

UNION ALL

SELECT 'SUM' type_of_lesson, COUNT(type_of_lesson)/12.
FROM lesson
WHERE EXTRACT(year FROM time_start) = 2022

--"List all instructors who has given more than a specific number of lessons during the current month. Sum all lessons, independent of type,
--and sort the result by the number of given lessons. This query will be used to find instructors risking to work too much, and will be executed daily.""
SELECT instructor_id as Instructor,
COUNT(CASE type_of_lesson WHEN 'Individual' THEN 1 END) as individual,
COUNT(CASE type_of_lesson WHEN 'Group' THEN 1 END) as group,
COUNT(CASE type_of_lesson WHEN 'Ensamble' THEN 1 END) as ensamble,
COUNT(type_of_lesson) as TotalLessons
FROM lesson

WHERE EXTRACT(month FROM time_start) = 1
GROUP BY instructor