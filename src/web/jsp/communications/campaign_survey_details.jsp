<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManagerAttachment.do"><dhv:label name="communications.campaign.CreateAttachments">Create Attachments</dhv:label></a> >
<a href="CampaignManagerSurvey.do?command=View"><dhv:label name="campaign.surveys">Surveys</dhv:label></a> >
<dhv:label name="campaign.surveyDetails">Survey Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="campaign-campaigns-surveys-edit">
<input type="button" name="action" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='CampaignManagerSurvey.do?command=Modify&id=<%=Survey.getId()%>'">
</dhv:permission>
<dhv:permission name="campaign-campaigns-surveys-delete">
<input type="button" name="action" value="<dhv:label name="campaign.deleteSurvey">Delete Survey</dhv:label>" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=ConfirmDelete&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=View', 'Delete_survey','330','200','yes','no');">
</dhv:permission>
<input type="button" name="action" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=Preview&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=Details&id=<%=Survey.getId()%>', 'Preview_Survey','760','510','yes','yes');">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="campaign.surveyDetails">Survey Details</dhv:label></strong>
    </th>
  </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="contacts.name">Name</dhv:label>
      </td>
      <td valign="top">
        <%= toHtml(Survey.getName()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
      </td>
      <td valign="top">
        <%= toHtml(Survey.getDescription()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= Survey.getEnteredBy() %>"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="quotes.date">Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= Survey.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="campaign.lastModifiedBy">Last Modified By</dhv:label>
      </td>
      <td valign="top">
        <dhv:username id="<%= Survey.getModifiedBy() %>"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="quotes.date">Date</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= Survey.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      </td>
    </tr>
  </table>
 <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th>
        <strong><dhv:label name="campaign.surveyIntroduction.text">Survey Introduction Text</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td>
        <%= toHtml(Survey.getIntro()) %>
      </td>
    </tr>
  </table>
  <br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="campaign.surveyQuestions">Survey Questions</dhv:label></strong>
    </th>
  </tr>
<%
	Iterator j = Survey.getQuestions().iterator();
	if ( j.hasNext() ) {
		int count = 0;
	  while (j.hasNext()) {
			count++;		
      SurveyQuestion thisQuestion = (SurveyQuestion) j.next();
      int type = thisQuestion.getType();
%>
 <tr>
  <td width="100%" valign="top">
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <td colspan="7" width="100%" valign="top" class="containerHeader">
          <input type="hidden" name="quest<%= count %>id" value="<%= thisQuestion.getId() %>">
          <%= count %>.&nbsp;<%= toHtml(thisQuestion.getDescription()) %>
        </td>
      </tr>
      <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_NOCOMMENTS) || (type == SurveyQuestion.QUANT_COMMENTS) %>">
      <tr class="containerBack">
      <% for(int i =0 ; i < 7;){%>
        <td valign="center" style="text-align: center;">
          <%= ++i %>
        </td>
        <%}%>
       </tr>
       <tr class="containerBack">
       <% for(int i =0 ; i < 7 ; i++){%>
        <td valign="center" style="text-align: center;">
          <input type="radio" name="quest<%= count %>qans">
        </td>
        <%}%>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_COMMENTS) || (type == SurveyQuestion.OPEN_ENDED) %>">
       <tr class="containerBody">
          <td width="15%" valign="center" style="text-align: right;">
            <dhv:label name="campaign.comments">Comments</dhv:label>
          </td>
          <td colspan="7" valign="center">
            <textarea name="quest<%= count %>comments" rows="2" cols="80"></textarea>
          </td>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate if="<%= (type == SurveyQuestion.ITEMLIST) %>">
      <%
        Iterator k = thisQuestion.getItemList().iterator();
        if ( k.hasNext() ) {
          while (k.hasNext()) {
            Item thisItem = (Item)k.next();
      %>
        <tr class="containerBack">
          <td valign="center" style="text-align: center" width="6%">
            <input type="checkbox" name="quest<%= thisQuestion.getId() %>item<%= thisItem.getId() %>" value="true">
          </td>
          <td valign="center">
            <%= toHtml(thisItem.getDescription()) %>
          </td>
        </tr>
       <%}
       }else{%>
       <tr>
           <td style="text-align: center">
            <dhv:label name="campaign.noItemsFound">No items found.</dhv:label>
           </td>
        </tr>
        <%}%>
      </dhv:evaluate>
      </table>
     </td>
    </tr>
    <% 
      }
    } else {%>
      <tr class="containerBody">
        <td colspan="6">
          <dhv:label name="campaign.neQuestionsFound">No Questions found in this Survey</dhv:label>
        </td>
      </tr>
      <%}%>
    </table>
   <br>
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="campaign.surveyThankYouText">Survey Thank You Text</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td colspan="2" valign="top">
        <%= toHtml(Survey.getOutro()) %>
      </td>
    </tr>
  </table>
  <br>
<dhv:permission name="campaign-campaigns-surveys-edit">
<input type="button" name="action" value="<dhv:label name="global.button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='CampaignManagerSurvey.do?command=Modify&id=<%=Survey.getId()%>'">
</dhv:permission>
<dhv:permission name="campaign-campaigns-surveys-delete">
<input type="button" name="action" value="<dhv:label name="campaign.deleteSurvey">Delete Survey</dhv:label>" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=ConfirmDelete&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=View', 'Delete_survey','330','200','yes','no');">
</dhv:permission>
<input type="button" name="action" value="<dhv:label name="button.preview">Preview</dhv:label>" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=Preview&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=Details&id=<%=Survey.getId()%>', 'Preview_Survey','700','550','yes','yes');">

