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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*"%>
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head>
  <title><dhv:label name="campaign.surveyPage.thankYou">Thank you for visiting our survey page</dhv:label></title>
<script language="JavaScript">
function checkForm(form) {
  formTest = true;
  message = "";
  for (i=0;i<form.length;i++){
    var tempobj=form.elements[i];
    if ( (tempobj.name).search("qans") != -1 ) {
      if (!validateRadio(document.survey.elements[tempobj.name])) {
        formTest = false;
      }
      i = i+7;
    }
    if (formTest == false) 
      break;
  }
  if (formTest == false) {
    alert(label("caution.provideanswer.survey","Please Provide an answer for each survey item.\r\n\r\n"));
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
      if (field[b].checked) return true;
    }
  }
  return false;
}
</script>
</head>
<body>
&nbsp;<br>
&nbsp;<br>
<% 
  boolean surveyBlank = false;
  if(request.getParameter("preview") != null && Integer.parseInt(request.getParameter("preview")) == 0){
    surveyBlank = true;
  }
%> 
<dhv:evaluate if="<%=!surveyBlank%>">
<center>
<table cellpadding="4" cellspacing="0" border="0" width="<%= (request.getParameter("inline") != null?"100%":"85%") %>">
  <tr class="row1">
    <td>
      <font color="#8c8c8c"><strong><dhv:label name="campaign.webSurvey">Web Survey</dhv:label></strong></font>
    </td>
  </tr>
</table>
&nbsp;<br>
<form name="survey" action="CampaignManagerSurvey.do?command=MockInsert&id=<%=Survey.getId()%>"  method="post" onSubmit="return checkForm(this);">
  <table cellpadding="4" cellspacing="0" border="0" width="<%= (request.getParameter("inline") != null?"100%":"85%") %>">
    <tr class="containerBody">
      <td colspan="2" valign="center">
        <%= toHtml(Survey.getIntro()) %>
      </td>
    </tr>
  </table>
  &nbsp;<br>
  <table cellpadding="4" cellspacing="0" border="0" width="<%= (request.getParameter("inline") != null?"100%":"85%") %>">
    <%
  Iterator j = Survey.getQuestions().iterator();
	if ( j.hasNext() ) {
		int count = 0;
	  while (j.hasNext()) {
			count++;		
      SurveyQuestion thisQuestion = (SurveyQuestion)j.next();
      int type = thisQuestion.getType();
   %>
 <tr>
  <td width="100%" valign="top">
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <td colspan="7" width="100%" valign="center" class="containerHeader">
          <input type="hidden" name="quest<%=count%>id" value="<%=thisQuestion.getId()%>">
          <%=count%>. &nbsp;<%=thisQuestion.getDescription()%>
        </td>
      </tr>
      <dhv:evaluate if="<%=(type == SurveyQuestion.QUANT_NOCOMMENTS) || (type == SurveyQuestion.QUANT_COMMENTS)%>">
      <tr class="containerBack">
      <% for(int i =0 ; i < 7;){%>
        <td valign="center" style="text-align: center;"><%=++i%></td>
        <%}%>
       </tr>
       <tr class="containerBack">
       <% for(int i =0 ; i < 7 ;){%>
        <td valign="center" style="text-align: center;">
          <input type="radio" name="quest<%= count %>qans" value="<%=++i%>">
        </td>
        <%}%>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate if="<%=(type == SurveyQuestion.QUANT_COMMENTS) || (type == SurveyQuestion.OPEN_ENDED)%>">
       <tr class="containerBody">
          <td width="15%" valign="center" style="text-align: right;">
            <dhv:label name="campaign.comments">Comments</dhv:label>
          </td>
          <td colspan="6" valign="center">
            <textarea name="quest<%=count%>comments" rows="2" cols="80"></textarea>
          </td>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate if="<%=(type == SurveyQuestion.ITEMLIST)%>">
     <input type="hidden" name="quest<%=count%>itemCount" value="<%= thisQuestion.getItemList()!=null?thisQuestion.getItemList().size():0%>">
      <%
        Iterator k = thisQuestion.getItemList().iterator();
        if ( k.hasNext() ) {
          int itemCount = 0;
          while (k.hasNext()) {
            Item thisItem = (Item)k.next();
        %>
        <tr class="containerBack">
          <td valign="center" style="text-align: center;" width="6%">
            <input type="checkbox" name="quest<%= count %>item<%=++itemCount%>" value="true">
            <input type="hidden" name="quest<%= count %>item<%= itemCount %>id" value="<%= thisItem.getId() %>">
          </td>
          <td valign="center">
            <%= toHtml(thisItem.getDescription()) %>
          </td>
        </tr>
       <%}
       }else{%>
        <tr>
           <td style="text-align: center;">
            <dhv:label name="campaign.noItemsFound">No items found.</dhv:label>
           </td>
        </tr>
        <%}%>
      </dhv:evaluate>
      </table>
     </td>
    </tr>
    <% 
      }
    } else {%>
      <tr bgcolor="white">
        <td colspan="6">
          <dhv:label name="campaign.neQuestionsFound">No Questions found in this Survey</dhv:label>
        </td>
      </tr>
      <%}%>
    </table><br>
    <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
    <input type="submit" value="<dhv:label name="campaign.submitSurvey">Submit Survey</dhv:label>"><br>
    </form>
  </center>
</dhv:evaluate>
 </body>
</html>

