<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="TicketDetails" class="com.darkhorseventures.cfsbase.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="com.darkhorseventures.cfsbase.TicketCategoryList" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<%@ include file="initPage.jsp" %>

<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
function HideSpans()
{
	isNS = (document.layers) ? true : false;
	isIE = (document.all) ? true : false;
	
  if( (isIE) )
  {
    //document.all.new0.style.visibility="hidden";
    //document.all.new1.style.visibility="hidden";
    //document.all.new2.style.visibility="hidden";
    //document.all.new3.style.visibility="hidden";
  }
  else if( (isNS) )
  {
    document.new0.visibility="hidden";
    document.new1.visibility="hidden";
    document.new2.visibility="hidden";
    document.new3.visibility="hidden";
  }

  return true;
}

function ShowSpan(thisID)
{
	isNS4 = (document.layers) ? true : false;
	isIE4 = (document.all && !document.getElementById) ? true : false;
	isIE5 = (document.all && document.getElementById) ? true : false;
	isNS6 = (!document.all && document.getElementById) ? true : false;

	if (isNS4){
	elm = document.layers[thisID];
	}
	else if (isIE4) {
	elm = document.all[thisID];
	}
	else if (isIE5 || isNS6) {
	elm = document.getElementById(thisID);
	elm.style.visibility="visible";
	}
   
}


//  End -->
</SCRIPT>

