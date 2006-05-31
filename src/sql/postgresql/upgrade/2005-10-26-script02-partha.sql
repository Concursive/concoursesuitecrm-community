UPDATE action_step SET group_id = null WHERE permission_type IS NULL OR permission_type <> 8;
UPDATE action_step SET department_id = null WHERE permission_type IS NULL OR permission_type <> 3;
UPDATE action_step SET role_id = null WHERE permission_type IS NULL OR permission_type <> 2;

