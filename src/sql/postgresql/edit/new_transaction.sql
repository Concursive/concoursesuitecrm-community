CREATE TABLE billing_transaction (
	id SERIAL PRIMARY KEY, 
	tax_id VARCHAR(9) NOT NULL,
	license_no VARCHAR(15) NOT NULL,
	npi VARCHAR(15),
	provider_id VARCHAR(20),
	namelast VARCHAR(80) NOT NULL,
	namefirst VARCHAR(80),
	payer_id VARCHAR(15) NOT NULL,
	type VARCHAR(15) NOT NULL,
	trans_id VARCHAR(30) NOT NULL,
	date_performed DATE NOT NULL,
	performed TIMESTAMP(3) NOT NULL,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);