<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="CampaignSurveyListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<form name="listView" method="post" action="/CampaignManagerSurvey.do?command=View">
<dhv:permission name="campaign-campaigns-surveys-add"><a href="/CampaignManagerSurvey.do?command=Add">Add a Survey</a></dhv:permission>
<dhv:permission name="campaign-campaigns-surveys-add" none="true"><br></dhv:permission>
<center><%= CampaignSurveyListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= CampaignSurveyListInfo.getOptionValue("my") %>>My Surveys</option>
        <option <%= CampaignSurveyListInfo.getOptionValue("all") %>>All Surveys</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
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
      <a href="/CampaignManagerSurvey.do?command=View&column=m.enteredby"><strong>Entered By</strong></a>
    </td>
    <td valign=center align=left nowrap>
      <a href="/CampaignManagerSurvey.do?command=View&column=m.modified"><strong>Last Modified</strong></a>
    </td>
  </tr>
  <tr class="containerBody">
    <td colspan="5" valign="center">
      No surveys found.
    </td>
  </tr>
</table>
<br>
</form>
