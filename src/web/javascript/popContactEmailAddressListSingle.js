function popContactEmailAddressListSingle() {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactEmailAddressSelector.do?command=List&popup=true', title,width,height,resize,bars);
}

function popContactEmailAddressListSingle(contactId) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactEmailAddressSelector.do?command=List&popup=true&contactId='+contactId, title,width,height,resize,bars);
}

function popContactEmailAddressListSingle(contactId, hiddenFieldId) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactEmailAddressSelector.do?command=List&popup=true&contactId='+contactId+'&hiddenFieldId='+hiddenFieldId, title,width,height,resize,bars);
}

function setContactEmailAddress(email,type){
  var something = opener.populateEmailAddress(email,type);
  self.close();
}

function setContactEmailAddress(email,type, hiddenFieldId){
  var something = opener.populateEmailAddress(email,type, hiddenFieldId);
  self.close();
}

