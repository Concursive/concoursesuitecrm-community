<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="CommentListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SurveyAnswerList" class="org.aspcfs.modules.communications.base.SurveyAnswerList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%
  boolean openEnded =  "open".equalsIgnoreCase(request.getParameter("type"));
%>
<br>
<center><%= CommentListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="CommentListInfo"/>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="<%= openEnded ? "2" : "3"%>">
      <strong>Survey Comments</strong>
    </td>
  </tr>
  <tr class="title">
    <%if(!openEnded){%>
      <td width="25" valign="top" align="center">
        Answer Provided
      </td>
    <%}%>
    <td width="85%" valign="center" align="left">
      Comment Provided
    </td>
    <td valign="center" align="left">
      User
    </td>
    <td valign="center" align="left">
      Entered
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
   <tr class="row<%= rowid %>">
    <%if(!openEnded){%>
      <td valign="top" align="center" class="row<%= rowid %>" nowrap>
        <%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns()+"" : "&nbsp;" %>
      </td>
    <%}%>
    
      <td width="80%">
        <%=toHtml(thisAnswer.getComments())%>
      </td>
      <td valign="center" align="left" nowrap>
        <dhv:contactname id="<%=thisAnswer.getContactId()%>" listName="SurveyContactList"/>
      </td>
      <td valign="center" align="left" >
        <%= toDateTimeString(thisAnswer.getEntered()) %>
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
    <td class="row2" valign="center" colspan="<%= openEnded ? "2" : "3" %>">
      No comments found for this question.
    </td>
  </tr>
  </table>
<%}%>
<br>

<input type="button" value="Close Window" onClick="javascript:window.close();">
