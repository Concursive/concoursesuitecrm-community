-- Cleanup after projects has been modified

ALTER TABLE project_team ADD CONSTRAINT project_team_userlevel_not_null CHECK(userlevel IS NOT NULL);

ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$5" FOREIGN KEY (userlevel) REFERENCES lookup_project_role(code) ON UPDATE NO ACTION ON DELETE NO ACTION;

