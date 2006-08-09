/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.admin.base;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Achyutha
 * @version $Id: $
 * @created June 16, 2006
 */
public class PopulatePortalRolePermissionList {

	private PermissionList permissionList = null;

	public PopulatePortalRolePermissionList() {
		permissionList = new PermissionList();
	}

	public PermissionList populatePortalPermissions(Connection db)
			throws SQLException {
		buildAccountPermissions(db);
		buildProjectPermissions(db);
		buildDocumentPermissions(db);
		return permissionList;
	}

	public void buildAccountPermissions(Connection db) throws SQLException {
		addPermission(db, "accounts", true);
		addPermission(db, "accounts-accounts", true);
		addPermission(db, "accounts-accounts-contacts", true);
		addPermission(db, "accounts-service-contracts", true);
		addPermission(db, "accounts-assets", true);
		addPermission(db, "accounts-accounts-tickets", true, true, false, false);
		addPermission(db, "accounts-accounts-tickets-maintenance-report", true);
		addPermission(db, "accounts-accounts-tickets-activity-log", true);
		addPermission(db, "accounts-accounts-documents", true, true, false, false);
		addPermission(db, "accounts-accounts-shareddocuments", true);

	}

	public void buildProjectPermissions(Connection db) throws SQLException {
		addPermission(db, "projects", true);
		addPermission(db, "projects-personal", true);
		addPermission(db, "projects-enterprise", true);
		addPermission(db, "projects-projects", true);

	}

	public void buildDocumentPermissions(Connection db) throws SQLException {
		addPermission(db, "documents", true);
		addPermission(db, "documents_documentstore", true);

	}

	public void addPermission(Connection db, String permission, boolean view)
			throws SQLException {
		addPermission(db, permission, view, false, false, false);
	}

	public void addPermission(Connection db, String name, boolean view,
			boolean add, boolean edit, boolean delete) throws SQLException {
		Permission permission = new Permission(db, name);
		if (permission != null) {
			permission.setView(view);
			permission.setEdit(edit);
			permission.setAdd(add);
			permission.setDelete(delete);
			permissionList.add(permission);
		}
	}

}
