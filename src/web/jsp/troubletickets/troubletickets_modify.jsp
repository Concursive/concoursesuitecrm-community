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
<jsp:useBean id="SubmiterOrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="TicketDetails"
             class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="CategoryList"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList"
             scope="request"/>
<jsp:useBean id="resolvedUserList"
             class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="SubList2"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="SubList3"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="actionPlans"
             class="org.aspcfs.modules.actionplans.base.ActionPlanList"
             scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactList"
             class="org.aspcfs.modules.contacts.base.ContactList"
             scope="request"/>
<jsp:useBean id="SubmitterContact"
             class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect"
             scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone"
             scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="details"
      action="TroubleTickets.do?command=Update&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") %><%= isPopup(request)?"&popup=true":"" %>"
      method="post">
  <dhv:evaluate if="<%= !isPopup(request) %>">
    <%-- Trails --%>
    <table class="trails" cellspacing="0">
      <tr>
        <td>
          <a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help
            Desk</dhv:label></a> >
          <%if (defectCheck != null && !"".equals(defectCheck.trim())) {%>
          <a href="TroubleTicketDefects.do?command=View"><dhv:label
              name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
          <a href="TroubleTicketDefects.do?command=Details&defectId=<%= defectCheck %>"><dhv:label
              name="tickets.defects.defectDetails">Defect
            Details</dhv:label></a> >
          <a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>&defectCheck=<%= defectCheck %>"><dhv:label
              name="tickets.details">Ticket Details</dhv:label></a> >
          <%} else {%>
          <% if (("list".equals((String) request.getParameter("return"))) ||
              ("searchResults".equals((String) request.getParameter("return")))) {%>
          <% if ("yes".equals((String) session.getAttribute("searchTickets"))) {%>
          <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label
              name="tickets.searchForm">Search Form</dhv:label></a> >
          <a href="TroubleTickets.do?command=SearchTickets"><dhv:label
              name="accounts.SearchResults">Search Results</dhv:label></a> >
          <%} else {%>
          <a href="TroubleTickets.do?command=Home"><dhv:label
              name="tickets.view">View Tickets</dhv:label></a> >
          <%}%>
          <%} else {%>
          <% if ("yes".equals((String) session.getAttribute("searchTickets"))) {%>
          <a href="TroubleTickets.do?command=SearchTickets"><dhv:label
              name="tickets.search">Search Tickets</dhv:label></a> >
          <%} else {%>
          <a href="TroubleTickets.do?command=Home"><dhv:label
              name="tickets.view">View Tickets</dhv:label></a> >
          <%}%>
          <a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label
              name="tickets.details">Ticket Details</dhv:label></a> >
          <%}%>
          <%}%>
          <dhv:label name="tickets.modify">Modify Ticket</dhv:label>
        </td>
      </tr>
    </table>
    <%-- End Trails --%>
  </dhv:evaluate>
  <% String param1 = "id=" + TicketDetails.getId(); %>
  <dhv:container name="tickets" selected="details" object="TicketDetails"
                 param="<%= param1 %>"
                 hideContainer='<%= (isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim()))) %>'>
    <%@ include file="ticket_header_include.jsp" %>
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
      <font color="red"><dhv:label name="tickets.alert.closed">This ticket has
        been closed:</dhv:label>
        <zeroio:tz timestamp="<%= TicketDetails.getClosed() %>"
                   timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </font><br/>
    </dhv:evaluate>
    <% if (TicketDetails.getClosed() != null) { %>
    <input type="button"
           value="<dhv:label name="button.reopen">Reopen</dhv:label>"
           onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&ticketId=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") %>';submit();">
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=Details&id="+ TicketDetails.getId() +"'":"javascript:window.close();") %>'/>
    <%} else {%>
    <input type="submit"
           value="<dhv:label name="global.button.update">Update</dhv:label>"
           onClick="return checkForm(this.form)">
    <% if ("list".equals(request.getParameter("return"))) {%>
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=Home'":"javascript:window.close();") %>'/>
    <%} else if ("searchResults".equals(request.getParameter("return"))) {%>
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=SearchTickets'":"javascript:window.close();") %>'/>
    <% } else {%>
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=Details&id="+ TicketDetails.getId() +"'":"javascript:window.close();") %>'/>
    <%}%>
    <%}%>
    <br/>
    <dhv:formMessage/>
    <iframe src="empty.html" name="server_commands" id="server_commands"
            style="visibility:hidden" height="0"></iframe>
    <%@ include file="troubletickets_modify_include.jsp" %>
    <% if (TicketDetails.getClosed() != null) { %>
    <input type="button"
           value="<dhv:label name="button.reopen">Reopen</dhv:label>"
           onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&ticketId=<%= TicketDetails.getId()%><%= defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"" %>';submit();">
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=Details&id="+ TicketDetails.getId() +(defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"")+"'":"javascript:window.close();") %>'/>
    <%} else {%>
    <input type="submit"
           value="<dhv:label name="global.button.update">Update</dhv:label>"
           onClick="return checkForm(this.form)">
    <% if ("list".equals(request.getParameter("return"))) {%>
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=Home'":"javascript:window.close();") %>'/>
    <%} else if ("searchResults".equals(request.getParameter("return"))) {%>
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=SearchTickets'":"javascript:window.close();") %>'/>
    <%} else {%>
    <input type="submit"
           value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
           onClick='<%= (!isPopup(request)?"javascript:this.form.action='TroubleTickets.do?command=Details&id="+ TicketDetails.getId()+(defectCheck != null && !"".equals(defectCheck.trim()) ?"&defectCheck="+defectCheck:"") +"'":"javascript:window.close();") %>'/>
    <%}%>
    <%}%>
  </dhv:container>
</form>
