function popContactPhoneNumberListSingle(hiddenField) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactPhoneNumberSelector.do?command=List&popup=true&hiddenField='+hiddenField, title,width,height,resize,bars);
}

function popContactPhoneNumberListSingle(contactId, hiddenField) {
  title  = 'Contact_Address';
  width  =  '575';
  height =  '400';
  resize =  'yes';
  bars   =  'yes';
  var posx = (screen.width - width)/2;
  var posy = (screen.height - height)/2;
  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + ',screenX=' + posx + ',screenY=' + posy;
  popURL('ContactPhoneNumberSelector.do?command=List&popup=true&hiddenField='+hiddenField+'&contactId='+contactId, title,width,height,resize,bars);
}

function setContactPhoneNumber(number,type, hiddenField){
  var something = opener.populatePhoneNumber(number,type, hiddenField);
  self.close();
}

