-- Droping few not null values from cfsinbox_message table

ALTER TABLE cfsinbox_message CHANGE reply_id reply_id INT; 
ALTER TABLE cfsinbox_message CHANGE enteredby enteredby INT;
ALTER TABLE cfsinbox_message ADD CONSTRAINT cfsinbox_message_enteredby_fkey FOREIGN KEY (enteredby)
      REFERENCES `access`(user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cfsinbox_message CHANGE sent sent TIMESTAMP NULL;
ALTER TABLE cfsinbox_message CHANGE modifiedby modifiedby INT;
ALTER TABLE cfsinbox_message ADD CONSTRAINT cfsinbox_message_modifiedby_fkey FOREIGN KEY (modifiedby)
      REFERENCES `access`(user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Droping few not null values from cfsinbox_messagelink table
ALTER TABLE cfsinbox_messagelink CHANGE sent_to sent_to INT;
ALTER TABLE cfsinbox_messagelink ADD CONSTRAINT cfsinbox_messagelink_sent_to_fkey FOREIGN KEY (sent_to)
      REFERENCES contact(contact_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cfsinbox_messagelink CHANGE sent_from sent_from INT;
ALTER TABLE cfsinbox_messagelink ADD CONSTRAINT cfsinbox_messagelink_sent_from_fkey FOREIGN KEY (sent_from)
      REFERENCES `access`(user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Adding few fields and constraints to cfsinbox_messagelink table 
alter table cfsinbox_messagelink add sent_to_mail_id varchar(255); 
alter table cfsinbox_messagelink add sent_from_mail_id varchar(255) ;
alter table cfsinbox_messagelink add email_account_id int4;
alter table cfsinbox_messagelink add replied_to_message_id int4;
alter table cfsinbox_messagelink add item_id int4;
alter table cfsinbox_messagelink add last_action int4;



alter table cfsinbox_messagelink add  CONSTRAINT cfsinbox_messagelink_email_account_id_fkey FOREIGN KEY (email_account_id)
      REFERENCES `email_account` (email_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Email account user preferences table

CREATE TABLE email_account_user_preferences (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT REFERENCES `access` (user_id),
  email_account_id INT NOT NULL REFERENCES email_account (email_id)
);

