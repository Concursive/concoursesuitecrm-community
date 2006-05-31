-- Messages received by an user, from a specific contact
CREATE TABLE contact_message (
  id SERIAL PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES message(id),
  received_date TIMESTAMP(3) NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES access(user_id)
);
