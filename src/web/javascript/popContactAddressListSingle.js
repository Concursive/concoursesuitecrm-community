function popContactAddressListSingle() {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactAddressSelector.do?command=List&popup=true', title,width,height,resize,bars);
}

function popContactAddressListSingle(contactId) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactAddressSelector.do?command=List&popup=true&contactId='+contactId, title,width,height,resize,bars);
}

function setContactAddress(line1,line2,line3, line4, city,state,otherState,zip,country,type){
  var something = opener.populateAddress(line1,line2,line3, line4, city,state,otherState,zip,country,type);
  self.close();
}

