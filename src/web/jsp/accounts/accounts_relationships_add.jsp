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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.relationships.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="secondOrganization" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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
      message += label("relationshiptype.needs.selected", "- A relationship type needs to be selected\r\n");
      formTest = false;
    }
    if (form.objectIdMapsTo.value == -1){
      message += label("provider.needs.relationship", "- A provider needs to be selected to form the relationship\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("no.create.relationship", "Could not create the relationship, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<form name="addRelation" action="AccountRelationships.do?command=Save&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post"  onSubmit="return doCheck(this);">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> >
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="AccountRelationships.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_relationships_add.Relationships">Relationships</dhv:label></a> >
<dhv:label name="accounts.accounts_relationships_add.AddRelationship">Add Relationship</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
  <dhv:container name="accounts" selected="relationships" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:formMessage showSpace="false" />
    <table cellpadding="4" cellspacing="0" border="0" class="details">
      <tr>
        <th valign="center" align="left" nowrap>
          <strong><dhv:label name="accounts.accounts_relationships_add.RelationshipFrom">Relationship From...</dhv:label></strong>
        </th>
        <th valign="center" align="left" nowrap>
          <strong><dhv:label name="accounts.accounts_relationships_add.RelationshipType">Relationship Type</dhv:label></strong>
        </th>
        <th valign="center" align="left" nowrap>
          <strong><dhv:label name="accounts.accounts_relationships_add.RelationshipTo">Relationship To...</dhv:label></strong>
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
          <%= htmlSelect.getHtml("relTypeId", (secondOrganization.getOrgId() != -1 && relationship.getObjectIdMapsFrom()==secondOrganization.getOrgId()?relationship.getTypeId()+"_reciprocal":""+relationship.getTypeId())) %>
        </td>
        <td class="formLabel" valign="top" nowrap>
          <%-- Select mapped entity --%>
          <table cellspacing="0" cellpadding="0" border="0" class="empty">
            <tr>
              <td nowrap>
                <div id="changeaccount">
                  <% if(relationship.getObjectIdMapsTo() != -1) {%>
                    <%= toHtml(secondOrganization.getName()) %>
                  <%} else {%>
                    <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                  <%}%>
                </div>
              </td>
              <td nowrap>
                <input type="hidden" name="objectIdMapsTo" id="objectIdMapsTo" value="<%=  (secondOrganization.getOrgId() != -1 && relationship.getObjectIdMapsFrom()==secondOrganization.getOrgId()?relationship.getObjectIdMapsFrom():relationship.getObjectIdMapsTo()) %>">
                &nbsp;<font color="red">-</font>
                [<a href="javascript:popAccountsListSingle('objectIdMapsTo','changeaccount', 'showMyCompany=false&siteId=<%=OrgDetails.getSiteId()%>&thisSiteIdOnly=true&filters=all|my');"><dhv:label name="accounts.accounts_relationships_add.SelectAccount">Select Account</dhv:label></a>]
              </td>
              <td nowrap>
                <%= showAttribute(request, "objectIdMapsToError") %>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <br>
    <input type="submit" value="<dhv:label name="global.button.CreateRelationship">Create Relationship</dhv:label>" onClick="this.form.dosubmit.value='true';" />
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountRelationships.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>';" />
    <input type="hidden" name="dosubmit" value="true" />
    <input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
    <input type="hidden" name="categoryIdMapsTo" value="<%= Constants.ACCOUNT_OBJECT %>" />
    <input type="hidden" name="objectIdMapsFrom" value="<%= OrgDetails.getOrgId() %>" />
  </dhv:container>
</form>
