<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>
<script language="javascript">
  var thisHeaderId = -1;
  var thisCallId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, headerId, callId) {
    thisHeaderId = headerId;
    thisCallId = callId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCall", "down", 0, 0, 170, getHeight("menuCallTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
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
    <table id="menuCallTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="pipeline-opportunities-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modify
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="forward()">
        <th>
          <img src="images/icons/stock_forward_mail-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Forward
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="pipeline-opportunities-calls-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteCall()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
