<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
function downloadMessage(campaignId){
  fileList = document.getElementById('attList');
  if(fileList.selectedIndex != -1){
    window.location.href='CampaignManager.do?command=DownloadMessage&id=' + campaignId +  '&fid=' + fileList.value;
  }else{
    alert('Please select a file to download');
  }
}
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>">Campaign Details</a> >
Message
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1 = "id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="message" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td width="100%" class="containerBack">
     <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr>
          <th colspan="2" valign="center" align="left">
             <strong>Message Details</strong>
          </th>
        </tr>
        <tr class="containerBody">
            <td valign="top" align="left">
               <strong>From:</strong> <%= toHtml(Campaign.getReplyTo()) %><br>
               <strong>Subject:</strong> <%= toHtml(Campaign.getSubject()) %><br>
               &nbsp;<br>
               <%= (Campaign.getMessage()) + "*" %><br />
               <br />
               * NOTE: If this message contains a link to a survey then the recipients of this message are provided a URL that is tailored for the survey.
            </td>
           <%
            Iterator j = FileItemList.iterator();
            if ( j.hasNext() ) {
              int rowid = 0;
            %>
            <td valign="top" align="right" width="20%">
              <table cellpadding="4" cellspacing="0" border="0" width="20%" class="empty">
                <tr class="containerBody">
                  <td valign="top" align="left">
                       <strong>Attachments:</strong>
                  </td>
                </tr>
                <tr>
                  <td valign="top" align="left">
                    <select size="3" name="attList" id="attList" multiple>
              <%
                while (j.hasNext()) {
                  rowid = (rowid != 1?1:2);
                  FileItem thisFile = (FileItem) j.next();
                %>      
                  <option value="<%= thisFile.getId() %>"><%= toHtml(thisFile.getClientFilename()) %></option>
               <%}%>
                  </select>
                 </td>
                </tr>
                <tr class="containerBody">
                   <td valign="top" align="center">
                     <a href="javascript:downloadMessage('<%= Campaign.getId() %>');">Download</a><br>
                   </td>
                 </tr>
                </table>
             </td>
           <%}%>
        </tr>
     </table>
    </td>
  </tr>
</table>

