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
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactTypeSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<% if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) { %>
<%-- Show the list of types to choose from --%>
<form name="elementListView" method="post" action="ExternalContacts.do?command=PopupSelector">
<br>
<center><%= ContactTypeSelectorInfo.getAlphabeticalPageLinks("setFieldSubmit","elementListView") %></center>
<br>
<input type="hidden" name="letter">
<table width="100%" border="0">
  <tr>
      <td align="right">
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactTypeSelectorInfo" showHiddenParams="true" enableJScript="true" form="elementListView"/>
      </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th width="100%">
      <dhv:label name="contact.option">Option</dhv:label>
    </th>
  </tr>
<%
  Iterator j = ContactTypeList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      ContactType thisElt = (ContactType)j.next();
      if ( thisElt.getEnabled() || (!thisElt.getEnabled() && (selectedElements.get(new Integer(thisElt.getId()))!= null)) ) {
%>
  <tr class="row<%= rowid+((selectedElements.get(new Integer(thisElt.getId()))!= null)?"hl":"") %>">
    <td align="center">
      <input type="checkbox" name="checkelement<%= count %>" value=<%= thisElt.getId() %><%= ((selectedElements.get(new Integer(thisElt.getId()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
    </td>
    <td valign="center">
      <%= toHtml(thisElt.getDescription()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisElt.getId() %>">
      <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml(thisElt.getDescription()) %>">
    </td>
  </tr>
<%
      } else {
        count--;
      }
    }
  } else {
%>
      <tr class="containerBody">
        <td colspan="2">
          <dhv:label name="quotes.noOptionsMatchedQuery">No options matched query.</dhv:label>
        </td>
      </tr>
<%
  }
%>
</table>
<input type="hidden" name="finalsubmit" value="false">
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<%= addHiddenParams(request, "contactId|category") %>
<input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:document.elementListView.finalsubmit.value='true';document.elementListView.submit();">
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
[<a href="javascript:SetChecked(1,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
[<a href="javascript:SetChecked(0,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
<br>
&nbsp;<br>
</form>
<%
  } else {
%>
<%-- Save the selected items to the parent form, then close the window --%>
<body OnLoad="javascript:setParentList(selectedIds,selectedValues,'list','<%= DisplayFieldId %>');window.close();">
  <script>selectedValues = new Array();selectedIds = new Array();</script>
<%
    Set s = selectedElements.keySet();
    Iterator i = s.iterator();
    int count = -1;
    while (i.hasNext()) {
      count++;
      Object id = i.next();
      Object st = selectedElements.get(id);
      String value = st.toString();
%>
  <script>
    selectedValues[<%= count %>] = "<%= StringUtils.jsStringEscape(value) %>";
    selectedIds[<%= count %>] = '<%= id %>';
  </script>
<%
    }
%>
</body>
<%
    session.removeAttribute("selectedElements");
    session.removeAttribute("finalElements");
  }
%>
