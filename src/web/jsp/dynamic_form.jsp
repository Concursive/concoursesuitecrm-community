<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CustomFormInfo" class="com.darkhorseventures.controller.CustomForm" scope="request"/>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.controller.CustomFormTab,com.darkhorseventures.controller.CustomFormGroup" %>
<%@ include file="initPage.jsp" %>
<%= CustomFormInfo.getJScripts() %>
<%
  int pg = 0;
  if (request.getParameter("pg") != null) {
  	pg = Integer.parseInt(request.getParameter("pg"));
  }
  String returnType = request.getParameter("return");
  if (returnType == null) {
    returnType = (String)request.getAttribute("return");
  }
  if (returnType == null) {
    returnType = "";
  }
%>
<script language="JavaScript">
function check(form) {
  if (form.clickFrom.value == "next") {
    return checkTab(form);
  } else if (form.clickFrom.value == "save" || form.clickFrom.value == "update") {
    return checkForm(form);
  } else {
    return true;
  }
}
<%= CustomFormInfo.getJsFormCheck() %>
<%= CustomFormInfo.getJsTabCheck() %>
<%= CustomFormInfo.getJsFormDefault() %>
</script>
<form name="<%=CustomFormInfo.getName()%>" method="post" action="<%=CustomFormInfo.getAction()%>"<%= (CustomFormInfo.hasJsFormCheck()?" onSubmit=\"return check(this);\"":"") %>>
<input type="hidden" name="return" value="<%= returnType %>">
<input type="hidden" name="clickFrom" value="none">
<% 
  if (CustomFormInfo.getReturnLinkText() != null && !(CustomFormInfo.getReturnLinkText().equals(""))) {
%>
<%=CustomFormInfo.getReturnLinkText()%>
<%
  }
  
  Iterator tabs = CustomFormInfo.iterator();
  while (tabs.hasNext()) {
    CustomFormTab thisTab = (CustomFormTab)tabs.next();
    if (pg == thisTab.getId()) {
    		CustomFormInfo.setSelectedTabName(thisTab.getName());
%>   
<%-- 1st set of buttons --%>

<%=thisTab.getButtonString()%>

<%=CustomFormInfo.displayButtons()%>
<br>&nbsp;
  <dhv:group object="CustomFormInfo" page="<%= pg %>" />
<%-- 2nd set of buttons --%>
<br>
<%=thisTab.getButtonString()%>

<%=CustomFormInfo.displayButtons()%>
<br>
<%}%>
<%}%>
</form>

