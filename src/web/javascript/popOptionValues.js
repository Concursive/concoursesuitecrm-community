function popOptionValues(moduleId, catalogId, optionId, resultId, displayValue) {
	title = 'ProductOptionValues';
	width = '700';
	height = '425';
	resize = 'yes';
	bars = 'yes';
	var posx = (screen.width - width)/2;
	var posy = (screen.width - width)/2;
	var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
  var newwin=window.open('ProductCatalogOptionPricings.do?command=ValueList&moduleId='+moduleId+'&catalogId='+catalogId+'&optionId='+optionId+'&displayValue='+displayValue+'&resultId='+resultId, title, windowParams);
  newwin.focus();
  if (newwin != null) {
    if (newwin.opener == null)
      newwin.opener = self;
  }
}

