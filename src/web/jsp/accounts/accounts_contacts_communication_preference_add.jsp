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
<%@ page import="java.util.*,java.sql.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
function checkForm(form) {
  if (form.typeId.value == '-1') {
    alert('Please select a Communication Type');
    return false;
  }
  return true;
}
</script>

<form name="addPreference" method="post" action="Contacts.do?command=SaveCommunicationsPreference&contactId=<%=ContactDetails.getId()%>" onSubmit="return checkForm(this);">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">Add a Communications Preference</th>
  </tr>
  <tr>
    <td class="formLabel">Preference Level</td>
    <td><%= CommunicationsPreference.getHtmlSelectLevel("level",1,5) %></td>
  </tr>
  <tr>
    <td class="formLabel">Type</td>
    <td><%= typeSelect.getHtmlSelectDefaultNone("typeId") %><font color="red">*</font> <%= showAttribute(request, "typeIdError") %></td>
  </tr>
  <tr>
    <td class="formLabel">Day Range</td>
    <td>
      <%= CommunicationsPreference.getHtmlSelect("startDay",2) %> 
      to
      <%= CommunicationsPreference.getHtmlSelect("endDay", 2) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">Time Range</td>
    <td>
       <zeroio:timeSelect baseName="startTime" value="<%= new Timestamp(Calendar.getInstance().getTimeInMillis()) %>"  timeZone="<%= User.getTimeZone() %>"/>
        to
        <zeroio:timeSelect baseName="endTime" value="<%= new Timestamp(Calendar.getInstance().getTimeInMillis()) %>"  timeZone="<%= User.getTimeZone() %>"/>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Time Zone
    </td>
    <td>
      <zeroio:timeSelect baseName="timeZone" value="<%= new Timestamp(Calendar.getInstance().getTimeInMillis()) %>"  timeZone="<%= User.getTimeZone() %>"/>
    </td>
  </tr>
</table>
<br />
<input type="submit" value="Save">
</form>

