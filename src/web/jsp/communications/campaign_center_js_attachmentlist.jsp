<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="surveyList" class="org.aspcfs.modules.communications.base.SurveyList" scope="request"/>
<html>
<head>
</head>
<body onload="page_init();">
<script language="JavaScript">
function newOpt(param, value) {
  var newOpt = parent.document.createElement("OPTION");
	newOpt.text=param;
	newOpt.value=value;
  return newOpt;
}
function page_init() {
  var list = parent.document.forms[0].elements['messageId'];
  list.options.length = 0;
  list.options[list.length] = newOpt("--None--", "-1");
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
</html>

