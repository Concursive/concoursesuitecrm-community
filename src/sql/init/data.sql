/*
#	Initial Site data (Base site)
#
#	$Id$
#	$Log#
#
*/
	
DELETE FROM org_type;
INSERT INTO org_type values ('CU','Customer');
INSERT INTO org_type values ('CO','Competitor');
INSERT INTO org_type values ('PA','Partner');
INSERT INTO org_type values ('VE','Vendor');
INSERT INTO org_type values ('IN','Investor');
INSERT INTO org_type values ('PR','Prospect');
INSERT INTO org_type values ('ME','Us');
INSERT INTO org_type values ('OT','Other');

DELETE FROM lookup_department;
INSERT INTO lookup_department (description) values ('Customer Relations');
INSERT INTO lookup_department (description) values ('Engineering');
INSERT INTO lookup_department (description) values ('Billing');
INSERT INTO lookup_department (description) values ('Shipping/Receiving');
INSERT INTO lookup_department (description) values ('Purchasing');
INSERT INTO lookup_department (description) values ('Accounting');
INSERT INTO lookup_department (description) values ('Human Resources');


/*
# This is if there's already a "ME" record
#
UPDATE organization
	SET name = 'Dark Horse Ventures Base Site'
	,   notes = 'data.init Entry.'
	WHERE type = 'ME';
#
#
*/

/* this needs to be updated for new table structure (address separated from contact)

DELETE FROM organization;
INSERT INTO organization (name,type,notes,enteredby,modifiedby) VALUES ('Dark Horse Ventures, L.L.C.','ME','Init',0,0);
INSERT INTO contact (contact_id,employee_id,org_id,title,department,namesalutation,namelast,namefirst,namemiddle,namesuffix,addrline1,addrline2,city,state,country,zip,phone,phone_cell,email,dateentered,created,type,contact_type,notes,site,enteredby,modifiedby) VALUES (1,NULL,1,'',0,NULL,'Wood','Doug','',NULL,'','','','VA','USA','            ','                    ','                    ','','2001-06-30','2001-06-30 13:05:38-04','OT','OTHER','',1,2,2);

*/


/* INSERT INTO sales_survey (org_id) VALUES(1); */

INSERT INTO config (name,textkey,textvalue) VALUES('IndustrySelection','ISP','Internet Service Provider');
INSERT INTO config (name,textkey,textvalue) VALUES('IndustrySelection','ASP','Application Service Provider');
INSERT INTO config (name,textkey,textvalue) VALUES('IndustrySelection','NSP','Nada Service Provider');



INSERT INTO config (name,textkey,textvalue) VALUES('InsuranceSelection','M','Medical');
INSERT INTO config (name,textkey,textvalue) VALUES('InsuranceSelection','D','Dental');

INSERT INTO config (name,textkey,textvalue) VALUES('LocationTypeSelection','P','Primary');
INSERT INTO config (name,textkey,textvalue) VALUES('LocationTypeSelection','B','Billing');
INSERT INTO config (name,textkey,textvalue) VALUES('LocationTypeSelection','S','Satellite');

