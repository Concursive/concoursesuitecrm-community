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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="surveyList" class="org.aspcfs.modules.communications.base.SurveyList" scope="request"/>
<%
  String form = request.getParameter("form");
%>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.<%= form %>.elements['surveyId'];
  list.options.length = 0;
  list.options[list.length] = newOpt(label("option.none","--None--"), "-1");
<%
  Iterator list1 = surveyList.iterator();
  while (list1.hasNext()) {
    Survey thisSurvey = (Survey)list1.next();
%>
  list.options[list.length] = newOpt("<%= thisSurvey.getName() %>", "<%= thisSurvey.getId() %>");
<%
  }
%>
  parent.window.frames['edit'].location.href='CampaignManager.do?command=PreviewSurvey&preview=0&id=-1&inline=true';
}
</script>
</body>
