<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="ResponseDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionList" scope="request"/>
<jsp:useBean id="ResponseListDetailsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
<a href="CampaignManager.do?command=ViewResponse&id=<%=Campaign.getId()%>">Response</a> >
Response Details
<hr color="#BFBFBB" noshade>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td colspan="2" valign="center" align="left">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td colspan="2">
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="response" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr>
            <td>
<%
        Iterator z = ResponseDetails.iterator();
        
        if ( z.hasNext() ) {
          int rowid = 0;
          int count = 0;
          while (z.hasNext()) {
            count++;		
            if (rowid != 1) {
              rowid = 1;
            } else {
              rowid = 2;
            }
            ActiveSurveyQuestion thisItem = (ActiveSurveyQuestion)z.next();
            int type = thisItem.getType();
            int border = (thisItem.getType() == SurveyQuestion.ITEMLIST || thisItem.getType() == SurveyQuestion.QUANT_COMMENTS ? 1 : 0);
            SurveyAnswerList answers = thisItem.getAnswerList();
             
%>
          <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
             <tr class="containerHeader">
               <td align="left" colspan="8" nowrap><%=count%>. <%= toHtml(thisItem.getDescription()) %> </td>
             </tr>
               <tr class="title">
                  <td valign="center" align="left" width="24">
                    Answer(s) Provided
                  </td>
               </tr>
                 <tr>
                   <td class="containerBody">
                    <table cellpadding="4" cellspacing="0" border="<%= border %>" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
                    <dhv:evaluate exp="<%= (type == SurveyQuestion.QUANT_COMMENTS) %>">
                      <tr><td align="left" width="4">Answer</td><td align="left">Comments</td></tr>
                    </dhv:evaluate>
                    <dhv:evaluate exp="<%= (type == SurveyQuestion.ITEMLIST) %>">
                      <tr><td width="100%" align="left">Item</td><td align="left">Selection</td></tr>
                      <% 
                        HashMap itemListResponse = thisItem.getItemListResponse(answers);
                        Iterator i = itemListResponse.keySet().iterator();
                        if(i.hasNext()){
                        while(i.hasNext()){
                          ActiveSurveyQuestionItem item = (ActiveSurveyQuestionItem) i.next();
                        %>
                          <tr><td width="80%" align="left"><%= toHtml(item.getDescription()) %></td><td align="left"><%= itemListResponse.get((Object) item) %></td></tr>
                        <%}
                        }else{
                      %>
                        <tr><td width="100%" align="left">No Items Selected.</td></tr>
                      <%}%>
                    </dhv:evaluate>
                    <dhv:evaluate exp="<%= (type != SurveyQuestion.ITEMLIST) %>">
                      <%
                         Iterator answerList = answers.iterator();
                         if(answerList.hasNext()){
                          while(answerList.hasNext()){
                          SurveyAnswer thisAnswer = (SurveyAnswer) answerList.next();
                       %>
                            
                            <dhv:evaluate exp="<%= (type == SurveyQuestion.OPEN_ENDED) %>">
                              <tr><td width="100%" align="left"><li><%= (thisAnswer.getComments() != null && !"".equals(thisAnswer.getComments())) ? toHtml(thisAnswer.getComments() : "No comments provided") %></li></td></tr>
                            </dhv:evaluate>
                            <dhv:evaluate exp="<%= (type == SurveyQuestion.QUANT_NOCOMMENTS) %>">
                              <tr><td width="100%" align="left"><li><%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns() + "" : "No answer provided" %></li></td></tr>
                            </dhv:evaluate>
                            <dhv:evaluate exp="<%= (type == SurveyQuestion.QUANT_COMMENTS) %>">
                              <tr><td width="4" align="center"><%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns() + "" : "-" %></td><td align="left"><%= (thisAnswer.getComments() != null && !"".equals(thisAnswer.getComments())) ? toHtml(thisAnswer.getComments()) : "No comments provided" %></td></tr>
                            </dhv:evaluate>
                       <% }
                         }else{
                       %>
                          <tr><td width="100%" align="left">No Answers Found.</td></tr>
                       <%}
                        %>
                     </dhv:evaluate>
                    </table>
                   </td>
                  </tr>
         </table>
         <br>
      <%
          }
        }else {
      %>
            <center>No Questions Found.</center>
      <%
        }
      %>
          </td>
        </tr>
      </table>
    </td>
   </tr>
</table>
