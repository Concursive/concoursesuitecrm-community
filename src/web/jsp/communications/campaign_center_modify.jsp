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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.io.*, java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.utils.web.*,java.text.DateFormat" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
  var userGroups = '';
  var currentUserGroupNames = '';
  var currentUserGroupIds = '';
<%
  Iterator iter = (Iterator) Campaign.getUserGroupMaps().iterator();
  while (iter.hasNext()) {
    CampaignUserGroupMap groupMap = (CampaignUserGroupMap) iter.next();
%>
    userGroups = userGroups + '<%= groupMap.getUserGroupId() +","+ StringUtils.jsStringEscape(groupMap.getGroupName()) %>|';
    currentUserGroupIds = currentUserGroupIds +'<%= groupMap.getUserGroupId() %>|';
    currentUserGroupNames = currentUserGroupNames + '<%= StringUtils.jsStringEscape(groupMap.getGroupName()) %>|';
<%}%>

  function resetUserGroups(groups) {
    var url = 'CampaignUserGroups.do?command=ListGroups&groups='+groups;
    document.forms['addForm'].userGroupMaps_elements.value=groups;
    userGroups = groups;
    currentUserGroupIds = '';
    currentUserGroupNames = '';
    var entry = groups.split("|");
    for (i=0;i<entry.length;i++) {
      if (entry[i] != '') {
        var values = entry[i].split(",");
        if (values[0] != '') {
          currentUserGroupIds = currentUserGroupIds + values[0]+'|';
          currentUserGroupNames = currentUserGroupNames + values[1]+'|';
        }
      }
    }
    window.frames['server_list'].location.href = url;
  }

  function removeGroup(code) {
    var copyGroups = '';
    var entry = userGroups.split("|");
    for (i=0;i<entry.length;i++) {
      if (entry[i] != '') {
        var values = entry[i].split(",");
        if (values[0] != '' && values[0] != code) {
          copyGroups = copyGroups+ entry[i]+"|";
        }
      }
    }
    resetUserGroups(copyGroups);
  }

  function refreshUserGroups() {
    var url = 'CampaignUserGroups.do?command=ListGroups&groups='+userGroups+'&id=<%= Campaign.getId() %>';
    document.forms['addForm'].userGroupMaps_elements.value=userGroups;
    window.frames['server_list'].location.href = url;
  }
  
  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }

  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    formTest = true;
    message = "";
    if (checkNullString(form.name.value)) { 
      message += label("check.campaign.name","- Campaign name is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      form.dosubmit.value = "true";
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
</script>
<body onLoad="javascript:document.addForm.name.focus();">
<form name="addForm" action="CampaignManager.do?command=Update&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> > 
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.campaignList">Campaign List</dhv:label></a> >
<dhv:evaluate if='<%= !"list".equals(request.getParameter("return")) %>'>
  <a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
</dhv:evaluate>
<dhv:label name="button.modify">Modify</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= Campaign.getId() %>">
  <input type="hidden" name="modified" value="<%= Campaign.getModified() %>">
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
  <% if("list".equals(request.getParameter("return"))){ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=View';">
  <% }else{ %>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='CampaignManager.do?command=ViewDetails';">
  <% } %>
  <br />
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="campaign.renameCampaign">Rename Campaign</dhv:label></strong>
      </th>
    </tr>
    <tr>
      <td class="formLabel">
       <dhv:label name="campaign.campaignName">Campaign Name</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="name" value="<%= toHtmlValue(Campaign.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
      </td>
    </tr>
    <tr>
      <td class="formLabel" valign="top">
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="description" ROWS="3" COLS="50"><%= toString(Campaign.getDescription()) %></TEXTAREA>
      </td>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
         <dhv:label name="campaigns.accessToActiveCampaigns">Access to Active Campaigns</dhv:label>
      </td>
      <td>
        <input type="button" value="<dhv:label name="button.choose">Choose</dhv:label>" onClick="javascript:popUserGroupsSelectMultiple('campaign','1','lookup_quote_remarks','<%= Campaign.getId() %>',currentUserGroupIds, currentUserGroupNames,'UserGroups');"/><br />
        <iframe src="../empty.html" name="server_list" id="server_list" border="0" frameborder="0" width="100%" height="0"></iframe>
        <input type="hidden" name="userGroupMaps_elements" id="userGroupMaps_elements" value="" />
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
  <% if("list".equals(request.getParameter("return"))){ %>
    <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=View';">
  <% }else{ %>
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='CampaignManager.do?command=ViewDetails';">
  <% } %>
</form>
</body>
<script type="text/javascript">
  refreshUserGroups();
</script>
