<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>

<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=ListGlobalParams"> Global Parameters</a> >
Modify Timeout<br>
<hr color="#BFBFBB" noshade>
The session timeout is the time in which a user will automatically be logged out if the specified period of inactivity is reached.<br>
&nbsp;<br>
<form name="modifyTimeout" action="Admin.do?command=UpdateTimeout" method="post"> 
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
      <td colspan="2">
        <strong>Modify Timeout</strong>
      </td>
    </tr>
    <tr class="containerBody">
      <td align="center" width="12">
        Timeout 
      </td>
      <%  String[] timeouts = { "5", "10", "15", "30", "45", "60", "90"} ;
          String currentTimeout = request.getParameter("timeout");
      %>
      <td align="left">
         <select size="1" name="timeout">
          <% for(int i = 0 ; i < timeouts.length; i++){ %>
            <option value="<%=timeouts[i]%>" <%=currentTimeout.equals(timeouts[i])?" selected":""%>><%=timeouts[i]%></option>
          <%}%>
         </select> minutes
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Update">
  &nbsp;<input type="button" value="Cancel" onClick="javascript:window.location.href='Admin.do?command=ListGlobalParams';">
 </form>
</dhv:permission>
