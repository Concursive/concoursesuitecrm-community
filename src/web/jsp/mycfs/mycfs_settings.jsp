<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="Locale" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZone" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form action="MyCFSSettings.do?command=UpdateSettings" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
<a href="MyCFS.do?command=MyProfile">Settings</a> >
Location
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-profile-settings-edit,myhomepage-profile-view">
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
</dhv:permission>
<br>
&nbsp;
<input type="hidden" name="modified" value="<%= User.getModifiedString() %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Location Settings</strong>
    </th>
  </tr>
<%--
  <tr>
    <td nowrap class="formLabel">Start of Day</td>
    <td><input type="text" name="dayStart" value=""></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">End of Day</td>
    <td><input type="text" name="dayEnd" value=""></td>
  </tr>
--%>
  <tr>
    <td nowrap class="formLabel">
      Time Zone
    </td>
    <td>
      <%= TimeZone.getSelect("timeZone", User.getTimeZone()).getHtml() %>
    </td>
  </tr>
</table>
<dhv:permission name="myhomepage-profile-settings-edit,myhomepage-profile-view">
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
</form>
</dhv:permission>
