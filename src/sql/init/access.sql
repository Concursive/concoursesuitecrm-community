/*	
  access init
	for references to access table, 0 is the admin user.
	admin record is owned (?) by super account, so modified points to it.
	consequently, the "1"-record is entered first, then the 0-record.

	Other initialized tables can point to admin (0 record) for the
	modifiedby and enteredby fields, if needed.
*/

INSERT INTO access (user_id, username,password,role_id,contact_id,manager_id,enteredby,modifiedby) 
VALUES (0, 'dhvadmin','37fc741a1eb9cb1e58761a166eb496e9',1,-1,-1,0,0);

