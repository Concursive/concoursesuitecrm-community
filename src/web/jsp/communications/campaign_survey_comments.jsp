<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="SurveyAnswerList" class="com.darkhorseventures.cfsbase.SurveyAnswerList" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="listView" method="post" action="/CampaignManager.do?command=ShowComments">

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
      <td width=25 valign=center align=left>
      <strong>Item</strong>
    </td>  
    <td width=25 valign=center align=left>
      <strong>Response</strong>
    </td>  
    <td width=100% valign=center align=left>
      <strong>Comments</strong>
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
              <td align="right" class="row<%= rowid %>" nowrap>
          <%=thisAnswer.getQuestionId()%>
        </td>
        <td align="right" class="row<%= rowid %>" nowrap>
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
    <td class="row2" valign="center" colspan="5">
      No comments found.
    </td>
  </tr>
<%}%>
</table>
<br>
</form>
