<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisQuoteId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id, quoteId) {
    thisQuoteId = quoteId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuQuote", "down", 0, 0, 170, getHeight("menuQuoteTable"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function details() {
    window.location.href='Quotes.do?command=Details&quoteId=' + thisQuoteId;
  }
  
  function modify() {
  }
  
  function deleteQuote() {
    popURLReturn('Quotes.do?command=ConfirmDelete&quoteId=' + thisQuoteId+ '&popup=true','Quotes.do?command=Search', 'Delete_Quote','330','200','yes','no');
  }
</script>
<div id="menuQuoteContainer" class="menu">
  <div id="menuQuoteContent">
    <table id="menuQuoteTable" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:details()">View Details</a>
        </td>
      </tr>
      <tr>
        <td>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td>
          <a href="javascript:deleteQuote()">Delete</a>
        </td>
      </tr>
    </table>
  </div>
</div>
