<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.pipeline.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += "- Subject is required\r\n";
      formTest = false;
    }
    if ((form.clientFilename.value) == "") {
      messageText += "- Filename is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      form.dosubmit.value = "true";
      messageText = "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<a href="Leads.do">Pipeline Management</a> > 
<a href="Leads.do?command=ViewOpp">View Components</a> >
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %>">Opportunity Details</a> >
<a href="LeadsDocuments.do?command=View&headerId=<%= opportunityHeader.getId() %>">Documents</a> > 
Modify Document<br>
<hr color="#BFBFBB" noshade>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
      <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<form method="post" name="inputForm" action="LeadsDocuments.do?command=Update" onSubmit="return checkFileForm(this);">
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); %>      
<dhv:container name="opportunities" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle"><b>Modify Document Information</b>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Subject of file
    </td>
    <td>
      <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
      <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Filename
    </td>
    <td>
      <input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      Version
    </td>
    <td>
      <%= FileItem.getVersion() %>
    </td>
  </tr>
</table>
&nbsp;<br>
  <input type="submit" value=" Update " name="update">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='LeadsDocuments.do?command=View&headerId=<%= opportunityHeader.getId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="headerId" value="<%= opportunityHeader.getId() %>">
	<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
</td>
</tr>
</table>
</form>
</body>
