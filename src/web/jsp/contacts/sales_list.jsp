<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="com.zeroio.iteam.base.*,org.aspcfs.modules.login.beans.*,org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="SalesListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="contacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="segmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="allOpenLeads" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="leadListBatchInfo" class="org.aspcfs.utils.web.BatchInfo" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_list_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

<%-- Clear form function --%>
  function workContact() {
    var s = selectedIds();
		popURL('Sales.do?command=AssignLead&action=toContact&ids='+ s + '&from='+ from() + '&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popup|popupType|actionId") %>&popup=true','Details','650','200','yes','yes');
  }
  function workAccount() {
    var s = selectedIds();
		popURL('Sales.do?command=AssignLead&action=toAccount&ids='+ s + '&from='+ from() + '&listForm=<%= (listForm!=null?listForm:"")  %><%= addLinkParams(request, "popup|popupType|actionId") %>&popup=true','Details','650','200','yes','yes');
  }
  function deleteLeads() {
    var s = selectedIds();
		window.location.href='Sales.do?command=ProcessBatch&action=delete&ids='+ s + '&from='+ from() + '&listForm=<%= (listForm!=null?listForm:"")  %>';
  }
  
  function clearForm(form) {
  }
  
  function checkForm(form) {
    return true;
  }
  function selectedIds() {
    var frm = document.forms['batchLeadsForm'];
    var len = document.forms['batchLeadsForm'].elements.length;
    var i=0;
    var s = "";
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name.indexOf('batchLeads')!=-1) {
        if (frm.elements[i].checked) {
          if (s != "") {
            s += "," + frm.elements[i].value;
          } else {
            s = frm.elements[i].value;
          }
        }
      }
    }
    return s;
  }
  function reassignLeads() {
    var listForms = '<%= (listForm!=null?listForm:"") %>';
    var s = selectedIds();
    var thisReturnValue = 'list';
    popURL('ContactsList.do?command=ContactList&action=reassign&listView=employees&listType=single&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true&source=leads&flushtemplist=true&usersOnly=true&leads=true&from='+ thisReturnValue + '&listForm='+ listForms, 'ReassignLead','650','200','yes','yes');
  }
  function assignLeads() {
    var listForms = '<%= (listForm!=null?listForm:"") %>';
    var s = selectedIds();
    var thisReturnValue = 'list';
    popURL('ContactsList.do?command=ContactList&action=assign&listView=employees&listType=single&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true&source=leads&flushtemplist=true&usersOnly=true&leads=true&from='+ thisReturnValue + '&listForm='+ listForms, 'ReassignLead','650','200','yes','yes');
  }

  function from() {
    return "list";
  }
</script>
<form name="batchLeadsForm">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (listForm != null && !"".equals(listForm)) { %>
  <a href="Sales.do?command=SearchForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
<%}%>
  <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SalesListInfo" showHiddenParams="false" />
<dhv:batch object="leadListBatchInfo">
<table cellpadding="3" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th nowrap>&nbsp;</th>
    <th><strong><a href="Sales.do?command=List&from=list&column=c.org_name"><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></a></strong>
          <%= SalesListInfo.getSortIcon("c.org_name") %>
    </th>
    <dhv:include name="sales.list.name" none="true">
      <th nowrap><strong><dhv:label name="contacts.name">Name</dhv:label></strong></th>
    </dhv:include>
    <th nowrap><strong><a href="Sales.do?command=List&from=list&column=ca.city"><dhv:label name="accounts.accounts_add.City">City</dhv:label></a></strong>
          <%= SalesListInfo.getSortIcon("ca.city") %>
    </th>
    <th nowrap><strong><a href="Sales.do?command=List&from=list&column=ca.postalcode"><dhv:label name="accounts.accounts_add.Zip">Zip Code</dhv:label></a></strong>
          <%= SalesListInfo.getSortIcon("ca.postalcode") %>
    </th>
    <th nowrap><strong><a href="Sales.do?command=List&from=list&column=c.potential"><dhv:label name="accounts.accounts_add.potential">Potential</dhv:label></a></strong>
          <%= SalesListInfo.getSortIcon("c.potential") %>
    </th>
    <th nowrap><strong><a href="Sales.do?command=List&from=list&column=c.industry_temp_code"><dhv:label name="actionPlan.businessSegment">Business Segment</dhv:label></a></strong>
          <%= SalesListInfo.getSortIcon("c.industry_temp_code") %>
    </th>
    <th nowrap><strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong></th>
    <dhv:evaluate if="<%= contacts.getOwner() != User.getUserId() %>">
      <th nowrap><strong><dhv:label name="reports.owner">Owner</dhv:label></strong></th>
    </dhv:evaluate>
<dhv:evaluate if='<%= SalesListInfo.getSearchOptionValue("searchcodeSiteId").equals(String.valueOf(Constants.INVALID_SITE)) || SiteIdList.size() > 2 %>'>
    <th nowrap>
      <strong><a href="Sales.do?command=List&from=list&column=lsi.description"><dhv:label name="accounts.site">Site</dhv:label></a></strong>
      <%= SalesListInfo.getSortIcon("lsi.description") %>
    </th>
</dhv:evaluate>
    <dhv:include name="sales.list.entered">
      <th nowrap><strong><a href="Sales.do?command=List&from=list&column=c.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
          <%= SalesListInfo.getSortIcon("ca.city") %>
      </th>
    </dhv:include>
  </tr>
