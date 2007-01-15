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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ResponseDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionList" scope="request"/>
<jsp:useBean id="ResponseListDetailsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>"><dhv:label name="campaign.response">Response</dhv:label></a> >
<dhv:label name="campaign.responseDetails">Response Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="response" object="Campaign" param='<%= "id=" + Campaign.getId() %>'>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <dhv:label name="campaign.responseFrom.colon" param='<%= "contact.name="+contact.getNameLastFirst() %>'><b>Response from:</b> <%= contact.getNameLastFirst() %></dhv:label><br />
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
                 <dhv:label name="campaign.answer">Answer</dhv:label>
               </td>
               <td style="text-align: left; border: none; border-bottom: 1px solid #000;">
                 <dhv:label name="campaign.comments">Comments</dhv:label>
               </td>
             </tr>
           </dhv:evaluate>
           <%-- Show the answer: Item List --%>
           <dhv:evaluate if="<%= (type == SurveyQuestion.ITEMLIST) %>">
             <tr>
               <td width="100%" style="text-align: center; border: none; border-right: 1px solid #000; border-bottom: 1px solid #000;">
                 <dhv:label name="accounts.accounts_documents_details.Item">Item</dhv:label>
               </td>
               <td style="text-align: left; border: none; border-bottom: 1px solid #000;">
                 <dhv:label name="product.selection">Selection</dhv:label>
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
                 <td width="100%" colspan="2"><dhv:label name="campaign.noItemsSelected">No Items Selected.</dhv:label></td>
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
                   <dhv:evaluate if="<%= (type == SurveyQuestion.OPEN_ENDED) %>">
                     <tr>
                       <td width="100%" colspan="2" style="text-align: left; border: none;">
                         <li>
                          <% if(thisAnswer.getComments() != null && !"".equals(thisAnswer.getComments())) {%>
                            <%= toHtml(thisAnswer.getComments()) %>
                          <%} else {%>
                            <dhv:label name="campaign.noCommentsProvided">No comments provided</dhv:label>
                          <%}%>
                         </li>
                       </td>
                     </tr>
                   </dhv:evaluate>
                   <%-- Quant. without comments --%>
                   <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_NOCOMMENTS) %>">
                     <tr>
                       <td width="100%" colspan="2" style="text-align: center; border: none;">
                         <li>
                          <% if(thisAnswer.getQuantAns() != -1) {%>
                            <%= thisAnswer.getQuantAns() %>
                          <%} else {%>
                            <dhv:label name="campaign.noAnswerProvided">No answer provided</dhv:label>
                          <%}%>
                         </li>
                       </td>
                     </tr>
                   </dhv:evaluate>
                   <%-- Quant. with comments --%>
                   <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_COMMENTS) %>">
                     <tr>
                       <td width="4" style="text-align: center; border: none; border-right: 1px solid #000">
                         <%= thisAnswer.getQuantAns() != -1 ? thisAnswer.getQuantAns() + "" : "-" %>
                       </td>
                       <td style="text-align: left; border: none;">
                        <% if(thisAnswer.getComments() != null && !"".equals(thisAnswer.getComments())) {%>
                          <%= toHtml(thisAnswer.getComments()) %>
                        <%} else {%>
                          <dhv:label name="campaign.noCommentsProvided">No comments provided</dhv:label>
                        <%}%>
                       </td>
                     </tr>
                   </dhv:evaluate>
              <% }
                } else {
              %>
                 <tr>
                   <td width="100%" colspan="2">
                     <dhv:label name="campaign.noAnswersFound">No Answers Found.</dhv:label>
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
        <center><dhv:label name="campaign.noQuestionsFound">No Questions Found.</dhv:label></center>
  <%
    }
  %>
      </td>
    </tr>
  </table>
</dhv:container>
