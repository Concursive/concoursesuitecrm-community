<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SurveyQuestionList" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionList" scope="request"/>
<jsp:useBean id="SurveyQuestionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>">Campaign Details</a> >
Results
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1 = "id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="results" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" width="100%" border="0">
        <tr>
          <td>
            <table cellpadding="4" cellspacing="0" width="100%" class="details">
              <tr>
                <th>
                  <strong>Summary</strong>
                </th>
              </tr>
              <tr class="containerBody">
                <td align="left" nowrap>
                  <b><%= Campaign.getResponseCount() %></b> response<%= Campaign.getResponseCount()==1?"":"s" %> received from
                  <b><%= Campaign.getRecipientCount() %></b> recipient<%= Campaign.getRecipientCount()==1?"":"s" %><br>
                  Last Response Received:
                  <dhv:evaluate if="<%= "".equals(Campaign.getLastResponseString()) %>">
                  --
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= !"".equals(Campaign.getLastResponseString()) %>">
                    <dhv:tz timestamp="<%= Campaign.getLastResponse() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>" default="&nbsp;"/>
                  </dhv:evaluate>
                </td>
              </tr>
            </table>
          </td>
        </tr>
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
             <dhv:evaluate exp="<%= (type != SurveyQuestion.OPEN_ENDED) %>">
                <tr>
                  <th colspan="8">
                    Quantitive Statistics
                  </th>
                </tr>
                 <tr>
                 <th width="16%" style="text-align: center;">Avg</th>
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
             <dhv:evaluate exp="<%= ((type != SurveyQuestion.QUANT_NOCOMMENTS)) %>">
                <tr>
                  <th colspan="8">
                    Most recent user comments &nbsp; [<a href="javascript:popURLReturn('CampaignManager.do?command=ShowComments&reset=true&surveyId=<%= SurveyQuestionList.getId() %>&questionId=<%= thisItem.getId() %>&type=<%= thisItem.getType()==1?"open":"quant" %>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Comments','500','400','yes','yes');">Show All...</a>]
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
                        No comments found.
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
                  [<a href="javascript:popURLReturn('CampaignManager.do?command=ShowItems&questionId=<%= thisItem.getId() %>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Items','600','450','yes','yes');">View Item Details</a>]
                </td>
             </tr>
            <%}%>
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
