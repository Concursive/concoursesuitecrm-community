<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*"%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="Task" class="org.aspcfs.modules.tasks.base.Task" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EstimatedLOETypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" type="text/javascript" src="/javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/tasks.js"></script>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<body onLoad="javascript:document.forms[0].description.focus();">
<form name="addTask" action="MyTasks.do?command=<%=Task.getId()!=-1?"Update":"Insert"%>&id=<%=Task.getId()%>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post" onSubmit="return validateTask();">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate exp="<%= !popUp %>">
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyTasks.do?command=ListTasks">My Tasks</a> >
<%= Task.getId()==-1?"Add":"Update" %> a Task <br>
<hr color="#BFBFBB" noshade>
</dhv:evaluate>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="button" value="Cancel" onClick="<%=popUp?"javascript:window.close();":"javascript:window.location.href='MyTasks.do?command=ListTasks';"%>"><br>
<%= showError(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>CFS Task</strong>
    </td>
  </tr>
  
  <tr class="containerBody">
  <td nowrap class="formLabel">
      Description
  </td>
  
  <td>
      <input type="text" name="description" value="<%= toHtmlValue(Task.getDescription()) %>" size="50" maxlength="80">
      <font color="red">*</font> <%= showAttribute(request, "descriptionError") %>
  </td>
  
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Due Date
    </td>
    <td>
      <input type=text size=10 name="dueDate" value="<%=(Task.getDueDate()==null)?"":Task.getDueDateString()%>">
      <a href="javascript:popCalendar('addTask', 'dueDate');">Date</a> (mm/dd/yyyy)
      <%= showAttribute(request, "dueDateError") %>
      </td> 
  </tr>


  <tr class="containerBody">
    <td class="formLabel">Priority</td>
    <td>
      <%=PriorityList.getHtmlSelect("priority",Task.getPriority()) %>
      </td>
  </tr>
  
  
  <tr class="containerBody"> 
   <td class="formLabel">Status</td>
   <td>
   <table cellpadding=3 cellspacing=0 border=0>
   <tr>
    <td><input type="checkbox" name="chk1" value="true" onclick="javascript:setField('complete',document.addTask.chk1.checked,'addTask');" <%=Task.getComplete()?" checked":""%>></td>
    <input type="hidden" name="complete" value="<%=Task.getComplete()?"1":"0"%>">
    <input type="hidden" name="modified" value="<%=Task.getModified()%>">
    <td>Complete</td>
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
            <div id="changeowner"><%= Task.getOwnerName()==null?toHtml(User.getUserRecord().getContact().getNameLastFirst()):""%><%= Task.getOwnerName()!=null ? Task.getOwnerName():"" %>
            <dhv:evaluate exp="<%=!(Task.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
            </div>
          </td>
          <td>
            <input type="hidden" name="ownerContactId" id="ownerid" value="<%= Task.getOwner() == -1 ? User.getUserRecord().getContactId() : Task.getOwnerContactId() %>">
            &nbsp;<a href="javascript:popContactsListSingle('ownerid','changeowner');">Change Owner</a>
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
      <input type="text" size="4" name="estimatedLOE" value="<%= Task.getEstimatedLOEValue() %>">
      &nbsp;<%=EstimatedLOETypeList.getHtmlSelect("estimatedLOEType",Task.getEstimatedLOEType()) %>
     </td>
     
  </tr>
  
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">Notes</td>
    <td width=100%>
      <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(Task.getNotes()) %></TEXTAREA>
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
            <div id="changecontact"><%=Task.getContactName()!=null?Task.getContactName():"None"%></div>
          </td>
          <td>
            <input type="hidden" name="contact" id="contactid" value="<%=(Task.getContactId() == -1)?-1:Task.getContactId()%>">
            &nbsp;<a href="javascript:popContactsListSingle('contactid','changecontact','true');">Change Contact</a>
          </td>
          <td>
            <a href="javascript:document.addTask.contact.value='-1';javascript:changeDivContent('changecontact','None');">Clear Contact</a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="<%= Task.getId()==-1?"Save":"Update" %>">
<input type="hidden" name="return" value="<%= request.getParameter("return") %>">
<input type="button" value="Cancel" onClick="<%=popUp?"javascript:window.close();":"javascript:window.location.href='MyTasks.do?command=ListTasks';"%>">
</form>
</body>

