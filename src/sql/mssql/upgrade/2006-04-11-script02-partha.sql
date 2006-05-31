UPDATE ticket SET user_group_id = NULL WHERE trashed_date IS NOT NULL;
