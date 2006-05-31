<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*,
                 org.aspcfs.modules.base.Constants" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="newsArticle" class="com.zeroio.iteam.base.NewsArticle" scope="request"/>
<jsp:useBean id="newsArticleCategoryList" class="com.zeroio.iteam.base.NewsArticleCategoryList" scope="request"/>
<jsp:useBean id="portalTemplateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="taskCategoryList" class="org.aspcfs.modules.tasks.base.TaskCategoryList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  if (clientType.getType() == -1) {
    clientType.setParameters(request);
  }
%>
<%-- Editor must go here, before the body onload --%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <jsp:include page="../tinymce_include.jsp" flush="true"/>
  <script language="javascript" type="text/javascript">
    initEditor('intro');
  </script>
</dhv:evaluate>
<body onload="document.inputForm.priorityId.focus();">
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
<%-- Setup Image Library --%>
  <dhv:evaluate if="<%= newsArticle.getId() > 0 %>">
    var ilConstant = <%= Constants.NEWSARTICLE_FILES %>;
    var ilId = <%= newsArticle.getId() %>;
  </dhv:evaluate>
  <dhv:evaluate if="<%= newsArticle.getId() == -1 %>">
    var ilConstant = <%= Constants.PROJECTS_FILES %>;
    var ilId = <%= Project.getId() %>;
  </dhv:evaluate>
