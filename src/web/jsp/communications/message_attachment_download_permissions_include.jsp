<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: message_attachment_download_permissions_include.jsp  18212 2007-01-04 16:57:55Z zhenya.zhidok@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.documents.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.accounts.base.*" %>

 <dhv:evaluate if="<%= thisFile.getLinkModuleId()==Constants.CONTACTS %>">
   <dhv:permission name="accounts-accounts-contacts-documents-view"> 
    <% hasPermissionDownload = true; %>
   </dhv:permission>  
 </dhv:evaluate>
 
  <dhv:evaluate if="<%= thisFile.getLinkModuleId()==Constants.ACCOUNTS %>">
   <dhv:permission name="accounts-accounts-documents-view"> 
    <% hasPermissionDownload = true; %>
   </dhv:permission>  
 </dhv:evaluate>
 
  <dhv:evaluate if="<%= thisFile.getLinkModuleId()==Constants.DOCUMENTS_DOCUMENTS %>">
   <dhv:permission name="documents-view"> 
    <% hasPermissionDownload = true; %>
   </dhv:permission>  
 </dhv:evaluate>
 
  <dhv:evaluate if="<%= thisFile.getLinkModuleId()==Constants.PROJECTS_FILES %>">
   <dhv:permission name="projects-view"> 
    <% hasPermissionDownload = true; %>
   </dhv:permission>   
 </dhv:evaluate>
