<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ResponseDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionList" scope="request"/>
<jsp:useBean id="ResponseListDetailsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>">Campaign Details</a> >
<a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>">Response</a> >
Response Details
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1 = "id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="response" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
        <tr>
          <td>
            <b>Response from:</b> <%= contact.getNameLastFirst() %><br>
            &nbsp;<br>
<%
        Iterator z = ResponseDetails.iterator();
        if ( z.hasNext() ) {
          int rowid = 0;
          int count = 0;
          while (z.hasNext()) {
            count++;		
            rowid = (rowid != 1?1:2);
            ActiveSurveyQuestion thisItem = (ActiveSurveyQuestion)z.next();
            int type = thisItem.getType();
            SurveyAnswerList answers = thisItem.getAnswerList();
%>
          <table cellpadding="4" cellspacing="0" width="100%" class="details">
            <%-- Show the question --%>
            <tr>
              <th colspan="8" nowrap><%=count%>. <%= toHtml(thisItem.getDescription()) %></th>
            </tr>
            <tr>
              <td class="containerBody">
               <table cellpadding="4" cellspacing="0" width="100%">
               <%-- Show the answer: Quantitative w/Comments --%>
               <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_COMMENTS) %>">
                 <tr>
                   <td style="text-align: center; border: none; border-right: 1px solid #000; border-bottom: 1px solid #000;" width="4">
                     Answer
                   </td>
                   <td style="text-align: left; border: none; border-bottom: 1px solid #000;">
                     Comments
                   </td>
                 </tr>
               </dhv:evaluate>
               <%-- Show the answer: Item List --%>
               <dhv:evaluate if="<%= (type == SurveyQuestion.ITEMLIST) %>">
                 <tr>
                   <td width="100%" style="text-align: center; border: none; border-right: 1px solid #000; border-bottom: 1px solid #000;">
                     Item
                   </td>
                   <td style="text-align: left; border: none; border-bottom: 1px solid #000;">
                     Selection
                   </td>
                 </tr>
                 <% 
                   HashMap itemListResponse = thisItem.getItemListResponse(answers);
                   Iterator i = itemListResponse.keySet().iterator();
                   if(i.hasNext()){
                   while(i.hasNext()){
                     ActiveSurveyQuestionItem item = (ActiveSurveyQuestionItem) i.next();
                   %>
                     <tr>
                       <td width="80%" style="text-align: center; border: none; border-right: 1px solid #000; border-bottom: 1px solid #000;">
                         <%= toHtml(item.getDescription()) %>
                       </td>
                       <td style="text-align: left; border: none; border-bottom: 1px solid #000;">
                         <%= itemListResponse.get((Object) item) %>
                       </td>
                     </tr>
                   <%}
                   }else{
                 %>
                   <tr>
                     <td width="100%" colspan="2">No Items Selected.</td>
                   </tr>
                 <%}%>
               </dhv:evaluate>
               <%-- Other than an item list, show the answer --%>
               <dhv:evaluate if="<%= (type != SurveyQuestion.ITEMLIST) %>">
<%
                    Iterator answerList = answers.iterator();
                    if (answerList.hasNext()) {
                      while (answerList.hasNext()) {
                        SurveyAnswer thisAnswer = (SurveyAnswer) answerList.next();
%>
                       <%-- Open Ended --%>
                       <dhv:evaluate exp="<%= (type == SurveyQuestion.OPEN_ENDED) %>">
                         <tr>
                           <td width="100%" colspan="2" style="text-align: left; border: none;">
                             <li><%= (thisAnswer.getComments() != null && !"".equals(thisAnswer.getComments())) ? toHtml(thisAnswer.getComments()) : "No comments provided" %></li>
                           </td>
                         </tr>
                       </dhv:evaluate>
                       <%-- Quant. without comments --%>
                       <dhv:evaluate exp="<%= (type == SurveyQuestion.QUANT_NOCOMMENTS) %>">
                         <tr>
                           <td width="100%" colspan="2" style="text-align: center; border: none;">
                             <li><%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns() + "" : "No answer provided" %></li>
                           </td>
                         </tr>
                       </dhv:evaluate>
                       <%-- Quant. with comments --%>
                       <dhv:evaluate exp="<%= (type == SurveyQuestion.QUANT_COMMENTS) %>">
                         <tr>
                           <td width="4" style="text-align: center; border: none; border-right: 1px solid #000">
                             <%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns() + "" : "-" %>
                           </td>
                           <td style="text-align: left; border: none;">
                             <%= (thisAnswer.getComments() != null && !"".equals(thisAnswer.getComments())) ? toHtml(thisAnswer.getComments()) : "No comments provided" %>
                           </td>
                         </tr>
                       </dhv:evaluate>
                  <% }
                    } else {
                  %>
                     <tr>
                       <td width="100%" colspan="2">
                         No Answers Found.
                       </td>
                     </tr>
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
