<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.base.*, org.aspcfs.modules.accounts.base.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="window.print();" />
<table>  
  <tr>
    <th align="center" colspan=2 width="100%" nowrap>
      <h2> Dataline, Incorporated </h2>
    </th>
  </tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <td>
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
	    <strong>Organization Information</strong>
	  </td>
    <td>
	    <strong>Dataline</strong>
	  </td>
  </tr>
  <tr>
    <td width="70%">
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
       <tr>
          <td width="40%"> Customer ID: </td>
          <td width="100%"> <%=toHtml(orgDetails.getAccountNumber())%>  </td>
        </tr>
        <tr>
          <td width="40%">  Customer Name/Location:   </td>
          <td width="60%">  <%=toHtml(orgDetails.getName())%>   </td>
        </tr>
        <tr>
          <td width="40%">  Contact Name: </td>
          <td width="60%"><%= toHtml(ticketDetails.getThisContact().getNameLastFirst()) %></td>
        </tr>
        <tr>
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
          String extension = "";
           if (phones != null) { 
              if(phones.size() > 0){
                phoneNumber = ((ContactPhoneNumber)phones.get(0)).getNumber();
                extension = ((ContactPhoneNumber)phones.get(0)).getExtension();
            }
           }
        %>
          <td width="40%">  Phone: </td>
          <td width="60%">  <%=toHtml(phoneNumber)%> </td>
        </tr>
        <tr>
          <td width="40%">  Ext:  </td>
          <td width="60%">  <%=toHtml(extension)%>&nbsp</td>
        </tr>
        <tr>
          <td width="40%">  Email ID: </td>
          <td width="60%">  <%=toHtml(email)%>&nbsp </td>
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
          <td width="60%"><%=toHtml(thisAddress.getStreetAddressLine1())%></td>
        </tr>
        <tr>
          <td width="40%">  Address 2:  </td>
          <td width="60%"><%=toHtml(thisAddress.getStreetAddressLine2())%></td>
        </tr>
        <tr>
          <td colspan="2">  
          City: <%=toHtml(thisAddress.getCity())%>&nbsp&nbsp&nbsp&nbsp State: <%=toHtml(thisAddress.getState())%> &nbsp&nbsp&nbsp&nbsp Zip: <%=toHtml(thisAddress.getZip())%> &nbsp 
          </td>
        </tr>
        <%}%>
     </table>
    </td>
    <td valign="top" width="30%">
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
       <tr>
        <td width="40%">  Priority:  </td>
        <td width="60%">  <%=toHtml(ticketDetails.getPriorityName())%>   </td>
      </tr>
       <tr>
       <td width="40%">   Call Category:  </td>
        <td width="60%">  <%=toHtml(ticketDetails.getCategoryName())%>   </td>
      </tr>
       <tr>
        <td width="40%">  Call Item </td>
        <td width="60%">  &nbsp </td>
      </tr>
       <tr>
        <td width="40%">  Call Source   </td>
        <td width="60%">  <%=toHtml(ticketDetails.getSourceName())%>   </td>
      </tr>
     </table>
    </td>
  </tr>
   <tr>
   </tr>
     <td colspan="2">
     <h3>Hardware Maintenance Incident Report</h3>
     <td>
   <tr>
     <td colspan="2">
       <table cellpadding="4" cellspacing="0" border="0" width="100%">
        <tr>
          <td colspan="2">
            <table cellpadding="4" cellspacing="0" border="0" width="100%">
              <tr>
                <td width="25%"> Serial Number </td>
                <td width="25%">
                <%= toHtml(ticketDetails.getAssetSerialNumber())%>
                </td>
                <td width="25%"> Hardware Location</td>
                <td width="25%">
                <%= toHtml(ticketDetails.getAssetLocation())%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td valign="top">
            <table cellpadding="4" cellspacing="0" border="0" width="100%">
              <tr>
              <td colspan="4"><h4> Maintenance Details </h4></td>
              </tr>
              <tr>
                <td width="15%"> Vendor </td>
                <td colspan="3">
                <%= toHtml(ticketDetails.getAssetVendor())%>
                </td>
              </tr>
              <tr>
                <td> Model </td>
                <td colspan="3">
                <%= toHtml(ticketDetails.getAssetModelVersion())%>
                </td>
              </tr>
              <tr>
                <td width="15%"> Start Date </td>
                <td width="35%">
                  <dhv:tz timestamp="<%= ticketDetails.getContractStartDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;" />
                </td>
                <td width="15%"> End Date </td>
                <td width="35%">
                  <dhv:tz timestamp="<%= ticketDetails.getContractEndDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;" />
                </td>
              </tr>
            </table>
          </td>
          <td>
            <table cellpadding="4" cellspacing="0" border="0" width="100%">
              <tr>
              <td colspan="4"><h4> Service Details </h4></td>
              </tr>
              <tr>
                <td width="40%"> Onsite Response </td>
                <td colspan="3">
                <%= toHtml((ticketDetails.getAssetOnsiteResponseModel() == -1)? onsiteModelList.getSelectedValue(ticketDetails.getContractOnsiteResponseModel()) : onsiteModelList.getSelectedValue(ticketDetails.getAssetOnsiteResponseModel()))%>
                </td>
              </tr>
              <tr width="40%">
                <td> Service Contract No. </td>
                <td colspan="3">
                  <%= toHtml(ticketDetails.getServiceContractNumber())%>  
                </td>
              </tr>
              <tr>
              <td colspan="4">Replacement Parts Used?</td>
              </tr>
            </table>
          </td>
         </tr> 
       </table>
     </td>
   </tr>
   <tr>
      <td colspan="2"><h4> Replacement Parts  </h4></td>
   </tr>
   <tr>
      <td colspan="2">
       <table cellpadding="4" cellspacing="0" border="0" width="100%" frame="border" rules="all">
         <tr>
           <td width="15%">Part Number</td>
           <td width="15%"></td>
           <td width="15%" height="40">Description</td>
           <td width="55%"></td>
         </tr>
         <tr>
         <td>Part Number</td>
           <td></td>
           <td height="40">Description</td>
           <td></td>
         </tr>
         <tr>
           <td>Part Number</td>
           <td></td>
           <td height="40">Description</td>
           <td></td>
         </tr>
       </table>
     </td>
   </tr>
  <tr>
    <td colspan="2" width="100%">
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
      <br>
        <tr>
          <td width="40%">
            <strong>Incident Log</strong>
          </td>
          <td width="20%">&nbsp</td>
          <td width="40%" align="right">&nbsp </td>
         </tr>
      </table>
     </td>
     </tr>
     <tr>
     <td colspan=2 width="100%">
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
         <tr>
           <td rowspan=2 width="15%">  Call Status:  </td>
           <td rowspan=2 width="25%"> <%= ticketDetails.getClosed() == null ? "Open" : "Closed" %></td>
           <td width="30%">  Incident/Call Type:     </td>
           <td width="30%">  &nbsp </td>
         </tr>
         <tr>
           <td width="20%">  Analyst:  </td>
           <td width="30%">  &nbsp </td>
         </tr>
         <tr> <td colspan="4">   Description of Incident   </td>  </tr>
         <tr> <td colspan="4"height="155">    </td>  </tr>
         <tr> <td colspan="4" colspan="4"> Solution Information        </td>  </tr>
         <tr> <td colspan="4" height="155">    </td>  </tr>
         <tr> 
           <td colspan="2"> Cause  </td>
           <td colspan="2"><%=toHtmlValue(ticketDetails.getCause())%></td>
         </tr>
         <tr>
           <td colspan="2"> Update Information </td>
           <td colspan="2"> Closed Information </td>
         </tr>
         <tr>
           <td colspan="2" height="40"> &nbsp </td>
           <td colspan="2" height="40" align="right">&nbsp&nbsp&nbsp/ &nbsp&nbsp / &nbsp&nbsp&nbsp : &nbsp&nbsp : &nbsp&nbsp&nbsp </td>
         </tr>
         <br>
        </table>
    </td>
   </tr>
   <tr>
     <td colspan=2 align="center">
           <strong> Acceptance of Work Completed </strong>
      </td>
    </tr>
     <tr>
       <td colspan=2 > Have our services met or exceeded your expectations?</td>
     </tr>
    <tr>
    <td colspan=2 width="100%">
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
