SELECT 'EXEC sp_generate_inserts ' + 
'[' + name + ']' + 
',@owner = ' + 
'[' + RTRIM(USER_NAME(uid)) + '],' + 
'@ommit_images = 1, @disable_constraints = 0'
FROM sysobjects 
WHERE type = 'U' AND 
OBJECTPROPERTY(id,'ismsshipped') = 0

