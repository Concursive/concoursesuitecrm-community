create table help_introduction_preference (
		user_id INT NOT NULL REFERENCES access(user_id),
		module_id INT NOT NULL REFERENCES help_module(module_id)
);
