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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="helpModule" class="org.aspcfs.modules.help.base.HelpModule" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <%-- Title --%>
  <tr>
  <td>
    <strong><dhv:label name="help.moduleName.colon">Module Name:</dhv:label>
    <% if((helpModule.getModuleName() == null) || ("".equals(helpModule.getModuleName()))) {%>
      <dhv:label name="account.notSpecified.label">Not Specified</dhv:label>
    <%} else {%>
      <%= toHtml(helpModule.getModuleName()) %>
    <%}%></strong>
  <dhv:evaluate if="<%= hasText(helpModule.getModuleName()) %>">
    <dhv:permission name="qa-edit">[<a href="javascript:popURL('Help.do?command=ModifyDescription&id=<%= helpModule.getId() %>&action=<%= helpModule.getRelatedAction()%>&popup=true', 'Help_Description','700','500','yes','yes');"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>]</td></dhv:permission>
  </dhv:evaluate>
  </tr>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
       <th>
          <strong><dhv:label name="help.briefDescription">Brief Description</dhv:label></strong> 
       </th>
      </tr>
       <tr>
         <td>
          <%=toHtml(helpModule.getBriefDescription())%>
        </td>
       </tr>
     </table><br>
    </td>
  </tr>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
       <th>
          <strong><dhv:label name="help.detailDescription">Detail Description</dhv:label></strong>
       </th>
      </tr>
       <tr>
         <td>
          <%=toHtml(helpModule.getDetailDescription())%>
        </td>
       </tr>
     </table><br>
    </td>
  </tr>
  </table>
