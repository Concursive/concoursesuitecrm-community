<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CustomFormInfo" class="com.darkhorseventures.controller.CustomForm" scope="request"/>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.controller.CustomFormTab,com.darkhorseventures.controller.CustomFormGroup" %>
<%@ include file="initPage.jsp" %>
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
<dhv:evaluate exp="<%= (CustomFormInfo.size() > 1) %>">
<% 
  if (thisTab.getPrev() != null && !(thisTab.getPrev().equals(""))) {
%>
<input type="submit" value="< Back" onClick="javascript:this.form.clickFrom.value='back';this.form.action='<%=thisTab.getPrev()%>'">
<%
  } else {
%>
<input type="submit" value="< Back" disabled>
<% 
  }
  if (thisTab.getNext() != null && !(thisTab.getNext().equals(""))) {
%>
<input type="submit" value="Next >" onClick="javascript:this.form.clickFrom.value='next';this.form.action='<%=thisTab.getNext()%>'">
<%
  } else {
%>
<input type="submit" value="Next >" disabled>
<%
  }
%>
</dhv:evaluate>
<%=CustomFormInfo.displayButtons()%>
<br>&nbsp;
<%  
  Iterator groups = thisTab.iterator();
    while (groups.hasNext()) {
      boolean tableClosed = false;
      CustomFormGroup thisGroup = (CustomFormGroup)groups.next();
%>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="2" valign="center" align="left">
        <strong><%=thisGroup.getName()%></strong>
      </td>
    </tr>
<%  
      Iterator fields = thisGroup.iterator();
      if (fields.hasNext()) {
        while (fields.hasNext()) {
          CustomField thisField = (CustomField)fields.next();
          if (thisField.getType() == CustomField.ROWLIST) {
            for(int k=1; k<=thisField.getMaxRowItems(); k++) {
%>
    <tr class="containerBody">
      <td width="125" valign="center" nowrap class="formLabel">
        <%= thisField.getDisplayHtml() %> <%=k%>
      </td>
      <td width="100%" valign="center">
        <%= thisField.getRowListElement(k, (thisField.getType() == CustomField.ROWLIST)) %>
        <font color="red"><%= (thisField.getRequired()&&thisField.getType() == CustomField.ROWLIST?"*":"") %></font>
        <font color="#006699"><%= toHtml(thisField.getError()) %></font>
      </td>
    </tr>
<%          }
          } else if (thisField.getType() == CustomField.DISPLAYROWLIST) {
            tableClosed = true;
%>
  </table>
  <br>
<%
            //Begin survey questions
            for(int k=1; k<=thisField.getMaxRowItems(); k++) {
%>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="containerHeader">
      <td colspan="7" width="100%" valign="top">
        <%= thisField.getRowListElement(k, (thisField.getType() == CustomField.ROWLIST)) %>
      </td>
    </tr>
    <tr class="containerBack">
      <% for(int z=1; z<8; z++) {%>
      <td valign="center" align="center">
        <%=z%>
      </td>
      <%}%>
    </tr>
    <tr class="containerBody">
      <% for(int z=1; z<8; z++) {%>
      <td valign="center" align="center">
        <input type="radio">
      </td>
      <%}%>
    </tr>
  </table>
<%              if (k<thisField.getMaxRowItems()) {    %>
  <br>
<%              }
              }
%>
          <%} else if (thisField.getType() == CustomField.HIDDEN) {%>
    <%= thisField.getHtmlElement() %>
          <%} else if (thisField.getType() == CustomField.DISPLAYTEXT) {%> 
    <tr class="containerBody">
      <dhv:evaluate exp="<%= thisField.hasDisplay() %>">
      <td valign="top" nowrap class="formLabel">
        <%= thisField.getDisplayHtml() %>
      </td>
      </dhv:evaluate>
      <td colspan="<%= (thisField.hasDisplay()?"1":"2") %>" valign="top">
        <%= thisField.getHtmlElement() %>&nbsp;
      </td>
    </tr>
          <%} else if (thisField.getType() == CustomField.LOOKUP_USERID) {%> 
    <tr class="containerBody">
      <td width="125" valign="top" nowrap class="formLabel">
        <%= thisField.getDisplayHtml() %>
      </td>
      <td valign="top">
        <dhv:username id="<%= thisField.getEnteredValue() %>"/>
      </td>
    </tr>
          <%} else {%>
    <tr class="containerBody">
      <td width="125" valign="top" nowrap class="formLabel">
        <%-- Do not use toHtml() here, it's done by CustomField --%>
        <%= thisField.getDisplayHtml() %>
      </td>
      <td valign="top">
        <%-- Do not use toHtml() here, it's done by CustomField --%>
        <%= thisField.getHtmlElement() %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
        <font color='#006699'><%= toHtml(thisField.getError()) %></font>
      </td>
    </tr>
          <%}%>
        <%}
        } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E">No fields to display.</font>
      </td>
    </tr>
<%
        }
      if (!tableClosed) {
%>
  </table>
<%
      }
%>
  <br>
    <%}%>
<%=CustomFormInfo.getHiddenValues()%>
<%-- 2nd set of buttons --%>
<dhv:evaluate exp="<%= (CustomFormInfo.size() > 1) %>">
<% 
  if (thisTab.getPrev() != null && !(thisTab.getPrev().equals(""))) {
%>
<input type="submit" value="< Back" onClick="javascript:this.form.clickFrom.value='back';this.form.action='<%=thisTab.getPrev()%>'">
<%
  } else {
%>
<input type="submit" value="< Back" disabled>
<% 
  }
  if (thisTab.getNext() != null && !(thisTab.getNext().equals(""))) {
%>
<input type="submit" value="Next >" onClick="javascript:this.form.clickFrom.value='next';this.form.action='<%=thisTab.getNext()%>'">
<%
  } else {
%>
<input type="submit" value="Next >" disabled>
<%
  }
%>
</dhv:evaluate>
<%=CustomFormInfo.displayButtons()%>
<br>
<%}%>
<%}%>
</form>

