CREATE TABLE lookup_delivery_options (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
insert into lookup_delivery_options (description,level) values ('Email only',1);
insert into lookup_delivery_options (description,level) values ('Fax only',2);
insert into lookup_delivery_options (description,level) values ('Letter only',3);
insert into lookup_delivery_options (description,level) values ('Email then Fax',4);
insert into lookup_delivery_options (description,level) values ('Email then Letter',5);
insert into lookup_delivery_options (description,level) values ('Email, Fax, then Letter',6);
