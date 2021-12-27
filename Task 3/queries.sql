-- "Show the number of lessons given per month during a specified year. It shall be possible to retrieve the total number of 
-- lessons per month (just one number per month) and the specific number of individual lessons, group lessons and ensembles (three numbers per month)."
SELECT EXTRACT(month FROM time_start) AS month, COUNT(*) as lessons, 
COUNT(CASE type_of_lesson WHEN 'Individual' THEN 1 END) as individual,
COUNT(CASE type_of_lesson WHEN 'Group' THEN 1 END) as group,
COUNT(CASE type_of_lesson WHEN 'Ensamble' THEN 1 END) as ensamble
FROM lesson 
WHERE EXTRACT(year FROM time_start) = 2022
GROUP BY EXTRACT(month FROM time_start);