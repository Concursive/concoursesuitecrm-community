<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="Revenue" class="com.darkhorseventures.cfsbase.Revenue" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="com.darkhorseventures.cfsbase.RevenueTypeList" scope="request"/>
<jsp:useBean id="MonthList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="YearList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addRevenue" action="/RevenueManager.do?command=Insert&auto-populate=true" method="post">
<a href="/Accounts.do">Account Management</a> > 
<a href="/Accounts.do?command=View">View Accounts</a> >
<a href="/Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="/RevenueManager.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Revenue</a> >
Add Revenue<br>
<hr color="#BFBFBB" noshade>
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
<input type="hidden" name="orgId" value="<%= request.getParameter("orgId") %>">
<input type=submit value="Save">
<input type=reset value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Add a New Revenue</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Type
    </td>
    <td valign=center>
      <%=RevenueTypeList.getHtmlSelect("type", Revenue.getType())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Description
    </td>
    <td valign=center>
      <input type=text size=40 name="description" value="<%= toString(Revenue.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Month
    </td>
    <td valign=center>
      <%= MonthList.getHtml() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Year
    </td>
    <td valign=center>
      <%= YearList.getHtml() %>
      <font color="red">*</font> <%= showAttribute(request, "yearError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Amount
    </td>
    <td valign=center>
      <input type=text size=15 name="amount" value="<%=Revenue.getAmountValue()%>">
      <font color="red">*</font> <%= showAttribute(request, "amountError") %>
    </td>
  </tr>
  
</table>
<br>
<input type=submit value="Save">
<input type=reset value="Reset">
    </td>
  </tr>
</table>
</form>
</body>
