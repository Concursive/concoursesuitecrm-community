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

