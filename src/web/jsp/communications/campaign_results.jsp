<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="SurveyQuestionList" class="com.darkhorseventures.cfsbase.ActiveSurveyQuestionList" scope="request"/>
<jsp:useBean id="SurveyQuestionListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<%@ include file="initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="/CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
Results
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
      <dhv:container name="communications" selected="results" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="0" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr>
          <td>
            <table cellpadding="4" cellspacing="0" border="1" width="100%">
              <tr class="title">
                <td valign="center" align="left">
                  <strong>Summary</strong>
                </td>
              </tr>
              <tr class="containerBody">
                <td align="left" nowrap>
                  <%= Campaign.getRecipientCount() %>  Total Recipients<br>
                  <%= Campaign.getResponseCount() %>  Total Responses<br>
                  Last Response Received : <%= Campaign.getLastResponseString() %>
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
            if (rowid != 1) {
              rowid = 1;
            } else {
              rowid = 2;
            }
            ActiveSurveyQuestion thisItem = (ActiveSurveyQuestion)z.next();
            int type = thisItem.getType();
      %>
          <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
            <%if(type != SurveyQuestion.ITEMLIST){%>
             <tr class="containerHeader">
               <td align="left" colspan="8" nowrap><%=count%>. <%= toHtml(thisItem.getDescription()) %> </td>
             </tr>
             <dhv:evaluate exp="<%=(type != SurveyQuestion.OPEN_ENDED)%>">
                <tr class="title">
                  <td colspan="8" valign="center" align="left" width="24">
                    Quantitive Statistics
                  </td>
                </tr>
                 <tr class="title">
                 <td width="24" align="center">Avg</td>
                  <% for(int i =0 ; i < 7;){%>
                    <td width="24" align="center"><%=++i%></td>
                  <%}%>
                 </tr>
                 <tr class="containerBody">
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getAverageValue())%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(String.valueOf(thisItem.getResponseTotals().get(0)))%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getResponseTotals().get(1) + "")%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getResponseTotals().get(2) + "")%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getResponseTotals().get(3) + "")%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getResponseTotals().get(4) + "")%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getResponseTotals().get(5) + "")%></td>
                  <td width="24" align="center" nowrap ><%= toHtml(thisItem.getResponseTotals().get(6) + "")%></td>
                 </tr>
             </dhv:evaluate>
             <dhv:evaluate exp="<%=((type != SurveyQuestion.QUANT_NOCOMMENTS))%>">
                <tr class="title">
                  <td colspan="8" valign="center" align="left">
                    Most recent user comments &nbsp; <a href="javascript:popURLReturn('/CampaignManager.do?command=ShowComments&surveyId=<%=SurveyQuestionList.getId()%>&questionId=<%=thisItem.getId()%>&type=<%=thisItem.getType()==1?"open":"quant"%>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Comments','500','240','yes','no');">Show All...</a>
                  </td>
                </tr>
                <%
                  LinkedHashMap comments = thisItem.getComments();
                  Iterator k = comments.keySet().iterator();
                  if(k.hasNext()){
                    while(k.hasNext()){
                    Integer user = (Integer)k.next();
                    Iterator l = ((ArrayList)comments.get(user)).iterator();
                    while(l.hasNext()){
                 %>
                    <tr class="containerBody">
                      <td colspan="7" valign="center" align="left" width="85%">
                        <%=(String)l.next()%>
                      </td>
                      <td valign="center" align="left">
                        <dhv:username id="<%=user.intValue()%>"/>
                      </td>
                    </tr>
                 <%}
                  }
                 %>
                 <%}else{
                 %>
                    <tr>
                      <td colspan="8" valign="center" align="left">
                        No comments found.
                      </td>
                    </tr>
                 <%
                  }
                 %>
               </dhv:evaluate>
            </dhv:evaluate>
            <%}else{%>
             <tr containerHeader>
               <td align="left" nowrap><%=count%>. <%= toHtml(thisItem.getDescription()) %> </td>
             </tr>
             <tr>
                  <td valign="center" align="left" width="100%">
                    <a href="javascript:popURLReturn('/CampaignManager.do?command=ShowItems&questionId=<%=thisItem.getId()%>&popup=true','CampaignManager.do?command=Details&reset=true','Survey_Items','600','450','yes','no');">View Item Details</a>
                  </td>
             </tr>
            
            <%}%>
         </table><br>
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
