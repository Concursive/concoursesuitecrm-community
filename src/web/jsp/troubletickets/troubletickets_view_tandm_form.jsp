<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*, org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="window.print();" />
<table>  
  <tr>
    <td colspan="2" align="center" width="100%" nowrap>
      <h2> Dataline, Incorporated [status: <%= ticketDetails.getClosed() == null ? "Open" : "Closed" %>]</h2>
    </td>
  </tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <td>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td colspan="2" width="100%">
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
        <tr>
          <td width="40%"> Customer ID: </td>
          <td width="100%"> <%=toHtml(orgDetails.getAccountNumber())%>  </td>
        </tr>
        <tr>
          <td width="40%">  Customer Name/Location:   </td>
          <td width="60%">  <%=toHtml(orgDetails.getName())%>   </td>
        </tr>
        <%
          Contact thisContact = ticketDetails.getThisContact();
          ContactEmailAddressList emails = thisContact.getEmailAddressList();
          String email = "";
          if (emails != null){
           if (emails.size() > 0){
              email = emails.getEmailAddress(0);
            }
          }
         ContactPhoneNumberList phones = thisContact.getPhoneNumberList();
          String phoneNumber = "";
           if (phones != null) { 
              if(phones.size() > 0){
                phoneNumber = ((ContactPhoneNumber)phones.get(0)).getPhoneNumber();
            }
           }
        %>
        <tr>
          <td width="40%">  Contact Name: </td>
          <td width="60%"> <%= toHtml(ticketDetails.getThisContact().getNameLastFirst()) %>&nbsp&nbsp&nbsp <%=toHtml(email)%></td>
        </tr>
        <tr>
          <td width="40%">  Phone: </td>
          <td width="60%">  <%=toHtml(phoneNumber)%>   </td>
        </tr>
        <%
          ContactAddressList addresses = thisContact.getAddressList();
          Iterator itr = addresses.iterator();
          ContactAddress thisAddress = null;
          while (itr.hasNext()){
            thisAddress = (ContactAddress)itr.next();
            if (thisAddress.getType() == 1)
              break;
          }
          if (thisAddress != null) {
        %>
        <tr>
          <td width="40%">  Address 1:  </td>
          <td width="60%">  <%=toHtml(thisAddress.getStreetAddressLine1())%>&nbsp </td>
        </tr>
        <%
          if (!(toHtml(thisAddress.getStreetAddressLine2()).equals(""))){
        %>
        <tr>
          <td width="40%">  Address 2:  </td>
          <td width="60%">  <%=toHtml(thisAddress.getStreetAddressLine2())%>&nbsp </td>
        </tr>
        <%}%>
        <tr>
          <td colspan="2">
          City:<%=toHtml(thisAddress.getCity())%>&nbsp&nbsp&nbsp&nbsp State: <%=toHtml(thisAddress.getState())%> &nbsp&nbsp&nbsp&nbsp Zip: <%=toHtml(thisAddress.getZip())%> &nbsp 
          </td>
        </tr>
        <%}%>
     </table>
    </td>
  </tr>
   <tr>
    <td colspan="2"><h4>Issue</h4></td>
   </tr>
   <tr>
    <td  height="60" colspan="2" valign="top" width="100%"><%=toHtml(ticketDetails.getProblem())%></td>
   </tr>
   <tr>
    <td colspan="2" align="center"><h3>Status Report</h3></td>
   </tr>
   <tr>
   <td align="center" colspan=2>
   <table cellpadding="4" cellspacing="0" border="0" width="100%">
   <tr>
    <td align="top" width="50%"> 
     <table cellpadding="4" cellspacing="0" border="0" width="100%">
       <tr>
         <td width="25%">  Resource  </td>
         <td width="75%">  &nbsp </td>
       </tr>
       <tr>
         <td width="25%">  Engineer </td>
         <td width="75%">
          <dhv:username id="<%= ticketDetails.getAssignedTo() %>" default="-- unassigned --"/>
          <dhv:evaluate if="<%= !(ticketDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
         </td>
       </tr>
     </table>
    </td>
    <td width="50%">
     <table cellpadding="4" cellspacing="0" border="0" width="100%"> 
       <tr>
       <td>  Contract Number </td>
       <td>  <%=toHtml(ticketDetails.getServiceContractNumber())%> </td>
       </tr>
       <tr>
       <td> Category </td>
       <td> <%=toHtml(ticketDetails.getCategoryName())%> </td>
       </tr>
       <tr>
       <td> Hours Remaining   </td>
       <td>  
          <dhv:evaluate if="<%= ticketDetails.getTotalHoursRemaining() > -1 %>">
          <%= ticketDetails.getTotalHoursRemaining() %>
          </dhv:evaluate>&nbsp;
       </td>
       </tr>
     </table>
    </td>
    </tr>
    </table>
    </td>
   </tr>
   <tr>
     <td colspan=2>
       <table cellpadding="4" cellspacing="0" border="0" width="100%" frame="border" rules="all">
         <tr>
           <td colspan=2 width="15%"> Date </td>
           <td rowspan=2 width="15%"> Travel Time</td>
           <td rowspan=2 width="15%"> Labor Time</td>
           <td rowspan=2 width="55%"> Description of Service </td>
         </tr>
         <tr>
           <td>  Month  </td>
           <td>  Day    </td>
         </tr>
         <tr>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td height="80"> </td>
         </tr>
         <tr>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td height="80"> </td>
         </tr>
         <tr>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td  height="80"> </td>
         </tr>
         <tr>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td  height="80"> </td>
         </tr>
         <tr>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td>   </td>
           <td  height="80"> </td>
         </tr>
         <tr>
           <td colspan=2> Total travel hours </td>
           <td colspan=2>  </td>
           <td> Follow-up required? </td>
         </tr>
         <tr>
           <td rowspan=2 colspan=2> Total labor hours </td>
           <td rowspan=2 colspan=2>  </td>
           <td> Follow-up Notes: </td>
         </tr>
         <tr>
           <td> Alert Date?  </td>
         </tr>
         <tr>
           <td colspan=4>Phone Response Time  </td>
           <td>Engineer Response Time </td>
         </tr>
       </table>
     </td>
   </tr>
   <tr>
     <td colspan="2" align="center">
           <strong> Acceptance of Work Completed </strong>
      </td>
    </tr>
     <tr>
       <td colspan="2" > Have our services met or exceeded your expectations?</td>
     </tr>
    <tr>
    <td colspan="2" width="100%">
       <table cellpadding="4" cellspacing="0" border="0" width="100%">
         <tr>
           <td width="25%">Customer Signature: </td>
           <td width="40%"> </td>
           <td width="10%"> Date: </td>
           <td width="25%"> </td>
         </tr>
         <tr>
           <td width="25%">Engineer Signature: </td>
           <td width="40%"> </td>
           <td width="10%"> Date: </td>
           <td width="25%"> </td>
         </tr>
       </table>
     </td>
   </tr>
 </table>
 </td>
 </tr>
</table>