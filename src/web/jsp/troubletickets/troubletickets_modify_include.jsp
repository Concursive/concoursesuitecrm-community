<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function updateSubList1() {
  var sel = document.forms['details'].elements['catCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&catCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList2() {
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat1=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateSubList3() {
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=CategoryJSList&subCat2=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets.do?command=DepartmentJSList&departmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}

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
function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  if (form.closeNow.checked && form.solution.value == "") { 
   alert("Resolution needs to be filled in when closing a ticket");
   return false;
  }
  return true;
}

function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0){
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate(){
  document.forms['details'].assignedDate.value = '';
}  

</SCRIPT>

  <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
      <th colspan="2">
        <strong>Ticket Information</strong>
      </th>
		</tr>
		<tr class="containerBody">
      <td class="formLabel">
        Ticket Source
      </td>
      <td>
        <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td class="formLabel">
        Contact
      </td>
      <td>
      <% if ( TicketDetails.getThisContact() == null ) {%>
        <%= ContactList.getHtmlSelect("contactId", 0 ) %>
      <%} else {%>
        <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
      <%}%>
        <font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
      </td>
		</tr>
    <tr class="containerBody">
    <td class="formLabel">
      Service Contract Number
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract"><%= toHtml((TicketDetails.getContractId() != -1) ? TicketDetails.getServiceContractNumber() :"None Selected") %></div>
        </td>
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%= TicketDetails.getContractId() %>">
          &nbsp;
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled', <%= TicketDetails.getOrgId() %>);">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addServiceContract','None Selected');javascript:resetNumericFieldValue('contractId');javascript:changeDivContent('addAsset','None Selected');javascript:resetNumericFieldValue('assetId');javascript:changeDivContent('addLaborCategory','None Selected');javascript:resetNumericFieldValue('productId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Asset
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addAsset"><%= toHtml((TicketDetails.getAssetId() != -1) ? TicketDetails.getAssetSerialNumber():"None Selected")%></div>
        </td>
        <td>
          <input type="hidden" name="assetId" id="assetId" value="<%=  TicketDetails.getAssetId() %>">
          &nbsp;
          <%= showAttribute(request, "assetIdError") %>
          [<a href="javascript:popAssetListSingle('assetId','addAsset', 'filters=allassets|undercontract','contractId','addServiceContract');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addAsset','None Selected');javascript:resetNumericFieldValue('assetId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Labor Category
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addLaborCategory"><%= (TicketDetails.getProductId() != -1) ? TicketDetails.getProductSku() :"None Selected" %></div>
        </td>
        <td>
          <input type="hidden" name="productId" id="productId" value="<%=  TicketDetails.getProductId() %>">
          &nbsp;
          <%= showAttribute(request, "productIdError") %>
          [<a href="javascript:popProductListSingle('productId','addLaborCategory', 'filters=all|my|disabled');">Select</a>]
          &nbsp [<a href="javascript:changeDivContent('addLaborCategory','None Selected');javascript:resetNumericFieldValue('productId');">Clear</a>] 
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </table>
  <br />
  <a name="categories"></a> 
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
      <th colspan="2">
        <strong>Classification</strong>
      </th>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="ticket.issue">Issue</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
            </td>
            <td valign="top">
              <font color="red">*</font> <%= showAttribute(request, "problemError") %>
              <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
              <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
              <input type="hidden" name="orgId" id="orgId" value="<%=TicketDetails.getOrgId()%>">
              <input type="hidden" name="companyName" value="<%=toHtml(TicketDetails.getCompanyName())%>">
              <input type="hidden" name="refresh" value="-1">
            </td>
          </tr>
        </table>
		<% if (request.getParameter("return") != null) {%>
			<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
		<%}%>
      </td>
		</tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Location
      </td>
      <td>
        <input type="text" name="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50" maxlength="256" />
      </td>
    </tr>
  <dhv:include name="ticket.catCode" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Category
      </td>
      <td>
        <%= CategoryList.getHtmlSelect("catCode", TicketDetails.getCatCode()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.subCat1" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 1
      </td>
      <td>
        <%= SubList1.getHtmlSelect("subCat1", TicketDetails.getSubCat1()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.subCat2" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 2
      </td>
      <td>
        <%= SubList2.getHtmlSelect("subCat2", TicketDetails.getSubCat2()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.subCat3" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Sub-level 3
      </td>
      <td>
        <%= SubList3.getHtmlSelect("subCat3", TicketDetails.getSubCat3()) %>
      </td>
		</tr>
  </dhv:include>
  <dhv:include name="ticket.severity" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Severity
      </td>
      <td>
        <%= SeverityList.getHtmlSelect("severityCode", TicketDetails.getSeverityCode()) %>
      </td>
		</tr>
  </dhv:include>
  </table>
  <br>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Assignment</strong>
      </th>
		</tr>
  <dhv:include name="ticket.priority" none="true">
		<tr class="containerBody">
      <td class="formLabel">
        Priority
      </td>
      <td>
        <%= PriorityList.getHtmlSelect("priorityCode", TicketDetails.getPriorityCode()) %>
      </td>
		</tr>
  </dhv:include>
		<tr class="containerBody">
      <td class="formLabel">
        Department
      </td>
      <td>
        <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td nowrap class="formLabel">
        Resource Assigned
      </td>
      <td>
        <% UserList.setJsEvent("onChange=\"javascript:setAssignedDate();\"");%>
        <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
      </td>
		</tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Assignment Date
      </td>
      <td>
        <input type="text" size="10" name="assignedDate" value="<zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true" />">
        <a href="javascript:popCalendar('details', 'assignedDate', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Estimated Resolution Date
      </td>
      <td>
        <input type="text" size="10" name="estimatedResolutionDate" value="<zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true" />">
        <a href="javascript:popCalendar('details', 'estimatedResolutionDate', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
      </td>
    </tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        Issue Notes
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td valign="top">
              <textarea name="comment" cols="55" rows="5"><%= toString(TicketDetails.getComment()) %></textarea>
            </td>
            <td valign="top">
              (This note is added to the ticket history. Previous notes for this ticket are listed under the history tab.)
            </td>
          </tr>
        </table>
      </td>
		</tr>
  </table>
  <br />
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Resolution</strong>
      </th>
		</tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        Cause
      </td>
      <td>
        <textarea name="cause" cols="55" rows="8"><%= toString(TicketDetails.getCause()) %></textarea>
      </td>
		</tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        Resolution
      </td>
      <td>
        <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea><br>
        <input type="checkbox" name="closeNow" value="true">Close ticket
        <%--
        <br>
        <input type="checkbox" name="kbase" value="true">Add this solution to Knowledge Base
        --%>
<%-- Added for voice demo, will show a list of surveys that can be emailed... --%>
<%--
<%
        CampaignList campaignList = (CampaignList) request.getAttribute("CampaignList");
        if (campaignList != null && campaignList.size() > 0) {
          HtmlSelect campaignSelect = new HtmlSelect();
          campaignSelect.addItem(-1, "-- None --");
          Iterator campaigns = campaignList.iterator();
          while (campaigns.hasNext()) {
            Campaign thisCampaign = (Campaign)campaigns.next();
            campaignSelect.addItem(thisCampaign.getId(), thisCampaign.getName());
          }
%>
        <br>Send contact a follow-up message: <%= campaignSelect.getHtml("campaignId", TicketDetails.getCampaignId()) %>
<%
        }
%>
--%>
<%-- End voice demo --%>
      </td>
		</tr>
    <tr class="containerBody">
      <td class="formLabel">
        Resolution Date
      </td>
      <td>
        <input type="text" size="10" name="resolutionDate" value="<zeroio:tz timestamp="<%= TicketDetails.getResolutionDate() %>" dateOnly="true" />">
        <a href="javascript:popCalendar('details', 'resolutionDate', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Have our services met or exceeded your expectations?
    </td>
    <td>
      <input type="radio" name="expectation" value="1" <%= (TicketDetails.getExpectation() == 1) ? " checked" : "" %>>Yes
      <input type="radio" name="expectation" value="0" <%= (TicketDetails.getExpectation() == 0) ? " checked" : "" %>>No
      <input type="radio" name="expectation" value="-1" <%= (TicketDetails.getExpectation() == -1) ? " checked" : "" %>>Undecided
    </td>
  </tr>
	</table>
&nbsp;<br>
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
