<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center">
      <strong>Action</strong>
    </th>
    <th width="12%" nowrap>
      <strong>Serial Number</strong>
    </th>
    <th width="13%" nowrap>
      <strong>Agreement Number</strong>
    </th>
    <th width="32%" >
      <strong>Services</strong>
    </th>
    <th width="10%" nowrap>
      <% if ("approved".equals(request.getAttribute("approved"))) {%>
        <strong><a href="NetworkApplications.do?command=Search&column=enddate">End Date</a></strong>
        <%= ExpirationContractListInfo.getSortIcon("enddate") %>
      <%}else{%>
        <strong><a href="NetworkApplicationsImports.do?command=ViewResults&importId=<%=ImportDetails.getId()%>">End Date</a></strong>
        <%= NetworkApplicationsImportResultsInfo.getSortIcon("enddate") %>
      <%}%>
    </th>
    <th width="32%">
      <strong>Installed at Company</strong>
    </th>
    <% if (!"approved".equals(request.getAttribute("approved"))) {%>
      <th width="8%">
        <strong>Approved?</strong>
      </th>
    <%}%>
  </tr>
<%    
	Iterator i = ImportResults.iterator();
	if (i.hasNext()) {
	int rowid = 0;
  int count  =0;
		while (i.hasNext()) {
      count++;
      rowid = (rowid != 1 ? 1 : 2);
      ContractExpiration thisExpiration = (ContractExpiration)i.next();
%>    
      <tr class="row<%= rowid %>">
        <td width="8" class="row<%= rowid %>" nowrap>
        <%
          int hasDeletePermission = 0;
          if(!thisExpiration.isApproved()){
        %>
            <dhv:permission name="netapps_expiration_contracts-delete">
              <% hasDeletePermission = 1; %>
            </dhv:permission>
        <% } %>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuContractExpiration','<%= thisExpiration.getId() %>','<%= hasDeletePermission %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuContractExpiration');">
         <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td> 
          <%=toHtml(thisExpiration.getSerialNumber())%>
        </td>
        <td> 
          <%=toHtml(thisExpiration.getAgreementNumber())%>
        </td>
        <td> 
          <%=toHtml(thisExpiration.getServices())%>
        </td>
        <td> 
          <zeroio:tz timestamp="<%= thisExpiration.getEndDate() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" default="&nbsp;"/>
        </td>
        <td> 
          <%=toHtml(thisExpiration.getInstalledAtCompanyName())%>
        </td>
        <% if (!"approved".equals(request.getAttribute("approved"))) {%>
          <td> 
            <% if (thisExpiration.getStatusId() == Import.PROCESSED_APPROVED){ %>
              YES
            <%}else{%>
              NO
            <%}%>
          </td>
        <%}%>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <% if ("approved".equals(request.getAttribute("approved"))) {%>
        <td class="containerBody" colspan="6">
    <%}else{%>
        <td class="containerBody" colspan="7">
    <%}%>
      No contracts found.
    </td>
  </tr>
<%}%>
</table>
