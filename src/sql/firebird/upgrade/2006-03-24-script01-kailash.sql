--add new column textmessageaddress_type_temp
ALTER TABLE contact_textmessageaddress ADD textmessageaddress_type_temp INTEGER;

--copy values from old(textmessageaddress_type)  to new(textmessageaddress_type_temp)
UPDATE contact_textmessageaddress SET textmessageaddress_type_temp = textmessageaddress_type;

--drop old column(textmessageaddress_type)
ALTER TABLE contact_textmessageaddress DROP textmessageaddress_type;

--re-add new column textmessageaddress_type
ALTER TABLE contact_textmessageaddress ADD textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code); ;

--copy values from previous(textmessageaddress_type_temp)  to new(textmessageaddress_type)
UPDATE contact_textmessageaddress SET textmessageaddress_type = textmessageaddress_type_temp;

--drop old column(textmessageaddress_type_temp)
ALTER TABLE contact_textmessageaddress DROP textmessageaddress_type_temp;
