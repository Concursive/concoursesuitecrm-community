<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="SurveyAnswerList" class="com.darkhorseventures.cfsbase.SurveyAnswerList" scope="request"/>
<%@ include file="initPage.jsp" %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Survey Comments</strong>
    </td>
  </tr>
  <tr class="title">
    <td width="25" valign="top" align="center">
      Answer Provided
    </td>  
    <td width="100%" valign="center" align="left">
      Comment Provided
    </td>
  </tr>
<%    
	Iterator i = SurveyAnswerList.iterator();
	if (i.hasNext()) {
    int rowid = 0;
		while (i.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
      SurveyAnswer thisAnswer = (SurveyAnswer)i.next();
%>      
  <tr>
    <td valign="top" align="center" class="row<%= rowid %>" nowrap>
      <%=thisAnswer.getQuantAns()%>
    </td>
    <td class="row<%= rowid %>">
      <%=toHtml(thisAnswer.getComments())%>
      </td>
    </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="row2" valign="center" colspan="2">
      No comments found for this question.
    </td>
  </tr>
<%}%>
</table>
<br>
<input type="button" value="Close Window" onClick="javascript:window.close();">
