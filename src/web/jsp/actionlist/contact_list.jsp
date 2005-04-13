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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionlist.base.ActionContact, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ActionContacts" class="org.aspcfs.modules.actionlist.base.ActionContactsList" scope="request"/>
<jsp:useBean id="ActionList" class="org.aspcfs.modules.actionlist.base.ActionList" scope="request"/>
<jsp:useBean id="ContactActionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="viewUser" class="java.lang.String" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="contact_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function loadHistory(itemId, history, contact, rowid, contactId){
    showSpan(contact);
    window.frames[history].location.href='MyActionContacts.do?command=ViewHistory&itemId=' + itemId + '&contactId=' + contactId + '&rowid=' + rowid + '&inline=true';
  }
  function closeHistory(history, contact){
    window.frames[history].location.href='empty.html';
    hideSpan(contact);
  }
  function toggleImage() {
    var img = document['hisImage' + toggleImage.arguments[0]];
    if(img.id == '1'){
      img.id = "0";
      img.src = 'images/arrowdown.gif';
      loadHistory(toggleImage.arguments[0], toggleImage.arguments[1], toggleImage.arguments[2], toggleImage.arguments[3], toggleImage.arguments[4]);
    } else {
      img.id = "1";
      img.src = 'images/arrowright.gif';
      closeHistory(toggleImage.arguments[1],toggleImage.arguments[2]);
    }
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="actionList.myHomePage">My Home Page</dhv:label></a> >
<a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>"><dhv:label name="myitems.actionLists">Action Lists</dhv:label></a> >
<dhv:label name="actionList.listDetails">List Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-action-lists-edit">
<a href="javascript:window.location.href='MyActionContacts.do?command=Prepare&actionId=<%= request.getParameter("actionId") %>&return=details&params=' + escape('&reset=true&filters=all|mycontacts|accountcontacts');"  onMouseOver="window.status='Add Contacts To List';return true;"  onMouseOut="window.status='';return true;"><dhv:label name="actionList.addContactsToList">Add Contacts to List</dhv:label></a>&nbsp;
<a href="MyActionContacts.do?command=Modify&actionId=<%= request.getParameter("actionId") %>"><dhv:label name="project.modifyList">Modify List</dhv:label></a>
<br>
</dhv:permission>
<br>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>">
    <td align="left">
      <strong><%= toHtml(ActionList.getDescription()) %></strong>
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= ContactActionListInfo.getOptionValue("inprogress") %>><dhv:label name="actionList.allInProgressContacts">All In Progress Contacts</dhv:label></option>
        <option <%= ContactActionListInfo.getOptionValue("complete") %>><dhv:label name="actionList.allCompleteContacts">All Complete Contacts</dhv:label></option>
        <option <%= ContactActionListInfo.getOptionValue("all") %>><dhv:label name="actionList.allContacts">All Contacts</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactActionListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <dhv:permission name="myhomepage-action-lists-edit">
    <th style="text-align: center;">
      &nbsp;
    </th>
  </dhv:permission>
    <th>
      <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="actionList.lastUpdated">Last Updated</dhv:label></strong>
    </th>
  </tr>
<%
  Iterator j = ActionContacts.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i = 0;
  while (j.hasNext()) {
      ++i;
      rowid = (rowid != 1 ? 1 : 2);
      ActionContact thisContact = (ActionContact) j.next();
%>
  <tr class="row<%= rowid %>">
   <dhv:permission name="myhomepage-action-lists-edit">
    <td valign="top">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <%-- To display the menu, pass the actionId, accountId and the contactId--%>
      <a href="javascript:displayMenu('select<%= i %>','menuContact',<%= thisContact.getActionId() %>,<%= thisContact.getContact().getId() %>,'<%= thisContact.getContact().getOrgId() %>', '<%= thisContact.getId() %>')"
         onMouseOver="over(0, <%= i %>)"
         onmouseout="out(0, <%= i %>); hideMenu('menuContact');"><img 
        src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
   </dhv:permission>
    <td nowrap valign="top">
    <dhv:permission name="myhomepage-action-lists-edit">
      <% if (thisContact.getComplete()) { %>
        <a href="javascript:changeImages('image<%= thisContact.getId() %>','MyActionContacts.do?command=ProcessImage&id=box.gif|gif|'+<%= thisContact.getId() %>+'|0','MyActionContacts.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisContact.getId() %>+'|1');" onMouseOver="this.style.color='blue';window.status='View Details';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box-checked.gif" name="image<%= thisContact.getId() %>" id="1" border="0" title="Click to change" align="absmiddle"></a>
      <% } else { %>
        <a href="javascript:changeImages('image<%= thisContact.getId() %>','MyActionContacts.do?command=ProcessImage&id=box.gif|gif|'+<%= thisContact.getId() %>+'|1','MyActionContacts.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisContact.getId() %>+'|1');"><img src="images/box.gif" name="image<%= thisContact.getId() %>" id="0" border="0" title="Click to change" align="absmiddle"></a>
      <%
        }
      %>
    </dhv:permission>
    <dhv:permission name="myhomepage-action-lists-edit">
      <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&actionId=<%= thisContact.getId() %>&id=<%= thisContact.getContact().getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');">
    </dhv:permission>
      <%= toHtml(thisContact.getContact().getNameFull()) %>
     <dhv:permission name="myhomepage-action-lists-edit"></a></dhv:permission>
    </td>
    <td valign="top" width="100%">
    <% if(thisContact.getMostRecentItem().getId() > 0){ %>
    <table border="0" width="100%" class="empty">
    <tr>
      <td valign="top">
        <a href="javascript:toggleImage('<%= thisContact.getId() %>', 'history<%= thisContact.getId() %>', 'contact<%= thisContact.getId() %>', '<%= rowid %>','<%= thisContact.getContact().getId() %>');"><img src="images/arrowright.gif" name="hisImage<%= thisContact.getId() %>" id="1" border="0" title="Click To View History"></a>
      </td>
      <td width="100%" valign="top">
        <%= toHtml(thisContact.getMostRecentItem().getTypeString()) %>: <a href="javascript:parent.location.href='<%= thisContact.getMostRecentItem().getItemLink(thisContact.getContact().getId()) %>';"  onMouseOver="this.style.color='blue';window.status='View Details';return true;"  onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisContact.getMostRecentItem().getDescription()) %></a> [<zeroio:tz timestamp="<%= thisContact.getMostRecentItem().getEntered() %>" />]<br>
        <span name="contact<%= thisContact.getId() %>" id="contact<%= thisContact.getId() %>" style="display:none">
          <iframe src="empty.html" name="history<%= thisContact.getId() %>" height="100" width="100%" frameborder="0" marginwidth="0" marginheight="0" ></iframe>
        </span>
      </td>
    </tr>
    </table>
    <% }else{ %>
      <dhv:label name="actionList.noItemsInHistory">No items in History.</dhv:label>
    <% } %>
    </td>
    <td nowrap align="center" valign="top">
      <zeroio:tz timestamp="<%= thisContact.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="4" valign="center">
          <dhv:label name="actionList.noActionContactsFound">No Action Contacts found in this view.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ContactActionListInfo" tdClass="row1"/>

