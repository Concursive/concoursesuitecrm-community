INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 1, 'Project Initialization');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 2, 'Analysis/Software Requirements');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 3, 'Functional Specifications');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 4, 'Prototype');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 5, 'System Development');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 6, 'Testing');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 7, 'Training');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 8, 'Documentation');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 9, 'Deployment');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (0, 10, 'Post Implementation Review');

INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Status Update');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Bug Report');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Network');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Hardware');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Permissions');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'User');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Documentation');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Feature');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Procedure');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Training');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, '3rd-Party Software');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Database');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Information');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Testing');
INSERT INTO lookup_project_issues (group_id, description) VALUES (0, 'Security');
 
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (0, 1, 'Not Started', 1, 'box.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (0, 2, 'In Progress', 2, 'box.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (0, 5, 'On Hold', 5, 'box-hold.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (0, 6, 'Waiting on Reqs', 5, 'box-hold.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (0, 3, 'Complete', 3, 'box-checked.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (0, 4, 'Closed', 4, 'box-checked.gif');
  
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (0, 1, 'Minute(s)', false, 60);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (0, 1, 'Hour(s)', true, 3600);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (0, 1, 'Day(s)', false, 86400);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (0, 1, 'Week(s)', false, 604800);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (0, 1, 'Month(s)', false, 18144000);

INSERT INTO lookup_project_priority (group_id, level, description, type) VALUES (0, 1, 'Low', 10);
INSERT INTO lookup_project_priority (group_id, level, description, type, default_item) VALUES (0, 2, 'Normal', 20, true);
INSERT INTO lookup_project_priority (group_id, level, description, type) VALUES (0, 3, 'High', 30);

