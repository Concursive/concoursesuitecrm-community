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
  - Version: $Id:  $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants,org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="selectedDocuments" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="finalDocuments" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="documentStoreList" class="org.aspcfs.modules.documents.base.DocumentStoreList" scope="request"/>
<jsp:useBean id="projectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>

<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
<jsp:useBean id="contactId" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popDocuments.js?1"></SCRIPT>
<script language="JavaScript">
  function checkFileForm(form) {
    var formTest = true;
    var messageText = "";
    if (checkNullString(form.subject.value)) {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if (form.attachment.value.length < 5) {
      messageText += label("file.required", "- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      alert(messageText);
      return false;
    } else {
      form.moduleId.value = document.listForm.moduleId[document.listForm.moduleId.selectedIndex].value;
      if (form.upload.value != label("button.pleasewait","Please Wait...")) {
        form.upload.value=label("button.pleasewait","Please Wait...");
        return true;
      } else {
        return false;
      }
    }
  }
  
</script>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
     String source = request.getParameter("source");
%>
<%-- Navigating the folder and document list, not the final submit --%>
<body>
<form method="post" name="listForm" action="DocumentSelector.do?command=ListDocuments">
  <input type="hidden" name="dosubmit" value="true">
  <dhv:formMessage showSpace="false"/>
  <%String orgParam="";%>
  <dhv:evaluate if = '<%= !"".equals(orgId) %>'>
    <%orgParam+="&orgId="+orgId; %>
  </dhv:evaluate>
  <dhv:evaluate if = '<%= !"".equals(contactId) %>'>
    <%orgParam+="&contactId="+contactId; %>
  </dhv:evaluate>
  <% 
    String previousSelection="";
    Iterator iter = selectedDocuments.iterator();
    while (iter.hasNext())
    {
    	previousSelection+=(String)iter.next()+"|";
    }

	String module = request.getParameter("moduleId");

	if (module == null) { module = (String) request.getAttribute("moduleId");}

	int moduleId = Integer.parseInt(module);

	String linkItem = request.getParameter("linkItemId");

	if (linkItem == null) { linkItem = (String) request.getAttribute("linkItemId");}

	int linkItemId = -1;

	if (linkItem != null){linkItemId=Integer.parseInt(linkItem);}

	String link = "DocumentSelector.do?command=ListDocuments"+orgParam+"&previousSelection="+previousSelection+"&moduleId="+ module +"&linkItemId="+linkItemId+"&listType="+ toHtmlValue(request.getParameter("listType")) + "&displayFieldId=" + toHtmlValue(request.getParameter("displayFieldId")) + "&hiddenFieldId="+ toHtmlValue(request.getParameter("hiddenFieldId"));
  %> 
  
<%-- START Document folder trails--%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <dhv:evaluate if="<%= (linkItemId != -1 && moduleId==Constants.DOCUMENTS_DOCUMENTS)%>">
        <a href="javascript:document.listForm.submit();"><dhv:label name="documents.DocumentStoreList">Document Store List</dhv:label></a> > 
      </dhv:evaluate>
      <dhv:evaluate if="<%= (linkItemId != -1 && moduleId==Constants.PROJECTS_FILES)%>">
        <a href="javascript:document.listForm.submit();"><dhv:label name="project.projectList">Project List</dhv:label></a> >
      </dhv:evaluate>
      <dhv:evaluate if="<%= (linkItemId == -1 && moduleId==Constants.DOCUMENTS_DOCUMENTS)%>">
        <dhv:label name="documents.DocumentStoreList">Document Store List</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= (linkItemId == -1 && moduleId==Constants.PROJECTS_FILES)%>">
        <dhv:label name="project.projectList">Project List</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= linkItemId != -1%>">
        <zeroio:folderHierarchy module="<%= module %>" link="<%= link %>" />
      </dhv:evaluate>
    </td>
  </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td align="left" valign="center">
      <select size="1" name="moduleId" onChange="javascript:document.listForm.submit();">
    <dhv:evaluate if="<%= (!"".equals(contactId) && !"".equals(orgId))%>">
      <dhv:evaluate if="<%=!"-1".equals(orgId) %>">
        <dhv:permission name="accounts-accounts-documents-view">
       		<option value="<%= Constants.ACCOUNTS %>" <dhv:evaluate if="<%= moduleId == Constants.ACCOUNTS %>">selected</dhv:evaluate>><dhv:label name="accounts.accounts">Accounts</dhv:label></option>
		</dhv:permission>
       </dhv:evaluate>
       <dhv:evaluate if="<%= (!"-1".equals(orgId) && !"-1".equals(contactId)) %>">
       	 <dhv:permission name="accounts-accounts-contacts-documents-view">
       		<option value="<%= Constants.CONTACTS %>" <dhv:evaluate if="<%= moduleId == Constants.CONTACTS %>">selected</dhv:evaluate>><dhv:label name="contacts">Contacts</dhv:label></option>
		 </dhv:permission>
       </dhv:evaluate>
       <dhv:evaluate if="<%="-1".equals(orgId) %>">
       	 <dhv:permission name="contacts-external_contacts-documents-view">
       		<option value="<%= Constants.CONTACTS %>" <dhv:evaluate if="<%= moduleId == Constants.CONTACTS %>">selected</dhv:evaluate>><dhv:label name="contacts">Contacts</dhv:label></option>
		 </dhv:permission>
       </dhv:evaluate>
    </dhv:evaluate>
      <dhv:permission name="documents-view">
       	<option value="<%= Constants.DOCUMENTS_DOCUMENTS %>" <dhv:evaluate if="<%= moduleId == Constants.DOCUMENTS_DOCUMENTS %>">selected</dhv:evaluate>><dhv:label name="documents.search.documents">Document Stores</dhv:label></option>
      </dhv:permission>
      <dhv:permission name="projects-view"> 
       	<option value="<%= Constants.PROJECTS_FILES %>" <dhv:evaluate if="<%= moduleId == Constants.PROJECTS_FILES %>">selected</dhv:evaluate>><dhv:label name="Projects">Projects</dhv:label></option>
      </dhv:permission>
      </select>
    </td>
    <td>
    <dhv:evaluate if="<%= linkItemId != -1%>">
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="documentListInfo" showHiddenParams="true" enableJScript="true" form="listForm"/>
    </dhv:evaluate>
    </td>
  </tr>
</table>
<%-- END Document folder trails--%>
<%-- START Document folder and files list--%>
<%boolean filesExists = false; %>
<dhv:evaluate if="<%= linkItemId != -1%>">
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
        <tr>
          <th><dhv:label name="message.selected">Selected</dhv:label></th>
          <th><dhv:label name="message.file">File</dhv:label></th>
          <th style="text-align: center;"><dhv:label name="message.type">Type</dhv:label></th>
          <th style="text-align: center;"><dhv:label name="message.size">Size</dhv:label></th>
          <th style="text-align: center;"><dhv:label name="message.version">Version</dhv:label></th>
          <th style="text-align: center;"><dhv:label name="message.dateModified">Date Modified</dhv:label></th>
        </tr>
	  <dhv:evaluate if="<%= fileItemList.size() == 0 && fileFolderList.size() == 0 %>">
	    <tr class="row2">
	      <td colspan="6"><dhv:label name="project.noFilesToDisplay">No files to display.</dhv:label></td>
	    </tr>
	  </dhv:evaluate>
      <%
      	int rowid = 0;
        Iterator i = fileFolderList.iterator();
        if ( i.hasNext() ) {

          while (i.hasNext()) {
            rowid = (rowid != 1?1:2);
            FileFolder thisFolder = (FileFolder)i.next();
      %>
          <tr class="row<%= rowid %>">
            <td width="10" valign="middle" style="text-align: center;" nowrap>
            &nbsp;
            </td>
            <td valign="middle" width="100%">
              <img src="images/stock_folder-23.gif" border="0" align="absmiddle">

              <a href="DocumentSelector.do?command=ListDocuments<%=orgParam%>&previousSelection=<%=previousSelection %>&moduleId=<%= moduleId %>&linkItemId=<%=linkItemId %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>&listType=<%= request.getParameter("listType")%>&displayFieldId=<%= request.getParameter("displayFieldId") %>&hiddenFieldId=<%= request.getParameter("hiddenFieldId") %>" enctype="multipart/form-data"><%= toHtml(thisFolder.getSubject()) %></a>
            </td>
            <td valign="middle" width="100%">
              <dhv:label name="message.Folder">Folder</dhv:label>
            </td>
            <td style="text-align: center;" valign="middle" nowrap>
              <%= thisFolder.getItemCount() %>
              <% if (thisFolder.getItemCount() == 1) {%>
                <dhv:label name="project.item.lowercase">item</dhv:label>
              <%} else {%>
                <dhv:label name="project.items.lowercase">items</dhv:label>
              <%}%>
            </td>
            <td style="text-align: center;" valign="middle" nowrap>
              --
            </td>
            <td nowrap>
              <zeroio:tz timestamp="<%= thisFolder.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/><br />
              <dhv:username id="<%= thisFolder.getModifiedBy() %>"/>
            </td>
          </tr>
        <%}%>
      <%}%>

      <%
        Iterator j = fileItemList.iterator();
        if ( j.hasNext() ) {
          int count = 0;
          while (j.hasNext()) {
          	count++;
            rowid = (rowid != 1?1:2);
            FileItem thisFile = (FileItem)j.next();
            String fileId = String.valueOf(thisFile.getId());
            filesExists = true;
      %>
          <tr class="row<%= rowid+(selectedDocuments.indexOf(fileId) != -1 ?"hl":"") %>">
            <td width="10" valign="middle" style="text-align: center;" nowrap>
	  <%
 		 if ("list".equals(request.getParameter("listType"))) {
	  %>
    	 <input type="checkbox" name="file<%= count %>" value="<%= thisFile.getId() %>" <%= (selectedDocuments.indexOf(fileId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
		<%} else {%>
   		  <a href="javascript:document.listForm.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','listForm');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>
		<%}%>
        <input type="hidden" name="hiddenFileId<%= count %>" value="<%= thisFile.getId() %>">
            </td>
            <td valign="middle" width="100%">
              <%= thisFile.getImageTag("-23") %>
              <%= toHtml(thisFile.getSubject()) %>
            </td>
            <td valign="middle" align="center" width="100%">
              <%= toHtml(thisFile.getExtension()) %>
            </td>
            <td style="text-align: center;" valign="middle" nowrap>
              <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
            </td>
            <td style="text-align: center;" valign="middle" nowrap>
              <%= thisFile.getVersion() %>&nbsp;
            </td>
            <td nowrap>
              <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/><br />
              <dhv:username id="<%= thisFile.getModifiedBy() %>"/>
            </td>
          </tr>
        <%}%>
      <%} %>
      </table>
</dhv:evaluate>
<%-- START Build document stores list--%>
<dhv:evaluate if="<%= (linkItemId == -1 && moduleId==Constants.DOCUMENTS_DOCUMENTS)%>">
  <%-- Show the document stores --%>
   <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
 	 <tr>
  	  <th width="20%" nowrap>
  	    <strong><dhv:label name="documents.details.title">Title</dhv:label></strong>
  	  </th>
  	  <th width="50%" >
  	    <strong><dhv:label name="documents.details.shortDescription">Short Description</dhv:label></strong>
  	  </th>
  	  <th width="30%" >
   		<strong><dhv:label name="documents.details.status">Status</dhv:label></strong>
  	  </th>
 	 </tr>
	<%
 	 
 	 int rowid = 0;
 	 Iterator itr = documentStoreList.iterator();
 	 if (itr.hasNext()) {
  	  while (itr.hasNext()) {
      DocumentStore thisDocumentStore = (DocumentStore) itr.next();
      rowid = (rowid!=1?1:2);
     %>
    <tr class="row<%= rowid%>">
      <td valign="top">
        <a href="DocumentSelector.do?command=ListDocuments<%=orgParam%>&previousSelection=<%=previousSelection %>&moduleId=<%= moduleId %>&linkItemId=<%= thisDocumentStore.getId()%>&listType=<%= request.getParameter("listType")%>&displayFieldId=<%= request.getParameter("displayFieldId") %>&hiddenFieldId=<%= request.getParameter("hiddenFieldId") %>" enctype="multipart/form-data"><%= toHtml(thisDocumentStore.getTitle()) %></a>
      </td>
      <td valign="top">
        <%= toHtml(thisDocumentStore.getShortDescription()) %>
      </td>
      <td>
      <dhv:evaluate if="<%= thisDocumentStore.getCloseDate() == null && thisDocumentStore.getApprovalDate() == null %>">
        <img border="0" src="images/box-hold.gif" alt="<dhv:label name='alt.onHold'>On Hold</dhv:label>" align="absmiddle">
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisDocumentStore.getCloseDate() != null %>">
        <font color="blue">
        <dhv:label name="documents.details.archivedMessage" param="<%= "time="+getTime(pageContext,thisDocumentStore.getCloseDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>">This document store was archived on&nbsp;<zeroio:tz timestamp="<%= thisDocumentStore.getCloseDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
        </font>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisDocumentStore.getCloseDate() == null %>">
        <dhv:evaluate if="<%= thisDocumentStore.getApprovalDate() == null %>">
          <font color="red"><dhv:label name="documents.details.unapprovedMessage">This document store is currently under review and has not been approved</dhv:label></font>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisDocumentStore.getApprovalDate() != null %>">
          <font color="darkgreen"><dhv:label name="documents.details.approvedMessage" param="<%= "time="+getTime(pageContext,thisDocumentStore.getApprovalDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;")  %>">This document store was approved on&nbsp; <zeroio:tz timestamp="<%= thisDocumentStore.getApprovalDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font>
        </dhv:evaluate>
      </dhv:evaluate>
      </td>
    </tr>
	<%      
      }
    }else {
	%>
 	 <tr>
  	  <td colspan="3">
  	    <dhv:label name="documents.enterprise.noDocumentStoresMessage">No document stores to display.</dhv:label>
  	  </td>
  	</tr>
	<%  
 	 }
	%>
   </table>
</dhv:evaluate>
<%-- END Build document stores list--%>  
<%-- START Build projects list--%>
<dhv:evaluate if="<%= (linkItemId == -1 && moduleId==Constants.PROJECTS_FILES)%>">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  	<tr>
  	  <th nowrap><dhv:label name="documents.details.startDate">Start Date</dhv:label></th>
  	  <th width="100%" nowrap><dhv:label name="project.projectTitle">Project Title</dhv:label></th>
 	  <th nowrap><dhv:label name="project.overallProgress">Overall Progress</dhv:label></th>
 	 </tr>
	<%
 	 if (projectList.size() == 0) {
	%>
 	 <tr class="row2">
   		 <td colspan="4"><dhv:label name="project.noProjectsToDisplay">There are currently no projects to display in this view.</dhv:label></td>
 	 </tr>
	<%
 	 }
 	 int rowid = 0;
 	 int count = 0;
 	 Iterator i = projectList.iterator();
 	 while (i.hasNext()) {
 	   rowid = (rowid != 1?1:2);
 	   ++count;
 	   Project thisProject = (Project) i.next();
   	   RequirementList requirements = thisProject.getRequirements();
	%>
  <tr class="row<%= rowid %>">
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisProject.getRequestDate() %>" dateOnly="true" default="&nbsp;" />
    </td>
    <td valign="top">
      <a href="DocumentSelector.do?command=ListDocuments<%=orgParam%>&previousSelection=<%=previousSelection %>&moduleId=<%= moduleId %>&linkItemId=<%= thisProject.getId()%>&listType=<%= request.getParameter("listType")%>&displayFieldId=<%= request.getParameter("displayFieldId") %>&hiddenFieldId=<%= request.getParameter("hiddenFieldId") %>" enctype="multipart/form-data"><%= toHtml(thisProject.getTitle()) %></a>
      <dhv:evaluate if="<%= thisProject.getApprovalDate() == null %>">
        <img src="images/unapproved.gif" border="0" alt="" align="absmiddle" />
      </dhv:evaluate>
      <br />
    </td>
    <td valign="top" align="right" nowrap>
      <table cellpadding="1" cellspacing="1" class="empty"><tr>
        <td><dhv:label name="project.progress.colon">Progress:</dhv:label></td>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() == 0 %>">
          <td width="<%= requirements.getPercentClosed() %>" bgColor="#CCCCCC" nowrap class="progressCell"></td>
        </dhv:evaluate>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() > 0 %>">
          <dhv:evaluate if="<%= requirements.getPercentClosed() > 0 %>">
            <td width="<%= requirements.getPercentClosed()  %>" bgColor="green" nowrap class="progressCell"></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= requirements.getPercentUpcoming() > 0 %>">
            <td width="<%= requirements.getPercentUpcoming() %>" bgColor="#99CC66" nowrap class="progressCell"></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= requirements.getPercentOverdue() > 0 %>">
            <td width="<%= requirements.getPercentOverdue() %>" bgColor="red" nowrap class="progressCell"></td>
          </dhv:evaluate>
        </dhv:evaluate>
      </tr></table>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() == 0 %>">
          <dhv:label name="project.zeroActivities">(0 activities)</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() > 0 %>">
          <% if (requirements.getPlanActivityCount() == 1) { %>
          <dhv:label name="project.oneOfOneActivityComplete.text" param="<%= "closedCount="+requirements.getPlanClosedCount()+"|activityCount="+requirements.getPlanActivityCount() %>">(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %> activity is complete)</dhv:label>
          <%} else {%>
            <% if (requirements.getPlanClosedCount() == 1) { %>
          <dhv:label name="project.oneOfSeveralActivitiesComplete.text" param="<%= "closedCount="+requirements.getPlanClosedCount()+"|activityCount="+requirements.getPlanActivityCount() %>">(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %> activities is complete)</dhv:label>
            <%} else {%>
          <dhv:label name="project.numberofSeveralActivitiesComplete.text" param="<%= "closedCount="+requirements.getPlanClosedCount()+"|activityCount="+requirements.getPlanActivityCount() %>">(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %> activities are complete)</dhv:label>
          <%}%>
          <%}%>
        </dhv:evaluate>
     </td>
   </tr>
	<%
	  }
	%>
  </table>

</dhv:evaluate>
    <input type="hidden" name="finalsubmit" value="false">
    <input type="hidden" name="rowcount" value="0">
    <dhv:evaluate if = '<%= !"".equals(orgId) %>'>
        <input type="hidden" name="orgId" value="<%= orgId%>">
    </dhv:evaluate>
    <dhv:evaluate if = '<%= !"".equals(contactId) %>'>
        <input type="hidden" name="contactId" value="<%= contactId%>">
    </dhv:evaluate>
    
    <input type="hidden" name="previousSelection" value="<%= previousSelection%>">
    <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
    <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
    <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
</form>
<%-- START Build file upload--%>  
<% boolean hasPermissions = false; %>
<dhv:evaluate if="<%= linkItemId != -1%>">
  <dhv:evaluate if="<%= (moduleId==Constants.ACCOUNTS || moduleId==Constants.CONTACTS)%>">
    <dhv:permission name="<%= "accounts-accounts-documents-add"+","+ "accounts-accounts-contacts-documents-add"+","+ "contacts-external_contacts-documents-add"%>" all="false">
      <%hasPermissions = true; %>
    </dhv:permission>
  </dhv:evaluate>  
  <dhv:evaluate if="<%= (moduleId==Constants.DOCUMENTS_DOCUMENTS)%>">
  
    <dhv:documentPermission name="documentcenter-documents-files-upload">
      <%hasPermissions=true; %>
    </dhv:documentPermission>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= (moduleId==Constants.PROJECTS_FILES)%>">
    <zeroio:permission name="project-documents-files-upload"> 
      <%hasPermissions=true; %>
    </zeroio:permission>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= hasPermissions%>">
  <form method="post" name="inputForm" action="DocumentSelector.do?command=Upload&displayFieldId=<%= request.getParameter("displayFieldId") %>&hiddenFieldId=<%= request.getParameter("hiddenFieldId") %>&listType=<%= request.getParameter("listType") %>&folderId=<%= fileItemList.getFolderId() %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">      
  <input type="hidden" name="moduleId" value="">
  <input type="hidden" name="linkItemId" value="<%=linkItemId %>">
  <input type="hidden" name="folderId" value="<%= fileItemList.getFolderId() %>">
  <input type="hidden" name="previousSelection" value="<%= previousSelection%>">
     <dhv:evaluate if = '<%= !"".equals(orgId) %>'>
        <input type="hidden" name="orgId" value="<%= orgId%>">
    </dhv:evaluate>
    <dhv:evaluate if = '<%= !"".equals(contactId) %>'>
        <input type="hidden" name="contactId" value="<%= contactId%>">
    </dhv:evaluate>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="10">
        <strong><dhv:label name="campaign.attachNewFile">Attach New File</dhv:label></strong>
      </th>
    </tr>
	  <tr class="containerBody">
	    <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
	    </td>
	    <td>
	      <%-- <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>"> --%>
	      <input type="text" name="subject" size="45" maxlength="200" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
        <%= showAttribute(request,"subjectError") %>
	    </td>
	  </tr>
	  <tr class="containerBody">
	    <td class="formLabel">
	      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
	    </td>
	    <td>
	      <input type="file" name="attachment" size="45">
	       <%= showAttribute(request,"attachmentError") %>
	    </td>
	  </tr>
	</table>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
    <input type="submit" value=" <dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload">
  </p>
  </form>
  </dhv:evaluate>
</dhv:evaluate>
<%-- END Build file upload--%> 
<dhv:evaluate if = '<%= filesExists %>'>
  <input type="submit" value=" <dhv:label name="global.button.Attach">Attach</dhv:label> " name="submit" onClick="javascript:document.listForm.finalsubmit.value = 'true';document.listForm.submit();">
</dhv:evaluate>
  <input type="submit" value=" <dhv:label name="global.button.cancel">Cancel</dhv:label> " onClick="javascript:window.close()">
</body>
<%} else { %>
<%-- The final submit --%>
  <script>
    fileIds = new Array();
    fileNames = new Array();
  </script>
<%
  Iterator i = finalDocuments.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    FileItem thisFile = (FileItem) i.next();
    
%>
<dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>

  <script>
  
    fileIds[<%= count %>] = "<%= thisFile.getId()%>";
    fileNames[<%= count %>] = "<%= toJavaScript(thisFile.getClientFilename()+" ("+thisFile.getRelativeSize()+User.getSystemStatus(getServletConfig()).getLabel("admin.oneThousand.abbreviation", "k")+")" )%>";
  
  </script>
<%	
  }
%>
  <body onLoad="javascript:setParentFileList(fileIds, fileNames, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">

  </body>
<%
     
  }
%>

