-- Add IM, Text Message, and Address Request

ALTER TABLE active_survey_responses ADD COLUMN address_updated INT;
--TODO: What about existing data? what to do with address_updated

CREATE TABLE lookup_im_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_im_services (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);


CREATE SEQUENCE lookup_textmessage_typ_code_seq;
CREATE TABLE lookup_textmessage_types (
  code INTEGER DEFAULT nextval('lookup_textmessage_typ_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
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
  address_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  imaddress_type INT references lookup_im_types(code),
  imaddress_service INT references lookup_im_services(code),
  imaddress VARCHAR(256),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_im BOOLEAN NOT NULL DEFAULT false
);


CREATE TABLE contact_textmessageaddress (
  address_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  textmessageaddress_type INT references lookup_im_types(code),
  textmessageaddress VARCHAR(256),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_textmessage_address BOOLEAN NOT NULL DEFAULT false
);


/*
 *  Inserting new delivery options
 */
INSERT INTO lookup_delivery_options VALUES (7, 'Instant Message', false, 7, false);
INSERT INTO lookup_delivery_options VALUES (8, 'Secure Socket', false, 8, false);
INSERT INTO lookup_delivery_options VALUES (9, 'Broadcast', false, 9, true);

DROP SEQUENCE lookup_instantmessenge_code_seq;
DROP TABLE lookup_instantmessenger_types;

-- Required for information requests to work
INSERT INTO survey (name, description, intro, outro, type, enteredby, modifiedby) values ('Address Update Request', '','','',2,0,0);

