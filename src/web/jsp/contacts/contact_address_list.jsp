<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="typeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactAddressList" class="org.aspcfs.modules.contacts.base.ContactAddressList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContactAddressListSingle.js"></script>
<script language="JavaScript" TYPE="text/javascript">
  function confirmDelete(addressId){
    if (confirm('Are you sure you want to delete the selected contact address?')) {
      window.location.href = 'ContactAddressSelector.do?command=Delete&addressId=' + addressId;
    }
  }
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3">Contact Address List</th>
  </tr>
  <tr>
    <td><strong>Action</strong></td>
    <td><strong>Address</strong></td>
    <td><strong>Type</strong></td>
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
  <tr>
    <td valign="top" class="row<%= rowid %>"><a href="javascript:setContactAddress('<%= address.getStreetAddressLine1() %>','<%= address.getStreetAddressLine2() %>','<%= address.getStreetAddressLine3() %>','<%= address.getCity() %>', '<%= address.getState() %>','<%= address.getOtherState() %>', '<%= address.getZip() %>', '<%= address.getCountry() %>','<%= address.getType() %>');">Select</a> / 
    <a href="javascript:confirmDelete('<%= address.getId() %>');">Delete</a></td>
    <td valign="top" class="row<%= rowid %>"><%= toHtml(address.toString()) %></td>
    <td valign="top" class="row<%= rowid %>"><%= toHtml(address.getTypeName()) %></td>
  </tr>
<%
    }
  } else {
%>
  <tr>
    <td colspan="3">No Contact Address found. Please add one.</td>
  </tr>
<%
  }
%>
</table>
<br /><br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <td><input type="button" value="Add Address" onClick="javascript:window.location.href='ContactAddressSelector.do?command=AddressForm';"/></td>
  </tr>
</table>

