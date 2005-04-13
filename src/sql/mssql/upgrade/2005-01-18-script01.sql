-- Add IM, Text Message, and Address Request

ALTER TABLE active_survey_responses ADD COLUMN address_updated INT;
--TODO: What about existing data? what to do with address_updated

CREATE TABLE lookup_im_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_im_services (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);


CREATE TABLE lookup_textmessage_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);


INSERT INTO lookup_im_types (description, level) VALUES ('Business',10);
INSERT INTO lookup_im_types (description, level) VALUES ('Personal',20);
INSERT INTO lookup_im_types (description, level) VALUES ('Other',30);

INSERT INTO lookup_im_services (description, level) VALUES ('AOL Instant Messenger',10);
INSERT INTO lookup_im_services (description, level) VALUES ('Jabber Instant Messenger',20);
INSERT INTO lookup_im_services (description, level) VALUES ('MSN Instant Messenger',30);

INSERT INTO lookup_textmessage_types (description, level) VALUES ('Business',10);
INSERT INTO lookup_textmessage_types (description, level) VALUES ('Personal',20);
INSERT INTO lookup_textmessage_types (description, level) VALUES ('Other',30);


CREATE TABLE contact_imaddress (
  address_id INT IDENTITY PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  imaddress_type INT references lookup_im_types(code),
  imaddress_service INT references lookup_im_services(code),
  imaddress VARCHAR(256),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_im BIT NOT NULL DEFAULT 0
);


CREATE TABLE contact_textmessageaddress (
  address_id INT IDENTITY PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  textmessageaddress_type INT references lookup_im_types(code),
  textmessageaddress VARCHAR(256),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_textmessage_address BIT NOT NULL DEFAULT 0
);


INSERT INTO lookup_delivery_options (description,level,enabled) VALUES ('Instant Message', 7, 0);
INSERT INTO lookup_delivery_options (description,level,enabled) VALUES ('Secure Socket', 8, 0);
INSERT INTO lookup_delivery_options (description,level) VALUES ('Broadcast', 9);

DROP TABLE lookup_instantmessenger_types;

-- Required for information requests to work
INSERT INTO survey (name, description, intro, outro, type, enteredby, modifiedby) values ('Address Update Request', '','','',2,0,0);

