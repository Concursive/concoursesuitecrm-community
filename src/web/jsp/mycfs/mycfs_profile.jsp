<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
My Settings<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Make a selection</strong>
    </td>
  </tr>
  <dhv:permission name="myhomepage-profile-personal-view">
  <tr>
    <td><a href="MyCFSProfile.do?command=MyCFSProfile">Update my personal information</a></td>
  </tr>
  </dhv:permission>
  <dhv:permission name="myhomepage-profile-settings-view">
  <tr>
    <td><a href="MyCFSSettings.do?command=MyCFSSettings">Configure my location</a></td>
  </tr>
  </dhv:permission>
  <dhv:permission name="myhomepage-profile-password-edit">
  <tr>
    <td><a href="MyCFSPassword.do?command=MyCFSPassword">Change my password</a></td>
  </tr>
  </dhv:permission>
</table>
