<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
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
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
function HideSpans() {
	isNS = (document.layers) ? true : false;
	isIE = (document.all) ? true : false;
  if( (isIE) ) {
    //document.all.new0.style.visibility="hidden";
    //document.all.new1.style.visibility="hidden";
    //document.all.new2.style.visibility="hidden";
    //document.all.new3.style.visibility="hidden";
  } else if( (isNS) ) {
    document.new0.visibility="hidden";
    document.new1.visibility="hidden";
    document.new2.visibility="hidden";
    document.new3.visibility="hidden";
  }
  return true;
}
function ShowSpan(thisID) {
	isNS4 = (document.layers) ? true : false;
	isIE4 = (document.all && !document.getElementById) ? true : false;
	isIE5 = (document.all && document.getElementById) ? true : false;
	isNS6 = (!document.all && document.getElementById) ? true : false;
	if (isNS4){
    elm = document.layers[thisID];
	} else if (isIE4) {
    elm = document.all[thisID];
	}	else if (isIE5 || isNS6) {
    elm = document.getElementById(thisID);
    elm.style.visibility="visible";
	}
}
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
//  End -->
</SCRIPT>
<body onLoad="HideSpans();">
<form name="details" action="TroubleTickets.do?command=Update&auto-populate=true" method="post">
<a href="TroubleTickets.do">Tickets</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="TroubleTickets.do?command=Home">View Tickets</a> >
  <%}%>
<%} else {%>
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<%}%>
Modify Ticket<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td class="containerHeader">
      <strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
      <%= toHtml(TicketDetails.getCompanyName()) %></strong>
      <dhv:evaluate if="<%= !(TicketDetails.getCompanyEnabled()) %>">
        <font color="red">(account disabled)</font>
      </dhv:evaluate>
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden;display:none" height="0"></iframe>
    </td>
  </tr>
<% if (TicketDetails.getClosed() != null) { %>  
  <tr>
    <td bgColor="#F1F0E0">
      <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font>
    </td>
  </tr>
<%}%>
  <tr>
  	<td class="containerBack">
  <table cellpadding="0" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr>
      <td colspan="2">
		<% if (TicketDetails.getClosed() != null) { %>
      <input type="button" value="Reopen">
			<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
		<%} else {%>
			<input type="submit" value="Update">
      <% if (request.getParameter("return") != null) {%>
        <% if (request.getParameter("return").equals("list")) {%>
          <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
        <%}%>
      <%} else {%>
        <input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
      <%}%>
		<%}%>
      </td>
    </tr>
  </table>
<%= showError(request, "actionError") %>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
		<tr class="title">
      <td colspan="2">
        <strong>Ticket Information</strong>
      </td>     
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
  </table>
  <br>
  <a name="categories"></a> 
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
		<tr class="title">
      <td colspan="2">
        <strong>Classification</strong>
      </td>     
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="tickets-problem">Issue</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <textarea name="problem" cols="55" rows="3"><%= toString(TicketDetails.getProblem()) %></textarea>
            </td>
            <td valign="top">
              <font color="red">*</font> <%= showAttribute(request, "problemError") %>
              <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
              <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
              <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
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
  <dhv:include name="tickets-code" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Category
      </td>
      <td>
        <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
<% if (TicketDetails.getCatCode() == 0) { %>
        <input type="checkbox" name="newCat0chk" onClick="javascript:ShowSpan('new0')">add new<span name="new0" ID="new0" style="position:relative; visibility:hidden">&nbsp;<input type=text size=25 name=newCat0></span>
<%}%>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-subcat1" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 1
      </td>
      <td>
        <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
        <% if (TicketDetails.getCatCode() != 0 && TicketDetails.getSubCat1() == 0) { %>
          <input type="checkbox" name="newCat1chk" onClick="javascript:ShowSpan('new1')">add new<span name="new1" ID="new1" style="visibility:hidden">&nbsp;<input type=text size=25 name=newCat1></span>
        <%}%>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-subcat2" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 2
      </td>
      <td>
        <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
        <% if (TicketDetails.getSubCat1() != 0 && TicketDetails.getCatCode() != 0 && TicketDetails.getSubCat2() == 0) { %>
          <input type="checkbox" name="newCat2chk" onClick="javascript:ShowSpan('new2')">add new<span name="new2" ID="new2" style="visibility:hidden">&nbsp;<input type=text size=25 name=newCat2></span>
        <%}%>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-subcat3" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 3
      </td>
      <td>
        <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
        <% if (TicketDetails.getSubCat2() != 0 && TicketDetails.getCatCode() != 0 && TicketDetails.getSubCat1() != 0) { %>
          <input type="checkbox" name="newCat3chk" onClick="javascript:ShowSpan('new3')">add new<span name="new3" ID="new3" style="visibility:hidden">&nbsp;<input type=text size=25 name=newCat3></span>
        <%}%>
      </td>
		</tr>
  </dhv:include>
  </table>
  <br>
  <a name="department"></a> 
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="2">
        <strong>Assignment</strong>
      </td>
		</tr>
  <dhv:include name="tickets-severity" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Severity
      </td>
      <td>
        <%= SeverityList.getHtmlSelect("severityCode", TicketDetails.getSeverityCode()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="tickets-priority" none="true">
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
        Reassign To
      </td>
      <td>
        <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        User Comments
      </td>
      <td>
        <textarea name="comment" cols="55" rows="3"><%= toString(TicketDetails.getComment()) %></textarea>
      </td>
		</tr>
  </table>
  <br>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
      <td colspan="2">
        <strong>Resolution</strong>
      </td>
		</tr>
		<tr>
      <td valign="top" class="formLabel">
        Solution
      </td>
      <td>
        <textarea name="solution" cols="55" rows="3"><%= toString(TicketDetails.getSolution()) %></textarea><br>
        <input type="checkbox" name="closeNow">Close ticket<br>
        <input type="checkbox" name="kbase">Add this solution to Knowledge Base
<%-- Added for voice demo, will show a list of surveys that can be emailed... --%>
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
<%-- End voice demo --%>
      </td>
		</tr>
	</table>
  &nbsp;
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="3">
        <strong>Ticket Log History</strong>
      </td>     
    </tr>
<%  
		Iterator hist = TicketDetails.getHistory().iterator();
		if (hist.hasNext()) {
			while (hist.hasNext()) {
				TicketLog thisEntry = (TicketLog)hist.next();
	%>    
  <% if (thisEntry.getSystemMessage() == true) {%>
    <tr bgColor="#F1F0E0">
  <% } else { %>
    <tr class="containerBody">
  <%}%>
			<td nowrap valign="top" class="formLabel">
        <%=toHtml(thisEntry.getEnteredByName())%>
			</td>
			<td nowrap valign="top">
        <%=thisEntry.getEnteredString()%>
			</td>
			<td valign="top" width="100%">
        <%= toHtml(thisEntry.getEntryText()) %>
			</td>
    </tr>
	<%    
			}
		} else {
	%>
    <tr class="containerBody">
      <td colspan="3">
        No Log Entries.
			</td>
    </tr>
  <%}%>
</table>
&nbsp;<br>
<% if (TicketDetails.getClosed() != null) { %>
  <input type="button" value="Reopen">
<%} else {%>
  <input type="submit" value="Update">
<%}%>
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
	<%}%>
<%} else {%>
	<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
<%}%>
  </td>
  </tr>
</table>
</form>
</body>
