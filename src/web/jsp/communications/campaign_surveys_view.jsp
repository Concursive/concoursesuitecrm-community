<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CampaignSurveyListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="SurveyList" class="com.darkhorseventures.cfsbase.SurveyList" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerAttachment.do">Create Attachments</a> >
Surveys
<hr color="#BFBFBB" noshade>
<dhv:permission name="campaign-campaigns-surveys-add"><a href="/CampaignManagerSurvey.do?command=Add">Add a Survey</a></dhv:permission>
<dhv:permission name="campaign-campaigns-surveys-add" none="true"><br></dhv:permission>
<center><%= CampaignSurveyListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="/CampaignManagerSurvey.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignSurveyListInfo.getOptionValue("my") %>>My Surveys</option>
        <option <%= CampaignSurveyListInfo.getOptionValue("all") %>>All Surveys</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
  <dhv:permission name="campaign-campaigns-surveys-edit,campaign-campaigns-surveys-delete">
   <td valign=center align=left bgcolor="#DEE0FA">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td width=40% valign=center align=left>
      <a href="/CampaignManagerSurvey.do?command=View&column=name"><strong>Name</strong></a>
    </td>  
    <td width=60% valign=center align=left>
      <a href="/CampaignManagerSurvey.do?command=View&column=description"><strong>Type</strong></a>
    </td>
    <td valign=center align=left nowrap>
      <a href="/CampaignManagerSurvey.do?command=View&column=s.enteredby"><strong>Entered By</strong></a>
    </td>
    <td valign=center align=left nowrap>
      <a href="/CampaignManagerSurvey.do?command=View&column=s.modified"><strong>Last Modified</strong></a>
    </td>
  </tr>
<%    
	Iterator i = SurveyList.iterator();
	
	if (i.hasNext()) {
	int rowid = 0;
	
		while (i.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
			
		Survey thisSurvey = (Survey)i.next();
%>      
      <tr>
        <dhv:permission name="campaign-campaigns-surveys-edit,campaign-campaigns-surveys-delete">
        <td width=8 valign=center nowrap class="row<%= rowid %>">
          <dhv:permission name="campaign-campaigns-surveys-edit"><a href="/CampaignManagerSurvey.do?command=Modify&id=<%=thisSurvey.getId()%>">Edit</a></dhv:permission><dhv:permission name="campaign-campaigns-surveys-edit,campaign-campaigns-surveys-delete" all="true">|</dhv:permission><dhv:permission name="campaign-campaigns-surveys-delete"><a href="javascript:confirmDelete('/CampaignManagerSurvey.do?command=Delete&id=<%=thisSurvey.getId()%>');">Del</a></dhv:permission>
        </td>
	</dhv:permission>
        <td class="row<%= rowid %>" nowrap>
          <a href="/CampaignManagerSurvey.do?command=Details&id=<%= thisSurvey.getId() %>"><%= toHtml(thisSurvey.getName()) %></a>
        </td>
        <td class="row<%= rowid %>">
          <%=toHtml(thisSurvey.getTypeName())%>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisSurvey.getEnteredByName()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisSurvey.getModifiedDateTimeString()) %>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="row2" valign="center" colspan="5">
      No surveys found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignSurveyListInfo" tdClass="row1"/>

