ALTER TABLE import ADD rating INTEGER REFERENCES lookup_contact_rating(code);
ALTER TABLE import ADD comments BLOB SUB_TYPE 1 SEGMENT SIZE 100;
