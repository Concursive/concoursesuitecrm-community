<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="MessageDetails" class="com.darkhorseventures.cfsbase.Message" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<a href="/CampaignManagerMessage.do?command=View">Back to Message List</a><br>
<form name="details" action="/CampaignManagerMessage.do?command=Modify&id=<%= MessageDetails.getId() %>" method="post">
<input type='submit' value='Modify' name='Modify'>
<input type="submit" value="Delete" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Delete&id=<%= MessageDetails.getId() %>'">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Message Details</strong>
    </td>
  </tr>
  
  <tr>
    <td class="formLabel" valign=center align=right>
      Name
    </td>
    <td width="100%" valign=center width=100%>
      <%= toHtml(MessageDetails.getName()) %>&nbsp; 
    </td>
  </tr>
  
  <tr>
    <td class="formLabel" valign=center align=right>
      Description
    </td>
    <td valign=center>
      <%= toHtml(MessageDetails.getDescription()) %>&nbsp; 
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign=top align=right>
      Preview
    </td>
    <td valign=center>
			<%= (MessageDetails.getMessageText()) %>&nbsp; 
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign=center align=right>
      URL
    </td>
    <td valign=center>
    	<a href="<%= MessageDetails.getUrl() %>" target="_new"><%= MessageDetails.getUrl() %></a>&nbsp;
    </td>
  </tr>
	<tr>
    <td class="formLabel" valign=center align=right>
      Reply To
    </td>
    <td valign=center>
    	<%= toHtml(MessageDetails.getReplyTo()) %>&nbsp;
    </td>
  </tr>
	<tr>
    <td class="formLabel" valign=center align=right>
      Template
    </td>
    <td valign=center>
      <%= toHtml("" + MessageDetails.getTemplateId()) %>&nbsp;
    </td>
  </tr> 
  <tr>
    <td class="formLabel" valign=center align=right>
      Image
    </td>
    <td valign=center>
      <%= toHtml(MessageDetails.getImage()) %>&nbsp;
    </td>
  </tr>
</table>
<br>
<input type='submit' value='Modify' name='Modify'>
<input type="submit" value="Delete" onClick="javascript:this.form.action='/CampaignManagerMessage.do?command=Delete&id=<%= MessageDetails.getId() %>'">
</form>
