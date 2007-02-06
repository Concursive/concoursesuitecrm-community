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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.LookupElement" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popDocuments.js?1"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript">
	var imageLibraryURL = "/../WebsiteMedia.do?command=View&forEmail=true&popup=true";
	var documentLibraryURL = "/../WebsiteDocuments.do?command=View&forEmail=true&popup=true";
  indSelected = 0;
  orgSelected = 1;
  onLoad = 1;
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }

  function clearSelections() {
    deleteOptions("selectedList");
    insertOption("None Selected", "", "selectedList");
  }
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="actionList.newMessage">New Message</dhv:label></strong>
    </th>
  </tr>
  <dhv:evaluate if='<%= (request.getParameter("actionId") == null) &&  (!"addressRequest".equals(request.getAttribute("messageType"))) && !(showBcc || showCc)  %>'>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="contacts.name">Name</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(Message.getName()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="campaign.initialDesctiption">Internal Description</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="255" name="description" value="<%= toHtmlValue(Message.getDescription()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_validateimport.AccessType">Access Type</dhv:label>
    </td>
    <td>
      <%=  AccessTypeList.getHtmlSelect("accessType", Message.getAccessType()) %>
    </td>
  </tr>
  </dhv:evaluate>
	<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="project.message">Message</dhv:label>
    </td>
    <td valign=center>
      <table cellpadding="0" cellspacing="0" class="empty">
      <tr><td nowrap>
      <dhv:label name="campaign.from">From</dhv:label>:</td><td nowrap><input type="text" size="40" maxlength="255" name="replyTo" value='<%= toHtmlValue((Message.getReplyTo() != null && !"".equals(Message.getReplyTo()) ? Message.getReplyTo():User.getUserRecord().getContact().getPrimaryEmailAddress())) %>' />
			<font color="red">*</font> <dhv:label name="campaign.emailAddress.brackets">(Email address)</dhv:label></td><td nowrap><%= showAttribute(request, "replyToError") %>
      </td></tr>
      <dhv:evaluate if="<%= showCc %>"><tr><td nowrap><dhv:label name="quotes.cc">CC</dhv:label>:</td><td nowrap><input type="text" size="40" maxlength="1024" name="cc" id="cc" value='<%= toHtmlValue((cc != null?cc:"")) %>'/> <dhv:label name="campaign.emailAddress.brackets">(Email address)</dhv:label></td><td nowrap><%= showAttribute(request, "ccError") %></td></tr></dhv:evaluate>
      <dhv:evaluate if="<%= showBcc %>"><tr><td nowrap><dhv:label name="quotes.bcc">BCC</dhv:label>:</td><td nowrap><input type="text" size="40" maxlength="255" name="bcc" id="bcc" value='<%= toHtmlValue((bcc != null?bcc:"")) %>'/> <dhv:label name="campaign.emailAddress.brackets">(Email address)</dhv:label></td><td nowrap><%= showAttribute(request, "bccError") %></td></tr></dhv:evaluate>
      <tr><td nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>:</td><td nowrap><input type="text" size="50" maxlength="255" name="messageSubject" value="<%= toHtmlValue(Message.getMessageSubject()) %>">
			<font color="red">*</font></td><td nowrap><%= showAttribute(request, "messageSubjectError") %>
      </td></tr>
      <tr><td colspan="3">
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <textarea id="messageText" name="messageText" style="width:550" rows="20"><%= toString(Message.getMessageText()) %></textarea>
</dhv:evaluate>
<dhv:evaluate if="<%= clientType.showApplet() %>">
<input type="hidden" name="messageText" value="<%= toHtmlValue(Message.getMessageText()) %>" />
<APPLET CODEBASE="." CODE="de.xeinfach.kafenio.KafenioApplet.class" ARCHIVE="applet.jar,gnu-regexp-1.1.4.jar,config.jar,icons.jar" NAME="Kafenio" WIDTH="510" HEIGHT="365" MAYSCRIPT>
	<PARAM NAME="CODEBASE" VALUE=".">
	<PARAM NAME="CODE" VALUE="de.xeinfach.kafenio.KafenioApplet.class">
	<PARAM NAME="ARCHIVE" VALUE="applet.jar,gnu-regexp-1.1.4.jar,config.jar,icons.jar">
	<PARAM NAME="NAME" VALUE="Kafenio">
	<PARAM NAME="TYPE" VALUE="application/x-java-applet;version=1.3">
	<PARAM NAME="SCRIPTABLE" VALUE="true">
	<PARAM NAME="STYLESHEET" VALUE="css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>.css,css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>-10pt.css">
	<PARAM NAME="LANGCODE" VALUE="en">
	<PARAM NAME="LANGCOUNTRY" VALUE="US">
	<PARAM NAME="TOOLBAR" VALUE="true">
	<PARAM NAME="TOOLBAR2" VALUE="true">
	<PARAM NAME="MENUBAR" VALUE="true">
	<PARAM NAME="SOURCEVIEW" VALUE="false">
	<PARAM NAME="MENUICONS" VALUE="true">
	<%-- all available toolbar items: NEW,OPEN,SAVE,CUT,COPY,PASTE,UNDO,REDO,FIND,BOLD,ITALIC,UNDERLINE,STRIKE,SUPERSCRIPT,SUBSCRIPT,ULIST,OLIST,CLEARFORMATS,INSERTCHARACTER,ANCHOR,VIEWSOURCE,STYLESELECT,LEFT,CENTER,RIGHT,JUSTIFY,DEINDENT,INDENT,IMAGE,COLOR,TABLE,SAVECONTENT,DETACHFRAME,SEPARATOR --%>
	<PARAM NAME="BUTTONS" VALUE="CUT,COPY,PASTE,SEPARATOR,BOLD,ITALIC,UNDERLINE,SEPARATOR,LEFT,CENTER,RIGHT,justify,SEPARATOR,DETACHFRAME">
	<PARAM NAME="BUTTONS2" VALUE="ULIST,OLIST,SEPARATOR,UNDO,REDO,SEPARATOR,DEINDENT,INDENT,SEPARATOR,ANCHOR,SEPARATOR,IMAGE,SEPARATOR,CLEARFORMATS,SEPARATOR,VIEWSOURCE,SEPARATOR,STRIKE,SUPERSCRIPT,SUBSCRIPT,INSERTCHARACTER,SEPARATOR,FIND,COLOR,TABLE">
	<%-- all available menuitems: <PARAM NAME="MENUITEMS" VALUE="FILE,EDIT,VIEW,FONT,FORMAT,INSERT,TABLE,FORMS,SEARCH,TOOLS,HELP,DEBUG"> --%>
	<PARAM NAME="MENUITEMS" VALUE="EDIT,FONT,FORMAT,INSERT,TABLE,SEARCH">
  <PARAM NAME="SERVLETMODE" VALUE="cgi">
<%--
	<PARAM NAME="SERVLETURL" VALUE="<%= request.getScheme() %>://<%= getServerUrl(request) %>/Portal.do?command=Images&out=text">
	<PARAM NAME="IMAGEDIR" VALUE="/media/images/">
	<PARAM NAME="FILEDIR" VALUE="/media/images/">
--%>
<%--
	<PARAM NAME="SYSTEMID" VALUE="">
	<PARAM NAME="POSTCONTENTURL" VALUE="http://www.xeinfach.de/media/projects/kafenio/demo/postTester.php">
--%>
	<PARAM NAME="CONTENTPARAMETER" VALUE="messageText">
	<PARAM NAME="OUTPUTMODE" VALUE="off">
	<PARAM NAME="BGCOLOR" VALUE="#FFFFFF">
	<PARAM NAME="DEBUG" VALUE="false">
  <dhv:evaluate if="<%= hasText(Message.getMessageText()) %>">
	  <PARAM NAME="BASE64" VALUE="true">
    <PARAM NAME="DOCUMENT" VALUE="<%= Base64.encodeBase64(Message.getMessageText().getBytes("UTF8"), true) %>">
  </dhv:evaluate>
</APPLET>
      </dhv:evaluate>
      </td></tr></table>
    </td>
  </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="campaign.attachments">Attachments</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <select multiple name="selectedList" id="selectedList" size="5">
                <dhv:evaluate if="<%=Message.getMessageAttachments().isEmpty() %>">
                  <option value="-1"><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
                </dhv:evaluate>
                <dhv:evaluate if="<%=!(Message.getMessageAttachments().isEmpty())%>">
                <%
                  Iterator i = Message.getMessageAttachments().iterator();
                  while (i.hasNext()) {
                    MessageAttachment thisAttachment = (MessageAttachment)i.next();
                %>
                  <option value="<%=thisAttachment.getFileItemId()%>"><%=thisAttachment.getFileName()+" ("+thisAttachment.getRelativeSize()+User.getSystemStatus(getServletConfig()).getLabel("admin.oneThousand.abbreviation", "k")+")"%></option>
                <%}%>
                </dhv:evaluate>
              </select>
            </td>
            <dhv:permission name="<%= "accounts-accounts-documents-view"+","+"accounts-accounts-contacts-documents-view"+","+ "contacts-external_contacts-documents-view" +","+ "documents-view"+","+ "projects-view"%>" all="false">
            <td valign="top">
              <input type="hidden" name="previousSelection" value="" />
              <% String params = "";
              int moduleId=-1;
              boolean hasContactPermission = false;
              boolean hasOrgPermission = false;
              boolean hasAccountContactPermission = false;
              %>
               <dhv:permission name="contacts-external_contacts-documents-view">
             	 <%hasContactPermission=true;%>
              </dhv:permission>
              <dhv:permission name="accounts-accounts-documents-view">
             	 <%hasOrgPermission=true;%>
              </dhv:permission>
              <dhv:permission name="accounts-accounts-contacts-documents-view">
             	 <%hasAccountContactPermission=true;%>
              </dhv:permission>
              <%
              if(request.getAttribute("ContactDetails")!=null){
              Contact contact = (Contact)request.getAttribute("ContactDetails");
              	params = "&contactId="+contact.getId()+"&orgId="+contact.getOrgId();
              	if (contact.getOrgId()!=-1 && hasOrgPermission){
              	 moduleId=Constants.ACCOUNTS;}
              	if (contact.getId()!=-1 && hasContactPermission){
              	 	moduleId=Constants.CONTACTS;}
              	if (contact.getOrgId()!=-1 && hasAccountContactPermission) {
              	  moduleId=Constants.CONTACTS;}
              	}
              %>
              <dhv:permission name="documents-view">
             	 <%if (moduleId==-1){moduleId=Constants.DOCUMENTS_DOCUMENTS;} %>
              </dhv:permission>
              <dhv:permission name="projects-view">
             	 <%if (moduleId==-1){moduleId=Constants.PROJECTS_FILES;} %>
              </dhv:permission>
              &nbsp;[<a href="javascript:popDocumentsListMultiple('selectedList','selectedList','<%=moduleId %>','<%=params %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              &nbsp;[<a href="javascript:clearSelections();"><dhv:label name="">Clear</dhv:label></a>]
            </td>
            </dhv:permission>
          </tr>
        </table>
      </td>
    </tr>

</table>
<%= addHiddenParams(request, "popup|popupType|actionId|actionListId") %>
<dhv:evaluate if="<%= Message.getId() > 0 %>">
<input type="hidden" name="id" value="<%= Message.getId() %>">
</dhv:evaluate>
