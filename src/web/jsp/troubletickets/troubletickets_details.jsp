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
<jsp:useBean id="TicketDetails"
             class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="SubmitterOrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<jsp:useBean id="SubmitterContact"
             class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ticketCategoryList"
             class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
             scope="request"/>
<jsp:useBean id="product"
             class="org.aspcfs.modules.products.base.ProductCatalog"
             scope="request"/>
<jsp:useBean id="customerProduct"
             class="org.aspcfs.modules.products.base.CustomerProduct"
             scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList"
             scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList"
             scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect"
             class="org.aspcfs.modules.troubletickets.base.TicketDefect"
             scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/confirmDelete.js"></script>
<script type="text/javascript">
  function popKbEntries() {
    var siteId = '<%= TicketDetails.getSiteId() %>';
    var catCodeValue = '<%= TicketDetails.getCatCode() %>';
    var subCat1Value = '<%= TicketDetails.getSubCat1() %>';
  <dhv:include name="ticket.subCat2" none="true">
    var subCat2Value = '<%= TicketDetails.getSubCat2() %>';
  </dhv:include>
  <dhv:include name="ticket.subCat2" none="true">
    var subCat3Value = '<%= TicketDetails.getSubCat3() %>';
  </dhv:include>
    var url = 'KnowledgeBaseManager.do?command=Search&popup=true&searchcodeSiteId=' + siteId + '&searchcodeCatCode=' + catCodeValue;
    url = url + '&searchcodeSubCat1=' + subCat1Value;
  <dhv:include name="ticket.subCat2" none="true">
    url = url + '&searchcodeSubCat2=' + subCat2Value;
  </dhv:include>
  <dhv:include name="ticket.subCat2" none="true">
    url = url + '&searchcodeSubCat3=' + subCat3Value;
  </dhv:include>
    popURL(url, 'KnowledgeBase', '600', '550', 'yes', 'yes');
  }
