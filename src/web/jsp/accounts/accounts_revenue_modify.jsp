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
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="RevenueManager.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Revenue</a> >
<% if (request.getParameter("return") == null) {%>
	<a href="RevenueManager.do?command=Details&id=<%=Revenue.getId()%>">Revenue Details</a> >
<%}%>
Modify Revenue
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
      <input type="hidden" name="id" value="<%= Revenue.getId() %>">
      <input type="hidden" name="modified" value="<%= Revenue.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
      <input type="submit" value="Update" name="Save">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>'">
	<%}%>
<%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='RevenueManager.do?command=Details&id=<%= Revenue.getId() %>'">
<%}%>
      <br>
      <%= showError(request, "actionError") %>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Modify <%= toHtml(Revenue.getDescription()) %></strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Reassign To
          </td>
          <td>
            <%= UserList.getHtmlSelect("owner", Revenue.getOwner() ) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Revenue Type
          </td>
          <td>
            <%= RevenueTypeList.getHtmlSelect("type", Revenue.getType()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Description
          </td>
          <td>
            <input type="text" size="40" name="description" value="<%=toHtml(Revenue.getDescription())%>">
            <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Month
          </td>
          <td>
            <% MonthList.setDefaultValue(String.valueOf(Revenue.getMonth())); %>
            <%= MonthList.getHtml() %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Year
          </td>
          <td>
            <% YearList.setDefaultValue(String.valueOf(Revenue.getYear())); %>
            <%= YearList.getHtml() %>
            <font color="red">*</font> <%= showAttribute(request, "yearError") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Amount
          </td>
          <td>
            <input type="text" size="15" name="amount" value="<%= Revenue.getAmountValue() %>">
            <font color="red">*</font> <%= showAttribute(request, "amountError") %>
          </td>
        </tr>
      </table>
      &nbsp;<br>
      <input type="submit" value="Update" name="Save">
<% if (request.getParameter("return") != null && request.getParameter("return").equals("list")) {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>'">
<%} else {%>
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='RevenueManager.do?command=Details&id=<%= Revenue.getId() %>'">
<%}%>
    </td>
  </tr>
</table>
</form>
