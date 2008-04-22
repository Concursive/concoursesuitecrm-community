-- new column in web_style

ALTER TABLE web_style ADD COLUMN active_style BOOLEAN DEFAULT false NOT NULL;

UPDATE web_style SET active_style = false WHERE custom = false; 

