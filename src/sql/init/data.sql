/*
#	Initial Site data (Base site)
#
#	$Id$
#	$Log#
#
*/
	
DELETE FROM org_type;
INSERT INTO org_type (org_type_code, org_type) values ('CU','Customer');
INSERT INTO org_type (org_type_code, org_type) values ('CO','Competitor');
INSERT INTO org_type (org_type_code, org_type) values ('PA','Partner');
INSERT INTO org_type (org_type_code, org_type) values ('VE','Vendor');
INSERT INTO org_type (org_type_code, org_type) values ('IN','Investor');
INSERT INTO org_type (org_type_code, org_type) values ('PR','Prospect');
INSERT INTO org_type (org_type_code, org_type) values ('ME','Us');
INSERT INTO org_type (org_type_code, org_type) values ('OT','Other');

DELETE FROM lookup_department;
INSERT INTO lookup_department (description) values ('Customer Relations');
INSERT INTO lookup_department (description) values ('Engineering');
INSERT INTO lookup_department (description) values ('Billing');
INSERT INTO lookup_department (description) values ('Shipping/Receiving');
INSERT INTO lookup_department (description) values ('Purchasing');
INSERT INTO lookup_department (description) values ('Accounting');
INSERT INTO lookup_department (description) values ('Human Resources');


INSERT INTO config (name,textkey,textvalue) VALUES('IndustrySelection','ISP','Internet Service Provider');
INSERT INTO config (name,textkey,textvalue) VALUES('IndustrySelection','ASP','Application Service Provider');
INSERT INTO config (name,textkey,textvalue) VALUES('IndustrySelection','NSP','Nada Service Provider');



INSERT INTO config (name,textkey,textvalue) VALUES('InsuranceSelection','M','Medical');
INSERT INTO config (name,textkey,textvalue) VALUES('InsuranceSelection','D','Dental');

INSERT INTO config (name,textkey,textvalue) VALUES('LocationTypeSelection','P','Primary');
INSERT INTO config (name,textkey,textvalue) VALUES('LocationTypeSelection','B','Billing');
INSERT INTO config (name,textkey,textvalue) VALUES('LocationTypeSelection','S','Satellite');

