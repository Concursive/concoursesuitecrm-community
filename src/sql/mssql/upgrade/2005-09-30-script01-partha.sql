--Ticket Defect table
CREATE TABLE ticket_defect (
  defect_id INT IDENTITY PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  start_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  enabled BIT NOT NULL DEFAULT 1,
  trashed_date DATETIME
);

ALTER TABLE ticket ADD defect_id INTEGER REFERENCES ticket_defect(defect_id);

