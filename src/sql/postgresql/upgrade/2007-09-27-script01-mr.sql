
CREATE TABLE lookup_payment_status (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

ALTER TABLE order_payment ADD order_item_id INT REFERENCES order_product(item_id);
ALTER TABLE order_payment ADD history_id INT REFERENCES customer_product_history(history_id);
ALTER TABLE order_payment ADD date_to_process TIMESTAMP(3);
ALTER TABLE order_payment ADD creditcard_id INTEGER REFERENCES payment_creditcard(creditcard_id);
ALTER TABLE order_payment ADD bank_id INTEGER REFERENCES payment_eft(bank_id);
ALTER TABLE order_payment ADD status_id INTEGER REFERENCES lookup_payment_status(code);

CREATE TABLE order_payment_status (
  payment_status_id SERIAL PRIMARY KEY,
  payment_id INTEGER NOT NULL REFERENCES order_payment(payment_id),
  status_id INTEGER REFERENCES lookup_payment_status(code),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);