<%-- Validations --%>
  function checkForm(form) {
    try { tinyMCE.triggerSave(false); } catch(e) { }
    var formTest = true;
    var messageText = "";
<dhv:evaluate if="<%= clientType.showApplet() %>">
      document.inputForm.intro.value = document.Kafenio.getDocumentBody();
</dhv:evaluate>
    //Check required fields
    if (document.inputForm.subject.value == "") {    
      messageText += label("check.subject","- Subject is a required field\r\n");
      formTest = false;
    }
    if (document.inputForm.intro.value == "") {    
      messageText += label("check.intro",label("check.intro","- Intro is a required field\r\n"));
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("check.message","The message could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementNews.do?command=Save&pid=<%= Project.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<%-- Trails --%>
  <dhv:evaluate if="<%= !isPopup(request) %>">
  <table border="0" cellpadding="1" cellspacing="0" width="100%">
    <tr class="subtab">
      <td>
        <img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle" />
        <a href="ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>"><zeroio:tabLabel name="News" type="project.news" object="Project"/></a> >
        <% if(newsArticle.getId() == -1 ) {%>
          <dhv:label name="button.add">Add</dhv:label>
        <%} else {%>
          <dhv:label name="button.update">Update</dhv:label>
        <%}%>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value=" <dhv:label name="global.button.save">Save</dhv:label> " onClick="javascript:this.form.newPage.value='false'" />
  <dhv:evaluate if="<%= newsArticle.getMessage() == null %>">
    <input type="submit" value="<dhv:label name="project.saveAndAddPage">Save and Add a Page</dhv:label>" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <dhv:evaluate if="<%= newsArticle.getMessage() != null %>">
    <input type="submit" value="<dhv:label name="project.saveAndModifyNextPage">Save and Modify Next Page</dhv:label>" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>';"><br />
  </dhv:evaluate>
  <dhv:formMessage />
  <%-- Begin details --%>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" align="left">
        <% if(newsArticle.getId() == -1) {%>
          <dhv:label name="project.addNewsArticle">Add News Article</dhv:label>
        <%} else {%>
          <dhv:label name="project.updateNewsArticle">Update News Article</dhv:label>
        <%}%>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
      </td>
      <td>
        <input type="radio" name="status" value="-1" <%= (newsArticle.getStatus() == -1 ? "checked" : "") %> /> <dhv:label name="project.draft">Draft</dhv:label>
        <input type="radio" name="status" value="1" <%= (newsArticle.getStatus() == 1 ? "checked" : "") %> /> <dhv:label name="project.unapproved">Unapproved</dhv:label>
        <input type="radio" name="status" value="2" <%= (newsArticle.getStatus() == 2 ? "checked" : "") %> /> <dhv:label name="project.published">Published</dhv:label>
        <dhv:evaluate if="<%= newsArticle.getMessage() == null %>">
          <dhv:label name="project.onePage">(1 Page)</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= newsArticle.getMessage() != null %>">
          <dhv:label name="project.twoPages">(2 Pages)</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="project.position">Position</dhv:label>
      </td>
      <td>
        <input type="text" name="priorityId" size="6" maxlength="3" value="<%= newsArticle.getPriorityId() %>"><font color=red>*</font>
        <%= showAttribute(request, "priorityIdError") %>
        <dhv:label name="project.lowerNumberedMesg.text">Lower numbered messages appear before higher numbered messages</dhv:label>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="project.startDateTime">Start Date/Time</dhv:label>
      </td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="startDate" timestamp="<%= newsArticle.getStartDate() %>" />
       <dhv:label name="project.at">at</dhv:label>
        <zeroio:timeSelect baseName="startDate" value="<%= newsArticle.getStartDate() %>" timeZone="<%= newsArticle.getStartDateTimeZone() %>" showTimeZone="true" />
        <%=showAttribute(request,"startDateError")%>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="project.archiveDateTime">Archive Date/Time</dhv:label>
      </td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="endDate" timestamp="<%= newsArticle.getEndDate() %>" />
       <dhv:label name="project.at">at</dhv:label>
        <zeroio:timeSelect baseName="endDate" value="<%= newsArticle.getEndDate() %>" timeZone="<%= newsArticle.getEndDateTimeZone() %>" showTimeZone="true" />
        <%=showAttribute(request,"endDateError")%><%= showWarningAttribute(request, "endDateWarning") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="accounts.accountasset_include.Category">Category</dhv:label>
      </td>
      <td>
        <%= newsArticleCategoryList.getHtmlSelect(systemStatus, "categoryId", newsArticle.getCategoryId()) %>
        <zeroio:permission name="project-news-add">
          <a href="javascript:popURL('ProjectManagementNews.do?command=EditCategoryList&pid=<%= Project.getId() %>&form=inputForm&field=categoryId&previousId=' + document.inputForm.categoryId.options[document.inputForm.categoryId.selectedIndex].value + '&popup=true','EditList','600','300','yes','yes');"><dhv:label name="project.editList">edit list</dhv:label></a>
        </zeroio:permission>
        <%= showAttribute(request, "categoryIdError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="project.linktoList">Link to List</dhv:label>
      </td>
      <td>
        <%= taskCategoryList.getHtmlSelect(systemStatus, "taskCategoryId", newsArticle.getTaskCategoryId()) %>
        <%= showAttribute(request, "taskCategoryIdError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
      </td>
      <td>
        <input type="text" name="subject" size="60" maxlength="255" value="<%= toHtmlValue(newsArticle.getSubject()) %>"><font color=red>*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="project.introPageOne">Intro (Page 1)</dhv:label>
      </td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
<textarea rows="20" id="intro" name="intro" cols="70"><%= toString(newsArticle.getIntro()) %></textarea>
</dhv:evaluate>

<dhv:evaluate if="<%= clientType.showApplet() %>">
<input type="hidden" name="intro" value="<%= toHtmlValue(newsArticle.getIntro()) %>" />
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
	<PARAM NAME="CONTENTPARAMETER" VALUE="intro">
	<PARAM NAME="OUTPUTMODE" VALUE="off">
	<PARAM NAME="BGCOLOR" VALUE="#FFFFFF">
	<PARAM NAME="DEBUG" VALUE="false">
  <dhv:evaluate if="<%= hasText(newsArticle.getIntro()) %>">
	  <PARAM NAME="BASE64" VALUE="true">
    <PARAM NAME="DOCUMENT" VALUE="<%= StringUtils.toBase64(newsArticle.getIntro()) %>">
  </dhv:evaluate>
</APPLET>
</dhv:evaluate>
              
            </td>
            <td valign="top">
              <font color="red">*</font>
              <%= showAttribute(request, "introError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value=" <dhv:label name="global.button.save">Save</dhv:label> " onClick="javascript:this.form.newPage.value='false'" />
  <dhv:evaluate if="<%= newsArticle.getMessage() == null %>">
    <input type="submit" value="<dhv:label name="project.saveAndAddPage">Save and Add a Page</dhv:label>" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <dhv:evaluate if="<%= newsArticle.getMessage() != null %>">
    <input type="submit" value="<dhv:label name="project.saveAndModifyNextPage">Save and Modify Next Page</dhv:label>" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>';" /><br>
  <br />
  <input type="hidden" name="onlyWarnings" value="<%=(newsArticle.getOnlyWarnings()?"on":"off")%>" />
  <input type="hidden" name="id" value="<%= newsArticle.getId() %>" />
  <input type="hidden" name="modified" value="<%= newsArticle.getModified() %>" />
  <input type="hidden" name="newPage" value="false" />
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>"/>
  <%-- popup settings --%>
  <input type="hidden" name="popup" value="<%= request.getParameter("popup") %>"/>
  <input type="hidden" name="param" value="<%= newsArticle.getProjectId() %>"/>
  <input type="hidden" name="param2" value="<%= newsArticle.getId() %>"/>
</form>
</body>