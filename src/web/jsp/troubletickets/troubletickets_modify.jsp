<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.communications.base.Campaign,org.aspcfs.modules.communications.base.CampaignList,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function updateSubList1() {
  var sel = document.forms['details'].elements['catCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&catCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList2() {
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat1=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList3() {
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}

function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  if (form.closeNow.checked && form.solution.value == "") { 
   alert("Resolution needs to be filled in when closing a ticket");
   return false;
  }
  return true;
}
</SCRIPT>
<body>
<form name="details" action="TroubleTickets.do?command=Update&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do">Help Desk</a> > 
<% if (("list".equals((String)request.getParameter("return"))) ||
      ("searchResults".equals((String)request.getParameter("return")))) {%>
    <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
      <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
    <%}else{%> 
      <a href="TroubleTickets.do?command=Home">View Tickets</a> >
    <%}%>
<%} else {%>
  <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
    <a href="TroubleTickets.do?command=SearchTickets">Search Tickets</a> >
  <%}else{%> 
    <a href="TroubleTickets.do?command=Home">View Tickets</a> >
  <%}%>
    <a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<%}%>
Modify Ticket
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
        <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font><br>
        &nbsp;<br>
      </dhv:evaluate>
      <% if (TicketDetails.getClosed() != null) { %>
        <input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();">
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
      <%} else {%>
        <input type="submit" value="Update" onClick="return checkForm(this.form)">
        <% if ("list".equals(request.getParameter("return"))) {%>
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
        <%} else if ("searchResults".equals(request.getParameter("return"))){%> 
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=SearchTickets'">
        <% }else {%>
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
        <%}%>
      <%}%>
      <br>
