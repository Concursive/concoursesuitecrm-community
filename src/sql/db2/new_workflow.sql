

CREATE SEQUENCE business_process_com_lb_id_seq AS DECIMAL(27,0);


CREATE TABLE business_process_comp_library(
    component_id INTEGER NOT NULL,
    component_name VARGRAPHIC(255) NOT NULL,
    type_id INTEGER NOT NULL,
    class_name VARGRAPHIC(255) NOT NULL,
    description VARGRAPHIC(510),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(component_id)
);


CREATE SEQUENCE business_proc_s_comp_re_id_seq AS DECIMAL(27,0);


CREATE TABLE business_pro_com_result_lookup(
    result_id INTEGER NOT NULL,
    component_id INTEGER NOT NULL  REFERENCES business_process_comp_library(component_id),
    return_id INTEGER NOT NULL,
    description VARGRAPHIC(255),
    "level" INTEGER DEFAULT 0 NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(result_id)
);


CREATE SEQUENCE business_process_pa_lib_id_seq AS DECIMAL(27,0);


CREATE TABLE business_process_param_library(
    parameter_id INTEGER NOT NULL,
    component_id INTEGER,
    param_name VARGRAPHIC(255),
    description VARGRAPHIC(510),
    default_value CLOB(2G) NOT LOGGED,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(parameter_id)
);


CREATE SEQUENCE business_proc_s_process_id_seq AS DECIMAL(27,0);



CREATE TABLE business_process(
    process_id INTEGER NOT NULL,
    process_name VARGRAPHIC(255) NOT NULL,
    description VARGRAPHIC(510),
    type_id INTEGER NOT NULL,
    link_module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    component_start_id INTEGER,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(process_id)
);



CREATE SEQUENCE business_proc_s_compone_id_seq AS DECIMAL(27,0);



CREATE TABLE business_process_component(
    id INTEGER NOT NULL,
    process_id INTEGER NOT NULL  REFERENCES business_process(process_id),
    component_id INTEGER NOT NULL  REFERENCES business_process_comp_library(component_id),
    parent_id INTEGER REFERENCES business_process_component(id),
    parent_result_id INTEGER,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(id)
);


CREATE SEQUENCE business_process_param_id_seq AS DECIMAL(27,0);


CREATE TABLE business_process_parameter(
    id INTEGER NOT NULL,
    process_id INTEGER NOT NULL  REFERENCES business_process(process_id),
    param_name VARGRAPHIC(255),
    param_value CLOB(2G) NOT LOGGED,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(id)
);


CREATE SEQUENCE business_proc_s_comp_pa_id_seq AS DECIMAL(27,0);


CREATE TABLE business_pro_comp_parameter(
    id INTEGER NOT NULL,
    component_id INTEGER NOT NULL  REFERENCES business_process_component(id),
    parameter_id INTEGER NOT NULL  REFERENCES business_process_param_library(parameter_id),
    param_value CLOB(2G) NOT LOGGED,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(id)
);


CREATE SEQUENCE business_proc_s_e_event_id_seq AS DECIMAL(27,0);



CREATE TABLE business_process_events(
    event_id INTEGER NOT NULL,
    "second" VARGRAPHIC(64) DEFAULT G'0',
    "minute" VARGRAPHIC(64) DEFAULT G'*',
    "hour" VARGRAPHIC(64) DEFAULT G'*',
    dayofmonth VARGRAPHIC(64) DEFAULT G'*',
    "month" VARGRAPHIC(64) DEFAULT G'*',
    "dayofweek" VARGRAPHIC(64) DEFAULT G'*',
    "year" VARGRAPHIC(64) DEFAULT G'*',
    task VARGRAPHIC(255),
    extrainfo VARGRAPHIC(255),
    businessDays VARGRAPHIC(6) DEFAULT G'true',
    enabled CHAR(1) DEFAULT '0',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    process_id INTEGER NOT NULL  REFERENCES business_process(process_id),
    PRIMARY KEY(event_id)
);



CREATE TABLE business_process_log(
    process_name VARGRAPHIC(255) NOT NULL,
    anchor TIMESTAMP NOT NULL
);


CREATE SEQUENCE business_proc_s_hl_hook_id_seq AS DECIMAL(27,0);



CREATE TABLE business_process_hook_library(
    hook_id INTEGER NOT NULL,
    link_module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    hook_class VARGRAPHIC(255) NOT NULL,
    enabled CHAR(1) DEFAULT '0',
    PRIMARY KEY(hook_id)
);


CREATE SEQUENCE business_proc_s_ho_trig_id_seq AS DECIMAL(27,0);


CREATE TABLE business_process_hook_triggers(
    trigger_id INTEGER NOT NULL,
    action_type_id INTEGER NOT NULL,
    hook_id INTEGER NOT NULL  REFERENCES business_process_hook_library(hook_id),
    enabled CHAR(1) DEFAULT '0',
    PRIMARY KEY(trigger_id)
);


CREATE SEQUENCE business_proc_s_ho_hook_id_seq AS DECIMAL(27,0);


CREATE TABLE business_process_hook(
    id INTEGER NOT NULL,
    trigger_id INTEGER NOT NULL  REFERENCES business_process_hook_triggers(trigger_id),
    process_id INTEGER NOT NULL  REFERENCES business_process(process_id),
    enabled CHAR(1) DEFAULT '0',
    priority INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(id)
);

