<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="newsArticle" class="com.zeroio.iteam.base.NewsArticle" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Editor must go here, before the body onload --%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
<jsp:include page="../htmlarea_include.jsp" flush="true"/>
<body onload="initEditor('intro');document.inputForm.subject.focus();">
</dhv:evaluate>
<%-- Use applet instead --%>
<dhv:evaluate if="<%= clientType.showApplet() %>">
<body onload="document.inputForm.subject.focus();">
</dhv:evaluate>
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    var formTest = true;
    var messageText = "";
<dhv:evaluate if="<%= clientType.showApplet() %>">
      document.inputForm.intro.value = document.Kafenio.getDocumentBody();
</dhv:evaluate>
    //Check required fields
    if (document.inputForm.subject.value == "") {    
      messageText += "- Subject is a required field\r\n";
      formTest = false;
    }
    if (document.inputForm.intro.value == "") {    
      messageText += "- Intro is a required field\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The message could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementNews.do?command=Save&pid=<%= Project.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>"><zeroio:tabLabel name="News" object="Project"/></a> >
      <%= newsArticle.getId() == -1 ? "Add" : "Update" %>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save " onClick="javascript:this.form.newPage.value='false'" />
  <dhv:evaluate if="<%= newsArticle.getMessage() == null %>">
    <input type="submit" value="Save and Add a Page" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <dhv:evaluate if="<%= newsArticle.getMessage() != null %>">
    <input type="submit" value="Save and Modify Next Page" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>';"><br>
  <%= !"&nbsp;".equals(showError(request, "actionError").trim())? showError(request, "actionError"):showWarning(request, "actionWarning")%><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" align="left">
        <strong><%= newsArticle.getId() == -1 ? "Add" : "Update" %> News Article</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Status
      </td>
      <td>
        <input type="radio" name="status" value="-1" <%= (newsArticle.getStatus() == -1 ? "checked" : "") %> /> Draft
        <input type="radio" name="status" value="1" <%= (newsArticle.getStatus() == 1 ? "checked" : "") %> /> Unapproved
        <input type="radio" name="status" value="2" <%= (newsArticle.getStatus() == 2 ? "checked" : "") %> /> Published
        <dhv:evaluate if="<%= newsArticle.getMessage() == null %>">
          (1 Page)
        </dhv:evaluate>
        <dhv:evaluate if="<%= newsArticle.getMessage() != null %>">
          (2 Pages)
        </dhv:evaluate>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Position
      </td>
      <td>
        <input type="text" name="priorityId" size="6" maxlength="3" value="<%= newsArticle.getPriorityId() %>"><font color=red>*</font>
        <%= showAttribute(request, "priorityIdError") %>
        Lower numbered messages appear before higher numbered messages
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Start Date/Time
      </td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="startDate" timestamp="<%= newsArticle.getStartDate() %>" />
        at
        <zeroio:timeSelect baseName="startDate" value="<%= newsArticle.getStartDate() %>" timeZone="<%= newsArticle.getStartDateTimeZone() %>" showTimeZone="yes" />
        <%=showAttribute(request,"startDateError")%>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Archive Date/Time
      </td>
      <td valign="top">
        <zeroio:dateSelect form="inputForm" field="endDate" timestamp="<%= newsArticle.getEndDate() %>" />
        at
        <zeroio:timeSelect baseName="endDate" value="<%= newsArticle.getEndDate() %>" timeZone="<%= newsArticle.getEndDateTimeZone() %>" showTimeZone="yes" />
        <%=showAttribute(request,"endDateError")%><%= showWarningAttribute(request, "endDateWarning") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Subject
      </td>
      <td>
        <input type="text" name="subject" size="60" maxlength="255" value="<%= toHtmlValue(newsArticle.getSubject()) %>"><font color=red>*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Intro (Page 1)
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
  <input type="submit" value=" Save " onClick="javascript:this.form.newPage.value='false'" />
  <dhv:evaluate if="<%= newsArticle.getMessage() == null %>">
    <input type="submit" value="Save and Add a Page" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <dhv:evaluate if="<%= newsArticle.getMessage() != null %>">
    <input type="submit" value="Save and Modify Next Page" onClick="javascript:this.form.newPage.value='true'" />
  </dhv:evaluate>
  <input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=News&pid=<%= Project.getId() %>';" /><br>
  <input type="hidden" name="onlyWarnings" value="<%=(newsArticle.getOnlyWarnings()?"on":"off")%>" />
  <input type="hidden" name="projectId" value="<%= Project.getId() %>" />
  <input type="hidden" name="id" value="<%= newsArticle.getId() %>" />
  <input type="hidden" name="modified" value="<%= newsArticle.getModified() %>" />
  <input type="hidden" name="newPage" value="false" />
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>"/>
</form>
</body>
