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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.relationships.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="relationship" class="org.aspcfs.modules.relationships.base.Relationship" scope="request"/>
<jsp:useBean id="TypeList" class="org.aspcfs.modules.relationships.base.RelationshipTypeList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.relTypeId.selectedIndex == -1){
      message += "- A relationship type needs to be selected\r\n";
      formTest = false;
    }
    if (form.objectIdMapsTo.value == -1){
      message += "- A provider needs to be selected to form the relationship\r\n";
      formTest = false;
    }
    if (formTest == false) {
      alert("Could not create the relationship, please check the following:\r\n\r\n" + message);
      return false;
    }
    return true;
  }
</script>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search">Search Results</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard">Dashboard</a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="AccountRelationships.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Relationships</a> >
  Add Relationship
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="relationships" param="<%= param1 %>" style="tabs"/>
<form name="addRelation" action="AccountRelationships.do?command=Save&auto-populate=true" method="post"  onSubmit="return doCheck(this);">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td class="containerBack">
      <br><%= showError(request, "objectIdError") %>
      <table cellpadding="4" cellspacing="0" border="0" class="details">
        <tr>
          <th valign="center" align="left" nowrap>
            <strong>Relationship From...</strong>
          </th>
          <th valign="center" align="left" nowrap>
            <strong>Relationship Type</strong>
          </th>
          <th valign="center" align="left" nowrap>
            <strong>Relationship To...</strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top" align="left" nowrap>
            <%-- Mapping Account --%>
            <%= toHtml(OrgDetails.getName()) %>
          </td>
          <td class="formLabel" valign="top" align="center">
          <%-- Relation --%>
            <%
              TypeList.setSize(10);
              HtmlSelect htmlSelect = TypeList.getHtmlSelect();
            %>
            <%= htmlSelect.getHtml("relTypeId", relationship.getTypeId()) %>
          </td>
          <td class="formLabel" valign="top" nowrap>
            <%-- Select mapped entity --%>
            <table cellspacing="0" cellpadding="0" border="0" class="empty">
              <tr>
                <td nowrap>
                  <div id="changeaccount"><%= relationship.getObjectIdMapsTo() != -1 ? "Infosys Inc" : "None Selected" %></div>
                </td>
                <td nowrap>
                  <input type="hidden" name="objectIdMapsTo" id="objectIdMapsTo" value="<%=  relationship.getObjectIdMapsTo() %>">
                  &nbsp;<font color="red">-</font>
                  [<a href="javascript:popAccountsListSingle('objectIdMapsTo','changeaccount', 'showMyCompany=true&filters=all|my');">Select <dhv:label name="accounts.account">Account</dhv:label></a>]
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" width="100%"><tr><td>&nbsp;</td></tr></table>
      <br>
      <input type="submit" value="Create Relationship" onClick="this.form.dosubmit.value='true';">
     </td>
   </tr>
</table>
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<input type="hidden" name="categoryIdMapsTo" value="<%= Constants.ACCOUNT_OBJECT
%>">
<input type="hidden" name="objectIdMapsFrom" value="<%= OrgDetails.getOrgId() %>">
</form>
