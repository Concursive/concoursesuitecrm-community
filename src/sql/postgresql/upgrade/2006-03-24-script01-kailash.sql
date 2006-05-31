--add new column textmessageaddress_type_temp
ALTER TABLE contact_textmessageaddress ADD COLUMN textmessageaddress_type_temp INTEGER;

--copy values from old(textmessageaddress_type)  to new(textmessageaddress_type_temp)
UPDATE contact_textmessageaddress SET textmessageaddress_type_temp = textmessageaddress_type;

--drop old column(textmessageaddress_type)
ALTER TABLE contact_textmessageaddress DROP COLUMN textmessageaddress_type;

--rename new column(textmessageaddress_type_temp) to (textmessageaddress_type)
ALTER TABLE contact_textmessageaddress RENAME COLUMN  textmessageaddress_type_temp TO textmessageaddress_type;
ALTER TABLE contact_textmessageaddress ADD CONSTRAINT "$2" foreign key (textmessageaddress_type) REFERENCES lookup_textmessage_types(code);