<body onLoad="HideSpans();">
<form name="details" action="/TroubleTickets.do?command=Update&auto-populate=true" method="post">
<a href="TroubleTickets.do?command=Home">Back to Ticket List</a>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td bgColor="#FFFF95"><strong>Ticket #<%=TicketDetails.getId()%> - <%=toHtml(TicketDetails.getCompanyName())%></strong></td>
  </tr>
  <tr>
    <td bgColor="#F1F0E0">
      <% if (TicketDetails.getClosed() != null) { %>
      	<font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font>
      <%} else {%>
      &nbsp;
      <%}%>
    </td>
  </tr>
  
  <tr>
  	<td>
  	<table cellpadding="0" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      				<tr>
		<td colspan=2 bgColor="white">
		
		<% if (TicketDetails.getClosed() != null) { %>
		        <input type=button value="Reopen">
			<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
		<%} else {%>
			<input type=submit value="Update">
			<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
			<%= showAttribute(request, "closedError") %>
		<%}%>
		

		</td>
		</tr>
		
				</table>
		<br>
		<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
		
		
		<tr bgcolor="#DEE0FA">
		<td colspan=2 valign=center align=left>
		<strong>Ticket Information</strong>
		</td>     
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Ticket Source
		</td>
		<td bgColor="white">
		<%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
		</td>
		</tr>
		
		<tr>
		<td nowrap class="formLabel">
		Contact
		</td>
		<td colspan=1 valign=center>
		<%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
		<font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
		</td>
		</tr>
		
		</table>
		<br>
		<a name="categories"></a> 
		<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
		<tr bgcolor="#DEE0FA">
		<td colspan=2 valign=center align=left>
		<strong>Classification</strong>
		</td>     
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
      		Problem
    		</td>
		
		<td valign=top bgColor="white">
		<textarea name="problem" cols=55 rows=3><%=TicketDetails.getProblem()%></textarea>
		<font color="red">*</font> <%= showAttribute(request, "problemError") %>
		<input type=hidden name=orgId value="<%=TicketDetails.getOrgId()%>">
		<input type=hidden name=id value="<%=TicketDetails.getId()%>">
		<input type=hidden name=companyName value="<%=toHtml(TicketDetails.getCompanyName())%>">
		<input type=hidden name=refresh value="-1">
		
		</td>
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Category
		</td>
		<td bgColor="white">
		<%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
		
			<% if (TicketDetails.getCatCode() == 0) { %>
			<input type=checkbox name="newCat0chk" onClick="javascript:ShowSpan('new0')">add new<span name="new0" ID="new0" style="position:relative; visibility:hidden">&nbsp;<input type=text size=25 name=newCat0></span>
			<%}%>
		
		</td>
		</tr>
				
		<tr>
		<td width=100 class="formLabel">
		Sub-level 1
		</td>
		<td bgColor="white">
		<%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
		
			<% if (TicketDetails.getCatCode() != 0 && TicketDetails.getSubCat1() == 0) { %>
			<input type=checkbox name="newCat1chk" onClick="javascript:ShowSpan('new1')">add new<span name="new1" ID="new1" style="visibility:hidden">&nbsp;<input type=text size=25 name=newCat1></span>
			<%}%>
		</td>
		</tr>
				
		<tr>
		<td width=100 class="formLabel">
		Sub-level 2
		</td>
		<td bgColor="white">
		<%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
		
			<% if (TicketDetails.getSubCat1() != 0 && TicketDetails.getCatCode() != 0 && TicketDetails.getSubCat2() == 0) { %>
			<input type=checkbox name="newCat2chk" onClick="javascript:ShowSpan('new2')">add new<span name="new2" ID="new2" style="visibility:hidden">&nbsp;<input type=text size=25 name=newCat2></span>
			<%}%>
		
		</td>
		</tr>
				
		<tr>
		<td width=100 class="formLabel">
		Sub-level 3
		</td>
		<td bgColor="white">
		<%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
		
			<% if (TicketDetails.getSubCat2() != 0 && TicketDetails.getCatCode() != 0 && TicketDetails.getSubCat1() != 0) { %>
			<input type=checkbox name="newCat3chk" onClick="javascript:ShowSpan('new3')">add new<span name="new3" ID="new3" style="visibility:hidden">&nbsp;<input type=text size=25 name=newCat3></span>
			<%}%>
		
		</td>
		</tr>
		
		</table>
		<br>
		<a name="department"></a> 
		<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
		<tr bgcolor="#DEE0FA">
		<td colspan=2 valign=center align=left>
		<strong>Assignment</strong>
		</td>     
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Severity
		</td>
		<td bgColor="white">
		<%= SeverityList.getHtmlSelect("severityCode", TicketDetails.getSeverityCode()) %>
		</td>
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Priority
		</td>
		<td bgColor="white">
		<%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
		
		</td>
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Department
		</td>
		<td bgColor="white">
		<%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
		</td>
		</tr>
		
		<tr>
		<td nowrap class="formLabel">
		Reassign To
		</td>
		<td colspan=1 valign=center>
		<%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
		</td>
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
      		User Comments
    		</td>
		
		<td bgColor="white">
		<textarea name=newticketlogentry cols=55 rows=3><%=TicketDetails.getNewticketlogentry()%></textarea>
		</td>
		</tr>
		
		</table>
		<br>
		<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
		<tr bgcolor="#DEE0FA">
		<td colspan=2 valign=center align=left>
		<strong>Resolution</strong>
		</td>     
		</tr>
		
		<tr>
		<td width=100 class="formLabel">
		Solution
		</td>
		
		<td bgColor="white">
		<textarea name=solution cols=55 rows=3><% if (TicketDetails.getSolution() != null) {%><%=TicketDetails.getSolution()%><%}%></textarea>
		<input type=checkbox name="closeNow">Close ticket
		</td>
		</tr>
		
				<tr>
	<td width=100 class="formLabel">
	Knowledge Base
	</td>
	
	<td bgColor="white">
	<input type=checkbox name="kbase" checked>Include this solution
	</td>
	</tr>

		
	</table>
	
	</td>
  </tr>
      	<tr>
  	<td>
  	<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
	
			<tr bgcolor="#DEE0FA">
		<td colspan=4 valign=center align=left>
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
			
			<td nowrap valign=center width=100 class="formLabel">
			<%=toHtml(thisEntry.getEnteredByName())%>
			</td>
			<td nowrap valign=center width=150>
			<%=thisEntry.getEnteredString()%>
			</td>
			<td valign=center>
			<%=toHtml(thisEntry.getEntryText())%>
			</td>
			</tr>
	<%    
			}
		} else {
	%>
			<tr><td>
			<font color="#9E9E9E" colspan=3>No Log Entries.</font>
			</td></tr>
		<%}%>
			
			   	</table>
	</td></tr>
		
		<tr>
		<td colspan=3 bgColor="white">
		
		<% if (TicketDetails.getClosed() != null) { %>
		        <input type=button value="Reopen">
			<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
		<%} else {%>
			<input type=submit value="Update">
			<input type="submit" value="Cancel" onClick="javascript:this.form.action='/TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>'">
		<%}%>
		</td>
		
		</tr>

</table>
</form>

