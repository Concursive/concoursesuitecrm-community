<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var thisQuoteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, contactId) {
    thisOrgId = orgId;
    thisQuoteId = contactId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuQuote", "down", 0, 0, 170, getHeight("menuQuoteTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href='AccountQuotes.do?command=Details&quoteId=' + thisQuoteId +'&orgId='+ thisOrgId;
  }

  function deleteQuote() {
    popURLReturn('AccountQuotes.do?command=ConfirmDelete&quoteId=' + thisQuoteId+ '&popup=true','AccountQuotes.do?command=View', 'Delete_Quote','330','200','yes','no');
  }
</script>
<div id="menuQuoteContainer" class="menu">
  <div id="menuQuoteContent">
    <table id="menuQuoteTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          View Details
        </td>
      </tr>
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteQuote()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Delete
        </td>
      </tr>
    </table>
  </div>
</div>