</script>
<%@ include file="../initPage.jsp" %>
<form name="details"
      action="TroubleTickets.do?command=Modify&auto-populate=true"
      method="post">
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
          name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
      <%} else {%>
      <% if ("yes".equals((String) session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label
          name="tickets.searchForm">Search Form</dhv:label></a> >
      <a href="TroubleTickets.do?command=SearchTickets"><dhv:label
          name="accounts.SearchResults">Search Results</dhv:label></a> >
      <%} else {%>
      <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View
        Tickets</dhv:label></a> >
      <%}%>
      <%}%>
      <dhv:label name="tickets.details">Ticket Details</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="details" object="TicketDetails"
               param="<%= param1 %>"
               hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
<%@ include file="ticket_header_include.jsp" %>
<% if (TicketDetails.isTrashed()) {%>
<dhv:permission name="tickets-tickets-delete">
  <input type="button"
         value="<dhv:label name="button.restore">Restore</dhv:label>"
         onClick="javascript:this.form.action='TroubleTickets.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
</dhv:permission>
<% }else if(TicketDetails.getClosed()!=null){ %>
<dhv:permission name="tickets-tickets-edit"><input type="button"
                                                   value="<dhv:label name="button.reopen">Reopen</dhv:label>"
                                                   onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"></dhv:permission>
<%}else{%>
<dhv:permission name="tickets-tickets-edit"><input type="button"
                                                   value="<dhv:label name="global.button.modify">Modify</dhv:label>"
                                                   onClick="javascript:this.form.action='TroubleTickets.do?command=Modify&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"></dhv:permission>
<%--
<dhv:permission name="quotes-view">
  <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
    <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&ticketId=<%= TicketDetails.getId() %>';submit();"/>
  </dhv:evaluate>
</dhv:permission>
--%>
<dhv:permission name="tickets-tickets-delete">
  <% if("searchResults".equals(request.getParameter("return"))){ %>
  <input type="button"
         value="<dhv:label name="global.button.delete">Delete</dhv:label>"
         onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
  <%}else{ %>
  <input type="button"
         value="<dhv:label name="global.button.delete">Delete</dhv:label>"
         onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
  <%}%>
</dhv:permission>
<%}%>
<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete"><br/>&nbsp;<br
  /></dhv:permission>
<%-- Ticket Information --%>
<%-- Primary Contact --%>
<dhv:evaluate if="<%= TicketDetails.getThisContact() != null %>">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="ticket.primaryContact">Primary
          Contact</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.name">Name</dhv:label>
      </td>
      <td>
        <dhv:evaluate if="<%= !TicketDetails.getThisContact().getEmployee() %>">
          <dhv:permission name="accounts-accounts-contacts-view">
            <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= TicketDetails.getContactId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%= toHtml(TicketDetails.getThisContact().getNameFull()) %>
            </a>
          </dhv:permission>
          <dhv:permission name="accounts-accounts-contacts-view" none="true">
            <%= toHtml(TicketDetails.getThisContact().getNameFull()) %>
          </dhv:permission>
        </dhv:evaluate>
        <dhv:evaluate if="<%= TicketDetails.getThisContact().getEmployee() %>">
          <dhv:permission name="contacts-internal_contacts-view">
            <a href="javascript:popURL('CompanyDirectory.do?command=EmployeeDetails&empid=<%= TicketDetails.getContactId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%= toHtml(TicketDetails.getThisContact().getNameLastFirst()) %>
            </a>
          </dhv:permission><dhv:permission
            name="contacts-internal_contacts-view" none="true">
          <%= toHtml(TicketDetails.getThisContact().getNameLastFirst()) %>
        </dhv:permission>
        </dhv:evaluate>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label>
      </td>
      <td>
        <%= toHtml(TicketDetails.getThisContact().getTitle()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_add.Email">Email</dhv:label>
      </td>
      <td>
        <%= TicketDetails.getThisContact().getEmailAddressTag("",toHtml(TicketDetails.getThisContact().getPrimaryEmailAddress()),"&nbsp;") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="reports.accounts.contacts.phone">Phone</dhv:label>
      </td>
      <td>
        <%= toHtml(TicketDetails.getThisContact().getPrimaryPhoneNumber()) %>
      </td>
    </tr>
  </table>
  &nbsp;
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
  <th colspan="2">
    <strong><dhv:label name="tickets.information">Ticket
      Information</dhv:label></strong>
  </th>
</tr>
<tr>
  <td class="formLabel">
    <dhv:label
        name="accounts.accountasset_include.Organization">Organization</dhv:label>
  </td>
  <td>
    <a href="javascript:popURL('Accounts.do?command=Details&orgId=<%= SubmitterOrgDetails.getOrgId() %>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(SubmitterOrgDetails.getName()) %>
    </a>
  </td>
</tr>

<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accountasset_include.OrganizationContact">Organization
      Contact</dhv:label>
  </td>
  <td>
    <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= SubmitterContact.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%= toHtml(SubmitterContact.getNameFull()) %>
    </a>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="tickets.ticketState">Ticket State</dhv:label>
  </td>
  <td>
    <%= toHtml(ticketStateList.getSelectedValue(TicketDetails.getStateId())) %>
  </td>
</tr>
<tr class="containerBody">
  <td nowrap class="formLabel">
    <dhv:label name="tickets.source">Ticket Source</dhv:label>
  </td>
  <td>
    <%= toHtml(TicketDetails.getSourceName()) %>
  </td>
</tr>
<dhv:include name="ticket.contractNumber" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service
        Contract Number</dhv:label>
    </td>
    <td>
      <dhv:evaluate
          if="<%= (TicketDetails.getServiceContractNumber()!= null && !"".equals(TicketDetails.getServiceContractNumber())) %>">
        <a href="javascript:popURL('AccountsServiceContracts.do?command=View&orgId=<%= SubmitterOrgDetails.getOrgId()%>&id=<%= TicketDetails.getContractId() %>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(TicketDetails.getServiceContractNumber()) %>
        </a>
      </dhv:evaluate>
    </td>
  </tr>
</dhv:include>
<dhv:include name="ticket.asset" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.assetSerialNumber">Asset Serial
        Number</dhv:label>
    </td>
    <td>
      <dhv:permission name="accounts-assets-view">
        <dhv:evaluate if="<%= TicketDetails.getAssetId()!= -1 %>">
          <a href="javascript:popURL('AccountsAssets.do?command=View&id=<%= TicketDetails.getAssetId() %>&popup=true&viewOnly=true','AccountAssetDetails','650','500','yes','yes');"><%= toHtml(TicketDetails.getAssetSerialNumber()) %>
          </a>
        </dhv:evaluate>
      </dhv:permission>
      <dhv:permission name="accounts-assets-view" none="true">
        <%= toHtml(TicketDetails.getAssetSerialNumber()) %>
      </dhv:permission>
    </td>
  </tr>
</dhv:include>
<dhv:include name="ticket.labor" none="true">
  <dhv:evaluate if="<%= TicketDetails.getProductId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="account.laborCategory">Labor Category</dhv:label>
      </td>
      <td>
        <%= toHtml(product.getName()) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:evaluate if="<%= TicketDetails.getCustomerProductId() != -1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.customerProduct">Customer Product</dhv:label>
    </td>
    <td>
      <%= toHtml(customerProduct.getDescription()) %> <input type="button"
                                                             value="<dhv:label name="button.display">Display</dhv:label>"
                                                             onClick="javascript:popURL('Publish.do?command=DisplayCustomerProduct&adId=<%= customerProduct.getId() %>&ticketId=<%= TicketDetails.getId() %>','Customer Product','500','200','yes','yes');"/>
    </td>
  </tr>
</dhv:evaluate>
  <%--
  <%
    if (quoteList.size() > 0) {
  %>
    <tr class="containerBody">
      <td nowrap class="formLabel">
  <%
      if( quoteList.size() > 1 ){
  %>
        <dhv:label name="account.relatedQuotes">Related Quotes</dhv:label>
  <%
      } else {
  %>
        <dhv:label name="account.relatedQuote">Related Quote</dhv:label>
  <%
      }
  %>
      </td>
      <td>
  <%
      Iterator quotes = (Iterator) quoteList.iterator();
      int quoteCounter = 0;
      while(quotes.hasNext()){
        Quote quote = (Quote) quotes.next();
        if(quoteCounter++ == 0 ){
  %>
          <a href="Quotes.do?command=Details&quoteId=<%= quote.getId() %>"><dhv:label name="quotes.symbol.number" param='<%= "number="+quote.getGroupId() %>'>Quote #<%= quote.getGroupId() %></dhv:label></a>
  <%
        } else {
  %>
          , <a href="Quotes.do?command=Details&quoteId=<%= quote.getId() %>"><dhv:label name="quotes.symbol.number" param='<%= "number="+quote.getGroupId() %>'>Quote #<%= quote.getGroupId() %></dhv:label></a>
  <%
        }
      }
  %>
      </td>
    </tr>
  <%
  }
  %>
  --%>
<tr class="containerBody">
  <td class="formLabel" valign="top">
    <dhv:label name="ticket.issue">Issue</dhv:label>
  </td>
  <td valign="top">
    <%
    //Show audio files so that they can be streamed
        Iterator files=TicketDetails.getFiles().iterator();
        while(files.hasNext()){
        FileItem thisFile=(FileItem)files.next();
        if(".wav".equalsIgnoreCase(thisFile.getExtension())){
    %>
    <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><img
        src="images/file-audio.gif" border="0" align="absbottom"><dhv:label
        name="tickets.playAudioMessage">Play Audio Message</dhv:label></a><br/>
    <%
    }
        }
    %>
    <%= toHtml(TicketDetails.getProblem()) %>
    <input type="hidden" name="problem"
           value="<%=toHtml(TicketDetails.getProblem())%>">
    <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
    <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
  </td>
</tr>
<dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label
          name="accounts.accountasset_include.Location">Location</dhv:label>
    </td>
    <td>
      <%= toHtml(TicketDetails.getLocation()) %>
    </td>
  </tr>
</dhv:include>
<dhv:include name="ticket.defect" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="tickets.defects.defect">Defect</dhv:label>
    </td>
    <td>
      <%= toHtml(defect.getTitle()) %>
      <dhv:evaluate
          if="<%= hasText(defect.getTitle()) && defect.isDisabled() %>">(X)</dhv:evaluate>
    </td>
  </tr>
</dhv:include>
<dhv:include name="ticket.catCode" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label
          name="accounts.accountasset_include.Category">Category</dhv:label>
    </td>
    <td>
      <%= toHtml(ticketCategoryList.getValueFromId(TicketDetails.getCatCode())) %><dhv:evaluate
        if="<%= TicketDetails.getCatCode() > 0 %>"><dhv:permission
        name="tickets-knowledge-base-view">&nbsp;(<a
      href="javascript:popKbEntries();"><dhv:label
        name="tickets.knowledgebase.displayKBforSelectedCategories.text">Display
      Knowledge Base for selected
      Categories</dhv:label></a>)</dhv:permission></dhv:evaluate>
    </td>
  </tr>
</dhv:include>
<dhv:include name="ticket.subCat1" none="true">
  <dhv:evaluate if="<%= TicketDetails.getSubCat1() > 0 %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.ticket.subLevel1">Sub-level 1</dhv:label>
      </td>
      <td>
        <%= toHtml(ticketCategoryList.getValueFromId(TicketDetails.getSubCat1())) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  <dhv:evaluate if="<%= TicketDetails.getSubCat2() > 0 %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.ticket.subLevel2">Sub-level 2</dhv:label>
      </td>
      <td>
        <%= toHtml(ticketCategoryList.getValueFromId(TicketDetails.getSubCat2())) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  <dhv:evaluate if="<%= TicketDetails.getSubCat3() > 0 %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.ticket.subLevel3">Sub-level 3</dhv:label>
      </td>
      <td>
        <%= toHtml(ticketCategoryList.getValueFromId(TicketDetails.getSubCat3())) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="ticket.severity" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.severity">Severity</dhv:label>
    </td>
    <td>
      <%= toHtml(TicketDetails.getSeverityName()) %>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
  </td>
  <td>
    <dhv:username id="<%= TicketDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= TicketDetails.getEntered() %>"
               timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
  </td>
</tr>
<tr class="containerBody">
  <td class="formLabel">
    <dhv:label
        name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
  </td>
  <td>
    <dhv:username id="<%= TicketDetails.getModifiedBy() %>"/>
    <zeroio:tz timestamp="<%= TicketDetails.getModified() %>"
               timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
  </td>
</tr>
</table>
&nbsp;
<%-- Assignment --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label
          name="project.assignment">Assignment</dhv:label></strong>
    </th>
  </tr>
  <dhv:include name="ticket.priority" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label
            name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
      </td>
      <td>
        <%= toHtml(TicketDetails.getPriorityName()) %>
      </td>
    </tr>
  </dhv:include>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.department">Department</dhv:label>
    </td>
    <td>
      <% if(TicketDetails.getDepartmentCode()>0){%>
      <%= toHtml(TicketDetails.getDepartmentName()) %>
      <%}else{%>
      <dhv:label name="ticket.unassigned.text">-- unassigned --</dhv:label>
      <%}%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= TicketDetails.getAssignedTo() %>"
                    default="ticket.unassigned.text"/>
      <dhv:evaluate
          if="<%= !(TicketDetails.getHasEnabledOwnerAccount()) %>"><font
          color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="usergroup.assignedGroup">Assigned Group</dhv:label>
    </td>
    <td>
      <dhv:evaluate
          if='<%= TicketDetails.getUserGroupName() != null && !"".equals(TicketDetails.getUserGroupName()) %>'>
        <%= toHtml(TicketDetails.getUserGroupName()) %>
      </dhv:evaluate>
      <dhv:evaluate
          if='<%= TicketDetails.getUserGroupName() == null || "".equals(TicketDetails.getUserGroupName()) %>'>
        <dhv:label name="ticket.unassigned.text">-- unassigned --</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.ticket.assignmentDate">Assignment
        Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>"
                 dateOnly="true"
                 timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
                 showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(TicketDetails.getAssignedDateTimeZone())){ %>
      <br/>
      <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
                 default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="tickets.escalationLevel">Escalation Level</dhv:label>
    </td>
    <td>
      <%= toHtml(TicketDetails.getEscalationLevelName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution
        Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>"
                 dateOnly="true"
                 timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"
                 showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(TicketDetails.getEstimatedResolutionDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
                 default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.issueNotes">Issue Notes</dhv:label>
    </td>
    <td>
      <font color="red"><dhv:label
          name="accounts.tickets.ticket.previousTicket">(Previous notes for this
        ticket are listed under the history tab.)</dhv:label></font>
    </td>
  </tr>
</table>
<br/>
<%-- Resolution --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_asset_history.Resolution">Resolution</dhv:label></strong>
    </th>
  </tr>
  <dhv:include name="ticket.cause" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="account.ticket.cause">Cause</dhv:label>
      </td>
      <td>
        <%= toHtml(TicketDetails.getCause()) %>
        <dhv:include name="ticket.causeId" none="true"><dhv:evaluate
            if='<%= TicketDetails.getCause() != null && !"".equals(TicketDetails.getCause().trim()) %>'><br/></dhv:evaluate>
          <%= causeList.getSelectedValue(TicketDetails.getCauseId()) %>
        </dhv:include>
      </td>
    </tr>
  </dhv:include>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.department">Department</dhv:label>
    </td>
    <td>
      <% if(TicketDetails.getResolvedByDeptCode()>0){%>
      <%= toHtml(TicketDetails.getResolvedByDeptName()) %>
      <%}else{%>
      <dhv:label name="ticket.unassigned.text">-- unassigned --</dhv:label>
      <%}%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.resolvedBy">Resolved By</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= TicketDetails.getResolvedBy() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="ticket.resolution">Resolution</dhv:label>
    </td>
    <td>
      <%= toHtml(TicketDetails.getSolution()) %>
      <dhv:include name="ticket.resolutionId" none="true"><dhv:evaluate
          if='<%= TicketDetails.getSolution() != null && !"".equals(TicketDetails.getSolution()) %>'><br/></dhv:evaluate>
        <%= resolutionList.getSelectedValue(TicketDetails.getResolutionId()) %>
      </dhv:include>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.resolutionDate">Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= TicketDetails.getResolutionDate() %>"
                 dateOnly="true"
                 timeZone="<%= TicketDetails.getResolutionDateTimeZone() %>"
                 showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(TicketDetails.getResolutionDateTimeZone())){ %>
      <br/>
      <zeroio:tz timestamp="<%= TicketDetails.getResolutionDate() %>"
                 timeZone="<%= User.getTimeZone() %>" showTimeZone="true"
                 default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <dhv:include name="ticket.resolution" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.serviceExpectation.question">Have our services
          met or exceeded your expectations?</dhv:label>
      </td>
      <td>
        <dhv:evaluate if="<%= TicketDetails.getExpectation() == 1 %>">
          <dhv:label name="account.yes">Yes</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= TicketDetails.getExpectation() == 0 %>">
          <dhv:label name="account.no">No</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= TicketDetails.getExpectation() == -1 %>">
          <dhv:label name="account.undecided">Undecided</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
  </dhv:include>
</table>
&nbsp;
<br/>
<% if(TicketDetails.isTrashed()){%>
<dhv:permission name="tickets-tickets-delete">
  <input type="button"
         value="<dhv:label name="button.restore">Restore</dhv:label>"
         onClick="javascript:this.form.action='TroubleTickets.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
</dhv:permission>
<% }else if(TicketDetails.getClosed()!=null){ %>
<dhv:permission name="tickets-tickets-edit">
  <input type="button"
         value="<dhv:label name="button.reopen">Reopen</dhv:label>"
         onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
</dhv:permission>
<%}else{%>
<dhv:permission name="tickets-tickets-edit"><input type="button"
                                                   value="<dhv:label name="global.button.modify">Modify</dhv:label>"
                                                   onClick="javascript:this.form.action='TroubleTickets.do?command=Modify&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"></dhv:permission>
<%--
<dhv:permission name="quotes-view">
  <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
    <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&ticketId=<%= TicketDetails.getId() %>';submit();"/>
  </dhv:evaluate>
</dhv:permission>
--%>
<dhv:permission name="tickets-tickets-delete">
  <% if("searchResults".equals(request.getParameter("return"))){ %>
  <input type="button"
         value="<dhv:label name="global.button.delete">Delete</dhv:label>"
         onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
  <%}else{%>
  <input type="button"
         value="<dhv:label name="global.button.delete">Delete</dhv:label>"
         onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
  <%}%>
</dhv:permission>
<%}%>
</dhv:container>
</form>
