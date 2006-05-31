UPDATE action_plan_work SET current_phase = NULL where current_phase = -1;
ALTER TABLE action_plan_work ADD CONSTRAINT "fk_current_phase" FOREIGN KEY (current_phase) REFERENCES action_phase(phase_id);
