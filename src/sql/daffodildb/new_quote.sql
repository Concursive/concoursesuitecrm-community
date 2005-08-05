
CREATE SEQUENCE lookup_quote_status_code_seq;
CREATE TABLE lookup_quote_status (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Each quote has a type
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend
CREATE SEQUENCE lookup_quote_type_code_seq;
CREATE TABLE lookup_quote_type (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Each quote has terms in which the quote was placed
CREATE SEQUENCE lookup_quote_terms_code_seq;
CREATE TABLE lookup_quote_terms (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
	"level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);


-- Each quote has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE SEQUENCE lookup_quote_source_code_seq;
CREATE TABLE lookup_quote_source (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
	"level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);


-- The details of a quote are listed
-- A quote can be requested by an organization, or a specific contact in an organization.
CREATE SEQUENCE quote_entry_quote_id_seq;
CREATE TABLE quote_entry (
  quote_id INT  PRIMARY KEY,
  parent_id INT REFERENCES quote_entry(quote_id),
	org_id INTEGER REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  grand_total FLOAT,
	status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration_date TIMESTAMP ,
  quote_terms_id INTEGER REFERENCES lookup_quote_terms(code),
  quote_type_id INTEGER REFERENCES lookup_quote_type(code),
  issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  short_description VARCHAR(4192),
  notes CLOB,
  ticketid INTEGER REFERENCES ticket(ticketid),
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
	modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	modifiedby INT REFERENCES access(user_id) NOT NULL
);

-- Each quote can contain multiple products(line items)
CREATE SEQUENCE quote_product_item_id_seq;
CREATE TABLE quote_product (
  item_id INT  PRIMARY KEY,
  quote_id INTEGER REFERENCES quote_entry(quote_id) NOT NULL ,
  product_id INTEGER REFERENCES product_catalog(product_id) NOT NULL ,
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
  estimated_delivery CLOB,
  comment VARCHAR(300)
);

-- Each quote_product can have configurable options
CREATE SEQUENCE quote_product_options_quote_product_option_id_seq;
CREATE TABLE quote_product_options (
	quote_product_option_id INT  PRIMARY KEY,
	item_id INTEGER REFERENCES quote_product(item_id) NOT NULL ,
	product_option_id INTEGER  REFERENCES product_option_map(product_option_id) NOT NULL,
	quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
	extended_price FLOAT DEFAULT 0 NOT NULL,
  total_price FLOAT DEFAULT 0 NOT NULL,
  status_id INTEGER REFERENCES lookup_quote_status(code)
);

CREATE TABLE quote_product_option_boolean (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value boolean NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_float (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value FLOAT NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_timestamp (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value TIMESTAMP NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_integer (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value INTEGER NOT NULL,
  id INTEGER
);

CREATE TABLE quote_product_option_text (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value CLOB NOT NULL,
  id INTEGER
);
