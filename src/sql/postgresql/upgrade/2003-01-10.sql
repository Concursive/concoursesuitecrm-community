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

CREATE TABLE cfsinbox_message (
  id serial PRIMARY KEY,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT NOT NULL,
  reply_id INT NOT NULL,
  enteredby INT NOT NULL,
  sent TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type int not null default -1,
  modifiedby INT NOT NULL
);

CREATE TABLE cfsinbox_messagelink (
  id INT NOT NULL,
  status INT NOT NULL DEFAULT 0,
  sent_to INT NOT NULL,
  viewed TIMESTAMP DEFAULT NULL,
  enabled BOOLEAN NOT NULL DEFAULT 't',
  sent_from INT NOT NULL
);
