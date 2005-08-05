
CREATE SEQUENCE lookup_quote_delivery_code_seq;
CREATE TABLE lookup_quote_delivery (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Each quote can have several conditions. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
CREATE SEQUENCE lookup_quote_condition_code_seq;
CREATE TABLE lookup_quote_condition (
  code INTEGER PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Create a new table to group quote versions
CREATE SEQUENCE quote_group_group_id_seq start with 1000 minvalue -1  increment by 1;
CREATE TABLE quote_group (
  group_id INTEGER PRIMARY KEY,
  unused CHAR(1) 
);

-- Adjustments to the quote_entry table
ALTER TABLE quote_entry ADD product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE quote_entry ADD customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE quote_entry ADD opp_id INTEGER REFERENCES opportunity_header(opp_id);
ALTER TABLE quote_entry ADD "version" VARCHAR(255) DEFAULT '0' NOT NULL;
ALTER TABLE quote_entry ADD COLUMN group_id INTEGER REFERENCES quote_group(group_id);
ALTER TABLE quote_entry ADD CONSTRAINT CHK_NOTNULL CHECK(group_id <> NULL);
ALTER TABLE quote_entry ADD delivery_id INTEGER REFERENCES lookup_quote_delivery(code);
ALTER TABLE quote_entry ADD email_address CLOB;
ALTER TABLE quote_entry ADD phone_number CLOB;
ALTER TABLE quote_entry ADD address CLOB;
ALTER TABLE quote_entry ADD fax_number CLOB;
ALTER TABLE quote_entry ADD submit_action INTEGER;
ALTER TABLE quote_entry ADD closed TIMESTAMP;
ALTER TABLE quote_entry ADD show_total boolean DEFAULT true;
ALTER TABLE quote_entry ADD show_subtotal boolean DEFAULT true;
ALTER TABLE quote_entry ADD logo_file_id INTEGER REFERENCES project_files(item_id);
ALTER TABLE quote_entry ADD trashed_date TIMESTAMP;

-- Create a new table to map quote conditions to a quote
CREATE SEQUENCE quote_condition_map_id_seq;
CREATE TABLE quote_condition (
  map_id INTEGER  PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  condition_id INTEGER NOT NULL REFERENCES lookup_quote_condition(code)
);

-- Create a new table to log the quote history
CREATE SEQUENCE quotelog_id_seq;
CREATE TABLE quotelog (
  id INTEGER  PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  status_id INTEGER REFERENCES lookup_quote_status(code),
  terms_id INTEGER REFERENCES lookup_quote_terms(code),
  type_id INTEGER REFERENCES lookup_quote_type(code),
  delivery_id INTEGER REFERENCES lookup_quote_delivery(code),
  notes CLOB ,
  grand_total FLOAT,
  enteredby INT NOT NULL REFERENCES access(user_id),
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	modifiedby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  issued_date TIMESTAMP,
  submit_action INTEGER,
  closed TIMESTAMP
);


-- Create a new table to lookup quote remarks
CREATE SEQUENCE lookup_quote_remarks_code_seq;
CREATE TABLE lookup_quote_remarks (
  code INTEGER   PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Create a new table to map remarks to a quote
CREATE SEQUENCE quote_remark_map_id_seq;
CREATE TABLE quote_remark (
  map_id INTEGER  PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  remark_id INTEGER NOT NULL REFERENCES lookup_quote_remarks(code)
);

-- Each quote has notes
-- Example: Notes entered by Advertiser, Notes entered by Designer
CREATE SEQUENCE quote_notes_notes_id_seq;
CREATE TABLE quote_notes (
  notes_id INT  PRIMARY KEY,
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  notes CLOB,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL
);

