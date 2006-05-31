<input type="hidden" name="id" value="<%=contractExpiration.getId()%>" />
<input type="hidden" name="modified" value="<%= contractExpiration.getModified() %>" />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>CSV Details</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Serial Number</td>
    <td><%= toHtml(contractExpiration.getSerialNumber()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Agreement Number</td>
    <td><%= toHtml(contractExpiration.getAgreementNumber()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Services</td>
    <td><%= toHtml(contractExpiration.getServices()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Start Date</td>
    <td><zeroio:tz timestamp="<%= contractExpiration.getStartDate()  %>" dateOnly="true" default="&nbsp" /></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>End Date</td>
    <td><zeroio:tz timestamp="<%= contractExpiration.getEndDate()  %>" dateOnly="true" default="&nbsp" /></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed-at Company Name</td>
    <td><%= toHtml(contractExpiration.getInstalledAtCompanyName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed at Site Name</td>
    <td><%= toHtml(contractExpiration.getInstalledAtSiteName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Group</td>
    <td><%= toHtml(contractExpiration.getGroupName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Product Number</td>
    <td><%= toHtml(contractExpiration.getProductNumber()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>OS</td>
    <td><%= toHtml(contractExpiration.getOperatingSystem()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap># of Shelves</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getNoOfShelves() > -1 %>">
      <%= contractExpiration.getNoOfShelves() %>
      </dhv:evaluate>&nbsp;
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap># of Disks</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getNoOfDisks() > -1 %>">
        <%= contractExpiration.getNoOfDisks() %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>NVRAM</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getNvram() > -1 %>">
        <%= contractExpiration.getNvram() %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Memory</td>
    <td>
      <dhv:evaluate if="<%= contractExpiration.getMemory() > -1 %>">
        <%= contractExpiration.getMemory() %>
      </dhv:evaluate>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Autosupport Status</td>
    <td><%= toHtml(contractExpiration.getAutosupportStatus()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed-at Address</td>
    <td><%= toHtml(contractExpiration.getInstalledAtAddress()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>City</td>
    <td><%= toHtml(contractExpiration.getCity()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>State/Province</td>
    <td><%= toHtml(contractExpiration.getStateProvince()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Postal Code</td>
    <td><%= toHtml(contractExpiration.getPostalCode()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Country</td>
    <td><%= toHtml(contractExpiration.getCountry()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Installed-at Contact First Name</td>
    <td><%= toHtml(contractExpiration.getInstalledAtContactFirstName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Contact Last Name</td>
    <td><%= toHtml(contractExpiration.getContactLastName()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Contact Email</td>
    <td><%= toHtml(contractExpiration.getContactEmail()) %></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Agreement Company</td>
    <td><%= toHtml(contractExpiration.getAgreementCompany()) %></td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Quote Information  (This quote information is logged in the history. Previous quote information may be viewed in the history tab.)</strong>
    </th>
  </tr>
  <%--
  <tr class="containerBody">
    <td class="formLabel" nowrap>Quote Amount</td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="quoteAmount" size="15" value="<zeroio:number value="<%= contractExpiration.getQuoteAmount() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "quoteAmountError") %>
    </td>
  </tr>
  --%>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Quote Generated</td>
    <td>
      <zeroio:dateSelect form="modifyContractExpiration" field="quoteGeneratedDate" />
      <%= showAttribute(request, "quoteGeneratedDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Quote Accepted</td>
    <td>
      <zeroio:dateSelect form="modifyContractExpiration" field="quoteAcceptedDate" />
      <%= showAttribute(request, "quoteAcceptedDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>Quote Rejected</td>
    <td>
      <zeroio:dateSelect form="modifyContractExpiration" field="quoteRejectedDate" />
      <%= showAttribute(request, "quoteModifiedDateError") %>
     </td>
  </tr>
  <%--
  <tr class="containerBody">
    <td valign="top" class="formLabel" nowrap>Quote Notes</td>
    <td>
      <textarea name="comment" cols="55" rows="5"><%= toString(contractExpiration.getComment()) %></textarea>
    </td>
  </tr>
  --%>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Record Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Entered
    </td>
    <td>
      <dhv:username id="<%= contractExpiration.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= contractExpiration.getEntered()  %>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Modified
    </td>
    <td>
      <dhv:username id="<%= contractExpiration.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= contractExpiration.getModified()  %>" />
    </td>
   </tr>
</table>

