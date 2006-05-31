// ImageLibrary Plugin for TinyMCE
// Author: Matt Rajkowski, Team Elements LLC
//
// $Id$

function ImageLibrary() {
	this.inTinyMCE = false;
}

ImageLibrary.prototype.getScriptPath = function() {
	var elements = document.getElementsByTagName('script');
	for (var i=0; i<elements.length; i++) {
		if (elements[i].src && elements[i].src.indexOf("image-library.js") != -1) {
			var src = elements[i].src;
			src = src.substring(0, src.lastIndexOf('/'));
			return src;
		}
	}
	return null;
}

ImageLibrary.prototype.open = function(form_name, element_names, file_url, js) {
	// NOTE: imageLibraryURL MUST be specified in the page that includes the editor
  var url = this.getScriptPath() + imageLibraryURL;
	var isMSIE = (navigator.appName == "Microsoft Internet Explorer");

	if (typeof form_name != "undefined")
		url += "&formname=" + escape(form_name);

	if (typeof element_names != "undefined") {
		url += "&elementnames=" + escape(element_names);

		// Poll url from field
		if (typeof file_url == "undefined") {
			if (element_names.indexOf(',') == -1)
				file_url = document.forms[form_name].elements[element_names].value;
		}
	}

	if (typeof file_url != "undefined")
		url += "&url=" + escape(file_url);

	if (typeof js != "undefined")
		url += "&js=" + escape(js);

	var width = 640;
	var height = 480;
	var x = parseInt(screen.width / 2.0) - (width / 2.0);
	var y = parseInt(screen.height / 2.0) - (height / 2.0);

	if (isMSIE) {
		// Pesky MSIE + XP SP2
		width += 15;
		height += 35;
	}

	var win = window.open(url, "ImageLibrary", "top=" + y + ",left=" + x + ",scrollbars=yes,width=" + width + ",height=" + height + ",resizable=yes");

	try {
		win.focus();
	} catch (e) {
	}
}

ImageLibrary.prototype.imageLibraryCallBack = function(field_name, url, type, win) {
	// Convert URL to absolute
	url = tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings['base_href'], url);

	// Save away
	this.field = field_name;
	this.win = win;
	this.inTinyMCE = true;

	// Open browser
	this.open(0, field_name, url, "ImageLibrary.insertImage");
}

ImageLibrary.prototype.insertImage = function(url) {
	if (this.inTinyMCE) {
		var url;

		// Set URL
		this.win.document.forms[0].elements[this.field].value = url;

		try {
			this.win.document.forms[0].elements[this.field].onchange();
		} catch (e) {
			// Skip it
		}
	}
}

// Global instance
var ImageLibrary = new ImageLibrary();

