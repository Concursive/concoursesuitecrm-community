<%@ page import="java.util.*"%>
<%@ include file="initPage.jsp" %>
<jsp:useBean id="Task" class="com.darkhorseventures.cfsbase.Task" scope="request"/>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
 
<br>
<form name="addTask" action="/MyTasks.do?command=Insert&id=<%=Task.getId()%>&auto-populate=true" method="post" onSubmit="return validateTask();">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>New CFS Task</strong>
    </td>
  </tr>
  
  <tr class="containerBody">
  <td nowrap class="formLabel">
      Title
  </td>
  
  <td>
      <input type=text name="description" value="<%=toHtmlValue(Task.getDescription())%>" size=50>
      <font color="red">*</font>
  </td>
  
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Due Date
    </td>
    <td>
      <input type=text size=10 name="dueDate" value="<%=(Task.getDueDate()==null)?"":Task.getDueDateString()%>">
      <a href="javascript:popCalendar('addTask', 'dueDate');">Date</a> (mm/dd/yyyy)
      </td> 
  </tr>


  <tr class="containerBody">
    <td class="formLabel">Priority</td>
    <td>
      <select name="priority">
        <option value="1" <%=(Task.getPriority()==1)?" selected":""%>>1</option>
        <option value="2" <%=(Task.getPriority()==2)?" selected":""%>>2</option>
        <option value="3" <%=(Task.getPriority()==3)?" selected":""%>>3</option>
        <option value="4" <%=(Task.getPriority()==4)?" selected":""%>>4</option>
        <option value="5" <%=(Task.getPriority()==5)?" selected":""%>>5</option>
      </select>
      </td>
  </tr>
  
  
  <tr class="containerBody"> 
   <td class="formLabel">Status</td>
   <td>
   <table cellpadding=3 cellspacing=0 border=0>
   <tr>
    <td><input type=checkbox name="chk1" value="true" onclick="javascript:setField('complete',document.addTask.chk1.checked,'addTask');" <%=Task.getComplete()?" checked":""%>></td>
    <input type=hidden name="complete" value="<%=Task.getComplete()?"1":"0"%>">
    <td>Done</td>
   </tr>
   </table>
   </td>
  </tr>
  
  <tr class="containerBody"> 
   <td class="formLabel">Sharing</td>
   <td>
   <table cellpadding=3 cellspacing=0 border=0>
   <tr>
    <td>
    <input type=checkbox name="chk2"  onclick="javascript:setField('sharing',document.addTask.chk2.checked,'addTask');" <%=(Task.getSharing()==1)?" checked":""%>>
    <input type=hidden name="sharing" value="<%=Task.getSharing()%>">
    </td>
      <td>personal</td>
   </tr>
   </table>
   </td>
  </tr>
  
  
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Assign To
    </td>
    <td>
      <table>
        <tr>
          <td>
            <div id="changeowner"><%=Task.getOwnerName().equals("")?toHtml(User.getNameLast())+" "+ toHtml(User.getNameFirst()):""%><%=Task.getOwnerName()%></div>
          </td>
          <td>
            <input type=hidden name="owner" value="<%=(Task.getOwner() == -1)?User.getUserRecord().getContact().getId():Task.getOwner()%>"><a href="javascript:popURLReturn('/MyCFSInbox.do?command=ContactList&popup=true&flushtemplist=true&parentFieldType=single&parentFormName=addTask', 'MyTasks.do?command=New', 'Inbox_message','700','450','yes','no');">Change Owner</a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  <tr class="containerBody">
  <td nowrap class="formLabel" valign="top">
     Estimated LOE
    </td>
    <td>
      <input type=text name="estimatedLOE" value="<%=(Task.getEstimatedLOE()==-1)?"":(new Integer(Task.getEstimatedLOE())).toString()%>">
     </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">Notes</td>
    <td width=100%>
      <textarea name="notes" style="width:50%;" rows=5 value="body"><%=Task.getNotes()%></textarea>
    </td>
  </tr>

  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      Link Contact
    </td>
    <td>
      <table>
        <tr>
          <td>
            <div id="changecontact"><%=Task.getContactName().equals("")?"None":Task.getContactName()%></div>
          </td>
          <td>
            <input type=hidden name="contact" value="<%=(Task.getContactId() == -1)?-1:Task.getContactId()%>"><a href="javascript:popURLReturn('/MyCFSInbox.do?command=ContactList&popup=true&flushtemplist=true&parentFieldType=contactsingle&parentFormName=addTask', 'MyTasks.do?command=New', 'Inbox_message','700','450','yes','no');">Change Contact</a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<br>
<input type="submit" value="Insert">
<input type="button" value="Cancel" onClick="javascript:window.location.href='MyTasks.do?command=ListTasks';">
</form>


