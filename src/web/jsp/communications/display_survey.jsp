<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CustomFormInfo" class="org.aspcfs.utils.web.CustomForm" scope="request"/>
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<%@ page import="java.util.*,org.aspcfs.modules.*,org.aspcfs.utils.web.CustomFormTab,org.aspcfs.utils.web.CustomFormGroup" %>
<%@ include file="../initPage.jsp" %>
<html>
<head>
  <title>Thank you for visiting our survey page</title>
  <script language="JavaScript">
  function checkForm(form) {
      formTest = true;
      message = "";
      
      for (i=0;i<form.length;i++){
      		var tempobj=form.elements[i];
		if ( (tempobj.name).search("qans") != -1 ) {
			if (!validateRadio(document.forms[0].elements[tempobj.name])) {
				formTest = false;
			}
			i = i+7;
		}
		
		if (formTest == false) 
			break;
    }
      
    if (formTest == false) {
      alert("Please Provide an answer for each survey item.\r\n\r\n");
      return false;
    } else {
      return true;
    }
  }
  
  function validateRadio (field) {
    if (!field.length && field.checked)
      return true;
    else {
      for (var b = 0; b < field.length; b++) {
        if (field[b].checked) {
          return true;
        }
      }
    }
    return false;
  }
  </script>
</head>
<body>
<form name="<%=CustomFormInfo.getName()%>" method="post" action="<%=CustomFormInfo.getAction()%>" onSubmit="return checkForm(this);">
<%
  int pg = 0;
  if (request.getParameter("pg") != null) {
  	pg = Integer.parseInt(request.getParameter("pg"));
  }
  Iterator tabs = CustomFormInfo.iterator();
  while (tabs.hasNext()) {
    CustomFormTab thisTab = (CustomFormTab)tabs.next();
    if (pg == thisTab.getId()) {
    		CustomFormInfo.setSelectedTabName(thisTab.getName());
%>   
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<%
		Iterator groups = thisTab.iterator();
			while (groups.hasNext()) {
				CustomFormGroup thisGroup = (CustomFormGroup)groups.next();
					Iterator fields = thisGroup.iterator();
					if (fields.hasNext()) {
						while (fields.hasNext()) {
							CustomField thisField = (CustomField)fields.next();
							if (thisField.getType() == CustomField.ROWLIST) {
								for(int k=1; k<=thisField.getMaxRowItems(); k++) {%>
  <tr class="containerBody">
    <td width="125" valign="center" nowrap class="formLabel">
      <%= thisField.getDisplayHtml() %> <%=k%>
    </td>
    <td width="100%" valign="center">
      <%= thisField.getRowListElement(k, (thisField.getType() == CustomField.ROWLIST)) %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
      <font color='#006699'><%= toHtml(thisField.getError()) %></font>
    </td>
  </tr>
								<%}
							} else if (thisField.getType() == CustomField.DISPLAYROWLIST) {%>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
								<%
								for(int k=1; k<=thisField.getMaxRowItems(); k++) {%>
  <tr>
    <td width=100% valign="center">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="containerHeader">
          <td colspan=7 width=100% valign="center">
            <%= thisField.getRowListElement(k, (thisField.getType() == CustomField.ROWLIST)) %>
            <input type="hidden" name="quest<%=k%>id" value="<%= ((SurveyQuestion)Survey.getQuestions().get(k-1)).getId() %>">
          </td>
        </tr>
									<% if (Survey.getType() != 1) { %>
        <tr class="containerBack">
									<% for(int z=1; z<8; z++) {%>
          <td valign=center align=center><%=z%></td>
									<%}%>
        </tr>
        <tr class="containerBody">
									<% for(int z=1; z<8; z++) {%>
          <td valign=center align=center><input name="quest<%=k%>qans" value="<%=z%>" type="radio"></td>
									<%}%>
        </tr>
									<%}%>
									<% if (Survey.getType() == 3) {%>
        <tr class="containerBody">
          <td width=15% valign=center align=right>
            Comments
          </td>
          <td colspan=6 valign=center>
            <textarea name="quest<%=k%>comments" rows=2 cols=80></textarea>
          </td>
        </tr>
									<%} else if (Survey.getType() == 1) {%>
        <tr class="containerBody">
          <td width=15% valign=center align=right>
            Response
          </td>
          <td colspan=6 valign=center><textarea name="quest<%=k%>response" rows=2 cols=80></textarea></td>
        </tr>
									<%}%>
      </table>
    </td>
  </tr>
								<%}%>
							<%} else if (thisField.getType() == CustomField.HIDDEN) {%>
							<%= thisField.getHtmlElement() %>
							<%} else if (thisField.getType() == CustomField.DISPLAYTEXT) {%> 
  <tr class="containerBody">
    <td colspan="2" valign="center">
      <%= thisField.getHtmlElement() %>
    </td>
  </tr>
							<%} else {%>
  <tr class="containerBody">
    <td width=125 valign="center" nowrap class="formLabel">
      <%-- Do not use toHtml() here, it's done by CustomField --%>
      <%= thisField.getDisplayHtml() %>
    </td>
    <td valign="center">
      <%= thisField.getHtmlElement() %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
      <font color='#006699'><%= toHtml(thisField.getError()) %></font>
    </td>
  </tr>
							<%}%>
						<%}
					} else {
				%>
  <tr class="containerBody">
    <td colspan=2>
      <font color="#9E9E9E">No custom form fields.</font>
    </td>
  </tr>
          <%}%>
			<%}%>
</table>
<br>
<%=CustomFormInfo.getHiddenValues()%>
<input type=hidden name="id" value="<%=Survey.getId()%>">
<%=CustomFormInfo.displayButtons()%>
<% if (request.getParameter("preview") == null) {%>
<input type="submit" value="Submit Survey">
<br>
<%}%>
<%}%>
<%}%>
</form>
</body>
</html>
