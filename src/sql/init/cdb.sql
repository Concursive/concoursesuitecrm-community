INSERT INTO lookup_contact_types (description) VALUES ('Employee');
INSERT INTO lookup_contact_types (description) VALUES ('Personal');
INSERT INTO lookup_contact_types (description) VALUES ('Sales');
INSERT INTO lookup_contact_types (description) VALUES ('Billing');
INSERT INTO lookup_contact_types (description) VALUES ('Technical');

INSERT INTO lookup_account_types (description) VALUES ('Customer');
INSERT INTO lookup_account_types (description) VALUES ('Competitor');
INSERT INTO lookup_account_types (description) VALUES ('Partner');
INSERT INTO lookup_account_types (description) VALUES ('Vendor');
INSERT INTO lookup_account_types (description) VALUES ('Investor');
INSERT INTO lookup_account_types (description) VALUES ('Prospect');

INSERT INTO lookup_orgaddress_types (description) VALUES ('Primary');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Auxiliary');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Billing');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Shipping');

INSERT INTO lookup_orgemail_types (description) VALUES ('Primary');
INSERT INTO lookup_orgemail_types (description) VALUES ('Auxiliary');

INSERT INTO lookup_orgphone_types (description) VALUES ('Main');
INSERT INTO lookup_orgphone_types (description) VALUES ('Fax');

INSERT INTO lookup_contactaddress_types (description) VALUES ('Business');
INSERT INTO lookup_contactaddress_types (description) VALUES ('Home');
INSERT INTO lookup_contactaddress_types (description) VALUES ('Other');

INSERT INTO lookup_contactemail_types (description) VALUES ('Business');
INSERT INTO lookup_contactemail_types (description) VALUES ('Personal');
INSERT INTO lookup_contactemail_types (description) VALUES ('Other');

INSERT INTO lookup_contactphone_types (description) VALUES ('Business');
INSERT INTO lookup_contactphone_types (description) VALUES ('Business2');
INSERT INTO lookup_contactphone_types (description) VALUES ('Business Fax');
INSERT INTO lookup_contactphone_types (description) VALUES ('Home');
INSERT INTO lookup_contactphone_types (description) VALUES ('Home2');
INSERT INTO lookup_contactphone_types (description) VALUES ('Home Fax');
INSERT INTO lookup_contactphone_types (description) VALUES ('Mobile');
INSERT INTO lookup_contactphone_types (description) VALUES ('Pager');
INSERT INTO lookup_contactphone_types (description) VALUES ('Other');

insert into lookup_delivery_options (description,level) values ('Email only',1);
insert into lookup_delivery_options (description,level) values ('Fax only',2);
insert into lookup_delivery_options (description,level) values ('Letter only',3);
insert into lookup_delivery_options (description,level) values ('Email then Fax',4);
insert into lookup_delivery_options (description,level) values ('Email then Letter',5);
insert into lookup_delivery_options (description,level) values ('Email, Fax, then Letter',6);

insert into lookup_industry (description) values ('Automotive');
insert into lookup_industry (description) values ('Biotechnology');
insert into lookup_industry (description) values ('Broadcasting and Cable');
insert into lookup_industry (description) values ('Computer');
insert into lookup_industry (description) values ('Consulting');
insert into lookup_industry (description) values ('Defense');
insert into lookup_industry (description) values ('Energy');
insert into lookup_industry (description) values ('Financial Services');
insert into lookup_industry (description) values ('Food');
insert into lookup_industry (description) values ('Healthcare');
insert into lookup_industry (description) values ('Hospitality');
insert into lookup_industry (description) values ('Insurance');
insert into lookup_industry (description) values ('Internet');
insert into lookup_industry (description) values ('Law Firms');
insert into lookup_industry (description) values ('Media');
insert into lookup_industry (description) values ('Pharmaceuticals');
insert into lookup_industry (description) values ('Real Estate');
insert into lookup_industry (description) values ('Retail');
insert into lookup_industry (description) values ('Telecommunications');
insert into lookup_industry (description) values ('Transportation');

insert into lookup_opportunity_types (description, level) values ('Type 1', 0);
insert into lookup_opportunity_types (description, level) values ('Type 2', 1);
insert into lookup_opportunity_types (description, level) values ('Type 3', 2);
insert into lookup_opportunity_types (description, level) values ('Type 4', 3);

