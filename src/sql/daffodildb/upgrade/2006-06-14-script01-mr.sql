UPDATE action_step SET action_id = null WHERE action_id = 110061037;
DELETE FROM step_action_map WHERE action_constant_id = 110061037;
DELETE FROM lookup_step_actions WHERE constant_id = 110061037;

UPDATE action_step SET action_id = null WHERE action_id = 110061032;
DELETE FROM step_action_map WHERE action_constant_id = 110061032;
DELETE FROM lookup_step_actions WHERE constant_id = 110061032;
