<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += "- Subject is required\r\n";
      formTest = false;
    }
    if (form.id<%= TicketDetails.getId() %>.value.length < 5) {
      messageText += "- File is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != 'Please Wait...') {
        form.upload.value='Please Wait...';
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do">Help Desk</a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home">View Tickets</a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<a href="TroubleTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Add Version
</td>
</tr>
</table>
<%-- End Trails --%>
<form method="post" name="inputForm" action="TroubleTicketsDocuments.do?command=UploadVersion" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td class="containerBack">
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
      <font color="red">This ticket was closed on
      <zeroio:tz timestamp="<%= TicketDetails.getClosed() %>" />
      </font><br>
      &nbsp;<br>
    </dhv:evaluate>
    <%= showError(request, "actionError") %>
    <%-- include add version form --%>
     <%@ include file="documents_addversion_include.jsp" %>
      <p align="center">
        * Large files may take a while to upload.<br>
        Wait for file completion message when upload is complete.
      </p>
      <input type="submit" value=" Upload " name="upload">
      <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='TroubleTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>&folderId=<%= (String)request.getAttribute("folderId") %>';">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
      <input type="hidden" name="fid" value="<%= FileItem.getId() %>">
    </td>
    </tr>
    </td>
  </tr>
</table>
</form>
</body>
