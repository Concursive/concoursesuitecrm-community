
CREATE SEQUENCE action_plan_category_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan_category(
    id INTEGER NOT NULL  PRIMARY KEY,
    cat_level INTEGER DEFAULT 0 NOT NULL,
    parent_cat_code INTEGER DEFAULT 0 NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    full_description CLOB(2G) NOT LOGGED DEFAULT NULL NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    site_id INTEGER REFERENCES lookup_site_id(code)
);


CREATE SEQUENCE action_plan_c_ory_draft_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan_category_draft(
    id INTEGER NOT NULL  PRIMARY KEY,
    link_id INTEGER DEFAULT -1,
    cat_level INTEGER DEFAULT 0 NOT NULL,
    parent_cat_code INTEGER DEFAULT 0 NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    full_description CLOB(2G) NOT LOGGED DEFAULT NULL NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    site_id INTEGER REFERENCES lookup_site_id(code)
);


CREATE SEQUENCE action_plan_c_tants_map_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan_constants(
    map_id INTEGER NOT NULL  PRIMARY KEY,
    constant_id INTEGER NOT NULL,
    description VARGRAPHIC(300)
);


CREATE INDEX action_plan_const1
    ON action_plan_constants(constant_id);


CREATE SEQUENCE action_plan_editor_loo_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan_editor_lookup(
    id INTEGER NOT NULL  PRIMARY KEY,
    module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    constant_id INTEGER NOT NULL  REFERENCES action_plan_constants(map_id),
    "level" INTEGER DEFAULT 0,
    description CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    category_id INTEGER NOT NULL
);


CREATE SEQUENCE action_plan_plan_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan(
    plan_id INTEGER NOT NULL  PRIMARY KEY,
    plan_name VARGRAPHIC(255) NOT NULL,
    description VARGRAPHIC(2000),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    approved TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    archive_date TIMESTAMP,
    cat_code INTEGER REFERENCES action_plan_category(id),
    subcat_code1 INTEGER REFERENCES action_plan_category(id),
    subcat_code2 INTEGER REFERENCES action_plan_category(id),
    subcat_code3 INTEGER REFERENCES action_plan_category(id),
    link_object_id INTEGER REFERENCES action_plan_constants(map_id),
    site_id INTEGER REFERENCES lookup_site_id(code)
);


CREATE SEQUENCE action_phase_phase_id_seq AS DECIMAL(27,0);


CREATE TABLE action_phase(
    phase_id INTEGER NOT NULL  PRIMARY KEY,
    parent_id INTEGER REFERENCES action_phase(phase_id),
    plan_id INTEGER NOT NULL  REFERENCES action_plan(plan_id),
    phase_name VARGRAPHIC(255) NOT NULL,
    description VARGRAPHIC(2000),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    random CHAR(1) DEFAULT '0',
    "global" CHAR(1) DEFAULT '0'
);



