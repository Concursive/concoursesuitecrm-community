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
      messageText += "- Name is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The form could not be submitted.          \r\nPlease verify the following:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    }
    return true;
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0" cellpadding="4">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
<a href="Leads.do?command=Dashboard">Dashboard</a> >
<% }else{ %>
<a href="Leads.do?command=Search">Search Results</a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Opportunity Details</a> >
<a href="LeadsDocuments.do?command=View&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Documents</a> > 
Add Document Folder
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
%>      
<dhv:container name="opportunities" selected="documents" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr><td class="containerBack">
<form method="POST" name="inputForm" action="LeadsDocumentsFolders.do?command=Save&auto-populate=true<%= addLinkParams(request, "viewSource") %>" onSubmit="return checkForm(this);">
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
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
  <input type="submit" value=" Save " name="save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='LeadsDocuments.do?command=View<%= addLinkParams(request, "viewSource") %>';"><br>
  <%= showError(request, "actionError") %><br>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= (fileFolder.getId() > -1 ? "Rename" : "New" ) %> Folder</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Name</td>
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
  <input type="submit" value=" Save " name="save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='LeadsDocuments.do?command=View<%= addLinkParams(request, "viewSource") %>';"><br>
</form>
</td>
</tr>
</table>
</body>
