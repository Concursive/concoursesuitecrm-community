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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SurveyQuestionList" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionList" scope="request"/>
<jsp:useBean id="SurveyQuestionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="yesAddressUpdateResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<jsp:useBean id="noAddressUpdateResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<jsp:useBean id="recipientList" class="org.aspcfs.modules.communications.base.RecipientList" scope="request"/>
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
<dhv:label name="accounts.accounts_contacts_listimports.Results">Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="results" object="Campaign" param='<%= "id=" + Campaign.getId() %>'>
    <table cellpadding="4" cellspacing="0" width="100%" border="0">
      <tr>
        <td>
          <dhv:evaluate if="<%= Campaign.getHasAddressRequest() %>">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th>
                  <strong><dhv:label name="campaign.addressRequestSummary">Address Request Summary</dhv:label></strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td align="left" nowrap>
                  <b><%= Campaign.getAddressResponseCount() %></b>
                  <% if(Campaign.getAddressResponseCount()==1) {%>
                    <dhv:label name="campaign.response.lowercase">response</dhv:label>
                  <%} else {%>
                    <dhv:label name="campaign.responses.lowercase">responses</dhv:label>
                  <%}%> <dhv:label name="admin.receivedFrom">received from</dhv:label> <b><%= Campaign.getRecipientCount() %></b>
                  <% if(Campaign.getRecipientCount()==1) {%>
                    <dhv:label name="campaign.recipient.lowercase">recipient</dhv:label>
                  <%} else {%>
                    <dhv:label name="campaign.recipients.lowercase">recipients</dhv:label>
                  <%}%><br />
                  <dhv:evaluate if='<%= "".equals(Campaign.getLastAddressResponseString()) %>'>
                    <dhv:label name="campaign.lastResponseReceived.colon" param="time=--">Last Response Received: --</dhv:label>
                  </dhv:evaluate>
                  <dhv:evaluate if='<%= !"".equals(Campaign.getLastAddressResponseString()) %>'>
                    <dhv:label name="campaign.lastResponseReceived.colon" param="time=">Last Response Received:</dhv:label><zeroio:tz timestamp="<%= Campaign.getLastAddressResponse() %>" timeZone="<%= User.getTimeZone() %>"  showTimeZone="true"/>
                  </dhv:evaluate>
                  <br />
                  For more information on those who updated their contact information (#<%=yesAddressUpdateResponseList.size()%>), click <a href="javascript:popURLReturn('CampaignManager.do?command=AddressUpdateResponseDetails&id=<%=Campaign.getId()%>&section=<%=SurveyResponse.ADDRESS_UPDATED%>&popup=true','CampaignManager.do?command=Details&reset=true', 'View_Address_Response_Details','700','500','yes','no');">here</a><br />
                  For more information on those who found their contact information valid (#<%=noAddressUpdateResponseList.size()%>), click <a href="javascript:popURLReturn('CampaignManager.do?command=AddressUpdateResponseDetails&id=<%=Campaign.getId()%>&section=<%=SurveyResponse.ADDRESS_VALID%>&popup=true','CampaignManager.do?command=Details&reset=true', 'View_Address_Response_Details','700','500','yes','no');">here</a><br />
                  For more information on those who did not respond (#<%=recipientList.size()%>), click <a href="javascript:popURLReturn('CampaignManager.do?command=AddressUpdateResponseDetails&id=<%=Campaign.getId()%>&section=<%=SurveyResponse.ADDRESS_NO_RESPONSE%>&popup=true','CampaignManager.do?command=Details&reset=true', 'View_Address_Response_Details','700','500','yes','no');">here</a><br />
                </td>
              </tr>
            </table>
            &nbsp;<br />
          </dhv:evaluate>
          <dhv:evaluate if="<%= Campaign.getHasSurvey() %>">
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th>
                  <strong><dhv:label name="campaign.summary">Summary</dhv:label></strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td align="left" nowrap>
                  <b><%= Campaign.getResponseCount() %></b>
                  <% if(Campaign.getResponseCount()==1) {%>
                    <dhv:label name="campaign.response.lowercase">response</dhv:label>
                  <%} else {%>
                    <dhv:label name="campaign.responses.lowercase">responses</dhv:label>
                  <%}%> <dhv:label name="admin.receivedFrom">received from</dhv:label> <b><%= Campaign.getRecipientCount() %></b>
                  <% if(Campaign.getRecipientCount()==1) {%>
                    <dhv:label name="campaign.recipient.lowercase">recipient</dhv:label>
                  <%} else {%>
                    <dhv:label name="campaign.recipients.lowercase">recipients</dhv:label>
                  <%}%><br />
                  <dhv:evaluate if='<%= "".equals(Campaign.getLastResponseString()) %>'>
                    <dhv:label name="campaign.lastResponseReceived.colon" param="time=--">Last Response Received: --</dhv:label>
                  </dhv:evaluate>
                  <dhv:evaluate if='<%= !"".equals(Campaign.getLastResponseString()) %>'>
                    <dhv:label name="campaign.lastResponseReceived.colon" param="time=">Last Response Received:</dhv:label><zeroio:tz timestamp="<%= Campaign.getLastResponse() %>" timeZone="<%= User.getTimeZone() %>"  showTimeZone="true"/>
                  </dhv:evaluate>
                </td>
              </tr>
            </table>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !Campaign.getHasSurvey() && !Campaign.getHasAddressRequest()%>">
            <strong><dhv:label name="campaign.noSurveyOrAddressUpdateRequest.text">This campaign does not have a survey or address update request.</dhv:label></strong>
          </dhv:evaluate>
        </td>
      </tr>
      <dhv:evaluate if="<%= Campaign.getHasSurvey() %>">
      <tr>
        <td>
<%
      Iterator z = SurveyQuestionList.iterator();

      if ( z.hasNext() ) {
        int rowid = 0;
        int count = 0;
        while (z.hasNext()) {
          count++;
          rowid = (rowid != 1?1:2);
          ActiveSurveyQuestion thisItem = (ActiveSurveyQuestion) z.next();
          int type = thisItem.getType();
%>
        <table cellpadding="4" cellspacing="0" width="100%" class="details">
          <% if (type != SurveyQuestion.ITEMLIST) { %>
           <tr class="containerHeader">
             <td colspan="8" width="100%"><%= count %>. <%= toHtml(thisItem.getDescription()) %></td>
           </tr>
           <dhv:evaluate if="<%= (type != SurveyQuestion.OPEN_ENDED) %>">
              <tr>
                <th colspan="8">
                  <dhv:label name="campaign.quantitativeStatistics">Quantitive Statistics</dhv:label>
                </th>
              </tr>
               <tr>
               <th width="16%" style="text-align: center;"><dhv:label name="campaign.avg">Avg</dhv:label></th>
                <% for (int i=0; i < 7;) {%>
                  <th width="12%" style="text-align: center;"><%= ++i %></th>
                <%}%>
               </tr>
               <tr class="containerBody">
                <td style="text-align: center;" nowrap><%= toHtml(thisItem.getAverageValue()) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(0))) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(1))) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(2))) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(3))) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(4))) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(5))) %></td>
                <td style="text-align: center;" nowrap><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(6))) %></td>
               </tr>
           </dhv:evaluate>
           <dhv:evaluate if="<%= ((type != SurveyQuestion.QUANT_NOCOMMENTS)) %>">
              <tr>
                <th colspan="8">
                  <dhv:label name="campaign.mostRecentUserComments">Most recent user comments</dhv:label> &nbsp; [<a href="javascript:popURLReturn('CampaignManager.do?command=ShowComments&reset=true&surveyId=<%= SurveyQuestionList.getId() %>&questionId=<%= thisItem.getId() %>&type=<%= thisItem.getType()==1?"open":"quant" %>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Comments','500','400','yes','yes');"><dhv:label name="campaign.showAll">Show All...</dhv:label></a>]
                </th>
              </tr>
              <%
                SurveyAnswerList commentList = thisItem.getAnswerList();
                HashMap contacts = (HashMap) commentList.getContacts();
                Iterator i = commentList.iterator();
                if (i.hasNext()) {
                  while (i.hasNext()) {
                    SurveyAnswer thisAnswer = (SurveyAnswer)i.next();
               %>
                  <tr class="containerBody">
                    <td colspan="7" valign="center" align="left" width="85%">
                      <%= toHtml(thisAnswer.getComments()) %>
                    </td>
                    <td valign="center" align="left">
                      <%= contacts.get(new Integer(thisAnswer.getContactId())) %>
                    </td>
                  </tr>
               <%}
               } else {
               %>
                  <tr class="containerBody">
                    <td colspan="8">
                      <dhv:label name="campaign.noCommentsFound">No comments found.</dhv:label>
                    </td>
                  </tr>
               <%
                }
               %>
          </dhv:evaluate>
          <%}else{%>
           <tr class="containerHeader">
             <td style="text-align: left;" nowrap><%= count %>. <%= toHtml(thisItem.getDescription()) %> </td>
           </tr>
           <tr>
              <td class="containerBody" valign="center" style="text-align: left;" width="100%">
                [<a href="javascript:popURLReturn('CampaignManager.do?command=ShowItems&questionId=<%= thisItem.getId() %>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Items','600','450','yes','yes');"><dhv:label name="campaign.viewItemDetails">View Item Details</dhv:label></a>]
              </td>
           </tr>
          <%}%>
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
      </dhv:evaluate>
    </table>
</dhv:container>
