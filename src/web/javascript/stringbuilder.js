//<script>
/*
 * This script was created by Erik Arvidsson (erik@eae.net)
 * for WebFX (http://webfx.eae.net)
 * Copyright 2001
 * 
 * For usage see license at http://webfx.eae.net/license.html	
 *
 * Created:		2000-10-02
 * Updates:		2000-10-05	Added a cache of the string so that it does not need to be
 *                          regenerated every time in toString() */
 function StringBuilder(sString) {
	
	// public
	this.length = 0;
	
	this.append = function (sString) {
		// append argument
		this.length += (this._parts[this._current++] = String(sString)).length;
		
		// reset cache
		this._string = null;
		return this;
	};
	
	this.toString = function () {
		if (this._string != null)
			return this._string;
		
		return this._string = this._parts.join("");
	};

	// private
	this._current	= 0;
	this._parts		= [];
	this._string	= null;	// used to cache the string
	
	// init
	if (sString != null)
		this.append(sString);
}
