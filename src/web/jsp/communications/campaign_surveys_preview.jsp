<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="CustomFormInfo" class="com.darkhorseventures.controller.CustomForm" scope="request"/>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.controller.CustomFormTab,com.darkhorseventures.controller.CustomFormGroup" %>
<%@ include file="initPage.jsp" %>

<% if (CustomFormInfo.getReturnLink() != null && !(CustomFormInfo.getReturnLink().equals(""))) {%>
<a href="<%=CustomFormInfo.getReturnLink()%>"><%=CustomFormInfo.getReturnLinkText()%></a>
<%}%>
<br>

<form name="<%=CustomFormInfo.getName()%>" method="post" action="<%=CustomFormInfo.getAction()%>">

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

<%=CustomFormInfo.displayButtons()%>
<input type="button" value="Back" onClick="javascript:this.form.action='<%=CustomFormInfo.getCancel()%>';this.form.submit()">

<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">


	
	<%  
		Iterator groups = thisTab.iterator();
			while (groups.hasNext()) {
				CustomFormGroup thisGroup = (CustomFormGroup)groups.next();
	%>
				<!--tr class="title">
				<td colspan=2 valign=center align=left>
				<strong><%=thisGroup.getName()%></strong>
				</td>
				</tr-->
				
				<%  
					Iterator fields = thisGroup.iterator();
					if (fields.hasNext()) {
						while (fields.hasNext()) {
							CustomField thisField = (CustomField)fields.next();
							
							if (thisField.getType() == CustomField.ROWLIST) {
								for(int k=1; k<=thisField.getMaxRowItems(); k++) {%>
								<tr class="containerBody">
								<td width=125 valign="center" nowrap class="formLabel">
								<%= thisField.getDisplayHtml() %> <%=k%>
								</td>
								<td width=100% valign="center">
								<%= thisField.getRowListElement(k, (thisField.getType() == CustomField.ROWLIST)) %> <font color="red"><%= (thisField.getRequired()?"*":"") %></font>
								<font color='#006699'><%= toHtml(thisField.getError()) %></font>
								</td>
								</tr>
								<%}
								
							} else if (thisField.getType() == CustomField.ROWLIST_QUESTION) {%>
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
									</td>
									</tr>
									
									<tr class="containerBack">
									<% for(int z=1; z<8; z++) {%>
										<td valign=center align=center><%=z%></td>
									<%}%>
									</tr>
									
									<tr class="containerBody">
									<% for(int z=1; z<8; z++) {%>
									<td valign=center align=center><input name="ans<%=k%>" type="radio"></td>
									<%}%>
									</tr>
									</table>
								</td>
								</tr>
								<%}%>
							<%} else if (thisField.getType() == CustomField.HIDDEN) {%>
							<%= thisField.getHtmlElement() %>
							<%} else if (thisField.getType() == CustomField.SURVEY_INTRO) {%> 
							<tr class="containerBody">
							<td colspan=2 valign="center">
							<%= thisField.getHtmlElement() %>
							</td>
							</tr>
							<%} else {%>
							<tr class="containerBody">
							<%-- Do not use toHtml() here, it's done by CustomField --%>
							<td width=125 valign="center" nowrap class="formLabel">
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
<%=CustomFormInfo.displayButtons()%>
<input type="button" value="Back" onClick="javascript:this.form.action='<%=CustomFormInfo.getCancel()%>';this.form.submit()">

<br>
<%}%>
<%}%>
</form>
</body>