<%= showError(request, "actionError") %>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
      <th colspan="2">
        <strong>Ticket Information</strong>
      </th>
		</tr>
		<tr class="containerBody">
      <td class="formLabel">
        Ticket Source
      </td>
      <td>
        <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td class="formLabel">
        Contact
      </td>
      <td>
      <% if ( TicketDetails.getThisContact() == null ) {%>
        <%= ContactList.getHtmlSelect("contactId", 0 ) %>
      <%} else {%>
        <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
      <%}%>
        <font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
      </td>
		</tr>
    <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract"><%= toHtml((TicketDetails.getContractId() != -1) ? TicketDetails.getServiceContractNumber() :"None Selected") %></div>
        </td>
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%= TicketDetails.getContractId() %>">
          &nbsp;
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled', <%= TicketDetails.getOrgId() %>);">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addServiceContract','None Selected');javascript:resetNumericFieldValue('contractId');javascript:changeDivContent('addAsset','None Selected');javascript:resetNumericFieldValue('assetId');javascript:changeDivContent('addLaborCategory','None Selected');javascript:resetNumericFieldValue('productId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Asset
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addAsset"><%= toHtml((TicketDetails.getAssetId() != -1) ? TicketDetails.getAssetSerialNumber():"None Selected")%></div>
        </td>
        <td>
          <input type="hidden" name="assetId" id="assetId" value="<%=  TicketDetails.getAssetId() %>">
          &nbsp;
          <%= showAttribute(request, "assetIdError") %>
          [<a href="javascript:popAssetListSingle('assetId','addAsset', 'filters=allassets|undercontract','contractId','addServiceContract');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addAsset','None Selected');javascript:resetNumericFieldValue('assetId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Labor Category
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addLaborCategory"><%= (TicketDetails.getProductId() != -1) ? TicketDetails.getProductSku() :"None Selected" %></div>
        </td>
        <td>
          <input type="hidden" name="productId" id="productId" value="<%=  TicketDetails.getProductId() %>">
          &nbsp;
          <%= showAttribute(request, "productIdError") %>
          [<a href="javascript:popProductListSingle('productId','addLaborCategory', 'filters=all|my|disabled');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addLaborCategory','None Selected');javascript:resetNumericFieldValue('productId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </table>
  <br />
  <a name="categories"></a> 
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
      <th colspan="2">
        <strong>Classification</strong>
      </th>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="ticket.issue">Issue</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <textarea name="problem" cols="55" rows="3"><%= toString(TicketDetails.getProblem()) %></textarea>
            </td>
            <td valign="top">
              <font color="red">*</font> <%= showAttribute(request, "problemError") %>
              <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
              <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
              <input type="hidden" name="orgId" id="orgId" value="<%=TicketDetails.getOrgId()%>">
              <input type="hidden" name="companyName" value="<%=toHtml(TicketDetails.getCompanyName())%>">
              <input type="hidden" name="refresh" value="-1">
            </td>
          </tr>
        </table>
		<% if (request.getParameter("return") != null) {%>
			<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
		<%}%>
      </td>
		</tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Location
      </td>
      <td>
        <input type="text" name="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50" maxlength="256" />
      </td>
    </tr>
  <dhv:include name="ticket.catCode" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Category
      </td>
      <td>
        <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.subCat1" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 1
      </td>
      <td>
        <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.subCat2" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 2
      </td>
      <td>
        <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.subCat3" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 3
      </td>
      <td>
        <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.severity" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Severity
      </td>
      <td>
        <%= SeverityList.getHtmlSelect("severityCode", TicketDetails.getSeverityCode()) %>
      </td>
		</tr>
  </dhv:include>
  </table>
  <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Assignment</strong>
      </th>
		</tr>
  <dhv:include name="ticket.priority" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Priority
      </td>
      <td>
        <%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
      </td>
		</tr>
  </dhv:include>
		<tr class="containerBody">
      <td class="formLabel">
        Department
      </td>
      <td>
        <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td nowrap class="formLabel">
        Resource Assigned
      </td>
      <td>
        <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
      </td>
		</tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Assignment Date
      </td>
      <td>
        <input type="text" size="10" name="assignedDate" value="<dhv:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
        <a href="javascript:popCalendar('details', 'assignedDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Estimated Resolution Date
      </td>
      <td>
        <input type="text" size="10" name="estimatedResolutionDate" value="<dhv:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
        <a href="javascript:popCalendar('details', 'estimatedResolutionDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      </td>
    </tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        Issue Notes
      </td>
      <td>
        <textarea name="comment" cols="55" rows="3"><%= toString(TicketDetails.getComment()) %></textarea>
      </td>
		</tr>
  </table>
  <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Resolution</strong>
      </th>
		</tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Cause
      </td>
      <td>
        <textarea name="cause" cols="55" rows="3"><%= toString(TicketDetails.getCause()) %></textarea>
      </td>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        Resolution
      </td>
      <td>
        <textarea name="solution" cols="55" rows="3"><%= toString(TicketDetails.getSolution()) %></textarea><br>
        <input type="checkbox" name="closeNow">Close ticket
        <%--
        <br>
        <input type="checkbox" name="kbase">Add this solution to Knowledge Base
        --%>
<%-- Added for voice demo, will show a list of surveys that can be emailed... --%>
<%--
<%
        CampaignList campaignList = (CampaignList) request.getAttribute("CampaignList");
        if (campaignList != null && campaignList.size() > 0) {
          HtmlSelect campaignSelect = new HtmlSelect();
          campaignSelect.addItem(-1, "-- None --");
          Iterator campaigns = campaignList.iterator();
          while (campaigns.hasNext()) {
            Campaign thisCampaign = (Campaign)campaigns.next();
            campaignSelect.addItem(thisCampaign.getId(), thisCampaign.getName());
          }
%>
        <br>Send contact a follow-up message: <%= campaignSelect.getHtml("campaignId", TicketDetails.getCampaignId()) %>
<%
        }
%>
--%>
<%-- End voice demo --%>
      </td>
		</tr>
    <tr class="containerBody">
      <td class="formLabel">
        Resolution Date
      </td>
      <td>
        <input type="text" size="10" name="resolutionDate" value="<dhv:tz timestamp="<%= TicketDetails.getResolutionDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
        <a href="javascript:popCalendar('details', 'resolutionDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Have our services met or exceeded your expectations?
    </td>
    <td>
      <input type="radio" name="expectation" value="1" <%= (TicketDetails.getExpectation() == 1) ? " checked" : "" %>>Yes
      <input type="radio" name="expectation" value="0" <%= (TicketDetails.getExpectation() == 0) ? " checked" : "" %>>No
      <input type="radio" name="expectation" value="-1" <%= (TicketDetails.getExpectation() == -1) ? " checked" : "" %>>Undecided
    </td>
  </tr>
	</table>
&nbsp;<br>
<% if (TicketDetails.getClosed() != null) { %>
  <input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();">
<%} else {%>
  <input type="submit" value="Update" onClick="return checkForm(this.form)">
<%}%>
	<% if ("list".equals(request.getParameter("return"))) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
  <%} else if ("searchResults".equals(request.getParameter("return"))){%> 
    <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=SearchTickets'">
  <%} else {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
  <%}%>
  </td>
  </tr>
</table>
</form>
</body>
