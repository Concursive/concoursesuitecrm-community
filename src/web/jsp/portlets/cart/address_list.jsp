<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
- 
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<%@ page import="java.util.*, org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="returnStr" class="java.lang.String" scope="request" />
<jsp:useBean id="addressList" class="org.aspcfs.modules.contacts.base.ContactAddressList" scope="request" />
<jsp:useBean id="billingAddress" class="org.aspcfs.modules.contacts.base.ContactAddress" scope="session" />
<jsp:useBean id="shippingAddress" class="org.aspcfs.modules.contacts.base.ContactAddress" scope="session" />

<portlet:defineObjects />

<form name="addressList" action="<portlet:actionURL />" method="post">
  <portlet:renderURL var="urlAdd">
     <portlet:param name="viewType" value="addAddress"/>
  </portlet:renderURL>
        <A href="<%= pageContext.getAttribute("urlAdd") %>">Add address</A>
  <br>
  <strong>Address List</strong>
  <br>
  
<% if(addressList!=null && !addressList.isEmpty()){ %>
  <table cellpadding="4" cellspacing="0" border="0">
    <tr>
      <th>
        Billing
      </th>
      <th>
       Shipping
      </th>
      <th>
        Address
      </th>
      <th>
       Modify
      </th>
      <th>
       Delete
      </th>
    </tr>
    <% 
  Iterator j = addressList.iterator();
  if (j.hasNext()) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      ContactAddress adr = (ContactAddress) j.next();
%>
    <tr>
      <td>
        <INPUT type="radio" name="billing" value="<%=adr.getId()%>"
        <%if(billingAddress!=null && adr.getId()==billingAddress.getId()) {%>
        checked="checked"  
        <%} %>
        />
      </td>
      <td>
        <INPUT type="radio" name="shipping" value="<%=adr.getId()%>" 
        <%if(shippingAddress!=null && adr.getId()==shippingAddress.getId()) {%>
        checked="checked"  
        <%} %>/>
      </td>
      <td>
        <%=adr.toString() %>
      </td>
      <td>
         <portlet:renderURL var="url">
           <portlet:param name="viewType" value="modifyAddress"/>
           <portlet:param name="addressId" value="<%=String.valueOf(adr.getId())%>"/>
        </portlet:renderURL>
        <A href="<%= pageContext.getAttribute("url") %>">Edit</A>
      </td>
      <td>
        <INPUT type="checkbox" name="deleteAddressId" value="<%=adr.getId()%>"/>
      </td>
    </tr>
    <%}}%>
  </table>
  <br />
  <input type="hidden" name="returnStr" value="<%= returnStr %>">
  <input type="submit" name="deleteAddress" value="Delete" onclick="javascript:if(!confirm('Do you wish to delete this address(es)?')){return false;}"/>
  <input type="submit" name="selectAddress" value="Continue" />
  <input type="reset" name="Clear" value="Clear" />
  <%} else { %>
  <br>
  You have no addresses on file
  <%} %>
</form>
