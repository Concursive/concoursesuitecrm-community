--2005-08-04-script02-mr.sql
UPDATE opportunity_header SET access_type = (SELECT code FROM lookup_access_types WHERE link_module_id = 804051057 AND rule_id = 626030335);
UPDATE opportunity_header SET manager = (SELECT TOP 1 owner FROM opportunity_component oc WHERE opportunity_header.opp_id = oc.opp_id);
UPDATE opportunity_header SET manager = enteredby WHERE manager IS NULL;

--2005-09-09-script01-partha.sql
UPDATE permission_category SET action_plans = 0;

UPDATE permission_category SET action_plans = 1 WHERE constant IN (1,8);

--2005-09-12-script03-partha.sql
UPDATE action_plan SET link_object_id = (SELECT map_id from action_plan_constants where constant_id=42420034);

--2005-09-12-script05-partha.sql
UPDATE action_step SET action_id = (SELECT code FROM lookup_step_actions WHERE level=100) WHERE action_id = 0;
UPDATE action_plan_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=1;
UPDATE action_item_work SET link_module_id = NULL WHERE link_module_id = -1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=2) WHERE link_module_id=2;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=3) WHERE link_module_id=3;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1) WHERE link_module_id=1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200519) WHERE link_module_id=831200519;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200520) WHERE link_module_id=831200520;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=42420034;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1011200517) WHERE link_module_id=1011200517;
UPDATE action_item_work SET link_item_id = NULL WHERE link_item_id = -1;

-- 2005-09-19-script01-ananth.sql
UPDATE action_plan_work_notes SET submittedby = (SELECT assignedto FROM action_plan_work apw WHERE action_plan_work_notes.plan_work_id = apw.plan_work_id);

-- 2005-09-22-script01-ananth.sql
UPDATE action_item_work_notes SET submittedby = (SELECT owner FROM action_item_work aiw WHERE action_item_work_notes.item_work_id = aiw.item_work_id);

-- 2005-10-10-script01-partha.sql
UPDATE action_phase SET random = 0;
