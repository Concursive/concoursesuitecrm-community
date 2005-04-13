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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
 function resetNumericFieldValue(propertyId){
  document.getElementById(propertyId).value = -1;
 }
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.addNewOption">Add New Option</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="product.optionName">Option Name</dhv:label>
		</td>
		<td>
			<input type="text" name="name" value="<%= toHtmlValue(ProductOption.getName()) %>"/>
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
		</td>
		<td>
			<input type="text" name="shortDescription" value="<%= toHtmlValue(ProductOption.getShortDescription()) %>"/>
		</td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="documents.details.longDescription">Long Description</dhv:label>
		</td>
		<td>
			<input type="text" name="longDescription" value="<%= toHtmlValue(ProductOption.getLongDescription()) %>"/>
		</td>
	</tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="documents.details.startDate">Start Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="paramForm" field="startDate" timestamp="<%= ProductOption.getStartDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="paramForm" field="endDate" timestamp="<%= ProductOption.getEndDate() %>"  timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2">
			<strong><dhv:label name="product.selectOptionConfigurator">Select Option Configurator</dhv:label></strong>
		</th>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="product.configurator">Configurator</dhv:label>
		</td>
		<td>
			<%= ConfiguratorList.getHtmlSelect("configuratorId", -1) %>
		</td>
	</tr>
</table>
