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
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="ShortChildList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="Viewpoints" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="oppList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="GraphTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Read in the image map for the graph --%>
<% 
  String includePage = "../graphs/" + (String) request.getAttribute("GraphFileName") + ".map";
  boolean allowMultiple = allowMultipleComponents(pageContext, OpportunityComponent.MULTPLE_CONFIG_NAME, "multiple");
%>          
<jsp:include page="<%= includePage %>" flush="true"/>
<script type="text/javascript">
function reopenOpportunity(id) {
  scrollReload('Leads.do?command=Dashboard');
  return id;
}
</script>
<form name="Dashboard" action="Leads.do?command=Dashboard" method=POST>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> > 
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
        <dhv:evaluate if="<%= Viewpoints.size() > 1 %>">
        <tr>
          <th valign="top" style="text-align: center;" nowrap<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>"> class="warning"</dhv:evaluate>>
            <% Viewpoints.setJsEvent("onChange=\"javascript:document.Dashboard.reset.value='true';document.Dashboard.submit();\""); %>
            <dhv:label name="pipeline.viewpoint.colon.only">Viewpoint:</dhv:label> <%= Viewpoints.getHtmlSelect("viewpointId", PipelineViewpointInfo.getVpUserId()) %><br>
          </th>
        </tr>
        </dhv:evaluate>
        <tr>
          <th valign="top" style="text-align: center;" nowrap>
          <% if (request.getSession().getAttribute("leadsoverride") != null) { %>
            <dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label>: <%= toHtml((String)request.getSession().getAttribute("leadsothername")) %>
          <%} else {%>
            <dhv:label name="accounts.accounts_revenue_dashboard.MyDashboard">My Dashboard</dhv:label>
          <%}%>
          </th>
        </tr>
        <tr>
          <td>
            <img src="graphs/<%= request.getAttribute("GraphFileName") %>.jpg" width="275" height="200" border="0" usemap="#<%= request.getAttribute("GraphFileName") %>">
          </td>
        </tr>
        <tr>
          <td style="text-align: center;" nowrap>
            <img src="images/icons/stock_chart-reorganize-16.gif" align="absMiddle" alt="" />
            <%= GraphTypeList.getHtml() %>&nbsp;
          </td>
        </tr>
      </table>
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td style="text-align: center;" width="100%">
            <% if (!(((String)request.getSession().getAttribute("leadsoverride")) == null)) {
              int prevId =  Integer.parseInt((String)request.getSession().getAttribute("leadspreviousId"));
              %>
            <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("leadsoverride"))%>">
            <a href="Leads.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("leadspreviousId"))%><%= PipelineViewpointInfo.getVpUserId() == prevId || ((String)request.getSession().getAttribute("leadspreviousId")).equals(String.valueOf(User.getUserId())) ? "&reset=true" : ""%>"><dhv:label name="accounts.accounts_revenue_dashboard.UpOneLevel">Up One Level</dhv:label></a> |
            <a href="Leads.do?command=Dashboard&reset=true"><dhv:label name="accounts.accounts_revenue_dashboard.BackMyDashboard">Back to My Dashboard</dhv:label></a>
            <% } else {%>
                &nbsp;
            <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="0" class="pagedList">
        <tr>
          <th valign="center" nowrap>
            <dhv:label name="pipeline.reports.grPipe.brackets">Reports (Gr. Pipe.)</dhv:label>
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
            <a href="Leads.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            <dhv:evaluate if="<%= thisRec.getGrossPipeline(1000) == 0.0 %>">
              (<zeroio:currency value="<%= 0 %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>K)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisRec.getGrossPipeline(1000) > 0 && thisRec.getGrossPipeline(1000) < 1 %>">
              (&lt; <zeroio:currency value="<%= 1 %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>K)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisRec.getGrossPipeline(1000) >= 1 %>">
              (<zeroio:currency value="<%= thisRec.getGrossPipeline(1000) %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>K)
            </dhv:evaluate>
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
      <%-- Opportunity List --%>
      <table cellpadding="3" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th><dhv:label name="quotes.opportunity">Opportunity</dhv:label></th>
          <th><dhv:label name="reports.pipeline.amount">Amount</dhv:label></th>
        </tr>
<%
	Iterator n = oppList.iterator();
  FileItem thisFile = new FileItem();
  if ( n.hasNext() ) {
    int rowid = 0;
    while (n.hasNext()) {
      rowid = (rowid != 1?1:2);
      OpportunityHeader thisHeader = (OpportunityHeader) n.next();
%>
				<tr class="row<%= rowid %>">
          <td width="100%" valign="center">
            <a href="Leads.do?command=DetailsOpp&headerId=<%= thisHeader.getId() %>&viewSource=dashboard&reset=true"><%= toHtml(thisHeader.getDisplayName()) %>:
            <%= toHtml(thisHeader.getDescription()) %></a>
            <dhv:evaluate if="<%= thisHeader.getComponentCount() > 1 %>">
              (<%= thisHeader.getOwnerComponentCount() %>/<%= thisHeader.getComponentCount() %>)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.hasFiles() %>">
              <%= thisFile.getImageTag("-23") %>
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.getPlanWorkList().size() > 0 %>">
              <table class="empty">
                <%
                  Iterator plans = thisHeader.getPlanWorkList().iterator();
                  while (plans.hasNext()) {
                    ActionPlanWork thisPlan = (ActionPlanWork) plans.next();
                %>
                  <tr>
                    <td><img border="0" src="images/arrowright.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisHeader.getAccountLink() != -1 %>">
                      <td><a href="AccountActionPlans.do?command=Details&actionPlanId=<%= thisPlan.getId() %>&orgId=<%= thisHeader.getAccountLink() %>"><%= toHtml(thisPlan.getPlanName()) %></a></td>
                    </dhv:evaluate>
                  </tr>
                <%
                  }
                %>
              </table>
            </dhv:evaluate>
          </td>
          <td width="55" nowrap align="right">
            <dhv:evaluate if="<%= thisHeader.getTotalValue(1000) == 0.0 %>">
              <zeroio:currency value="<%= 0 %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>K
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.getTotalValue(1000) > 0 && thisHeader.getTotalValue(1000) < 1 %>">
              &lt; <zeroio:currency value="<%= 1 %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>K
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.getTotalValue(1000) >= 1 %>">
              <zeroio:currency value="<%= thisHeader.getTotalValue(1000) %>" fractionDigits="false" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>K
            </dhv:evaluate>
          </td>
        </tr>
<%
      }
	  } else {
%>
        <tr>
          <td valign="center" colspan="7"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.NoOpportunitiesFound">No opportunities found.</dhv:label></td>
        </tr>
<%}%>
      </table>
      <table width="100%" border="0" cellpadding="3">
        <tr>
          <td style="text-align: center;">
            <dhv:pagedListStatus object="DashboardListInfo" showRefresh="false" showControlOnly="true"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="reset" value="false">
</form>
