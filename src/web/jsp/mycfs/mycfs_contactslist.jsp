<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="ContactListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>

<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>


<%
 if(!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))){
%>

<br>
<center><%= ContactListInfo.getAlphabeticalPageLinks("lettersubmit") %></center>


<form name="contactListView" method="post" action="/MyCFSInbox.do?command=ContactList">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
<input type=hidden name="letter">
<table width="100%" border="0">
  <tr>
    
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ContactListInfo.getOptionValue("all") %>>All Contacts</option>
	<option <%= ContactListInfo.getOptionValue("employees") %>>Employees</option>
	<option <%= ContactListInfo.getOptionValue("mycontacts") %>>My Contacts</option>
	<option <%= ContactListInfo.getOptionValue("accountcontacts") %>>Account Contacts</option>
	</select>
     </td>
    
  </tr>
</table>



<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
   <tr class="title">
    <td align=center width=8>
    To 
    </td>
     <td align=left>
      Name
    </td>
    <td align=left>
      Email    
    </td>
     <td align=left>
      Contact Type    
    </td>
   </tr>

   <%
	Iterator j = ContactList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = 0;
		
	  while (j.hasNext()) {
			count++;		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Contact thisContact = (Contact)j.next();
   
%>      
  <tr class="row<%= rowid+((selectedContacts.get(new Integer(thisContact.getId()))!= null)?"hl":"") %>">
    <td align="center" nowrap width=8>
    
	<input type=checkbox name="checkcontact<%=count%>" value=<%=thisContact.getId()%><%=((selectedContacts.get(new Integer(thisContact.getId()))!= null)?" checked":"")%> onClick="highlight(this,'<%=User.getBrowserId()%>');">
	<input type=hidden name="hiddencontactid<%=count%>" value=<%=thisContact.getId()%>>
    
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameLastFirst()) %>
    </td>
     <%
      String email ="",emailType ="";
      int size   = thisContact.getEmailAddressList().size();
      Iterator i = thisContact.getEmailAddressList().iterator();
      if(size < 2){
  	if(i.hasNext()){
		EmailAddress thisAddress = (EmailAddress)i.next();
		email     =  thisAddress.getEmail();
		emailType =  thisAddress.getTypeName();
	}
	if(!email.equals("")){
      %>
	<td nowrap><%=toHtml(email)%> (<%=toHtml(emailType)%>)</td>
      <%}
      else{
      %>
	<td align=left nowrap>None</td>
	<input type=hidden name="hiddenname<%=count%>" value="<%=toHtml(thisContact.getNameLastFirst())%>">
      <%}
      }
      else{%>
      
      <td nowrap>
      <select size="1" name="contactemail<%=count%>">
      <% 
      	while (i.hasNext()) {
      	EmailAddress thisAddress = (EmailAddress)i.next();
	email     =  thisAddress.getEmail();
	emailType =  thisAddress.getTypeName();
	String selectedEmail = "";
	if((selectedContacts.get(new Integer(thisContact.getId()))!= null)){
		selectedEmail = (String)selectedContacts.get(new Integer(thisContact.getId()));
	}
	if(!email.equals("")){
      %>
	<option value="<%=email%>" <%=((selectedEmail.equals(email))?" selected":"")%>><%=toHtml(email)%> (<%=toHtml(emailType)%>)</option>
	<%
	}
	}%>
	</select>
       	</td>
       <%}%>
       <td nowrap><%=toHtml(thisContact.getTypeName())%></td>
       	<input type=hidden name="contactemail<%=count%>" value=<%=email%>>
    </tr>
    
  <%
  }
  %>
<br>
<input type=hidden name="finalsubmit" value="false">


<%
}
else {%>
  <tr bgcolor="white">
    <td colspan="3" valign="center">
      No contacts matched query.
    </td>
  </tr>
  <%}%>
</table>

<input type='button' value="Done" onClick="javascript:contactsubmit();">
<input type="button" value="Cancel" onClick="javascript:window.close()">
<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Check All</a>
<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Clear All</a>
<dhv:pagedListControl object="ContactListInfo" showForm="false"/>
<br>
<br>
</form>
<%}
	else {
	%>
	<body OnLoad="javascript:setParentList(recipients);window.close()">
	
	<script>recipients = new Array();</script>
	<%
		Set s = selectedContacts.keySet();
		Iterator i = s.iterator();
		int count = -1;
		while (i.hasNext()) {
			count++;
			Object st = selectedContacts.get(i.next());
			String email = st.toString();
			if(email.startsWith("P:")){
				email = email.substring(2);
			}
			%>
			<script>
				recipients[<%=count%>] = "<%=email%>";
			</script>
			<%	
			}%>
	</body>
	<%}
%>


