<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.pipeline.base.OpportunityHeader,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OpportunityHeaderList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="ExternalOppsPagedListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="ExternalContacts.do">General Contacts</a> > 
<a href="ExternalContacts.do?command=ListContacts">View Contacts</a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>">Contact Details</a> >
Opportunities<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(ContactDetails.getNameFull()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + ContactDetails.getId(); %>      
      <dhv:container name="contacts" selected="opportunities" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="contacts-external_contacts-opportunities-add"><a href="ExternalContactsOpps.do?command=AddOpp&contactId=<%= ContactDetails.getId() %>">Add an Opportunity</a></dhv:permission>
<center><%= ExternalOppsPagedListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ExternalContactsOpps.do?command=ViewOpps&contactId=<%= ContactDetails.getId() %>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ExternalOppsPagedListInfo.getOptionValue("my") %>>My Opportunities </option>
        <option <%= ExternalOppsPagedListInfo.getOptionValue("all") %>>All Opportunities</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ExternalOppsPagedListInfo"/>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
    <td valign=center align=left>
      <strong>Action</strong>
    </td>
    </dhv:permission>
  
    <td valign=center align=left>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>&column=description">Opportunity Name</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("description") %>
    </td>
    
    <td valign=center align=left>
      <strong>Best Guess Total</strong>
    </td>
    
    <td valign=center align=left>
      <strong><a href="ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>&column=modified">Last Modified</a></strong>
      <%= ExternalOppsPagedListInfo.getSortIcon("modified") %>
    </td>
    
  </tr>
<%
	Iterator j = OpportunityHeaderList.iterator();
  FileItem thisFile = new FileItem();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	    while (j.hasNext()) {
		
        if (rowid != 1) {
          rowid = 1;
        } else {
          rowid = 2;
        }
		
		OpportunityHeader thisOpp = (OpportunityHeader)j.next();
%>      
    <tr class="containerBody">
      <dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
      <dhv:permission name="contacts-external_contacts-opportunities-edit"><a href="ExternalContactsOpps.do?command=ModifyOpp&oppId=<%= thisOpp.getOppId() %>&contactId=<%= thisOpp.getContactLink() %>&return=list">Edit</a></dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-delete" all="true">|</dhv:permission><dhv:permission name="contacts-external_contacts-opportunities-delete"><a href="javascript:popURLReturn('ExternalContactsOpps.do?command=ConfirmDelete&contactId=<%=ContactDetails.getId()%>&id=<%=thisOpp.getOppId()%>','ExternalContactsOpps.do?command=ViewOpps&contactId=<%=ContactDetails.getId()%>', 'Delete_opp','320','200','yes','no');">Del</a></dhv:permission>
      </td>
      </dhv:permission>
      <td width=40% valign=center class="row<%= rowid %>">
        <a href="ExternalContactsOpps.do?command=DetailsOpp&oppId=<%=thisOpp.getOppId()%>&contactId=<%= ContactDetails.getId() %>">
        <%= toHtml(thisOpp.getDescription()) %></a>
        (<%=thisOpp.getComponentCount()%>)
        
        <% if (thisOpp.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>
      </td>  
      <td width="115" valign=center class="row<%= rowid %>">
        $<%= toHtml(thisOpp.getTotalValueCurrency()) %>
      </td>      
      <td valign=center class="row<%= rowid %>">
        <%= toHtml(thisOpp.getModifiedString()) %>
      </td>       
    </tr>
<%
    }
	} else {
%>
    <tr class="containerBody">
      <td colspan=5 valign=center>
        No opportunities found.
      </td>
    </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="ExternalOppsPagedListInfo"/>
</td>
</tr>
</table>

