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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="Revenue" class="org.aspcfs.modules.accounts.base.Revenue" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<form name="listRevenue" action="RevenueManager.do" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="RevenueManager.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Revenue</a> >
Revenue Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="revenue" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-revenue-edit"><input type='button' value="Modify" onClick="javascript:this.form.action='RevenueManager.do?command=Modify&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-revenue-delete"><input type='button' value="Delete" onClick="javascript:this.form.action='RevenueManager.do?command=Delete&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';confirmSubmit(this.form);"></dhv:permission>
<dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete"><br>&nbsp;</dhv:permission>
<input type="hidden" name="type" value="<%= Revenue.getType() %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong><%= toHtml(Revenue.getDescription()) %></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Owner
    </td>
    <td>
      <dhv:username id="<%= Revenue.getOwner() %>" />
      <dhv:evaluate exp="<%= !(Revenue.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Type
    </td>
    <td>
      <%= toHtml(Revenue.getTypeName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Month
    </td>
    <td>
      <%= toHtml(Revenue.getMonthName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Year
    </td>
    <td>
      <%= Revenue.getYear() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Amount
    </td>
    <td>
      <zeroio:currency value="<%= Revenue.getAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Entered
    </td>
    <td>
      <dhv:username id="<%= Revenue.getEnteredBy() %>" />
      -
      <%= Revenue.getEnteredDateTimeString() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Modified
    </td>
    <td>
      <dhv:username id="<%= Revenue.getModifiedBy() %>" />
      -
      <%= Revenue.getModifiedDateTimeString() %>
    </td>
  </tr>
</table>
<br>
<dhv:permission name="accounts-accounts-revenue-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='RevenueManager.do?command=Modify&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-revenue-delete"><input type="button" value="Delete" onClick="javascript:this.form.action='RevenueManager.do?command=Delete&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';confirmSubmit(this.form);"></dhv:permission>
</td>
</tr>
</table>
</form>
