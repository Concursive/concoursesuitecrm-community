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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="Revenue" class="org.aspcfs.modules.accounts.base.Revenue" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="org.aspcfs.modules.accounts.base.RevenueTypeList" scope="request"/>
<jsp:useBean id="MonthList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="YearList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modify" action="RevenueManager.do?command=Update&auto-populate=true&orgId=<%=Revenue.getOrgId()%>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="RevenueManager.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label></a> >
<% if (request.getParameter("return") == null) {%>
	<a href="RevenueManager.do?command=Details&id=<%=Revenue.getId()%>"><dhv:label name="accounts.accounts_revenue_details.RevenueDetails">Revenue Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_revenue_modify.ModifyRevenue">Modify Revenue</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="revenue" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
      <input type="hidden" name="id" value="<%= Revenue.getId() %>">
      <input type="hidden" name="modified" value="<%= Revenue.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" />
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>'" />
	<%}%>
<%} else {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='RevenueManager.do?command=Details&id=<%= Revenue.getId() %>'" />
<%}%>
      <br />
      <dhv:formMessage />
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="button.modify">Modify</dhv:label> <%= toHtml(Revenue.getDescription()) %></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="accounts.accounts_revenue_modify.ReassignTo">Reassign To</dhv:label>
          </td>
          <td>
            <%= UserList.getHtmlSelect("owner", Revenue.getOwner() ) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="accounts.accounts_revenue_modify.RevenueType">Revenue Type</dhv:label>
          </td>
          <td>
            <%= RevenueTypeList.getHtmlSelect("type", Revenue.getType()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
          </td>
          <td>
            <input type="text" size="40" name="description" value="<%=toHtml(Revenue.getDescription())%>">
            <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="accounts.accounts_revenue_add.Month">Month</dhv:label>
          </td>
          <td>
            <% MonthList.setDefaultValue(String.valueOf(Revenue.getMonth())); %>
            <%= MonthList.getHtml() %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="accounts.accounts_revenue_add.Year">Year</dhv:label>
          </td>
          <td>
            <% YearList.setDefaultValue(String.valueOf(Revenue.getYear())); %>
            <%= YearList.getHtml() %>
            <font color="red">*</font> <%= showAttribute(request, "yearError") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="accounts.accounts_revenue_add.Amount">Amount</dhv:label>
          </td>
          <td>
            <input type="text" size="15" name="amount" value="<%= Revenue.getAmountValue() %>">
            <font color="red">*</font> <%= showAttribute(request, "amountError") %>
          </td>
        </tr>
      </table>
      &nbsp;<br />
      <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" />
<% if (request.getParameter("return") != null && request.getParameter("return").equals("list")) {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>'" />
<%} else {%>
      <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='RevenueManager.do?command=Details&id=<%= Revenue.getId() %>'" />
<%}%>
</dhv:container>
</form>
