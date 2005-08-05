-- Relationships

CREATE TABLE lookup_relationship_types (
    type_id serial NOT NULL,
    category_id_maps_from integer NOT NULL,
    category_id_maps_to integer NOT NULL,
    reciprocal_name_1 character varying(512),
    reciprocal_name_2 character varying(512),
    "level" integer DEFAULT 0,
    default_item boolean DEFAULT false,
    enabled boolean DEFAULT true
);



CREATE TABLE relationship (
    relationship_id serial NOT NULL,
    type_id integer,
    object_id_maps_from integer NOT NULL,
    category_id_maps_from integer NOT NULL,
    object_id_maps_to integer NOT NULL,
    category_id_maps_to integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);

INSERT INTO lookup_relationship_types VALUES (1, 42420034, 42420034, 'Subsidiary of', 'Parent of', 10, false, true);
INSERT INTO lookup_relationship_types VALUES (2, 42420034, 42420034, 'Customer of', 'Supplier to', 20, false, true);
INSERT INTO lookup_relationship_types VALUES (3, 42420034, 42420034, 'Partner of', 'Partner of', 30, false, true);
INSERT INTO lookup_relationship_types VALUES (4, 42420034, 42420034, 'Competitor of', 'Competitor of', 40, false, true);
INSERT INTO lookup_relationship_types VALUES (5, 42420034, 42420034, 'Employee of', 'Employer of', 50, false, true);
INSERT INTO lookup_relationship_types VALUES (6, 42420034, 42420034, 'Department of', 'Organization made up of', 60, false, true);
INSERT INTO lookup_relationship_types VALUES (7, 42420034, 42420034, 'Group of', 'Organization made up of', 70, false, true);
INSERT INTO lookup_relationship_types VALUES (8, 42420034, 42420034, 'Member of', 'Organization made up of', 80, false, true);
INSERT INTO lookup_relationship_types VALUES (9, 42420034, 42420034, 'Consultant to', 'Consultant of', 90, false, true);
INSERT INTO lookup_relationship_types VALUES (10, 42420034, 42420034, 'Influencer of', 'Influenced by', 100, false, true);
INSERT INTO lookup_relationship_types VALUES (11, 42420034, 42420034, 'Enemy of', 'Enemy of', 110, false, true);
INSERT INTO lookup_relationship_types VALUES (12, 42420034, 42420034, 'Proponent of', 'Endorsed by', 120, false, true);
INSERT INTO lookup_relationship_types VALUES (13, 42420034, 42420034, 'Ally of', 'Ally of', 130, false, true);
INSERT INTO lookup_relationship_types VALUES (14, 42420034, 42420034, 'Sponsor of', 'Sponsored by', 140, false, true);
INSERT INTO lookup_relationship_types VALUES (15, 42420034, 42420034, 'Relative of', 'Relative of', 150, false, true);
INSERT INTO lookup_relationship_types VALUES (16, 42420034, 42420034, 'Affiliated with', 'Affiliated with', 160, false, true);
INSERT INTO lookup_relationship_types VALUES (17, 42420034, 42420034, 'Teammate of', 'Teammate of', 170, false, true);
INSERT INTO lookup_relationship_types VALUES (18, 42420034, 42420034, 'Financier of', 'Financed by', 180, false, true);

ALTER TABLE ONLY lookup_relationship_types
    ADD CONSTRAINT lookup_relationship_types_pkey PRIMARY KEY (type_id);



ALTER TABLE ONLY relationship
    ADD CONSTRAINT relationship_pkey PRIMARY KEY (relationship_id);


ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$1" FOREIGN KEY (type_id) REFERENCES lookup_relationship_types(type_id);



SELECT pg_catalog.setval('lookup_relationship_types_type_id_seq', 18, true);



SELECT pg_catalog.setval('relationship_relationship_id_seq', 1, false);



