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
<jsp:useBean id="ExpirationContractListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript">
  function clearForm() {}
</script>
<body onLoad="javascript:document.forms[0].searchcodeSerialNumber.focus();">
<form name="searchForm" action="NetworkApplications.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="NetworkApplications.do">Net App</a> > 
Search Form
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Search Contracts</strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      Serial Number
    </td>
    <td>
      <input type="text" size="30" name="searchcodeSerialNumber" value="<%= ExpirationContractListInfo.getSearchOptionValue("searchcodeSerialNumber") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Agreement Number
    </td>
    <td>
      <input type="text" size="30" name="searchcodeAgreementNumber" value="<%= ExpirationContractListInfo.getSearchOptionValue("searchcodeAgreementNumber") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Services
    </td>
    <td>
      <input type="text" size="30" name="searchcodeServices" value="<%= ExpirationContractListInfo.getSearchOptionValue("searchcodeServices") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      End Date between
    </td>
    <td>
      <zeroio:dateSelect form="searchForm" field="searchdateEndDateStart" timestamp='<%= ExpirationContractListInfo.getSearchOptionValue("searchdateEndDateStart") %>' />
      &nbsp;and <%=showAttribute(request,"searchdateEndDateStartError")%><br />
      <zeroio:dateSelect form="searchForm" field="searchdateEndDateEnd" timestamp='<%= ExpirationContractListInfo.getSearchOptionValue("searchdateEndDateEnd") %>' />
      &nbsp;<%=showAttribute(request,"searchdateEndDateEndError")%>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Installed at Company Name
    </td>
    <td>
      <input type="text" size="30" name="searchcodeInstalledAtCompanyName" value="<%= ExpirationContractListInfo.getSearchOptionValue("searchcodeInstalledAtCompanyName") %>">
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Search">
<input type="button" value="Clear" onClick="javascript:clearForm();">
</form>
</body>
