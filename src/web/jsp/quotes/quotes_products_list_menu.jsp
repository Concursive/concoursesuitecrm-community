<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisItemId = -1;
  var thisQuoteId = -1;
  var thisOrgId = -1;
  var thisLocation = '';
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, quoteId, itemId, location, otherId) {
    thisOrgId = otherId;
    thisItemId = itemId;
    thisQuoteId = quoteId;
    thisLocation = location;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuQuoteProduct", "down", 0, 0, 170, getHeight("menuQuoteProductTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href='QuotesProducts.do?command=Details&itemId=' + thisItemId;
  }

  function modify() {
    popURLReturn('QuotesProducts.do?command=Modify&quoteId='+thisQuoteId+'&quoteProductId=' + thisItemId+'&popup=true','Quotes.do?command=Details&quoteId='+thisQuoteId,  'Delete_Quote_Product','450','500','yes','yes');
  }

  function clone() {
    if(confirm(label("confirm.quoteproductclone","Are you sure you want to clone the selected quote item?"))) {
      popURLReturn('QuotesProducts.do?command=Clone&quoteId='+thisQuoteId+'&quoteProductId=' + thisItemId+'&popup=true','Quotes.do?command=Details&quoteId='+thisQuoteId,  'Delete_Quote_Product','450','500','yes','yes');
    }
  }

  function deleteQuoteProduct() {
    if (confirm(label("confirm.delete.item","Are you sure you want to remove this item?"))) {
      if(thisLocation == 'quotes') {
        scrollReload('Quotes.do?command=RemoveProduct&quoteId=' + thisQuoteId+ '&productId='+thisItemId);
      } else if (thisLocation == 'accountsQuotes') {
        scrollReload('AccountQuotes.do?command=RemoveProduct&quoteId=' + thisQuoteId+ '&productId='+thisItemId+'&orgId='+thisOrgId);
      } else if (thisLocation == 'opportunitiesQuotes') {
        scrollReload('LeadsQuotes.do?command=RemoveProduct&quoteId=' + thisQuoteId+ '&productId='+thisItemId+'&orgId='+thisOrgId+'<%= addLinkParams(request, "viewSource") %>');
      } else if (thisLocation == 'accountsContactsOppsQuotes') {
        scrollReload('AccountContactsOppQuotes.do?command=RemoveProduct&quoteId=' + thisQuoteId+ '&productId='+thisItemId+'&orgId='+thisOrgId);
      }else {
        alert('Programming Error: the location/module has to be set correctly');
      }
    }
  }
</script>
<div id="menuQuoteProductContainer" class="menu">
  <div id="menuQuoteProductContent">
    <table id="menuQuoteProductTable" class="pulldown" width="80" cellspacing="0">
      <dhv:permission name="quotes-quotes-edit,accounts-quotes-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.modify">Modify</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="quotes-quotes-edit,accounts-quotes-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="clone();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.clone">Clone</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="quotes-quotes-edit,accounts-quotes-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteQuoteProduct();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.remove">Remove</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
