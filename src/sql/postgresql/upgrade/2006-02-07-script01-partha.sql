ALTER TABLE import ADD COLUMN rating INTEGER REFERENCES lookup_contact_rating(code);
ALTER TABLE import ADD COLUMN comments TEXT;

