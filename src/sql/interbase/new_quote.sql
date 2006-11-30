
CREATE GENERATOR lookup_quote_status_code_seq;
CREATE TABLE lookup_quote_status (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

-- Each quote has a type
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend
CREATE GENERATOR lookup_quote_type_code_seq;
CREATE TABLE lookup_quote_type (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

-- Each quote has terms in which the quote was placed
CREATE GENERATOR lookup_quote_terms_code_seq;
CREATE TABLE lookup_quote_terms (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


-- Each quote has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE GENERATOR lookup_quote_source_code_seq;
CREATE TABLE lookup_quote_source (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


-- The details of a quote are listed
-- A quote can be requested by an organization, or a specific contact in an organization.
CREATE GENERATOR quote_entry_quote_id_seq;
CREATE TABLE quote_entry (
  quote_id INTEGER  NOT NULL,
  parent_id INTEGER ,
  org_id INTEGER REFERENCES organization(org_id),
  contact_id INTEGER REFERENCES contact(contact_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  grand_total FLOAT,
  status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration_date TIMESTAMP ,
  quote_terms_id INTEGER REFERENCES lookup_quote_terms(code),
  quote_type_id INTEGER REFERENCES lookup_quote_type(code),
  issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  short_description VARCHAR(4192),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  ticketid INTEGER REFERENCES ticket(ticketid),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (QUOTE_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE QUOTE_ENTRY ADD CONSTRAINT FK_QUOTE_ENTRY_QUOTE_ID
  FOREIGN KEY (PARENT_ID) REFERENCES QUOTE_ENTRY
  (QUOTE_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Each quote can contain multiple products(line items)
CREATE GENERATOR quote_product_item_id_seq;
CREATE TABLE quote_product (
  item_id INTEGER  NOT NULL,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL ,
  total_price FLOAT DEFAULT 0 NOT NULL ,
  estimated_delivery_date TIMESTAMP,
  status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  estimated_delivery BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  "comment" VARCHAR(300),
  PRIMARY KEY (ITEM_ID)
);

-- Each quote_product can have configurable options
-- Old Name: quote_product_options_quote_product_option_id_seq;
CREATE GENERATOR quote_product_uct_option_id_seq;
CREATE TABLE quote_product_options (
  quote_product_option_id INTEGER  NOT NULL,
  item_id INTEGER NOT NULL REFERENCES quote_product(item_id),
  product_option_id INTEGER NOT NULL  REFERENCES product_option_map(product_option_id),
  quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL,
  total_price FLOAT DEFAULT 0 NOT NULL,
  status_id INTEGER REFERENCES lookup_quote_status(code),
  PRIMARY KEY (QUOTE_PRODUCT_OPTION_ID)
);

CREATE TABLE quote_product_option_boolean (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" BOOLEAN NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_float (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" FLOAT NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_timestamp (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" TIMESTAMP NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_integer (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" INTEGER NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_text (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  id INTEGER
);

CREATE GENERATOR lookup_quote_status_code_seq;
CREATE TABLE lookup_quote_status (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

-- Each quote has a type
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend
CREATE GENERATOR lookup_quote_type_code_seq;
CREATE TABLE lookup_quote_type (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

-- Each quote has terms in which the quote was placed
CREATE GENERATOR lookup_quote_terms_code_seq;
CREATE TABLE lookup_quote_terms (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


-- Each quote has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE GENERATOR lookup_quote_source_code_seq;
CREATE TABLE lookup_quote_source (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


-- The details of a quote are listed
-- A quote can be requested by an organization, or a specific contact in an organization.
CREATE GENERATOR quote_entry_quote_id_seq;
CREATE TABLE quote_entry (
  quote_id INTEGER  NOT NULL,
  parent_id INTEGER ,
  org_id INTEGER REFERENCES organization(org_id),
  contact_id INTEGER REFERENCES contact(contact_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  grand_total FLOAT,
  status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration_date TIMESTAMP ,
  quote_terms_id INTEGER REFERENCES lookup_quote_terms(code),
  quote_type_id INTEGER REFERENCES lookup_quote_type(code),
  issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  short_description VARCHAR(4192),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  ticketid INTEGER REFERENCES ticket(ticketid),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (QUOTE_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE QUOTE_ENTRY ADD CONSTRAINT FK_QUOTE_ENTRY_QUOTE_ID
  FOREIGN KEY (PARENT_ID) REFERENCES QUOTE_ENTRY
  (QUOTE_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Each quote can contain multiple products(line items)
CREATE GENERATOR quote_product_item_id_seq;
CREATE TABLE quote_product (
  item_id INTEGER  NOT NULL,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL ,
  total_price FLOAT DEFAULT 0 NOT NULL ,
  estimated_delivery_date TIMESTAMP,
  status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  estimated_delivery BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  "comment" VARCHAR(300),
  PRIMARY KEY (ITEM_ID)
);

-- Each quote_product can have configurable options
-- Old Name: quote_product_options_quote_product_option_id_seq;
CREATE GENERATOR quote_product_uct_option_id_seq;
CREATE TABLE quote_product_options (
  quote_product_option_id INTEGER  NOT NULL,
  item_id INTEGER NOT NULL REFERENCES quote_product(item_id),
  product_option_id INTEGER NOT NULL  REFERENCES product_option_map(product_option_id),
  quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL,
  total_price FLOAT DEFAULT 0 NOT NULL,
  status_id INTEGER REFERENCES lookup_quote_status(code),
  PRIMARY KEY (QUOTE_PRODUCT_OPTION_ID)
);

CREATE TABLE quote_product_option_boolean (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" BOOLEAN NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_float (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" FLOAT NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_timestamp (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" TIMESTAMP NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_integer (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" INTEGER NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_text (
  quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  id INTEGER
);
