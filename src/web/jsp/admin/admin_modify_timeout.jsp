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
<form name="modifyTimeout" action="AdminConfig.do?command=UpdateTimeout" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a> >
<dhv:label name="admin.modifyTimeout">Modify Timeout</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><b><dhv:label name="admin.timeout.question">What should the session timeout be for users?</dhv:label></b><br />
<dhv:label name="admin.timeout.text">The session timeout is the time in which a user will automatically be logged out if the specified period of inactivity is reached.</dhv:label>
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.modifyTimeout">Modify Timeout</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="admin.timeout">Timeout</dhv:label> 
      </td>
      <%  String[] timeouts = { "5", "10", "15", "30", "45", "60", "90"} ;
          String currentTimeout = request.getParameter("timeout");
      %>
      <td align="left">
         <select size="1" name="timeout">
          <% for(int i = 0 ; i < timeouts.length; i++){ %>
            <option value="<%=timeouts[i]%>" <%= currentTimeout.equals(timeouts[i])?" selected":"" %>><%= timeouts[i] %></option>
          <%}%>
         </select> <dhv:label name="admin.minutes">minutes</dhv:label>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
