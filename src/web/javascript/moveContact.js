function moveContact(args) {
	if (args.length == 2) {
		var msg = "Are you sure you want to move the contact to " + args[1] + "?" ;
		return msg;
	} else if (args.length == 4) {
		// args[0] = function name; args[1] = contactId; args[2] = old orgId; args[3] = new orgId
		var params = "&id=" + args[1] + "&orgId=" + args[2] + "&neworgId=" + args[3] ;
		window.location.href = "Contacts.do?command=Move" + params ;
	}
}

function check(functionName, orgId, itemId, params, isPrimary) {
	if(isPrimary == "true") {
		title  = 'Accounts';
		width  =  '300';
		height =  '150';
		resize =  'no';
		bars   =  'no';
		var posx = (screen.width - width)/2;
		var posy = (screen.height - height)/2;
		var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
		url = 'Contacts.do?command=Move';
		url += '&id=' + itemId;
		var newwin=window.open(url, title, windowParams);
		newwin.focus();
		if (newwin != null) {
			if (newwin.opener == null)
				newwin.opener = self;
		}
	} else {
		popAccountsListSingleAlert(functionName, orgId, itemId, params);
	}
}
