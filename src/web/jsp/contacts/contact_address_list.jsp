<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*,org.aspcfs.utils.*" %>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactAddressList" class="org.aspcfs.modules.contacts.base.ContactAddressList" scope="request"/>
<jsp:useBean id="contact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript">
  function confirmDelete(addressId){
    if (confirm(label('confirm.delete.contact.address','Are you sure you want to delete the selected contact address?'))) {
      window.location.href = 'ContactAddressSelector.do?command=Delete&addressId=' + addressId+'&contactId='+'<%= (contact != null)?""+contact.getId():"" %>';
    }
  }
</script>
<a href="ContactAddressSelector.do?command=AddressForm&contactId=<%= (contact != null? ""+contact.getId():"") %>"><dhv:label name="button.addAdderss">Add Address</dhv:label></a><br />
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3"><dhv:label name="contacts.contactAddressList">Contact Address List</dhv:label></th>
  </tr>
  <tr>
    <td nowrap><strong><dhv:label name="accounts.Action">Action</dhv:label></strong></td>
    <td><strong><dhv:label name="quotes.address">Address</dhv:label></strong></td>
    <td><strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong></td>
  </tr>
<%
  if (contactAddressList.size() > 0) {
     int rowid=0;
     int i=0;
    Iterator iterator = (Iterator) contactAddressList.iterator();
    while (iterator.hasNext()) {
      ContactAddress address = (ContactAddress) iterator.next();
      i++;
      rowid = ( rowid != 1 ? 1:2 );
%>
  <tr class="row<%= rowid %>">
    <td nowrap valign="top"><a href="javascript:setContactAddress('<%= StringUtils.jsStringEscape(address.getStreetAddressLine1()) %>','<%= StringUtils.jsStringEscape(address.getStreetAddressLine2()) %>','<%= StringUtils.jsStringEscape(address.getStreetAddressLine3()) %>','<%= (address.getCity()!=null?StringUtils.jsStringEscape(address.getCity()):"") %>', '<%= (address.getState()!=null?StringUtils.jsStringEscape(address.getState()):"") %>','<%= (address.getOtherState()!=null?StringUtils.jsStringEscape(address.getOtherState()):"") %>', '<%= (address.getZip()!=null?address.getZip():"") %>', '<%= address.getCountry() %>','<%= address.getType() %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a> /
    <a href="javascript:confirmDelete('<%= address.getId() %>');"><dhv:label name="global.button.delete">Delete</dhv:label></a></td>
    <td valign="top" width="50%"><%= toHtml(address.toString()) %></td>
    <td valign="top" width="50%"><%= toHtml(address.getTypeName()) %></td>
  </tr>
<%
    }
  } else {
%>
  <tr>
    <td colspan="3"><dhv:label name="contacts.noContactAddressFound">No Contact Address found. Please add one.</dhv:label></td>
  </tr>
<%
  }
%>
</table>
<br />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();" />
