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
    alert(label("caution.selectfiletodownload","Please select a file to download"));
  }
}
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="project.message">Message</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="message" object="Campaign" param="<%= "id=" + Campaign.getId() %>">
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
        <th colspan="2" valign="center" align="left">
           <strong><dhv:label name="accounts.MessageDetails">Message Details</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
          <td valign="top" align="left">
             <dhv:label name="campaign.from.colon" param="<%= "from="+toHtml(Campaign.getReplyTo()) %>"><strong>From:</strong> <%= toHtml(Campaign.getReplyTo()) %></dhv:label><br />
             <dhv:label name="mail.label.subject" param="<%= "subject="+toHtml(Campaign.getSubject()) %>"><strong>Subject:</strong> <%= toHtml(Campaign.getSubject()) %></dhv:label><br />
             &nbsp;<br />
             <%= (Campaign.getMessage()) + "*" %><br />
             <br />
             <dhv:label name="campaign.message.note">* NOTE: If this message contains a link to a survey then the recipients of this message are provided a URL that is tailored for the survey.</dhv:label>
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
                     <strong><dhv:label name="Attachments.colon">Attachments:</dhv:label></strong>
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
                   <a href="javascript:downloadMessage('<%= Campaign.getId() %>');"><dhv:label name="accounts.accounts_documents_details.Download">Download</dhv:label></a><br>
                 </td>
               </tr>
              </table>
           </td>
         <%}%>
      </tr>
   </table>
</dhv:container>

