<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>
<script language="javascript">
  var thisHeaderId = -1;
  var thisCallId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, headerId, callId) {
    thisHeaderId = headerId;
    thisCallId = callId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCall", "down", 0, 0, 170, getHeight("menuCallTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  
  //Menu link functions
  function details() {
    window.location.href = 'LeadsCalls.do?command=Details&id=' + thisCallId + '&headerId=' + thisHeaderId + '<%= addLinkParams(request, "viewSource") %>';
  }
  
  function modify() {
    window.location.href = 'LeadsCalls.do?command=Modify&id=' + thisCallId + '&headerId=' + thisHeaderId + '&return=list' + '<%= addLinkParams(request, "viewSource") %>';
  }
  
  function forward() {
    window.location.href = 'LeadsCallsForward.do?command=ForwardCall&forwardType=<%= Constants.PIPELINE_CALLS %>&headerId=' + thisHeaderId + '&id=' + thisCallId + '&return=list' + '<%= addLinkParams(request, "viewSource") %>';
  }
  
  function deleteCall() {
    confirmDelete('LeadsCalls.do?command=Delete&id=' + thisCallId + '&headerId=' + thisHeaderId + '<%= addLinkParams(request, "viewSource") %>');
  }
  
</script>
<div id="menuCallContainer" class="menu">
  <div id="menuCallContent">
    <table id="menuCallTable" class="pulldown" width="170">
      <dhv:permission name="pipeline-opportunities-calls-view">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit">
      <tr>
        <td>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:modify()">Modify</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view">
      <tr>
        <td>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:forward()">Forward</a>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete">
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:deleteCall()">Delete</a>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
