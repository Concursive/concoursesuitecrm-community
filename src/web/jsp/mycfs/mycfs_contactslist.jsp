<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<jsp:useBean id="ContactListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="session"/>
<jsp:useBean id="ProjectListSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>


<%
 if(!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))){
%>

<br>
<center><%= ContactListInfo.getAlphabeticalPageLinks("setFieldSubmit","contactListView") %></center>


<form name="contactListView" method="post" action="/MyCFSInbox.do?command=ContactList">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
<input type=hidden name="letter">
<table width="20%" border="0">
  <tr>
    
    <td align="left">
      <select size="1" name="listView" onChange="javascript:setFieldSubmit('listFilter1','-1','contactListView');">
        <option <%= ContactListInfo.getOptionValue("all") %>>All Contacts</option>
	<option <%= ContactListInfo.getOptionValue("employees") %>>Employees</option>
	<option <%= ContactListInfo.getOptionValue("mycontacts") %>>My Contacts</option>
	<option <%= ContactListInfo.getOptionValue("accountcontacts") %>>Account Contacts</option>
  <option <%= ContactListInfo.getOptionValue("myprojects") %>>My Projects</option>
	</select>
     </td>
    <td>
      <% 
        if(ContactListInfo.getListView().equals("employees")){
          DepartmentList.setSelectSize(1); 
          DepartmentList.setJsEvent("onchange=\"javascript:document.forms[0].submit();\"");
      %>
      <%=DepartmentList.getHtmlSelect("listFilter1",ContactListInfo.getFilterKey("listFilter1")) %>
      <%}
      else if(ContactListInfo.getListView().equals("myprojects")){
         ProjectListSelect.setSelectSize(1);  
         ProjectListSelect.setJsEvent("onchange=\"javascript:document.forms[0].submit();\"");
      %>
      <%=ProjectListSelect.getHtml("listFilter1",ContactListInfo.getFilterKey("listFilter1")) %>
      <%}
      else{%>
        <select size="1" name="temp">
          <option value="0">--None--</option>
        </select>
      <%}%>
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
  <%if(ContactListInfo.getParentFieldType().equalsIgnoreCase("list")){%>  
	<input type=checkbox name="checkcontact<%=count%>" value=<%=thisContact.getId()%><%=((selectedContacts.get(new Integer(thisContact.getId()))!= null)?" checked":"")%> onClick="highlight(this,'<%=User.getBrowserId()%>');">
  <%}
  else{%>
  <%--<input type=checkbox name="checkcontact<%=count%>" value=<%=thisContact.getId()%><%=((selectedContacts.get(new Integer(thisContact.getId()))!= null)?" checked":"")%> onClick="return keepCount('checkcontact','contactListView')">--%>
  <a href="javascript:document.contactListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%=count%>','contactListView');">ADD</a>
  <%}%>
	<input type=hidden name="hiddencontactid<%=count%>" value=<%=thisContact.getId()%>>
  <input type=hidden name="hiddenname<%=count%>" value="<%=toHtml(thisContact.getNameLastFirst())%>">
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
    <input type=hidden name="rowcount" value="0">
    
    <%
    }
    else {%>
      <tr bgcolor="white">
        <td colspan="4" valign="center">
          No contacts matched query.
        </td>
      </tr>
      <%}%>
    </table>
    
    <input type='button' value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','contactListView');">
    <input type="button" value="Cancel" onClick="javascript:window.close()">
    <%if(ContactListInfo.getParentFieldType().equalsIgnoreCase("list")){%>
    <a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Check All</a>
    <a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Clear All</a>
    <%}%>
    <dhv:pagedListControl object="ContactListInfo" showForm="false" resetList="false"/>
    <br>
    <br>
    </form>
    <%}
      else {
      %>
      <body OnLoad="javascript:setParentList(recipientEmails,recipientIds,'<%=ContactListInfo.getParentFormName()%>','<%=ContactListInfo.getParentFieldType()%>');window.close()">
      <script>recipientEmails = new Array();recipientIds = new Array();</script>
      <%
        Set s = selectedContacts.keySet();
        Iterator i = s.iterator();
        int count = -1;
        while (i.hasNext()) {
          count++;
          Object id = i.next();
          Object st = selectedContacts.get(id);
          String email = st.toString();
          if(email.startsWith("P:")){
            email = email.substring(2);
          }
          %>
          <script>
            recipientEmails[<%=count%>] = "<%=email%>";
            recipientIds[<%=count%>] = "<%=id%>";
          </script>
          <%	
          }%>
      </body>
      <%}
    %>