<%
	Iterator iterator = contacts.iterator();
  if (iterator.hasNext()) {
    int rowid = 0;
    int menuCount=0;
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      menuCount++;
      Contact thisLead = (Contact) iterator.next();
%>
    <tr class="row<%= rowid %>">
      <td valign="top" align="center" width="40">
        <dhv:batchInput object="leadListBatchInfo" value="<%= thisLead.getId() %>" hiddenParams="contactId" 
                    hiddenValues='<%= String.valueOf(thisLead.getId()) %>'/>
        <a href="javascript:displayMenu('select<%= menuCount %>','menuContact','<%= thisLead.getId() %>','list','<%= thisLead.getIsLead() %>', '<%= thisLead.getOrgId() %>', '<%= thisLead.getOwner() != -1 %>', <%= thisLead.getSiteId() %>,'<%= thisLead.getLeadStatus() %>');" 
        onMouseOver="over(0, <%= menuCount %>);" 
        onmouseout="out(0, <%= menuCount %>);hideMenu('menuContact');"><img
        src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0" /></a>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= thisLead.getIsLead() %>">
          <a href="Sales.do?command=Details&contactId=<%= thisLead.getId() %>&from=list&listForm=true">
            <dhv:evaluate if='<%= thisLead.getCompany() == null || "".equals(thisLead.getCompany()) %>'>
              <dhv:label name="actionPlan.notAvailable">Not available</dhv:label>
            </dhv:evaluate><dhv:evaluate if='<%= thisLead.getCompany() != null && !"".equals(thisLead.getCompany()) %>'>
              <%= toHtml(thisLead.getCompany()) %>
            </dhv:evaluate>
          </a>
        </dhv:evaluate><dhv:evaluate if="<%= !thisLead.getIsLead() %>">
          <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= thisLead.getId() %>&popup=true&viewOnly=true','Details','650','500','yes','yes');">
            <dhv:evaluate if='<%= thisLead.getCompany() == null || "".equals(thisLead.getCompany()) %>'>
              <dhv:label name="actionPlan.notAvailable">Not available</dhv:label>
            </dhv:evaluate><dhv:evaluate if='<%= thisLead.getCompany() != null && !"".equals(thisLead.getCompany()) %>'>
              <%= toHtml(thisLead.getCompany()) %>
            </dhv:evaluate>
          </a>
        </dhv:evaluate>
      </td>
      <dhv:include name="sales.list.name" none="true">
        <td valign="top"><%= toHtml(thisLead.getNameLastFirst()) %></td>
      </dhv:include>
      <td valign="top">
        <%= toHtml(thisLead.getCity()) %>
      </td>
      <td valign="top">
        <%= toHtml(thisLead.getPostalcode()) %>
      </td>
      <td valign="top" align="right">
        <zeroio:currency value="<%= thisLead.getPotential() %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
      <td valign="top">
        <%= toHtml(segmentList.getValueFromId(thisLead.getIndustryTempCode())) %>
      </td>
      <td valign="top">
        <dhv:evaluate if="<%= !thisLead.getIsLead() %>" >
          <dhv:label name="sales.working">Working</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisLead.getIsLead() %>">
          <dhv:label name='<%= "sales."+thisLead.getLeadStatusString() %>'><%= toHtml(thisLead.getLeadStatusString()) %></dhv:label>
        </dhv:evaluate>
      </td>
      <dhv:evaluate if="<%= contacts.getOwner() != User.getUserId() %>">
        <td valign="top">
          <dhv:username id="<%= thisLead.getOwner() %>" />
        </td>
      </dhv:evaluate>
<dhv:evaluate if='<%= SalesListInfo.getSearchOptionValue("searchcodeSiteId").equals(String.valueOf(Constants.INVALID_SITE)) || SiteIdList.size() > 2 %>'>
      <dhv:include name="sales.list.site" none="true">
        <td valign="top"><%= SiteIdList.getSelectedValue(thisLead.getSiteId()) %></td>
      </dhv:include>
</dhv:evaluate>
      <dhv:include name="sales.list.entered">
        <td valign="top" nowrap>
          <zeroio:tz timestamp="<%= thisLead.getEntered() %>" dateOnly="true" showTimeZone="false" />
        </td>
      </dhv:include>
    </tr>
<%
      }
	  } else {
%>
  <tr>
    <td valign="center" colspan="<%= SalesListInfo.getSearchOptionValue("searchcodeSiteId").equals(String.valueOf(Constants.INVALID_SITE)) || SiteIdList.size() > 2 ? "10":"9" %>"><dhv:label name="sales.noLeadsFound">No leads found.</dhv:label></td>
  </tr>
<%}%>
</table>
</dhv:batch>
<br />
<dhv:pagedListControl object="SalesListInfo" tdClass="row1">
  <dhv:batchList object="leadListBatchInfo" returnURL="Sales.do?command=Dashboard">
    <dhv:batchItem display='<%= systemStatus.getLabel("", "Delete Leads") %>' 
                        link="javascript:deleteLeads();" />
    <dhv:batchItem display='<%= systemStatus.getLabel("", "Assign Leads") %>' 
                        link="javascript:assignLeads();" />
    <dhv:batchItem display='<%= systemStatus.getLabel("", "Re-assign Leads") %>' 
                        link="javascript:reassignLeads();" />
    <dhv:batchItem display='<%= systemStatus.getLabel("", "Convert to Contact") %>' 
                        link="javascript:workContact();" />
    <dhv:batchItem display='<%= systemStatus.getLabel("", "Convert to Account") %>' 
                         link="javascript:workAccount();" />
  </dhv:batchList>
</dhv:pagedListControl>
</form>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
