<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="Revenue" class="com.darkhorseventures.cfsbase.Revenue" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listRevenue" action="/RevenueManager.do" method="post">
<a href="RevenueManager.do?command=View&orgId=<%= Revenue.getOrgId() %>">Back to Revenue List</a><br>&nbsp;
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
<dhv:permission name="accounts-accounts-revenue-edit"><input type='button' value="Modify" onClick="javascript:this.form.action='/RevenueManager.do?command=Modify&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-revenue-delete"><input type='button' value="Delete" onClick="javascript:this.form.action='/RevenueManager.do?command=Delete&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';confirmSubmit(this.form);"></dhv:permission>
<dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete"><br>&nbsp;</dhv:permission>
<input type=hidden name="type" value="<%=Revenue.getType()%>">

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=3 valign=center align=left>
      <strong><%= toHtml(Revenue.getDescription()) %></strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td width=125 class="formLabel">
      Owner
    </td>
    <td>
      <%= toHtml(Revenue.getOwnerName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=125 class="formLabel">
      Type
    </td>
    <td>
      <%= toHtml(Revenue.getTypeName()) %>
    </td>
  </tr>
    <tr class="containerBody">
    <td width=125 class="formLabel">
      Month
    </td>
    <td>
      <%= Revenue.getMonthName() %>
    </td>
  </tr>
      <tr class="containerBody">
    <td width=125 class="formLabel">
      Year
    </td>
    <td>
      <%= Revenue.getYear() %>
    </td>
  </tr>
      <tr class="containerBody">
    <td width=125 class="formLabel">
      Amount
    </td>
    <td>
      $<%= Revenue.getAmountCurrency() %>
    </td>
  </tr>
    <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Entered
    </td>
    <td>
      <%= Revenue.getEnteredByName() %>&nbsp;-&nbsp;<%= Revenue.getEnteredDateTimeString() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width=125 nowrap class="formLabel">
      Modified
    </td>
    <td>
      <%= Revenue.getModifiedByName() %>&nbsp;-&nbsp;<%= Revenue.getModifiedDateTimeString() %>
    </td>
  </tr>

</table>
<br>
<dhv:permission name="accounts-accounts-revenue-edit"><input type='button' value="Modify" onClick="javascript:this.form.action='/RevenueManager.do?command=Modify&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';submit();"></dhv:permission>
<dhv:permission name="accounts-accounts-revenue-delete"><input type='button' value="Delete" onClick="javascript:this.form.action='/RevenueManager.do?command=Delete&id=<%=Revenue.getId()%>&orgId=<%=Revenue.getOrgId()%>';confirmSubmit(this.form);"></dhv:permission>
</td>
  </tr>
</table>
</form>
