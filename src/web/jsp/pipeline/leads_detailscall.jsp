<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CallDetails" class="org.aspcfs.modules.contacts.base.Call" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<form name="addCall" action="LeadsCalls.do?id=<%= CallDetails.getId() %>&headerId=<%= opportunityHeader.getId() %>" method="post">
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Opportunities</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
<a href="LeadsCalls.do?command=View&headerId=<%= opportunityHeader.getId() %>">Calls</a> >
Call Details<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
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
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete"><input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardMessage&forwardType=<%= Constants.PIPELINE_CALLS %>&headerId=<%= opportunityHeader.getId() %>&id=<%=CallDetails.getId()%>&return='+escape('LeadsCalls.do?command=Details&id=<%=CallDetails.getId()%>&headerId=<%= opportunityHeader.getId() %>') + '&sendUrl=' + escape('LeadsCallsForward.do?command=SendMessage&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %>');"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br>&nbsp;</dhv:permission>
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="2">
            <strong>Call Details</strong>
          </td>     
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Type
          </td>
          <td>
            <%= toHtml(CallDetails.getCallType()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Contact Name
          </td>
          <td>
            <%= toHtml(CallDetails.getContactName()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Length
          </td>
          <td>
            <%= toHtml(CallDetails.getLengthText()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Subject
          </td>
          <td>
            <%= toHtml(CallDetails.getSubject()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Notes
          </td>
          <td>
            <%= toHtml(CallDetails.getNotes()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Alert Description
          </td>
          <td>
            <%= toHtml(CallDetails.getAlertText()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Alert Date
          </td>
          <td>
            <%= toHtml(CallDetails.getAlertDateString()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Entered
          </td>
          <td>
            <%= toHtml(CallDetails.getEnteredName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getEnteredString()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            Modified
          </td>
          <td>
            <%= toHtml(CallDetails.getModifiedName()) %>&nbsp;-&nbsp;<%= toHtml(CallDetails.getModifiedString()) %>
          </td>
        </tr>
      </table>
      <dhv:permission name="pipeline-opportunities-calls-edit,pipeline-opportunities-calls-delete"><br></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit"><input type="submit" name="command" value="Modify"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete"><input type="submit" name="command" value="Delete" onClick="javascript:return confirmAction()"></dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view"><input type="button" name="action" value="Forward" onClick="javascript:window.location.href='LeadsCallsForward.do?command=ForwardMessage&forwardType=<%= Constants.PIPELINE_CALLS %>&headerId=<%= opportunityHeader.getId() %>&id=<%=CallDetails.getId()%>&return='+escape('LeadsCalls.do?command=Details&id=<%=CallDetails.getId()%>&headerId=<%= opportunityHeader.getId() %>') + '&sendUrl=' + escape('LeadsCallsForward.do?command=SendMessage&headerId=<%= opportunityHeader.getId() %>&id=<%= CallDetails.getId() %>');"></dhv:permission>
    </td>
  </tr>
</table>
