<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="com.darkhorseventures.cfsbase.Campaign" scope="request"/>
<jsp:useBean id="FileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="initPage.jsp" %>
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

<a href="CampaignManager.do">Communications Manager</a> >
<a href="/CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="/CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
Message
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="message" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td width="100%" class="containerBack">
     <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td colspan="2" valign="center" align="left">
             <strong>Message Details</strong>
          </td>
        </tr>
        <tr class="containerBody">
            <td valign="top" align="left">
               <strong>From:</strong> <%= toHtml(Campaign.getReplyTo()) %><br>
               <strong>Subject:</strong> <%= toHtml(Campaign.getSubject()) %><br>
               &nbsp;<br>
               <%= (Campaign.getMessage()) %>
            </td>
           <%
            Iterator j = FileItemList.iterator();
            if ( j.hasNext() ) {
              int rowid = 0;
            %>
            <td valign="top" align="right" width="20%">
              <table cellpadding="4" cellspacing="0" border="0" width="20%">
                <tr class="containerBody">
                  <td valign="top" align="left">
                       <strong>Attachments :</strong>
                  </td>
                </tr>
                <tr>
                  <td valign="top" align="left">
                    <select size="3" name="attList" id="attList" multiple>
              <%
                while (j.hasNext()) {
                  if (rowid != 1) rowid = 1; else rowid = 2;
                  FileItem thisFile = (FileItem)j.next();
                %>      
                  <option  value = "<%= thisFile.getId() %>"><%= toHtml(thisFile.getClientFilename()) %></option>
               <%}%>
                  </select>
                 </td>
                </tr>
                <tr class="containerBody">
                    <td valign="top" align="center">
                         <a href="javascript:downloadMessage('<%=Campaign.getId()%>');">Download</a><br>
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

