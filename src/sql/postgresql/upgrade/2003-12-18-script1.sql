/* New field for joins */

SELECT * INTO saved_criteriaelement_tmp FROM saved_criteriaelement;

DROP TABLE saved_criteriaelement;

CREATE TABLE saved_criteriaelement (
  id_key SERIAL PRIMARY KEY,
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL references search_fields(id),
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL references field_types(id),
  value VARCHAR(80) NOT NULL,
  source INT NOT NULL DEFAULT -1,
  value_id INT NULL
);


