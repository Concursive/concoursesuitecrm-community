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
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Name.required", "- Name is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Form.not.submitted", "The form could not be submitted.          \r\nPlease verify the following:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    }
    return true;
  }
</script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    } else {
      scrollReload('Leads.do?command=Search');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<form method="POST" name="inputForm" action="LeadsDocumentsFolders.do?command=Save&auto-populate=true<%= addLinkParams(request, "viewSource") %>" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0" cellpadding="4">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<% }else{ %>
<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<a href="LeadsDocuments.do?command=View&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> > 
<% if(fileFolder.getId() > -1) {%>
  <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
<%} else {%>
  <dhv:label name="pipeline.addDocumentFolder">Add Document Folder</dhv:label>
<%}%>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param="<%= "username="+PipelineViewpointInfo.getVpUserName() %>"><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<% String param1 = "id=" + opportunityHeader.getId();
   String param2 = addLinkParams(request, "viewSource");
%>      
<dhv:container name="opportunities" selected="documents" object="opportunityHeader" param="<%= param1 %>" appendToUrl="<%= param2 %>">
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
<%
String documentFolderList = "LeadsDocuments.do?command=View&headerId="+ opportunityHeader.getId();
String documentModule = "Pipeline";
%>
      <zeroio:folderHierarchy module="<%= documentModule %>" link="<%= documentFolderList %>"/>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" <dhv:label name="global.button.save">Save</dhv:label> " name="save">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='LeadsDocuments.do?command=View<%= addLinkParams(request, "viewSource") %>';"><br />
  <dhv:formMessage /><br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>
          <% if(fileFolder.getId() > -1) {%>
            <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
          <%} else {%>
            <dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
          <%}%>
        </strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="contacts.name">Name</dhv:label></td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue(fileFolder.getSubject()) %>">
        <input type="hidden" name="display" value="-1"/>
        <font color="red">*</font> <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
  </table>
  <br>
  <input type="hidden" name="modified" value="<%= fileFolder.getModifiedString() %>">
  <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
  <input type="hidden" name="id" value="<%= fileFolder.getId() %>">
  <input type="hidden" name="parentId" value="<%= fileFolder.getParentId() %>">
  <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" name="save">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='LeadsDocuments.do?command=View<%= addLinkParams(request, "viewSource") %>';"><br />
</dhv:container>
</form>
</body>
