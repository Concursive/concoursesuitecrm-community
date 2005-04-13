-- New column to specify a logo in a quote
ALTER TABLE quote_entry ADD COLUMN logo_file_id INTEGER REFERENCES project_files(item_id);
