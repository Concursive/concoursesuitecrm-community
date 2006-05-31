-- Messages received by an user, from a specific contact
CREATE TABLE contact_message (
  id INT IDENTITY PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES message(id),
  received_date DATETIME NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES access(user_id)
);
