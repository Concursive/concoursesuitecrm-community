DROP INDEX contact_address.postalcode_idx;
CREATE INDEX contact_address_postalcode_idx ON contact_address(postalcode);
CREATE INDEX organization_address_postalcode_idx ON organization_address(postalcode);

ALTER TABLE contact ADD revenue FLOAT;
ALTER TABLE contact ADD industry_temp_code INTEGER REFERENCES lookup_industry(code);
alter table contact add potential FLOAT;

ALTER TABLE organization ADD source integer REFERENCES lookup_contact_source(code);
ALTER TABLE organization ADD rating integer REFERENCES lookup_contact_rating(code);
alter table organization add potential float;

