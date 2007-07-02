<%--
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
   - Version: $Id: fields.jsp 4.1 2007-06-14 09:25:17 +0530 (Thu,14 Jun 2007) nagarajay $
  - Description:
  --%>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.aspcfs.modules.base.CustomFieldGroup" %>
<%@ page import="org.aspcfs.modules.base.CustomField" %>
<%@ page import="org.aspcfs.modules.base.CustomFieldCategory" %>
<%@ include file="../../initPopupMenu.jsp" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>

<jsp:useBean id="randomNum" class="java.lang.String" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="recordCategories" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="totalMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="avgMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="isRecordId" class="java.lang.String" scope="request"/>
<%@ page contentType="text/html; charset=UTF-8" %>
<portlet:defineObjects/>

<%@ include file="../../initPage.jsp" %>
<%@ include file="website_fields_list_menu.jsp" %>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>

<form name="fieldListForm" method="post">
<%
  int k = 0;
  CustomField thisField;
  ArrayList displayInList = Category.getDisplayInList();
  CustomFieldCategory thisCategory = null;
  CustomFieldGroup thisGroup = null;
  int colCount = displayInList.size();
  Iterator recordList = recordCategories.iterator();
%>
<br>
<dhv:evaluate if="<%=Category.getCanAdd()%>">
  <a href="<portlet:renderURL ><portlet:param name="viewType" value="add"/></portlet:renderURL>">
    <dhv:label name="accounts.accounts_fields_add.AddFolderRecord">Add Folder Record</dhv:label></a>
</dhv:evaluate>
<br><br>
<dhv:pagedListStatus object="recordListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
  <th width="2%">&nbsp;</th>
  <%
    CustomFieldGroup fieldsGroup = null;
    Iterator groups = null;
    Iterator fields = null;
    for (int j = 0; j < displayInList.size(); j++) {
      groups = Category.iterator();
      while (groups.hasNext()) {
        fieldsGroup = (CustomFieldGroup) groups.next();
        Iterator fields1 = fieldsGroup.iterator();
        while (fields1.hasNext()) {
          thisField = (CustomField) fields1.next();
          if ((Integer.parseInt(displayInList.get(j).toString())) == thisField.getId()) {
  %>
  <th><strong><%=thisField.getName()%></strong></th>
  <%
          }
        }
      }
    }
  %>
</tr>
<%
  int row = 0;
  int recordCount = 1;
  boolean isUserMessage = false;
   if(recordList.hasNext()) {
  while (recordList.hasNext()) {
    k++;
%>
<tr class="containerBody">
  <%
    thisCategory = (CustomFieldCategory) recordList.next();
    for (int j = 0; j < displayInList.size(); j++) {
      groups = thisCategory.iterator();
      while (groups.hasNext()) {
        thisGroup = (CustomFieldGroup) groups.next();
        fields = thisGroup.iterator();
        while (fields.hasNext()) {
          thisField = (CustomField) fields.next();
          if ((Integer.parseInt(displayInList.get(j).toString())) == thisField.getId()) {
          if(!isUserMessage) {
              if(isRecordId != null && !"".equals(isRecordId) && !isRecordId.equals("false")) {
                if(thisField.getRecordId() == Integer.parseInt(isRecordId)) {
                 out.println("* The Record " + recordCount + " was modifed by the another user.");
                 isUserMessage = true;
                }
              }
            }
  %>
  <%if (row == 0) {%>
  <td>
    <a href="javascript:displayMenu<%=randomNum%>('select<%= k+Integer.parseInt(randomNum) %>','menuField<%=randomNum%>', '<%= thisField.getRecordId()%>');"
       onMouseOver="over(0, <%= k+Integer.parseInt(randomNum) %>)"
       onmouseout="out(0, <%= k+Integer.parseInt(randomNum) %>); new ypSlideOutMenu('menuField<%=randomNum%>', 'down', 0, 0, 170, getHeight('menuField<%=randomNum%>Table'));hideMenu('menuField<%=randomNum%>');"><img
            src="images/select.gif" name="select<%= k+Integer.parseInt(randomNum) %>"
            id="select<%= k+Integer.parseInt(randomNum) %>" align="absmiddle" border="0"></a></td>
  <%}%>
  <% if (thisField.getTypeString().equals("Number") || thisField.getTypeString().equals("Decimal Number") || thisField.getTypeString().equals("Percent") || thisField.getTypeString().equals("Currency")) {%>
  <td align="right"><%=thisField.getValueHtml()%></td>
  <%} else {%>
  <td><%=thisField.getValueHtml()%></td>
  <%}%>
  <%

            row++;
          }
        }
      }
    }
  %>
</tr>
<%  recordCount++;
    row = 0;
  }
 }else{
%>
<tr>
  <td colspan="<%=colCount+1%>">
     <dhv:label name="folder.noRecords">No records in this folder.</dhv:label>
  </td>
</tr>
<%}%>
<% String finalTotal = "", finalAvg = "";%>
<dhv:evaluate if="<%=Category.isDoTotal() && recordCategories.size()!=0%>">
  <tr class="containerBody">
    <td><b>Total</b></td>
    <% for(int i = 1; i <= totalMap.size(); i++) { %>
    <td align="right"> <% finalTotal = (String) totalMap.get(i);%>
    
      &nbsp;<%= finalTotal == null ? "" : finalTotal %></td>
    <%} %>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%=Category.isDoAvegrage() && recordCategories.size()!=0%>">
  <tr class="containerBody">
    <td><b>Avg</b></td>
    <%  
    for(int i = 1; i <= avgMap.size(); i++) { %>
        <td align="right"> <% finalAvg = (String) avgMap.get(i); %>

      &nbsp;<%= finalAvg == null ? "" : finalAvg %></td>
    <%
      }
    %>
  </tr>
</dhv:evaluate>
</table>
</form>

