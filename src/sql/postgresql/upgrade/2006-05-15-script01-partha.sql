ALTER TABLE web_page_row ADD COLUMN row_column_id INT REFERENCES web_row_column(row_column_id);
