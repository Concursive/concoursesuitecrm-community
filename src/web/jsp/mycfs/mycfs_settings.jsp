<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="Locale" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZone" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form action='MyCFSSettings.do?command=UpdateSettings' method='post'>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyCFS.do?command=MyProfile">My Settings</a> >
Locale<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="myhomepage-profile-settings-edit">
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
</dhv:permission>
<br>
&nbsp;
<input type="hidden" name="modified" value="<%= User.getModifiedString() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Location Settings</strong>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Start of Day</td>
    <td><input type="text" name="dayStart" value=""></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">End of Day</td>
    <td><input type="text" name="dayEnd" value=""></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Time Zone
    </td>
    <td>
      <% TimeZone.setTypeTimeZone(); %><%= TimeZone.getHtml("timeZone", User.getTimeZone()) %>
    </td>
  </tr>
</table>

<dhv:permission name="myhomepage-profile-settings-edit">
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
</form>
</dhv:permission>
