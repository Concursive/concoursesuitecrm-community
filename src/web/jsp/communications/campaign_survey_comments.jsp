<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="CommentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SurveyAnswerList" class="org.aspcfs.modules.communications.base.SurveyAnswerList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  boolean openEnded =  "open".equalsIgnoreCase(request.getParameter("type"));
%>
<center><%= CommentListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CommentListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="<%= openEnded ? "3" : "4"%>">
      <strong>Survey Comments</strong>
    </td>
  </tr>
  <tr class="title">
    <dhv:evaluate if="<%= !openEnded %>">
      <td width="20" align="center" nowrap>
        Answer Provided
      </td>
    </dhv:evaluate>
    <td width="60" align="left" nowrap>
      Comment Provided
    </td>
    <td valign="center" align="left" nowrap>
      User
    </td>
    <td valign="center" align="left" nowrap>
      Entered
    </td>
  </tr>
<%    
	Iterator i = SurveyAnswerList.iterator();
	if (i.hasNext()) {
    int rowid = 0;
		while (i.hasNext()) {
			rowid = (rowid != 1?1:2);
      SurveyAnswer thisAnswer = (SurveyAnswer)i.next();
%>      
   <tr class="row<%= rowid %>">
 <dhv:evaluate if="<%= !openEnded %>">
    <td valign="top" align="center" class="row<%= rowid %>" nowrap>
      <%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns()+"" : "&nbsp;" %>
    </td>
  </dhv:evaluate>
    <td width="60">
      <%=toHtml(thisAnswer.getComments())%>
    </td>
    <td valign="center" align="left" nowrap>
      <dhv:contactname id="<%=thisAnswer.getContactId()%>" listName="SurveyContactList"/>
    </td>
    <td valign="center" align="left" nowrap>
      <%= toDateString(thisAnswer.getEntered()) %>
    </td>
  </tr>
<%
   }
%>
</table>
    <br>
    <dhv:pagedListControl object="CommentListInfo" />
  <%} else {%>  
  <tr>
    <td class="containerBody" colspan="<%= openEnded ? "3" : "4" %>">
      No comments found for this question.
    </td>
  </tr>
  </table>
<%}%>
<br>
<input type="button" value="Close Window" onClick="javascript:window.close();">
