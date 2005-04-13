<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CustomFormInfo" class="org.aspcfs.utils.web.CustomForm" scope="request"/>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.CustomFormTab,org.aspcfs.utils.web.CustomFormGroup" %>
<%@ include file="../initPage.jsp" %>
<%= CustomFormInfo.getJScripts() %>
<%
  int pg = 0;
  if (request.getParameter("pg") != null) {
    if (request.getAttribute("pg") != null) {
      try {
        int test = Integer.parseInt((String)request.getAttribute("pg"));
        pg = test;
      } catch (NumberFormatException numEx){
      }
    } else {
  	  pg = Integer.parseInt(request.getParameter("pg"));
    }
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
<%= CustomFormInfo.getJsTabCheck() %>
<%= CustomFormInfo.getJsFormOnLoad() %>
</script>
<form name="<%= CustomFormInfo.getName() %>" method="post" action="<%= CustomFormInfo.getAction() %>"<%= (!"".equals(CustomFormInfo.getJsTabCheck())  ? " onSubmit=\"return checkTab(this);\"" : "") %>>
<input type="hidden" name="return" value="<%= returnType %>">
<input type="hidden" name="clickFrom" value="none">
<%
  Iterator tabs = CustomFormInfo.iterator();
  while (tabs.hasNext()) {
    CustomFormTab thisTab = (CustomFormTab)tabs.next();
    if (pg == thisTab.getId()) {
      CustomFormInfo.setSelectedTabName(thisTab.getName());
      if (thisTab.getReturnLinkText() != null && !(thisTab.getReturnLinkText().equals(""))) {
%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<%= thisTab.getReturnLinkText() %>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
      }
%>
        <%-- 1st set of buttons --%>
        <%= thisTab.getButtonString() %>
        <br />
        <%= showError(request, "actionError") %>
        <%-- Draw the form --%>
        <dhv:group object="CustomFormInfo" page="<%= pg %>" />
        <br />
        <%-- 2nd set of buttons --%>
        <%= thisTab.getButtonString() %>
<%
    }
  }
%>
</form>

