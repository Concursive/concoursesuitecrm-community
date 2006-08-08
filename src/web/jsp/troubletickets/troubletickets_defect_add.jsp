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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*, org.aspcfs.modules.base.EmailAddress " %>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function checkForm(form) {
    message = '';
    formTest = true;
    if (checkNullString(form.title.value)) { 
      message += label("title.required","- Title is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<body onLoad="document.addDefect.title.focus()">
<form name="addDefect" method="post" action="TroubleTicketDefects.do?command=Save&auto-populate=true" onSubmit="javascript:return checkForm(this);">
<input type="hidden" name="id" value="<%= defect.getId() %>"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
  <a href="TroubleTicketDefects.do?command=View"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> > 
<dhv:evaluate if="<%= defect.getId() > -1 %>">
  <dhv:evaluate if="<%= request.getParameter("return") == null || !request.getParameter("return").equals("list") %>">
    <a href="TroubleTicketDefects.do?command=Details&defectId=<%= defect.getId() %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> > 
  </dhv:evaluate>
  <dhv:label name="tickets.defects.modifyDefect">Modify Defect</dhv:label>
</dhv:evaluate><dhv:evaluate if="<%= defect.getId() == -1 %>">
  <dhv:label name="tickets.defects.addDefect">Add Defect</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= defect.getId() > -1 %>">
<dhv:permission name="tickets-defects-edit"><input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>"></dhv:permission> 
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='TroubleTicketDefects.do?command=Details&defectId=<%= defect.getId() %>';" />
</dhv:evaluate><dhv:evaluate if="<%= defect.getId() == -1 %>">
<dhv:permission name="tickets-defects-add"><input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>"></dhv:permission> 
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='TroubleTicketDefects.do?command=View';" />
</dhv:evaluate>
<br />
<dhv:formMessage showSpace="true"/>
<%-- Defect Form --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>
        <dhv:evaluate if="<%= defect.getId() > -1 %>"><dhv:label name="tickets.defects.modifyDefect">Modify Defect</dhv:label></dhv:evaluate>
        <dhv:evaluate if="<%= defect.getId() == -1 %>"><dhv:label name="tickets.defects.addDefect">Add Defect</dhv:label></dhv:evaluate>
      </strong>
    </th>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
    </td>
    <td><input type="text" size="58" maxlength="255" name="title" value="<%= toHtmlValue(defect.getTitle()) %>"/><font color="red">*</font><%= showAttribute(request, "titleError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td><textarea rows="4" cols="35" name="description"><%= toString(defect.getDescription()) %></textarea></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="enabled" value="true" <%= defect.getEnabled()?"checked":"" %> />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="product.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addDefect" field="startDate" timestamp="<%= defect.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" /><font color="red">*</font>
      <%=showAttribute(request,"startDateError")%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="reports.parameter.endDate">End Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addDefect" field="endDate" timestamp="<%= defect.getEndDate() %>" timeZone="<%= User.getTimeZone() %>" />
      <%=showAttribute(request,"endDateError")%>
    </td>
  </tr>
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= User.getSiteId() == -1 && defect.getId() == -1 %>" >
        <%= SiteList.getHtmlSelect("siteId",defect.getSiteId()) %><font color="red">*</font>
        <%= showAttribute(request, "siteIdError") %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= User.getSiteId() != -1 || defect.getId() > -1 %>" >
         <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId() != -1? User.getUserRecord().getSiteId():defect.getSiteId()) %>
        <input type="hidden" name="siteId" value="<%= User.getUserRecord().getSiteId() != -1? User.getUserRecord().getSiteId():defect.getSiteId() %>" >
      </dhv:evaluate>
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</table>
<br />
<dhv:evaluate if="<%= defect.getId() > -1 %>">
<dhv:permission name="tickets-defects-edit"><input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>"></dhv:permission>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='TroubleTicketDefects.do?command=Details&defectId=<%= defect.getId() %>';" />
</dhv:evaluate><dhv:evaluate if="<%= defect.getId() == -1 %>">
<dhv:permission name="tickets-defects-edit"><input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>"></dhv:permission>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='TroubleTicketDefects.do?command=View';" />
</dhv:evaluate> 
</form>
</body>