<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="Revenue" class="com.darkhorseventures.cfsbase.Revenue" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="com.darkhorseventures.cfsbase.RevenueTypeList" scope="request"/>
<jsp:useBean id="MonthList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="YearList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="modify" action="/RevenueManager.do?command=Update&auto-populate=true&orgId=<%=Revenue.getOrgId()%>" method="post">
<a href="/RevenueManager.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Back to Revenue List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="revenue" param="<%= param1 %>" />
    </td>
  </tr>
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
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='/RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>'">
	<%}%>
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/RevenueManager.do?command=Details&id=<%= Revenue.getId() %>'">
<%}%>
<input type="reset" value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Modify <%= toHtml(Revenue.getDescription()) %></strong>
    </td>
  </tr>
  
	<tr class="containerBody">
	<td width=125 nowrap class="formLabel">
	Reassign To
	</td>
	<td colspan=1 valign=center>
	<%= UserList.getHtmlSelect("owner", Revenue.getOwner() ) %>
	</td>
	</tr>
  
  <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Revenue Type
    </td>
    <td valign=center>
      <%= RevenueTypeList.getHtmlSelect("type", Revenue.getType()) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Description
    </td>
    <td valign=center>
      <input type=text size=40 name="description" value="<%=toHtml(Revenue.getDescription())%>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Month
    </td>
    <td valign=center>
      <% MonthList.setDefaultValue("" + Revenue.getMonth()); %>
      <%= MonthList.getHtml() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Year
    </td>
    <td valign=center>
      <% YearList.setDefaultValue("" + Revenue.getYear()); %>
      <%= YearList.getHtml() %>
      <font color="red">*</font> <%= showAttribute(request, "yearError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Amount
    </td>
    <td valign=center>
      <input type=text size=15 name="amount" value="<%=Revenue.getAmountValue()%>">
      <font color="red">*</font> <%= showAttribute(request, "amountError") %>
    </td>
  </tr>
</table>
<input type="submit" value="Update" name="Save">
<% if (request.getParameter("return") != null && request.getParameter("return").equals("list")) {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>'">
<%} else {%>
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/RevenueManager.do?command=Details&id=<%= Revenue.getId() %>'">
<%}%>

<input type="reset" value="Reset">
  </td>
  </tr>
  </table>
</form>
