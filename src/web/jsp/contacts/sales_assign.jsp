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
  - Version: $Id: sales_assign_lead.jsp 15115 2006-05-31 16:47:51 +0000 (Wed, 31 May 2006) matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.base.*, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="contactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ratingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="readStatus" class="java.lang.String" scope="request" />
<jsp:useBean id="nextValue" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.owner.value == -1) {
      message += label("check.actionplan.assignedTo", "- Plan Assignee is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    assignLead();
  }
  
  function assignLead() {
    var owner = document.forms['assignLead'].owner.value;
    
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var nextTo = '<%= from %>';
    var url = 'Sales.do?command=CheckAssignStatus&contactId=<%= contactDetails.getId() %>&next=assign&from='+ nextTo +'&listForm=<%= (listForm != null?listForm:"") %>&owner=' + owner;
    window.frames['server_commands'].location.href=url;
  }
  
  function continueAssignLead() {
    var contactId = '<%= contactDetails.getId() %>';
    var nextTo = '<%= from %>';
    var owner = document.forms['assignLead'].owner.value;
    var comments = document.forms['assignLead'].comments.value;
    if (owner == '-1') {
     owner = '<%= User.getUserRecord().getId() %>';
    }
    var url = "Sales.do?command=Update&contactId="+contactId+"&nextValue=true&leadAssignment=true&id="+contactId+"&next=assignaccount&nextValue=true&owner="+owner+"&leadStatus=<%= Contact.LEAD_ASSIGNED %>&from="+nextTo+"&listForm=<%= (listForm != null?listForm:"") %>";
    <dhv:evaluate if="<%= ratingList.size() > 0 %>">
      var rating = document.forms['assignLead'].rating.options[document.forms['assignLead'].rating.selectedIndex].value;
      url += "&rating="+rating;
    </dhv:evaluate>
    url += "&comments="+comments + "&popup=true";

    window.location.href= url;
  }
</script>
<form name="assignLead">
<dhv:evaluate if='<%= (readStatus != null && !readStatus.equals("-1") && !readStatus.equals(""+User.getUserRecord().getId())) %>'>
  <br />
  <img src="images/error.gif" border="0" align="absmiddle"/>
  <font color="red"><dhv:label name="sales.leadBeingReadBy" param='<%= "username="+getUsername(pageContext,Integer.parseInt(readStatus),false,false,"&nbsp;") %>'>This lead is being read by <dhv:username id="<%= readStatus %>" /></dhv:label></font><br />
  <br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= contactDetails.getOwner() > 0 %>">
        <strong><dhv:label name="button.reassignLead">Reassign Lead</dhv:label></strong>
      </dhv:evaluate><dhv:evaluate if="<%= contactDetails.getOwner() <= 0 %>">
        <strong><dhv:label name="button.assignLead">Assign Lead</dhv:label></strong>
      </dhv:evaluate>
	  </th>
  </tr>
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td>
       <%= SiteIdList.getSelectedValue(contactDetails.getSiteId()) %>
       <input type="hidden" name="siteId" value="<%=contactDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
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
            <% if (contactDetails.getOwner() > 0) { %>
              <dhv:username id="<%= contactDetails.getOwner() %>"/>
            <% }else{ %>
               <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="owner" id="ownerid" value="<%= contactDetails.getOwner() %>">
            &nbsp;[<a href="javascript:popContactsListSingle('ownerid','changeowner', 'listView=employees&usersOnly=true<%= User.getUserRecord().getSiteId() > -1?"&mySiteOnly=true":"" %>&siteId=<%=contactDetails.getSiteId()%>&searchcodePermission=sales-leads-edit,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            &nbsp; [<a href="javascript:changeDivContent('changeowner',label('none.selected','None Selected'));javascript:resetNumericFieldValue('ownerid');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <dhv:evaluate if="<%= ratingList.size() > 0 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.rating">Rating</dhv:label>
    </td>
    <td>
      <%= ratingList.getHtmlSelect("rating", contactDetails.getRating()) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= SourceList.size() > 0 %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="contact.source">Source</dhv:label>
    </td>
    <td>
      <%= toHtml(SourceList.getValueFromId(contactDetails.getSource())) %>
    </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sales.assignmentMessage">Assignment Message</dhv:label>
    </td>
    <td>
      <textarea name="comments" id="comments" rows="3" cols="35" ><%= toString(contactDetails.getComments()) %></textarea>
    </td>
  </tr>
</table>
<dhv:permission name="sales-leads-edit"><input type="button" value="<dhv:label name="button.save">Save</dhv:label>" onClick="javascript:checkForm(this.form);" /></dhv:permission>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
</form>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
