<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.actionlist.base.ActionContact, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ActionContacts" class="org.aspcfs.modules.actionlist.base.ActionContactsList" scope="request"/>
<jsp:useBean id="ActionList" class="org.aspcfs.modules.actionlist.base.ActionList" scope="request"/>
<jsp:useBean id="ContactActionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
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
<a href="MyCFS.do?command=Home">My Home Page</a> >
<a href="MyActionLists.do?command=List&linkModuleId=<%= Constants.ACTIONLISTS_CONTACTS %>">My Action Lists</a> >
Action Contacts<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="myhomepage-action-lists-edit">
<a href="javascript:window.location.href='MyActionContacts.do?command=Prepare&actionId=<%= request.getParameter("actionId") %>&return=details&params=' + escape('filters=all|mycontacts|accountcontacts');"  onMouseOver="window.status='Add Contacts To List';return true;"  onMouseOut="window.status='';return true;">Add Contacts to List</a>&nbsp;
<a href="MyActionContacts.do?command=Modify&actionId=<%= request.getParameter("actionId") %>">Modify List</a>
<br>
</dhv:permission>
<br>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="MyActionContacts.do?command=List&actionId=<%= request.getParameter("actionId") %>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ContactActionListInfo.getOptionValue("inprogress") %>>All In Progress Contacts</option>
        <option <%= ContactActionListInfo.getOptionValue("complete") %>>All Complete Contacts</option>
        <option <%= ContactActionListInfo.getOptionValue("all") %>>All Contacts</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactActionListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="4">
      <strong><%= toHtml(ActionList.getDescription()) %></strong>
    </th>
  </tr>
  <tr>
  <dhv:permission name="myhomepage-action-lists-edit">
    <th style="text-align: center;">
      <strong>Action</strong>
    </th>
  </dhv:permission>
    <th>
      <strong>Name</strong>
    </th>
    <th>
      <strong>Status</strong>
    </th>
    <th nowrap>
      <strong>Last Updated</strong>
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
      <a href="javascript:displayMenu('menuContact',<%= thisContact.getActionId() %>,<%= thisContact.getContact().getId() %>,'<%= thisContact.getContact().getOrgId() %>', '<%= thisContact.getId() %>')"
         onMouseOver="over(0, <%= i %>)"
         onmouseout="out(0, <%= i %>)"><img 
        src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
   </dhv:permission>
    <td nowrap valign="top">
    <dhv:permission name="myhomepage-action-lists-edit">
      <% 
        if (thisContact.getComplete()) {
      %>
        <a href="javascript:changeImages('image<%= thisContact.getId() %>','MyActionContacts.do?command=ProcessImage&id=box.gif|gif|'+<%= thisContact.getId() %>+'|0','MyActionContacts.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisContact.getId() %>+'|1');" onMouseOver="this.style.color='blue';window.status='View Details';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box-checked.gif" name="image<%= thisContact.getId() %>" id="1" border="0" title="Click to change" align="absmiddle"></a>
      <% 
        } else {
      %>
        <a href="javascript:changeImages('image<%= thisContact.getId() %>','MyActionContacts.do?command=ProcessImage&id=box.gif|gif|'+<%= thisContact.getId() %>+'|1','MyActionContacts.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisContact.getId() %>+'|1');"><img src="images/box.gif" name="image<%= thisContact.getId() %>" id="0" border="0" title="Click to change" align="absmiddle"></a>
      <%
        }
      %>
    </dhv:permission>
    <dhv:permission name="myhomepage-action-lists-edit">
      <a href="javascript:popURL('ExternalContacts.do?command=ContactDetails&actionId=<%= thisContact.getId() %>&id=<%= thisContact.getContact().getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');">
    </dhv:permission>
      <%= toHtml(thisContact.getContact().getNameLastFirst()) %>
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
        <%= toHtml(thisContact.getMostRecentItem().getTypeString()) %>: <a href="javascript:parent.location.href='<%= thisContact.getMostRecentItem().getItemLink(thisContact.getContact().getId()) %>';"  onMouseOver="this.style.color='blue';window.status='View Details';return true;"  onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisContact.getMostRecentItem().getDescription()) %></a> [<dhv:tz timestamp="<%= thisContact.getMostRecentItem().getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>]<br>
        <span name="contact<%= thisContact.getId() %>" id="contact<%= thisContact.getId() %>" style="display:none">
          <iframe src="empty.html" name="history<%= thisContact.getId() %>" height="100" width="100%" frameborder="0" marginwidth="0" marginheight="0" ></iframe>
        </span>
      </td>
    </tr>
    </table>
    <% }else{ %>
      No Items in history
    <% } %>
    </td>
    <td nowrap align="center" valign="top">
      <dhv:tz timestamp="<%= thisContact.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="4" valign="center">
          No Action Contacts found in this view.
        </td>
      </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ContactActionListInfo" tdClass="row1"/>

