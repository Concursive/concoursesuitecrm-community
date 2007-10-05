-- Droping few not null values from cfsinbox_message table

alter table cfsinbox_message alter reply_id drop not null;
alter table cfsinbox_message alter enteredby drop not null;
alter table cfsinbox_message alter sent drop not null ;
alter table cfsinbox_message alter sent drop default;
alter table cfsinbox_message alter modifiedby drop not null;


-- Droping few not null values from cfsinbox_messagelink table
alter table cfsinbox_messagelink alter sent_to drop not null ;
alter table cfsinbox_messagelink alter sent_from drop not null ;

-- Adding few fields and constraints to cfsinbox_messagelink table
alter table cfsinbox_messagelink add sent_to_mail_id varchar(255);
alter table cfsinbox_messagelink add sent_from_mail_id varchar(255) ;
alter table cfsinbox_messagelink add email_account_id int4;
alter table cfsinbox_messagelink add replied_to_message_id int4;
alter table cfsinbox_messagelink add item_id int4;
alter table cfsinbox_messagelink add last_action int4;



alter table cfsinbox_messagelink add  CONSTRAINT cfsinbox_messagelink_email_account_id_fkey FOREIGN KEY (email_account_id)
      REFERENCES "email_account" (email_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Email account user preferences table

CREATE TABLE email_account_user_preferences
(
  id SERIAL PRIMARY KEY,
  user_id int4 ,
  email_account_id int4 NOT NULL,
  CONSTRAINT email_account_user_preferences_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES "access" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT email_account_user_preferences_email_account_id_fkey FOREIGN KEY (email_account_id)
      REFERENCES "email_account" (email_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) ;
