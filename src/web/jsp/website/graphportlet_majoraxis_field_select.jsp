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
  - Author: dharmas
  - Date: Mar 29, 2007
  - Time: 12:37:57 PM
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="ParentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="SelectedCategories" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="IgnoredCategories" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="FinalCategories" class="org.aspcfs.modules.base.CustomFieldList" scope="request"/>
<jsp:useBean id="ProductCategoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ page import="org.aspcfs.modules.base.CustomField"%>
<jsp:useBean id="fieldNameList" class="org.aspcfs.modules.base.CustomFieldList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/pageListInfo.js"></script>

<%
  Iterator p = SelectedCategories.iterator();
  String previousSelection = "";
  while (p.hasNext()) {
    String catId = (String) p.next();
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    previousSelection = previousSelection + catId;
  }

  Iterator q = IgnoredCategories.iterator();
  String ignoreIds = "";
  while (q.hasNext()) {
    String catId = (String) q.next();
    if (!"".equals(ignoreIds)) {
      ignoreIds = ignoreIds + "|";
    }
    ignoreIds = ignoreIds + catId;
  }
%>
<form name="folderListView" method="post" action="FolderAndFieldSelector.do?command=FieldSelect">
<%
	if (!"true".equals(request.getParameter("finalsubmit"))) {
%>
<% if (request.getParameter("catMaster") != null) { %>
	<input type="hidden" name="catMaster" value="<%= request.getParameter("catMaster") %>"/>
<% } else { %>
	<input type="hidden" name="catMaster" value=""/>
<% } %>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th align="center" width="8">
			&nbsp;
		</th>
		<th>
			<strong><dhv:label name="portlets.folder.field">Field</dhv:label></strong>
		</th>
	</tr>
	<%
		Iterator j = fieldNameList.iterator();
		if (j.hasNext()) {
			int c = 0;
			int rowid = 0;
			int count = 0;
			while (j.hasNext()) {
				count++;
				rowid = (rowid != 1 ? 1 : 2);
				//ProductCategory thisCategory = (ProductCategory) j.next();
                CustomField thisField = (CustomField)j.next();
                String fieldId = String.valueOf(thisField.getId());
	%>
		  <tr class="row<%= rowid+(SelectedCategories.indexOf(fieldId) != -1 ? "hl" : "") %>">
          <td align="center" nowrap width="8">
 <%
//checking for only date type fields to display for Major Axis Field Select --- field_type of date is 8
 if (thisField.getType() == 8) {
    c++;
    if ("list".equals(request.getParameter("listType"))) {
  %>
         <input type="checkbox" name="field<%= count %>" value="<%= thisField.getId() %>" <%= (SelectedCategories.indexOf(fieldId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
    <% } else { %>

         <a href="javascript:document.folderListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','folderListView');">Select</a>
     <%} %>
            <input type="hidden" name="hiddenFolderFieldId<%= count %>" value="<%= thisField.getId() %>">
			</td>
					<td nowrap>
						 <%= toHtml(thisField.getName()) %>
					</td>
			</tr>
	<%
			}
		  }
		   if(c == 0) { %>
			<tr>
		      <td class="containerBody" colspan="4">
		        <dhv:label name="folder.minoraxis.nomatch">No folder fields matched query</dhv:label>
		      </td>
		    </tr>
    <% }
		} else {
  %>
			<tr>
      <td class="containerBody" colspan="4">
        <dhv:label name="portlets.folder.nomatch">No folder fields matched query</dhv:label>
      </td>
    </tr>
	<%
		}
	%>
  <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
  <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
  <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
  <input type="hidden" name="setParentList" value="<%= toHtmlValue(request.getParameter("setParentList")) %>"/>
  <input type="hidden" name="finalsubmit" value="false"/>
  <input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
  <input type="hidden" name="ignoreIds" value="<%= ignoreIds %>"/>
</table>
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','folderListView');">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'category','folderListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'category','folderListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
<%}else{%>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<%}%>
</form>
<%} else {%>

  <body onLoad="javascript:setParentList(fieldIds, fieldNames, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <script>fieldIds = new Array();fieldNames = new Array();</script>
<%
  Iterator i = FinalCategories.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    CustomField thisField = (CustomField) i.next();
%>
  <script>
    fieldIds[<%= count %>] = "<%= thisField.getId() %>";
    fieldNames[<%= count %>] = "<%= toJavaScript(thisField.getName()) %>";
  </script>
<%
  }
%>
  </body>
<%
  }
//}
%>
