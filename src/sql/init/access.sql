/*	access init
	for references to access table, 0 is the admin user.
	admin record is owned (?) by super account, so modified points to it.
	consequently, the "1"-record is entered first, then the 0-record.

	Other initialized tables can point to admin (0 record) for the
	modifiedby and enteredby fields, if needed.
*/

INSERT INTO access (username,password,role_id,contact_id,manager_id,enteredby,modifiedby) 
VALUES ('dhvadmin','ba97c515e3180cfd429fb65141de2b56',1,-1,-1,0,0);

