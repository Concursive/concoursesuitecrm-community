UPDATE "pg_class" SET "reltriggers" = 0 WHERE "relname" !~ '^pg_';
DELETE FROM access;

/*	access init
	for references to access table, 0 is the admin user.
	admin record is owned (?) by super account, so modified points to it.
	consequently, the "1"-record is entered first, then the 0-record.

	Other initialized tables can point to admin (0 record) for the
	modifiedby and enteredby fields, if needed.
*/

DROP SEQUENCE access_user_id_seq;
CREATE SEQUENCE access_user_id_seq start 1 increment 1 maxvalue 2147483647 minvalue 1  cache 1 ;
SELECT nextval ('access_user_id_seq');

INSERT INTO access (user_id,username,password,role_id,contact_id,manager_id,enteredby,modifiedby) 
VALUES (1,'super','f8788660ff31d110c242b3d7659ce1eb',1,-1,-1,1,1);
INSERT INTO access (user_id,username,password,role_id,contact_id,manager_id,enteredby,modifiedby) 
VALUES (0,'dhvadmin','f8788660ff31d110c242b3d7659ce1eb',1,-1,-1,1,1);

BEGIN TRANSACTION;
CREATE TEMP TABLE "tr" ("tmp_relname" name, "tmp_reltriggers" smallint);
INSERT INTO "tr" SELECT C."relname", count(T."oid") FROM "pg_class" C, "pg_trigger" T WHERE C."oid" = T."tgrelid" AND C."relname" !~ '^pg_' GROUP BY 1;
UPDATE "pg_class" SET "reltriggers" = TMP."tmp_reltriggers" FROM "tr" TMP WHERE "pg_class"."relname" = TMP."tmp_relname";
COMMIT TRANSACTION;