CREATE SEQUENCE lookup_duration_type_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_duration_type(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE lookup_step_actions_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_step_actions(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    constant_id INTEGER NOT NULL  UNIQUE
);

CREATE SEQUENCE action_step_step_id_seq AS DECIMAL(27,0);


CREATE TABLE action_step(
    step_id INTEGER NOT NULL  PRIMARY KEY,
    parent_id INTEGER REFERENCES action_step(step_id),
    phase_id INTEGER NOT NULL  REFERENCES action_phase(phase_id),
    description VARGRAPHIC(2000),
    duration_type_id INTEGER REFERENCES lookup_duration_type(code),
    estimated_duration INTEGER,
    category_id INTEGER REFERENCES custom_field_category(category_id),
    field_id INTEGER REFERENCES custom_field_info(field_id),
    permission_type INTEGER,
    role_id INTEGER REFERENCES "role"(role_id),
    department_id INTEGER REFERENCES lookup_department(code),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    allow_skip_to_here CHAR(1) DEFAULT '0' NOT NULL,
    label VARGRAPHIC(80),
    action_required CHAR(1) DEFAULT '0' NOT NULL,
    group_id INTEGER REFERENCES user_group(group_id),
    target_relationship VARGRAPHIC(80),
    action_id INTEGER REFERENCES lookup_step_actions(constant_id),
    allow_update CHAR(1) DEFAULT '1' NOT NULL,
    campaign_id INTEGER REFERENCES campaign(campaign_id),
    allow_duplicate_recipient CHAR(1) DEFAULT '0' NOT NULL
);


CREATE SEQUENCE step_action_map_map_id_seq AS DECIMAL(27,0);

CREATE TABLE step_action_map(
    map_id INTEGER NOT NULL  PRIMARY KEY,
    constant_id INTEGER NOT NULL  REFERENCES action_plan_constants(map_id),
    action_constant_id INTEGER NOT NULL  REFERENCES lookup_step_actions(constant_id)
);


CREATE SEQUENCE action_plan_w_plan_work_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan_work(
    plan_work_id INTEGER NOT NULL  PRIMARY KEY,
    action_plan_id INTEGER NOT NULL  REFERENCES action_plan(plan_id),
    manager INTEGER,
    assignedTo INTEGER NOT NULL  REFERENCES "access"(user_id),
    link_module_id INTEGER NOT NULL  REFERENCES action_plan_constants(map_id),
    link_item_id INTEGER NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    current_phase INTEGER REFERENCES action_phase(phase_id)
);



CREATE SEQUENCE action_plan_w_otes_note_id_seq AS DECIMAL(27,0);


CREATE TABLE action_plan_work_notes(
    note_id INTEGER NOT NULL  PRIMARY KEY,
    plan_work_id INTEGER NOT NULL  REFERENCES action_plan_work(plan_work_id),
    description CLOB(2G) NOT LOGGED NOT NULL,
    submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    submittedby INTEGER NOT NULL  REFERENCES "access"(user_id)
);


CREATE SEQUENCE action_phase__hase_work_id_seq AS DECIMAL(27,0);


CREATE TABLE action_phase_work(
    phase_work_id INTEGER NOT NULL  PRIMARY KEY,
    plan_work_id INTEGER NOT NULL  REFERENCES action_plan_work(plan_work_id),
    action_phase_id INTEGER NOT NULL  REFERENCES action_phase(phase_id),
    status_id INTEGER,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    "level" INTEGER DEFAULT 0,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id)
);



CREATE SEQUENCE action_item_w_item_work_id_seq AS DECIMAL(27,0);


CREATE TABLE action_item_work(
    item_work_id INTEGER NOT NULL  PRIMARY KEY,
    phase_work_id INTEGER NOT NULL  REFERENCES action_phase_work(phase_work_id),
    action_step_id INTEGER NOT NULL  REFERENCES action_step(step_id),
    status_id INTEGER,
    owner INTEGER REFERENCES "access"(user_id),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    link_module_id INTEGER REFERENCES action_plan_constants(map_id),
    link_item_id INTEGER,
    "level" INTEGER DEFAULT 0,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id)
);


CREATE SEQUENCE action_item_w_otes_note_id_seq AS DECIMAL(27,0);


CREATE TABLE action_item_work_notes(
    note_id INTEGER NOT NULL  PRIMARY KEY,
    item_work_id INTEGER NOT NULL  REFERENCES action_item_work(item_work_id),
    description CLOB(2G) NOT LOGGED NOT NULL,
    submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    submittedby INTEGER NOT NULL  REFERENCES "access"(user_id)
);


CREATE SEQUENCE action_step_lookup_code_seq AS DECIMAL(27,0);


CREATE TABLE action_step_lookup(
    code INTEGER NOT NULL  PRIMARY KEY,
    step_id INTEGER NOT NULL  REFERENCES action_step(step_id),
    description VARGRAPHIC(255) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE TABLE action_step_account_types(
    step_id INTEGER NOT NULL  REFERENCES action_step(step_id),
    type_id INTEGER NOT NULL  REFERENCES lookup_account_types(code)
);


CREATE SEQUENCE action_item_w_selection_id_seq AS DECIMAL(27,0);


CREATE TABLE action_item_work_selection(
    selection_id INTEGER NOT NULL  PRIMARY KEY,
    item_work_id INTEGER NOT NULL  REFERENCES action_item_work(item_work_id),
    selection INTEGER NOT NULL  REFERENCES action_step_lookup(code)
);


CREATE SEQUENCE ticket_catego_n_map_map_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_category_plan_map(
    map_id INTEGER NOT NULL  PRIMARY KEY,
    plan_id INTEGER NOT NULL  REFERENCES action_plan(plan_id),
    category_id INTEGER NOT NULL  REFERENCES ticket_category(id)
);


CREATE TABLE ticket_category_draft_plan_map(
    map_id INTEGER NOT NULL  PRIMARY KEY,
    plan_id INTEGER NOT NULL  REFERENCES action_plan(plan_id),
    category_id INTEGER NOT NULL  REFERENCES ticket_category_draft(id)
);
