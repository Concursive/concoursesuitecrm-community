<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(id,customerProductId) {
    thisId = customerProductId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu(id, "down", 0, 0, 170, getHeight(id+"Table"));
    }
    return ypSlideOutMenu.displayMenu(id);
  }
  //Menu link functions
  function customerProduct() {
    if(thisId != -1){
      window.location.href='ProductHistory.do?command=Details&adId=' + thisId;
    } else { 
      alert('No Product found for this Order Item');
    }
  }
</script>
<div id="menuOrderHistoryContainer" class="menu">
  <div id="menuOrderHistoryContent">
    <table id="menuOrderHistoryTable" class="pulldown" width="170">
      <tr>
        <td>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </td>
        <td width="100%">
          <a href="javascript:customerProduct()">View Product</a>
        </td>
      </tr>
    </table>
  </div>
</div>
