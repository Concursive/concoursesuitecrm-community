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
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Serial Number</strong>
    </th>
    <th width="15%">
      <strong>Service Contract</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Manufacturer</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Model Version</strong>
    </th>
    <th width="25%" nowrap>
      <strong>Category</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Status</strong>
    </th>
  </tr>
  <% 
    Iterator itr = assetList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        Asset thisAsset = (Asset)itr.next();
    %>    
  <tr class="row<%=rowid%>">
    <td width="8" valign="center" nowrap>
        <% int status = -1;%>
        <% status = OrgDetails.getEnabled() ? 1 : 0; %>
      	<%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuAsset', '<%=request.getParameter("orgId") %>', '<%= thisAsset.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAsset');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
		<td width="15%" nowrap>
      <a href="AccountsAssets.do?command=View&orgId=<%=request.getParameter("orgId")%>&id=<%= thisAsset.getId()%>"><%= toHtml(thisAsset.getSerialNumber()) %></a>
		</td>
    <td width="15%" nowrap>
      <%= toHtml(thisAsset.getServiceContractNumber()) %>
		</td>
		<td width="15%" nowrap>
      <%= toHtml(thisAsset.getManufacturer()) %>
    </td>
		<td width="15%" nowrap>
      <%= toHtml(thisAsset.getModelVersion()) %>
    </td>
    <td width="25%">
      <dhv:evaluate if="<%= thisAsset.getLevel1() > 0 %>"><%= toHtml(categoryList1.getSelectedValue(thisAsset.getLevel1())) %></dhv:evaluate><dhv:evaluate if="<%= thisAsset.getLevel2() > 0 %>">,
      <%= toHtml(categoryList2.getSelectedValue(thisAsset.getLevel2())) %></dhv:evaluate><dhv:evaluate if="<%= thisAsset.getLevel3() > 0 %>">,
      <%= toHtml(categoryList3.getSelectedValue(thisAsset.getLevel3())) %></dhv:evaluate>
      &nbsp;
		</td>
    <td width="15%" nowrap>
      <dhv:evaluate if="<%= thisAsset.getStatus() > 0 %>">
        <%= toHtml(assetStatusList.getSelectedValue(thisAsset.getStatus())) %>
      </dhv:evaluate>&nbsp;      
		</td>
   </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="7">
        No assets found.
      </td>
    </tr>
    <%
    }
    %></table>

