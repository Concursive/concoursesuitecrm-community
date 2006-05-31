ALTER TABLE import ADD rating INTEGER REFERENCES lookup_contact_rating(code);
ALTER TABLE import ADD comments TEXT;

