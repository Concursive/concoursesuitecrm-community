INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 1, 'Project Initialization');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 2, 'Analysis/Software Requirements');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 3, 'Functional Specifications');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 4, 'Prototype');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 5, 'System Development');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 6, 'Testing');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 7, 'Training');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 8, 'Documentation');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 9, 'Deployment');
INSERT INTO lookup_project_activity (group_id, level, description) VALUES (1, 10, 'Post Implementation Review');

INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (1, 1, 'Not Started', 1, 'box.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (1, 2, 'In Progress', 2, 'box.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (1, 5, 'On Hold', 5, 'box-hold.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (1, 6, 'Waiting on Reqs', 5, 'box-hold.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (1, 3, 'Complete', 3, 'box-checked.gif');
INSERT INTO lookup_project_status (group_id, level, description, type, graphic) VALUES
  (1, 4, 'Closed', 4, 'box-checked.gif');
  
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (1, 1, 'Minute(s)', @FALSE@, 60);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (1, 1, 'Hour(s)', @TRUE@, 3600);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (1, 1, 'Day(s)', @FALSE@, 86400);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (1, 1, 'Week(s)', @FALSE@, 604800);
INSERT INTO lookup_project_loe (group_id, level, description, default_item, base_value) VALUES (1, 1, 'Month(s)', @FALSE@, 18144000);

INSERT INTO lookup_project_priority (group_id, level, description, type) VALUES (1, 1, 'Low', 10);
INSERT INTO lookup_project_priority (group_id, level, description, type, default_item) VALUES (1, 2, 'Normal', 20, @TRUE@);
INSERT INTO lookup_project_priority (group_id, level, description, type) VALUES (1, 3, 'High', 30);

INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 10, 'Project Lead');
INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 20, 'Contributor');
INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 30, 'Observer');
INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 100, 'Guest');

/* Permissions */
INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 10, 'Project Details');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 1, 10, 4, 'project-details-view', 'View project details');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 1, 20, 1, 'project-details-edit', 'Modify project details');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 1, 30, 1, 'project-details-delete', 'Delete project');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 20, 'Team Members');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 10, 4, 'project-team-view', 'View team members');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 20, 3, 'project-team-view-email', 'See team member email addresses');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 30, 1, 'project-team-edit', 'Modify team');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 40, 1, 'project-team-edit-role', 'Modify team member role');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 30, 'News');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 10, 4, 'project-news-view', 'View current news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 20, 2, 'project-news-view-unreleased', 'View unreleased news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 30, 3, 'project-news-view-archived', 'View archived news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 40, 2, 'project-news-add', 'Add news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 50, 2, 'project-news-edit', 'Edit news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 60, 1, 'project-news-delete', 'Delete news');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 40, 'Plan/Outlines');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 10, 4, 'project-plan-view', 'View outlines');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 20, 1, 'project-plan-outline-add', 'Add an outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 40, 1, 'project-plan-outline-edit', 'Modify details of an existing outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 50, 1, 'project-plan-outline-delete', 'Delete an outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 60, 1, 'project-plan-outline-modify', 'Make changes to an outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 70, 1, 'project-plan-activities-assign', 'Re-assign activities');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 50, 'Lists');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 10, 4, 'project-lists-view', 'View lists');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 20, 2, 'project-lists-add', 'Add a list');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 30, 2, 'project-lists-edit', 'Modify details of an existing list');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 40, 1, 'project-lists-delete', 'Delete a list');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 50, 2, 'project-lists-modify', 'Make changes to list items');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 60, 'Discussion');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 10, 4, 'project-discussion-forums-view', 'View discussion forums');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 20, 1, 'project-discussion-forums-add', 'Add discussion forum');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 30, 1, 'project-discussion-forums-edit', 'Modify discussion forum');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 40, 1, 'project-discussion-forums-delete', 'Delete discussion forum');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 50, 4, 'project-discussion-topics-view', 'View forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 60, 2, 'project-discussion-topics-add', 'Add forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 70, 2, 'project-discussion-topics-edit', 'Modify forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 80, 2, 'project-discussion-topics-delete', 'Delete forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 90, 3, 'project-discussion-messages-add', 'Post messages');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 100, 3, 'project-discussion-messages-reply', 'Reply to messages');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 110, 2, 'project-discussion-messages-edit', 'Modify existing messages');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 120, 2, 'project-discussion-messages-delete', 'Delete messages');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 70, 'Tickets');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 10, 4, 'project-tickets-view', 'View tickets');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 20, 3, 'project-tickets-add', 'Add a ticket');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 30, 2, 'project-tickets-edit', 'Modify existing ticket');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 40, 1, 'project-tickets-delete', 'Delete tickets');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 50, 1, 'project-tickets-assign', 'Assign tickets');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 80, 'Document Library');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 10, 4, 'project-documents-view', 'View documents');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 20, 1, 'project-documents-folders-add', 'Create folders');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 30, 1, 'project-documents-folders-edit', 'Modify folders');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 40, 1, 'project-documents-folders-delete', 'Delete folders');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 50, 2, 'project-documents-files-upload', 'Upload files');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 60, 4, 'project-documents-files-download', 'Download files');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 70, 2, 'project-documents-files-rename', 'Rename files');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 80, 1, 'project-documents-files-delete', 'Delete files');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 90, 'Setup');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 9, 10, 1, 'project-setup-customize', 'Customize project features');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 9, 20, 1, 'project-setup-permissions', 'Configure project permissions');

