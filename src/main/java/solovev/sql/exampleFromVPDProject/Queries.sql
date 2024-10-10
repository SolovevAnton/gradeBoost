SELECT p.status, COUNT(*) AS count
FROM Package p
WHERE p.created BETWEEN COALESCE (CAST (:startDateCreated AS timestamp)
    , '2024-01-01 00:00:00')
  AND COALESCE (CAST (:endDateCreated AS timestamp)
    , NOW())
GROUP BY p.status;

SELECT CAST(p.created AS DATE) AS created_date, COUNT(*)
FROM Package p
WHERE CAST(p.created AS DATE) BETWEEN :startDate AND :endDate
GROUP BY created_date
ORDER BY created_date;

SELECT status, CAST(p.created AS DATE) AS created_date, COUNT(*)
FROM Package p
WHERE CAST(p.created AS DATE) BETWEEN :startDate AND :endDate
GROUP BY status, created_date
ORDER BY created_date;
