--Ticket Defect table
CREATE TABLE ticket_defect (
  defect_id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  start_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP(3),
  enabled BOOLEAN NOT NULL DEFAULT true,
  trashed_date TIMESTAMP(3)
);

ALTER TABLE ticket ADD COLUMN defect_id INTEGER REFERENCES ticket_defect(defect_id);

