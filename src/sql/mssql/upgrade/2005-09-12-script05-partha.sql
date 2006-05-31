UPDATE action_step SET action_id = (SELECT code FROM lookup_step_actions WHERE level=100) WHERE action_id = 0;

ALTER TABLE [action_step] ADD
	 FOREIGN KEY
	(
		[action_id]
	) REFERENCES [lookup_step_actions] (
		[code]
	)
GO

CREATE TABLE step_action_map (
  map_id INT IDENTITY PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_id INTEGER NOT NULL REFERENCES lookup_step_actions(code)
);

UPDATE action_plan_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=1;

ALTER TABLE [action_plan_work] ADD
	 FOREIGN KEY
	(
		[link_module_id]
	) REFERENCES [action_plan_constants] (
		[map_id]
	)
GO


ALTER TABLE action_item_work ALTER COLUMN link_module_id INTEGER NULL;

UPDATE action_item_work SET link_module_id = NULL WHERE link_module_id = -1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=2) WHERE link_module_id=2;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=3) WHERE link_module_id=3;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1) WHERE link_module_id=1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200519) WHERE link_module_id=831200519;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200520) WHERE link_module_id=831200520;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=42420034;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1011200517) WHERE link_module_id=1011200517;

ALTER TABLE [action_item_work] ADD
	 FOREIGN KEY
	(
		[link_module_id]
	) REFERENCES [action_plan_constants] (
		[map_id]
	)
GO

ALTER TABLE action_item_work ALTER COLUMN link_item_id INTEGER NULL;

UPDATE action_item_work SET link_item_id = NULL WHERE link_item_id = -1;
