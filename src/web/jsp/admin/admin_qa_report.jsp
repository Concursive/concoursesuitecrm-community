<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.help.base.*" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="contents" class="org.aspcfs.modules.help.base.HelpItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<font size="+1"><b>QA Report <%= new java.util.Date() %></b></font><br>
<%
  int modCount = 0;
  int itemCount = 0;
  String previousModule = "";
  String previousSection = "";
  String previousSubsection = "";
  Iterator i = contents.iterator();
  boolean open = false;
  while (i.hasNext()) {
    ++modCount;
    HelpItem thisItem = (HelpItem) i.next();
%>
<%-- This check should only evaluate if an item was previously printed, but there is an error --%>
<dhv:evaluate if="<%= itemCount > 0 && ((thisItem.getModule().equals(previousModule) && !previousSection.equals(thisItem.getSection())) || !thisItem.getModule().equals(previousModule)) && open %>">
      </td>
    </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= modCount > 1 && !thisItem.getModule().equals(previousModule) && open %>">
  </table>
</dhv:evaluate>
<%-- end check --%>
<dhv:evaluate if="<%= !thisItem.getModule().equals(previousModule) && !thisItem.getNotes().isEmpty() %>">
<%
  open = false;
%>
<table border="0" width="100%" cellpadding="4" cellspacing="0">
  <tr>
    <th colspan="2"><%= thisItem.getId() %>. <%= toHtml(thisItem.getModule()) %></th>
  </tr>
</dhv:evaluate>
<%
    Iterator j = thisItem.getNotes().iterator();
    while (j.hasNext()) {
      open = true;
      ++itemCount;
      HelpNote thisNote = (HelpNote) j.next();
%>
<dhv:evaluate if="<%= !previousSection.equals(thisItem.getSection()) %>">
    <tr>
      <td width="25%" align="center" valign="top" style="border: 1px solid #777">
        <b><%= toHtml(thisItem.getSection()) %></b>
      </td>
      <td width="75%" style="border: 1px solid #777">
</dhv:evaluate>
        <%= thisNote.getId() %>.
<dhv:evaluate if="<%= thisNote.getComplete() %>">
        <img src="images/box-checked.gif" border="0"/>
</dhv:evaluate>
<dhv:evaluate if="<%= !thisNote.getComplete() %>">
        <img src="images/box.gif" border="0"/>
</dhv:evaluate>
        [<b><dhv:username id="<%= thisNote.getModifiedBy() %>"/></b>]
        <%= toHtml(thisNote.getDescription()) %><br>
<%
      previousSection = thisItem.getSection();
      if (previousSection == null) {
        previousSection = "";
      }
    }
    previousSection = (new java.util.Date()).toString();
    if (!thisItem.getNotes().isEmpty()) {
      previousModule = thisItem.getModule();
    }
  }
%>
<dhv:evaluate if="<%= open %>">
    </td>
  </tr>
</table>
</dhv:evaluate>
