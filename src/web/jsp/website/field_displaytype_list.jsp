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
  - Version: $Id: field_displaytype_list.jsp dharmas$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.aspcfs.modules.base.CustomField"%>
<jsp:useBean id="fieldNameList" class="org.aspcfs.modules.base.CustomFieldList" scope="request"/>
<jsp:useBean id="graphTypeList" class="org.aspcfs.modules.base.GraphTypeList" scope="request"/>
<jsp:useBean id="GraphType" class="org.aspcfs.modules.base.GraphType" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/folderListCheck.js"></script>

<html>
  <head><title>FolderGraph MinorAxis Select Page</title></head>
   <body>
    <form name="fieldDisplay" action="">
        <table  width="100%" cellspacing="0" cellpadding="3" border="0" class="pagedList" >

          <tr>
             <th width="50%">
               <strong><dhv:label name="portlets.folder.fieldsInFolders">Field in the Folder</dhv:label></strong>
            </th>
            <th width="10%">&nbsp;
            </th>
            <th width="40%">
             <strong><dhv:label name="portlets.folder.parametersforMinorAxis">Parameters for Minor Axis</dhv:label></strong>
            </th>
          </tr>
          <tr>
             <td width="50%">
                <table width="100%" cellspacing="0" cellpadding="4" border="0" class="pagedList">
                  <tr><th>&nbsp;</th>
                    <th><dhv:label name="portlets.folder.field">Field</dhv:label></th>
                  </tr>

                        <%
                            Iterator iter = fieldNameList.iterator();
                            if (iter.hasNext()) {
                            int rowid = 0;
                            int count = 1;
                            while(iter.hasNext()){
                              CustomField field = (CustomField)iter.next();
                               rowid = (rowid != 1?1:2);
                               // checking for only numeric, decimal and currency type fields to display for Minor Axis Parameters
                               // field_type of numeric is 9, decimal is 10 and currency is 12
                               if (field.getType() == 9 || field.getType() == 10 || field.getType() == 12) { 
                        %>
		                  <tr class="row<%= rowid %>">
		                     <td width="8"><input type="checkbox" name='<%="checkelement"+count%>' id='<%="checkelement"+count%>' value='<%=field.getId()%>'/></td>
		                     <td><%= toHtml(field.getName())%></td>
		                     <input type="hidden" name='<%="hiddenelement"+count%>' id='<%="hiddenelement"+count%>' value='<%=field.getName()%>'/>
		                  </tr>
		             <%  count++;
		                 } } %>
		               <script>size=<%=count%></script>
        </table>
        </td>

        <td >
        <table width="100%" aligen="center" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td >
            <input type="button" name="addButton" id="addButton" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:collectValues()">
          </td>
        </tr>
        <tr>
          <td >
            <input type="button" value="<dhv:label name="button.remove">Remove</dhv:label>" onclick="javascript:removeValues()">
          </td>
        </tr>
       </table>
    </td>

     <td>
          <table width="100%" cellspacing="0" cellpadding="2" border="0" class="details">
        <tr>
          <td>
           <select name="selectedList" multiple id="selectedList" size="10" style="width: 250px" onChange="javascript:resetOptions();">
           </select>
          </td> </tr></table>    
      </td>

        </tr>
         <tr>

         <tr >
              <td colspan="3">
                 <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
                    <tr>
                        <td  >
                        <dhv:label name="portlets.folder.graphType">Graph Type:</dhv:label> &nbsp;&nbsp;&nbsp;
                            <%= graphTypeList.getHtmlSelect("graphType", GraphType.getId()) %>
                        </td>
                    </tr>
                </table>
               </td>
          </tr>
                <td colspan="3">
                    <table width="100%" cellspacing="0" cellpadding="4" border="0" class="empty">
                        <tr>
                            <input type="hidden" name="displayFieldId" id="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
                            <input type="hidden" name="hiddenFieldId" id="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
                            <td align="right"><input type="button" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="javascript:returnToParent();"/></td>
                            <td align="left"><input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"></td>
                        </tr>
                   </table>
                </td>
          </tr>
        <%
          } else {
        %>
			<tr>
              <td class="containerBody" colspan="4">
                <dhv:label name="portlets.folder.nomatch">No folder fields matched query</dhv:label>
              </td>
            </tr>
            <tr>
                <td align="left" colspan="4"><input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"></td>
            </tr>
        <%
            }
        %>
        </table>
      </form>
  </body>
</html>