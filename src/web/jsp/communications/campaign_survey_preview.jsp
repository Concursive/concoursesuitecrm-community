<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*"%>
<jsp:useBean id="Survey" class="org.aspcfs.modules.Survey" scope="request"/>
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
<dhv:evaluate exp="<%=!surveyBlank%>">
<center>
<table cellpadding="4" cellspacing="0" border="0" width="85%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="row1">
    <td>
      <font color="#8c8c8c"><strong>Web Survey</strong></font>
    </td>
  </tr>
</table>
&nbsp;<br>
<form name="survey" action="CampaignManagerSurvey.do?command=MockInsert&id=<%=Survey.getId()%>"  method="post" onSubmit="return checkForm(this);">
  <table cellpadding="4" cellspacing="0" border="0" width="85%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="containerBody">
      <td colspan="2" valign="center">
        <%=toHtml(Survey.getIntro())%>
      </td>
    </tr>
  </table>
  &nbsp;<br>
  <table cellpadding="4" cellspacing="0" border="0" width="85%" bordercolorlight="#000000" bordercolor="#FFFFFF">
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
    <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr>
        <td colspan="7" width="100%" valign="center" class="containerHeader" align="left">
          <input type="hidden" name="quest<%=count%>id" value="<%=thisQuestion.getId()%>">
          <%=count%>. &nbsp;<%=thisQuestion.getDescription()%>
        </td>
      </tr>
      <dhv:evaluate exp="<%=(type == SurveyQuestion.QUANT_NOCOMMENTS) || (type == SurveyQuestion.QUANT_COMMENTS)%>">
      <tr class="containerBack">
      <% for(int i =0 ; i < 7;){%>
        <td valign="center" align="center"><%=++i%></td>
        <%}%>
       </tr>
       <tr class="containerBack">
       <% for(int i =0 ; i < 7 ;){%>
        <td valign="center" align="center">
          <input type="radio" name="quest<%=count%>qans" value="<%=++i%>">
        </td>
        <%}%>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate exp="<%=(type == SurveyQuestion.QUANT_COMMENTS) || (type == SurveyQuestion.OPEN_ENDED)%>">
       <tr class="containerBody">
          <td width="15%" valign="center" align="right">
            Comments
          </td>
          <td colspan="6" valign="center">
            <textarea name="quest<%=count%>comments" rows="2" cols="80"></textarea>
          </td>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate exp="<%=(type == SurveyQuestion.ITEMLIST)%>">
     <input type="hidden" name="quest<%=count%>itemCount" value="<%= thisQuestion.getItemList()!=null?thisQuestion.getItemList().size():0%>">
      <%
        Iterator k = thisQuestion.getItemList().iterator();
        if ( k.hasNext() ) {
        int itemCount = 0;
        while (k.hasNext()) {
        Item thisItem = (Item)k.next();
        %>
        <tr class="containerBack">
          <td valign="center" align="center" width="6%">
            <input type="checkbox" name="quest<%=count%>item<%=++itemCount%>">
            <input type="hidden" name="quest<%=count%>item<%=itemCount%>id" value="<%=thisItem.getId()%>">
          </td>
          <td valign="center" align="left">
            <%=thisItem.getDescription()%>
          </td>
        </tr>
       <%}
       }else{%>
       <tr>
           <td valign="center" align="center">
            No items found.
           </td>
        </tr>
        <%}%>
      </dhv:evaluate>
      </table>
     </td>
    </tr>
    <% 
      }
    }
    else {%>
      <tr bgcolor="white">
        <td colspan="6" valign="center">
          No Questions found in this Survey
        </td>
      </tr>
      <%}%>
    </table><br>
    <input type=hidden name="id" value="<%=request.getParameter("id")%>">
    <input type="submit" value="Submit Survey"><br>
    </form>
  </center>
</dhv:evaluate>
 </body>
</html>

