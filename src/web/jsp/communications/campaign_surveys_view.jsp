<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="CampaignSurveyListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SurveyList" class="org.aspcfs.modules.communications.base.SurveyList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerAttachment.do">Create Attachments</a> >
Surveys
<hr color="#BFBFBB" noshade>
<dhv:permission name="campaign-campaigns-surveys-add"><a href="CampaignManagerSurvey.do?command=Add">Add a Survey</a></dhv:permission>
<dhv:permission name="campaign-campaigns-surveys-add" none="true"><br></dhv:permission>
<center><%= CampaignSurveyListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="CampaignManagerSurvey.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignSurveyListInfo.getOptionValue("my") %>>My Surveys</option>
        <option <%= CampaignSurveyListInfo.getOptionValue("all") %>>All Surveys</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="CampaignSurveyListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
  <dhv:permission name="campaign-campaigns-surveys-edit,campaign-campaigns-surveys-delete">
   <th>
      <strong>Action</strong>
    </th>
    </dhv:permission>
    <th nowrap>
      <a href="CampaignManagerSurvey.do?command=View&column=name"><strong>Name</strong></a>
      <%= CampaignSurveyListInfo.getSortIcon("name") %>
    </th>  
    <th nowrap>
      <strong>Entered By</strong>
    </th>
    <th nowrap>
      <a href="CampaignManagerSurvey.do?command=View&column=s.modified"><strong>Last Modified</strong></a>
      <%= CampaignSurveyListInfo.getSortIcon("s.modified") %>
    </th>
  </tr>
<%    
	Iterator i = SurveyList.iterator();
	if (i.hasNext()) {
    int rowid = 0;
		while (i.hasNext()) {
			rowid = (rowid != 1?1:2);
      Survey thisSurvey = (Survey)i.next();
%>      
      <tr>
        <dhv:permission name="campaign-campaigns-surveys-edit,campaign-campaigns-surveys-delete">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="campaign-campaigns-surveys-edit"><a href="CampaignManagerSurvey.do?command=Modify&id=<%=thisSurvey.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-surveys-edit,campaign-campaigns-surveys-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-surveys-delete"><a href="javascript:popURLReturn('CampaignManagerSurvey.do?command=ConfirmDelete&id=<%=thisSurvey.getId()%>&popup=true','CampaignManagerSurvey.do?command=View', 'Delete_survey','330','200','yes','no');">Del</a></dhv:permission>
        </td>
	</dhv:permission>
        <td class="row<%= rowid %>" width="100%" nowrap>
          <a href="CampaignManagerSurvey.do?command=Details&id=<%= thisSurvey.getId() %>"><%= toHtml(thisSurvey.getName()) %></a>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <dhv:username id="<%= thisSurvey.getEnteredBy() %>"/>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisSurvey.getModifiedDateTimeString()) %>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="containerBody" colspan="5">
      No surveys found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignSurveyListInfo" tdClass="row1"/>

