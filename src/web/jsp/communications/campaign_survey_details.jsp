<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Survey" class="org.aspcfs.modules.communications.base.Survey" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerAttachment.do">Create Attachments</a> >
<a href="CampaignManagerSurvey.do?command=View">Surveys</a> >
Survey Details<br>
<hr color="#BFBFBB" noshade>
<input type="button" name="action" value="Modify" onClick="javascript:window.location.href='CampaignManagerSurvey.do?command=Modify&id=<%=Survey.getId()%>'">
<input type="button" name="action" value="Delete Survey" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=ConfirmDelete&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=View', 'Delete_survey','330','200','yes','no');">
<input type="button" name="action" value="Preview" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=Preview&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=Details&id=<%=Survey.getId()%>', 'Preview_Survey','760','510','yes','yes');">
<br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="2">
        <strong>Survey Details</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Name
      </td>
      <td valign="top">
        <%= toHtml(Survey.getName()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Description
      </td>
      <td valign="top">
        <%= toHtml(Survey.getDescription()) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Entered By
      </td>
      <td>
        <dhv:username id="<%= Survey.getEnteredBy() %>"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Date
      </td>
      <td>
        <%= Survey.getEnteredString() %>&nbsp;
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Last Modified By
      </td>
      <td valign="top">
        <dhv:username id="<%= Survey.getModifiedBy() %>"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Date
      </td>
      <td>
        <%= Survey.getModifiedString() %>&nbsp;
      </td>
    </tr>
  </table>
 <br>
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td>
        <strong>Survey Introduction Text</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td>
        <%= toHtml(Survey.getIntro()) %>
      </td>
    </tr>
  </table>
  <br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Survey Questions</strong>
    </td>
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
    <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <tr>
        <td colspan="7" width="100%" valign="top" class="containerHeader">
          <input type="hidden" name="quest<%= count %>id" value="<%= thisQuestion.getId() %>">
          <%= count %>.&nbsp;<%= toHtml(thisQuestion.getDescription()) %>
        </td>
      </tr>
      <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_NOCOMMENTS) || (type == SurveyQuestion.QUANT_COMMENTS) %>">
      <tr class="containerBack">
      <% for(int i =0 ; i < 7;){%>
        <td valign="center" align="center">
          <%= ++i %>
        </td>
        <%}%>
       </tr>
       <tr class="containerBack">
       <% for(int i =0 ; i < 7 ; i++){%>
        <td valign="center" align="center">
          <input type="radio" name="quest<%= count %>qans">
        </td>
        <%}%>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate if="<%= (type == SurveyQuestion.QUANT_COMMENTS) || (type == SurveyQuestion.OPEN_ENDED) %>">
       <tr class="containerBody">
          <td width="15%" valign="center" align="right">
            Comments
          </td>
          <td colspan="7" valign="center">
            <textarea name="quest<%= count %>comments" rows="2" cols="80"></textarea>
          </td>
       </tr>
     </dhv:evaluate>
     <dhv:evaluate exp="<%= (type == SurveyQuestion.ITEMLIST) %>">
      <%
        Iterator k = thisQuestion.getItemList().iterator();
        if ( k.hasNext() ) {
          while (k.hasNext()) {
            Item thisItem = (Item)k.next();
      %>
        <tr class="containerBack">
          <td valign="center" align="center" width="6%">
            <input type="checkbox" name="quest<%= thisQuestion.getId() %>item<%= thisItem.getId() %>">
          </td>
          <td valign="center" align="left">
            <%= toHtml(thisItem.getDescription()) %>
          </td>
        </tr>
       <%}
       }else{%>
       <tr>
           <td align="center">
            No items found.
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
          No Questions found in this Survey
        </td>
      </tr>
      <%}%>
    </table>
   <br>
   <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="2">
        <strong>Survey Thank You Text</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td colspan="2" valign="top">
        <%= toHtml(Survey.getOutro()) %>
      </td>
    </tr>
  </table>
  <br>
<input type="button" name="action" value="Modify" onClick="javascript:window.location.href='CampaignManagerSurvey.do?command=Modify&id=<%=Survey.getId()%>'">
<input type="button" name="action" value="Delete Survey" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=ConfirmDelete&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=View', 'Delete_survey','330','200','yes','no');">
<input type="button" name="action" value="Preview" onClick="javascript:popURLReturn('CampaignManagerSurvey.do?command=Preview&id=<%=Survey.getId()%>&popup=true','CampaignManagerSurvey.do?command=Details&id=<%=Survey.getId()%>', 'Preview_Survey','700','680','yes','yes');">

