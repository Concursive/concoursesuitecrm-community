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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="contacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<form name="modifyOpp" action="Opportunities.do?command=Update&orgId=<%= OrgDetails.getId() %>&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Opportunities.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></a> >
<% if (request.getParameter("return") == null) {%>
	<a href="Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<%}%>
<dhv:label name="accounts.accounts_contacts_opps_modify.ModifyOpportunity">Modify Opportunity</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="opportunities" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
  <input type="hidden" name="modified" value="<%= opportunityHeader.getModified() %>">
<% if (request.getParameter("return") != null) {%>
  <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';">
<% if (request.getParameter("return") != null) {%>
<% if (request.getParameter("return").equals("list")) {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<%}%>
<%} else {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
<%}%>
  <br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><%= opportunityHeader.getDescription() %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="description" value="<%= toHtmlValue(opportunityHeader.getDescription()) %>">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
    </td>
  </tr>
  <dhv:permission name="accounts-accounts-contacts-move-view">
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="account.opportunities.associateWith">Associate With</dhv:label>
    </td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
              <td>
                <input type="radio" name="type" value="org"  onclick=<%= "\"javascript:document.forms['modifyOpp'].contactLink.value = '-1';\" " %><dhv:evaluate if="<%=opportunityHeader.getAccountLink() > -1 || "org".equals(request.getParameter("type")) %>">checked</dhv:evaluate>>
              </td>
              <td>
                <dhv:label name="account.account.colon">Account:</dhv:label>&nbsp;
              </td>
              <td>
                <div id="changeaccount">
                  <% if(opportunityHeader.getAccountLink() != -1) {%>
                    <%= toHtml(opportunityHeader.getAccountName()) %>
                  <%} else {%>
                    <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                  <%}%>
                </div>
              </td>
              <td>
                <input type="hidden" name="accountLink" id="accountLink" value="<%= opportunityHeader.getAccountLink() %>">&nbsp;<font color="red">*</font> <%= showAttribute(request, "acctContactError") %>
                &nbsp;[<a href="<%= "javascript:document.forms['modifyOpp'].type[0].checked='t';popAccountsListSingle('accountLink','changeaccount');" %>" onMouseOver="window.status='Select an Account';return true;" onMouseOut="window.status='';return true;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              </td>
            </tr>
       </table>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="radio" name="type" value="contact" onclick=<%= "\"javascript:document.forms['opportunityForm'].accountLink.value = '-1';\" " %> <dhv:evaluate if="<%= opportunityHeader.getContactLink() > -1 || "contact".equals(request.getParameter("type"))%>">checked</dhv:evaluate>>
          </td>
          <td>
            <dhv:label name="account.contact.colon">Contact:</dhv:label>&nbsp;
          </td>
          <td>
            <div id="changecontact">
              <% if(String.valueOf(opportunityHeader.getContactLink()).equals("-1")) {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%} else {%>
                &nbsp;<%= toHtml(opportunityHeader.getContactName()) %>
              <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="contactLink" id="contactLink" value="<%= opportunityHeader.getContactLink() %>">
            &nbsp;[<a href=<%= "\"javascript:document.forms['modifyOpp'].type[1].checked='t';popContactsListSingle('contactLink','changecontact','reset=true&filters=mycontacts|accountcontacts');\" "%> onMouseOver="window.status='Select a Contact';return true;" onMouseOut="window.status='';return true;"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  </dhv:permission>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="this.form.dosubmit.value='true';">
  <% if (request.getParameter("return") != null) {%>
    <% if (request.getParameter("return").equals("list")) {%>
    <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
    <%}%>
  <%} else {%>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Opportunities.do?command=Details&headerId=<%= opportunityHeader.getId() %>&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';">
  <%}%>
  <input type="hidden" name="dosubmit" value="true">
</dhv:container>
</form>
