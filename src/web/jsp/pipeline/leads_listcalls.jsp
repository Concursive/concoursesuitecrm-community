<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.contacts.base.Call,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="LeadsCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="LeadsCallListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
Calls<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
      <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(opportunityHeader.getDescription()) %></strong>&nbsp;
      <dhv:evaluate exp="<%= (opportunityHeader.getAccountEnabled() && opportunityHeader.getAccountLink() > -1) %>">
        <dhv:permission name="accounts-view,accounts-accounts-view">[ <a href="Accounts.do?command=Details&orgId=<%= opportunityHeader.getAccountLink() %>">Go to this Account</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= opportunityHeader.getContactLink() > -1 %>">
        <dhv:permission name="contacts-view,contacts-external_contacts-view">[ <a href="ExternalContacts.do?command=ContactDetails&id=<%= opportunityHeader.getContactLink() %>">Go to this Contact</a> ]</dhv:permission>
      </dhv:evaluate>
      <dhv:evaluate if="<%= opportunityHeader.hasFiles() %>">
        <% FileItem thisFile = new FileItem(); %>
        <%= thisFile.getImageTag()%>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + opportunityHeader.getId(); %>      
      <dhv:container name="opportunities" selected="calls" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    <dhv:permission name="contacts-external_contacts-calls-add"><a href="LeadsCalls.do?command=Add&headerId=<%= opportunityHeader.getId() %>">Add a Call</a></dhv:permission>
    <center><%= LeadsCallListInfo.getAlphabeticalPageLinks() %></center>
  <%= showError(request, "actionError") %>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
    <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete">
      <td valign=center align=left>
        <strong>Action</strong>
      </td>
    </dhv:permission>  
      <td valign=center align=left>
        <strong>Subject</strong>
      </td>
    
      <td valign=center align=left>
        <strong>Type</strong>
      </td>
      
      <td valign=center align=center>
        <strong>Length</strong>
      </td>
      
      <td valign=center align=center>
        <strong>Date</strong>
      </td>
    </tr>
  
  <%
    Iterator j = LeadsCallList.iterator();
    
    if ( j.hasNext() ) {
      int rowid = 0;
        while (j.hasNext()) {
      
          if (rowid != 1) {
            rowid = 1;
          } else {
            rowid = 2;
          }
      
      Call thisCall = (Call)j.next();
  %>      
      <tr class="containerBody">
        <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete">
        <td width=8 valign=center nowrap class="row<%= rowid %>">
            <dhv:permission name="pipeline-opportunities-calls-edit"><a href="LeadsCalls.do?command=Modify&id=<%= thisCall.getId() %>&headerId=<%= opportunityHeader.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete" all="true">|</dhv:permission><dhv:permission name="pipeline-opportunities-calls-delete"><a href="javascript:confirmDelete('LeadsCalls.do?command=Delete&id=<%= thisCall.getId() %>&headerId=<%= opportunityHeader.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        
        <td width="100%" valign=center class="row<%= rowid %>">
          <a href="LeadsCalls.do?command=Details&id=<%= thisCall.getId() %>&headerId=<%= opportunityHeader.getId() %>">
          <%= toHtml(thisCall.getSubject()) %>
          </a>
        </td>
        <td valign=center nowrap class="row<%= rowid %>">
          <%= toHtml(thisCall.getCallType()) %>
        </td>
        
        <td align=center valign=center nowrap class="row<%= rowid %>">
          <%= toHtml(thisCall.getLengthText()) %>
        </td>
        
        <td valign=center nowrap class="row<%= rowid %>">
          <%= toHtml(thisCall.getEnteredString()) %>
        </td>
      </tr>
   <%}%>
  <%} else {%>
      <tr class="containerBody">
        <td colspan=5 valign=center>
          No calls found.
        </td>
      </tr>
  <%}%>
  </table>
<dhv:pagedListControl object="LeadsCallListInfo"/>
</td>
</tr>
</table>
</body>
