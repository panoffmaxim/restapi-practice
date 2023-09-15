UPDATE orders
SET creationDateTime = CONVERT_TZ(NOW(), @@session.time_zone, '+00:00')
WHERE creationDateTime IS NULL;