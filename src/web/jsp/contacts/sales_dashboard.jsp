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
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="com.zeroio.iteam.base.*,org.aspcfs.modules.login.beans.*,org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="ShortChildList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SalesDashboardListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="myLeads" class="java.lang.String" scope="session" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="GraphFileName" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_list_menu.jsp" %>
<% String includePage = "../graphs/" + GraphFileName + ".map";%>
<jsp:include page="<%= includePage %>" flush="false" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select-arrow');
  function checkForm(form) {
    return true;
  }
  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }
  function reopenOnDelete() {
    window.location.href='Sales.do?command=Dashboard';
  }
  function reopen() {
    window.location.href='Sales.do?command=Dashboard';
  }
</script>
<%--Graph related code --%>
<form name="listView" method="post" action="Sales.do?command=Dashboard">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
  <table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <%-- Left Column --%>
    <td width="275" valign="top">
      <%-- Graphic --%>
      <table width="275" cellpadding="3" cellspacing="0" border="0" class="pagedList">
        <tr>
          <th valign="top" style="text-align: center;" nowrap>
          <% if (request.getSession().getAttribute("salesoverride") != null) { %>
            <dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label>: <%= toHtml((String)request.getSession().getAttribute("salesothername")) %>
          <%} else {%>
            <dhv:label name="accounts.accounts_revenue_dashboard.MyDashboard">My Dashboard</dhv:label>
          <%}%>
          </th>
        </tr>
        <tr>
          <td>
            <img src="graphs/<%= request.getAttribute("GraphFileName") %>.jpg" width="275" height="200" border="0" usemap="#<%= request.getAttribute("GraphFileName") %>" />
          </td>
        </tr>
        <tr>
          <td style="text-align: center;" nowrap>
            <img src="images/icons/stock_chart-reorganize-16.gif" align="absMiddle" alt="" />
            <dhv:label name="sales.leadToContactConversionRate">Lead Conversion Rate</dhv:label>
          </td>
        </tr>
      </table>
      <br />
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td style="text-align: center;" width="100%">
            <% if (request.getSession().getAttribute("salesoverride") != null) {
              int prevId =  Integer.parseInt((String)request.getSession().getAttribute("salespreviousId"));
              %>
            <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("salesoverride"))%>">
            <a href="Sales.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("salespreviousId"))%><%= ((String)request.getSession().getAttribute("salespreviousId")).equals(String.valueOf(User.getUserId())) ? "&reset=true" : ""%>"><dhv:label name="accounts.accounts_revenue_dashboard.UpOneLevel">Up One Level</dhv:label></a> |
            <a href="Sales.do?command=Dashboard&reset=true"><dhv:label name="accounts.accounts_revenue_dashboard.BackMyDashboard">Back to My Dashboard</dhv:label></a>
            <%} else {%>
                &nbsp;
            <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="0" class="pagedList">
        <tr>
          <th valign="center" nowrap>
            <dhv:label name="qa.reports">Reports</dhv:label>
          </th>
          <th width="125" valign="center">
            <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
          </th>
        </tr>
<%
    Iterator x = ShortChildList.iterator();
      if ( x.hasNext() ) {
        int rowid = 0;
        while (x.hasNext()) {
          if (rowid != 1) {
            rowid = 1;
          } else {
            rowid = 2;
          }
          User thisRec = (User)x.next();
%>
        <tr class="row<%= rowid %>">
          <td valign="center" nowrap>
            <a href="Sales.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            <dhv:evaluate if="<%=!thisRec.getEnabled() || (thisRec.getExpires() != null && thisRec.getExpires().before(new Timestamp(Calendar.getInstance().getTimeInMillis())))%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <td width="125" valign="center">
            <%= toHtml(thisRec.getContact().getTitle()) %>
          </td>
        </tr>
<%      }
      } else {
%>
        <tr>
          <td valign="center" colspan="3"><dhv:label name="accounts.accounts_revenue_dashboard.NoReportingStaff">No Reporting staff.</dhv:label></td>
        </tr>
      <%}%>
      </table>
    </td>
    <%-- Right Column --%>
    <td valign="top" width="100%">
      <table width="100%" class="empty">
      <tr><td valign="top">
      <select name="myLeads" onChange="javascript:document.listView.submit();" size="1">
        <option value="false" <%= myLeads.equals("true")?"":"SELECTED" %>><dhv:label name="sales.allLeads">All Leads</dhv:label></option>
        <option value="true" <%= myLeads.equals("true")?"SELECTED":"" %>><dhv:label name="sales.ownedOrReadLeads">Owned/Read Leads</dhv:label></option>
      </select>
      </td><td valign="top">
      <%-- TODO: Dropdown lists--%>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SalesDashboardListInfo"/></td></tr></table>
      <%-- Leads List --%>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>&nbsp;</th>
          <th><strong><dhv:label name="contacts.nameAndCompany">Name / Company</dhv:label></strong></th>
          <th><strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong></th>
          <th><strong><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></strong></th>
          <th><strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong></th>
        </tr>
<%
	Iterator iterator = contactList.iterator();
  if (iterator.hasNext()) {
    int rowid = 0;
    int menuCount=0;
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      menuCount++;
      Contact thisLead = (Contact) iterator.next();
%>
				<tr class="row<%= rowid %>">
         <td valign="top">
            <a href="javascript:displayMenu('select-arrow<%= menuCount %>','menuContact','<%= thisLead.getId() %>','dashboard','<%= thisLead.getIsLead() %>', '<%= thisLead.getOrgId() %>', '<%= thisLead.getOwner() != -1 %>',<%= thisLead.getSiteId() %>,'<%= thisLead.getLeadStatus() %>');" 
            onMouseOver="over(0, <%= menuCount %>);" 
            onmouseout="out(0, <%= menuCount %>);hideMenu('menuContact');"><img
            src="images/select-arrow.gif" name="select-arrow<%= menuCount %>" id="select-arrow<%= menuCount %>" align="absmiddle" border="0" /></a>
          </td>
          <td valign="top" width="100%">
            <dhv:evaluate if="<%= thisLead.getNameLastFirst() != null && !"".equals(thisLead.getNameLastFirst()) %>">
              <%= toHtml(thisLead.getNameLastFirst()) %>
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisLead.getNameLastFirst() != null && !"".equals(thisLead.getNameLastFirst()) && thisLead.getCompany() != null && !"".equals(thisLead.getCompany()) %>">
              <br />
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisLead.getCompany() != null && !"".equals(thisLead.getCompany()) %>">
              <%= toHtml(thisLead.getCompany()) %>
            </dhv:evaluate>
          </td>
          <td valign="top">
            <dhv:evaluate if="<%= !thisLead.getIsLead() %>" >
              <dhv:label name="sales.working">Working</dhv:label>
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisLead.getIsLead() %>">
              <dhv:label name="<%= "sales." + thisLead.getLeadStatusString() %>"><%= toHtml(thisLead.getLeadStatusString()) %></dhv:label>
            </dhv:evaluate>
          </td>
          <td valign="top">
              <dhv:username id="<%= thisLead.getOwner() %>" />
          </td>
          <td valign="top">
            <zeroio:tz timestamp="<%= thisLead.getEntered() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>
          </td>
        </tr>
<%
      }
	  } else {
%>
        <tr>
          <td valign="center" colspan="<%= User.getUserRecord().getSiteId() == -1?"6":"5" %>"><dhv:label name="sales.noLeadsFound">No leads found.</dhv:label></td>
        </tr>
<%}%>
      </table>
      <table width="100%" border="0" cellpadding="3">
        <tr>
          <td style="text-align: center;">
            <dhv:pagedListStatus object="SalesDashboardListInfo" showRefresh="false" showControlOnly="true"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="reset" value="false">
</form>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
