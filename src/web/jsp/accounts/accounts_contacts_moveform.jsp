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
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.utils.web.*,org.aspcfs.modules.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request" />
<jsp:useBean id="dependencies" class="org.aspcfs.modules.base.DependencyList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<script language="JavaScript" type="text/javascript">
  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
  }
</script>
<form name="moveContact" action="Contacts.do?command=MoveContact&id=<%= ContactDetails.getId() %>" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><dhv:label name="account.move">Move</dhv:label> <%= toHtml(ContactDetails.getNameFull()) %></th>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="account.fromOrganization">From Organization</dhv:label></td>
    <td><%= toHtml(ContactDetails.getOrgName()) %></td>
  </tr>
  <tr class="formLabel">
    <td class="formLabel"><dhv:label name="account.toOrganization">To Organization</dhv:label></td>
    <td>
      <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="changeaccount">
              <%= toHtml(ContactDetails.getOrgName()) %>
            </div>
          </td>
          <td>
            <input type="hidden" name="neworgId" id="neworgId" value="<%= ContactDetails.getOrgId() %>"/>
            <font color="red">*</font>
            <%= showAttribute(request, "orgIdError") %>
            [<a href="javascript:popAccountsListSingle('neworgId','changeaccount', 'showMyCompany=false&filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
	</tr>
</table>   
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="4"><dhv:label name="account.dependencies.label">Dependencies</dhv:label></th>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="dependency.opportunities">Opportunities</dhv:label> (<%= dependencies.getDependencyCount("opportunities") %>)</td>
    <td nowrap>
      <input type="radio" name="moveOpportunities" id="moveOpportunities" value="<%= Constants.UNDEFINED %>" CHECKED /><dhv:label name="account.noChange">No Change</dhv:label><br />
      <input type="radio" name="moveOpportunities" id="moveOpportunities" value="<%= Constants.FALSE %>" /><dhv:label name="button.delete">Delete</dhv:label><br />
      <input type="radio" name="moveOpportunities" id="moveOpportunities" value="<%= Constants.TRUE %>" /><dhv:label name="account.moveToOldAccount">Move to Old Account</dhv:label>
    </td>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="dependency.activities">Activities</dhv:label> (<%= dependencies.getDependencyCount("activities") %>)</td>
    <td nowrap>
      <input type="radio" name="moveActivities" id="moveActivities" value="<%= Constants.TRUE %>" CHECKED /><dhv:label name="account.noChange">No Change</dhv:label><br />
      <input type="radio" name="moveActivities" id="moveActivities" value="<%= Constants.FALSE %>" /><dhv:label name="button.delete">Delete</dhv:label>
    </td nowrap>
  </tr>
  <tr>
    <td class="formLabel"><dhv:label name="accounts.folders.long_html">Folders</dhv:label> (<%= dependencies.getDependencyCount("folders") %>)</td>
    <td nowrap>
      <input type="radio" name="moveFolders" id="moveFolders" value="<%= Constants.TRUE %>" CHECKED /><dhv:label name="account.noChange">No Change</dhv:label><br />
      <input type="radio" name="moveFolders" id="moveFolders" value="<%= Constants.FALSE %>" /><dhv:label name="button.delete">Delete</dhv:label>
    </td nowrap>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();"/>
</form>
