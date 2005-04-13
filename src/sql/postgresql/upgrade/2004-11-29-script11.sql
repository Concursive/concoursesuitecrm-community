-- Add the show_total field to quote_entry
ALTER TABLE quote_entry ADD COLUMN show_total BOOLEAN;
ALTER TABLE quote_entry ALTER COLUMN show_total SET DEFAULT true;

