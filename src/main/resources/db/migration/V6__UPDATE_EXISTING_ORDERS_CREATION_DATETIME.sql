UPDATE orders
SET creation_datetime = CONVERT_TZ(NOW(), @@session.time_zone, '+00:00')
WHERE creation_datetime IS NULL;