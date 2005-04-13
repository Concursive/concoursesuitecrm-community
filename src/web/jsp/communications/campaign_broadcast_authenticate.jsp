<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Dialog" class="org.aspcfs.utils.web.HtmlDialog" scope="session"/>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function displayCampaignList(id,modified){
    opener.location.href='CampaignManager.do?command=Dashboard&id=' + id + '&notify=true&modified=' + modified ;
  }
</script>	
<html>
  <title>Broadcast Authentication</title>
<%
  if (!"true".equals(request.getAttribute("finalsubmit"))) {
     String source = request.getParameter("source");
%>  
  <body>
<form name="broadcastAuthentication" action="CampaignManager.do?command=BroadcastCampaign&id=<%= Campaign.getId() %>" method="post">
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="95%">
      <tr align="center" valign="center">
        <td>
           <strong>
            <dhv:formMessage /><br />
            <%if ((Message.getMessageText().length() > 255) || (Message.getMessageText().indexOf("<img") != -1)){ %>
              <img src="images/box-hold.gif" border="0" align="absmiddle" /><font color="red"><dhv:label name="communications.campaign.largeMessageOrGraphics">The message length exceeds 255 characters or contains graphics.</dhv:label></font><br />
            <%}%>
            <font size="2"><dhv:label name="communications.campaign.broadCastAuthenicate">To broadcast this campaign, please enter your password</dhv:label></font>
          </strong>
        </td>
      </tr>
      <tr align="center" valign="center">
        <td>
            <font size="2"><dhv:label name="setup.password.colon">Password:</dhv:label></font>&nbsp;<input type="password" name="broadcastPassword" value="" size="20">
        </td>
      </tr>
      <tr align="center">
        <td>
          <input type="submit" value="Broadcast">
          <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
          <input type="hidden" name="modified" value="<%=Campaign.getModified()%>" />
        </td>
      </tr>
     </table>
</form>
   </body>
<%}else{%>
  <body OnLoad="javascript:displayCampaignList('<%=Campaign.getId()%>','<%=Campaign.getModified()%>');window.close()" />
<%}%>
</html>

