INSERT INTO system_prefs (category, data) VALUES ('graphic', '/images/logo.gif');
INSERT INTO system_prefs (category, data) VALUES ('template', 'template0');
INSERT INTO system_prefs (category, data) VALUES ('css', 'template0');
INSERT INTO system_prefs (category, data) VALUES ('license', 'fdb61e1f82a9e32b89263c81db0115d5');

INSERT INTO system_modules (description) VALUES ('Account Management');
INSERT INTO system_modules (description) VALUES ('Contacts & Resources');

INSERT INTO lookup_contact_types (description) VALUES ('Employee');
INSERT INTO lookup_contact_types (description) VALUES ('Personal');
INSERT INTO lookup_contact_types (description) VALUES ('Sales');
INSERT INTO lookup_contact_types (description) VALUES ('Billing');
INSERT INTO lookup_contact_types (description) VALUES ('Technical');

INSERT INTO lookup_account_types (description) VALUES ('Customer');
INSERT INTO lookup_account_types (description) VALUES ('Competitor');
INSERT INTO lookup_account_types (description) VALUES ('Partner');
INSERT INTO lookup_account_types (description) VALUES ('Vendor');

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

INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Phone Call', true, 10);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax', false, 20);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('In-Person', false, 30);

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
