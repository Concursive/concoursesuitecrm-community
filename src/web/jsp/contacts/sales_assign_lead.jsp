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
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.base.*, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="actionPlanSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="ratingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="readStatus" class="java.lang.String" scope="request" />
<jsp:useBean id="nextValue" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.planManager.value == -1) {
      message += label("check.actionplan.managerId", "- Plan Manager is a required field\r\n");
      formTest = false;
    }
    if (form.owner.value == -1) {
      message += label("check.actionplan.assignedTo", "- Plan Assignee is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    assignAccount();
  }
  
  function assignAccount() {
    var owner = document.forms['assignLead'].owner.value;
    <dhv:evaluate if="<%= actionPlanSelect.size() > 1 %>">
      var actionPlan = document.forms['assignLead'].actionPlan.options[document.forms['assignLead'].actionPlan.selectedIndex].value;
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionPlanSelect.size() < 2 %>">
      var actionPlan = '-1';
    </dhv:evaluate>
    var manager = document.forms['assignLead'].planManager.value;
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= contactDetails.getId() %>&next=assignaccount&from='+ nextTo +'&listForm=<%= (listForm != null?listForm:"") %>&owner=' + owner + '&actionPlan=' + actionPlan + '&manager=' + manager;
    window.frames['server_commands'].location.href=url;
  }
  
  function continueAssignAccount() {
  <dhv:evaluate if="<%= ratingList.size() > 1 %>">
    var rating = document.forms['assignLead'].rating.value;
  </dhv:evaluate>
    var contactId = '<%= contactDetails.getId() %>';
    var owner = document.forms['assignLead'].owner.value;
    <dhv:evaluate if="<%= actionPlanSelect.size() > 1 %>">
      var actionPlan = document.forms['assignLead'].actionPlan.options[document.forms['assignLead'].actionPlan.selectedIndex].value;
    </dhv:evaluate>
    <dhv:evaluate if="<%= actionPlanSelect.size() < 2 %>">
      var actionPlan = '-1';
    </dhv:evaluate>
    var manager = document.forms['assignLead'].planManager.value;
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    var url = "Sales.do?command=Update&contactId="+contactId+"&id="+contactId+"&next=assignaccount&nextValue=true&owner="+owner+"&leadStatus=<%= Contact.LEAD_ASSIGNED %>&from="+nextTo+"&listForm=<%= (listForm != null?listForm:"") %>";
  <dhv:evaluate if="<%= ratingList.size() > 1 %>">
    url += "&rating="+rating;
  </dhv:evaluate>
    url += "&actionPlan=" + actionPlan + "&manager=" + manager + "&popup=true";
    window.location.href= url;
    opener.hideSpan("worklead");
    opener.showSpan("nextlead");
  }
</script>
<form name="assignLead">
<dhv:evaluate if="<%= (readStatus != null && !readStatus.equals("-1") && !readStatus.equals(""+User.getUserRecord().getId())) %>">
  <br />
  <img src="images/error.gif" border="0" align="absmiddle"/>
  <font color="red"><dhv:label name="sales.leadBeingReadBy" param="<%= "username="+getUsername(pageContext,Integer.parseInt(readStatus),false,false,"&nbsp;") %>">This lead is being read by <dhv:username id="<%= readStatus %>" /></dhv:label></font><br />
  <br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="button.convertToAccount">Convert to Account</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="actionPlan.planManager">Plan Manager</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeplanmanager">
            <% if (actionPlanWork.getManagerId() > 0) { %>
              <dhv:username id="<%= actionPlanWork.getManagerId() %>"/>
            <% }else{ %>
               <dhv:username id="<%= User.getUserRecord().getId() %>"/>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="planManager" id="planManagerId" value="<%= ((actionPlanWork.getManagerId() > 0) ? actionPlanWork.getManagerId() : User.getUserRecord().getId()) %>">
            &nbsp;[<a href="javascript:popContactsListSingle('planManagerId','changeplanmanager', 'listView=employees&usersOnly=true&hierarchy=<%= User.getUserId() %>&siteId=<%=contactDetails.getSiteId()%>&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeplanmanager',label('none.selected','None Selected'));javascript:resetNumericFieldValue('planManagerId');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <dhv:evaluate if="<%= actionPlanSelect.size() > 1 %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </td>
      <td>
        <%= actionPlanSelect.getHtml("actionPlan") %>
      </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="actionList.assignTo">Assign To</dhv:label>
    </td>
    <td>
      <table class="empty">
        <tr>
          <td>
            <div id="changeowner">
            <% if (actionPlanWork.getAssignedTo() > 0) { %>
              <dhv:username id="<%= actionPlanWork.getAssignedTo() %>"/>
            <% }else{ %>
               <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="-1">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true<%= User.getUserRecord().getSiteId() > -1?"&mySiteOnly=true":"" %>&siteId=<%=contactDetails.getSiteId()%>&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('none.selected','None Selected'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <dhv:evaluate if="<%= ratingList.size() > 1 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.rating">Rating</dhv:label>
    </td>
    <td>
      <%= ratingList.getHtmlSelect("rating", contactDetails.getRating()) %>
    </td>
  </tr>
  </dhv:evaluate>
</table>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.save">Save</dhv:label>" onClick="javascript:checkForm(this.form);" /></dhv:permission>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
</form>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
