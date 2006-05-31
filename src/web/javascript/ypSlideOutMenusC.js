/*****************************************************
* ypSlideOutMenu, -youngpup--, 3/04/2001 free
* create menus for ns4, ns6, mozilla, opera, ie4, ie5 
* modified to handle dynamic menus, but had to turn off
* sliding effect, now appear under mouse
* $Id$
*****************************************************/
ypSlideOutMenu.Registry = []
ypSlideOutMenu.aniLen = 250
ypSlideOutMenu.hideDelay = 500
ypSlideOutMenu.minCPUResolution = 10
//rollover
function cmOver(thisRow) {
  thisRow.className='hover';
  for (i=0;i<thisRow.childNodes.length;i++) {
    if (thisRow.childNodes[i].tagName == 'TH') {
      thisRow.childNodes[i].className='hover';
    }
    if (thisRow.childNodes[i].tagName == 'TD') {
      thisRow.childNodes[i].className='hover';
    }
  }
}
function cmOut(thisRow) {
  thisRow.className='';
  for (i=0;i<thisRow.childNodes.length;i++) {
    if (thisRow.childNodes[i].tagName == 'TH') {
      thisRow.childNodes[i].className='';
    }
    if (thisRow.childNodes[i].tagName == 'TD') {
      thisRow.childNodes[i].className='';
    }
  }
}
//utils
function getHeight(tbId) {
  this.ie = document.all ? 1 : 0;
  this.ns4 = document.layers ? 1 : 0;
  this.dom = document.getElementById ? 1 : 0;
  var tbObj = this.dom ? document.getElementById(tbId) : this.ie ? document.all[tbId] : document.layers[tbId];
  return (!this.ns4 ? tbObj.offsetHeight : tbObj.height ? tbObj.height : tbObj.clip.height);
}
function hideMenu(id) {
  if (menu_init) {
    ypSlideOutMenu.hideMenu(id);
  }
}
function getPageOffsetTop(el) {
  var y;
  y = el.offsetTop;
  if (el.offsetParent != null)
    y += getPageOffsetTop(el.offsetParent);
  return y;
}
function getPageOffsetLeft(el) {
  var x;
  x = el.offsetLeft;
  if (el.offsetParent != null)
    x += getPageOffsetLeft(el.offsetParent);
  return x;
}
function moveMenu(mid) {
  var x = document.getElementById(mid + 'Container');
  x.style.left = xMousePos - 5;
  x.style.top = yMousePos - 5;
}
function moveDropMenu(mid, id) {
  var mel = document.getElementById(mid + 'Container');
  var el = document.getElementById(id);
  var x = getPageOffsetLeft(el);
  var y = getPageOffsetTop(el) + el.offsetHeight;
  var c = 0;
  var e = 0;
  if (document.all) {
    c = document.body.clientHeight;
    e = document.body.clientWidth;
  } else {
    c = window.innerHeight;
    e = window.innerWidth;
  }
  var d = null;
  if (document.documentElement && document.documentElement.scrollTop) {
    d = document.documentElement;
  } else {
    d = document.body;
  }
  if (window.scrollY) {
    c = c + window.scrollY;
    e = e + window.scrollX;
  } else {
    c = c + d.scrollTop;
    e = e + d.scrollLeft;
  }
  // adjust if offscreen (y)
  var height = mel.offsetHeight;
  if (y + height > c) {
    y = y - ((y + height) - c) - 10;
  }
  // adjust if offscreen (x)
  var width = mel.offsetWidth;
  if (x + width > e) {
    x = x - ((x + width) - e) - 10;
  }
  mel.style.left = x;
  mel.style.top = y;
}
// constructor
function ypSlideOutMenu(id, dir, left, top, width, height)
{
this.ie = document.all ? 1 : 0
this.ns4 = document.layers ? 1 : 0
this.dom = document.getElementById ? 1 : 0
if (this.ie || this.ns4 || this.dom) {
this.id = id
this.dir = dir
this.orientation = dir == "left" || dir == "right" ? "h" : "v"
this.dirType = dir == "right" || dir == "down" ? "-" : "+"
this.dim = this.orientation == "h" ? width : height
this.hideTimer = false
this.aniTimer = false
this.open = false
this.over = false
this.startTime = 0
this.gRef = "ypSlideOutMenu_"+id
eval(this.gRef+"=this")
ypSlideOutMenu.Registry[id] = this
var d = document
this.load()
}
}
ypSlideOutMenu.prototype.load = function() {
var d = document
var lyrId1 = this.id + "Container"
var lyrId2 = this.id + "Content"
var obj1 = this.dom ? d.getElementById(lyrId1) : this.ie ? d.all[lyrId1] : d.layers[lyrId1]
if (obj1) var obj2 = this.ns4 ? obj1.layers[lyrId2] : this.ie ? d.all[lyrId2] : d.getElementById(lyrId2)
var temp
if (!obj1 || !obj2) window.setTimeout(this.gRef + ".load()", 100)
else {
this.container = obj1
this.menu = obj2
this.style = this.ns4 ? this.menu : this.menu.style
this.homePos = eval("0" + this.dirType + this.dim)
this.outPos = 0
this.accelConst = (this.outPos - this.homePos) / ypSlideOutMenu.aniLen / ypSlideOutMenu.aniLen 
// set event handlers.
if (this.ns4) this.menu.captureEvents(Event.MOUSEOVER | Event.MOUSEOUT);
this.menu.onmouseover = new Function("ypSlideOutMenu.showMenu('" + this.id + "')")
this.menu.onmouseout = new Function("ypSlideOutMenu.hideMenu('" + this.id + "')")
//set initial state
this.endSlide()
}
}
ypSlideOutMenu.displayMenu = function(id)
{
moveMenu(id);
this.showMenu(id);
}
ypSlideOutMenu.displayDropMenu = function(id, id2)
{
moveDropMenu(id, id2);
this.showMenu(id);
}
ypSlideOutMenu.showMenu = function(id)
{
var reg = ypSlideOutMenu.Registry
var obj = ypSlideOutMenu.Registry[id]
if (obj.container) {
obj.over = true
for (menu in reg) if (id != menu) ypSlideOutMenu.hide(menu)
if (obj.hideTimer) { reg[id].hideTimer = window.clearTimeout(reg[id].hideTimer) }
if (!obj.open && !obj.aniTimer) reg[id].startSlide(true)
}
}
ypSlideOutMenu.hideMenu = function(id)
{
var obj = ypSlideOutMenu.Registry[id]
if (obj.container) {
ypSlideOutMenu.hide(id)
if (obj.hideTimer) window.clearTimeout(obj.hideTimer)
}
}
ypSlideOutMenu.hideAll = function()
{
var reg = ypSlideOutMenu.Registry
for (menu in reg) {
ypSlideOutMenu.hide(menu);
if (menu.hideTimer) window.clearTimeout(menu.hideTimer);
}
}
ypSlideOutMenu.hide = function(id)
{
var obj = ypSlideOutMenu.Registry[id]
obj.over = false
if (obj.hideTimer) window.clearTimeout(obj.hideTimer)
obj.hideTimer = 0
if (obj.open && !obj.aniTimer) obj.startSlide(false)
}
ypSlideOutMenu.prototype.startSlide = function(open) {
this[open ? "onactivate" : "ondeactivate"]()
this.open = open
if (open) this.setVisibility(true)
this.startTime = (new Date()).getTime() 
this.aniTimer = window.setInterval(this.gRef + ".slide()", ypSlideOutMenu.minCPUResolution)
}
ypSlideOutMenu.prototype.slide = function() {
var elapsed = (new Date()).getTime() - this.startTime
if (elapsed > ypSlideOutMenu.aniLen) this.endSlide()
else {
var d = Math.round(Math.pow(ypSlideOutMenu.aniLen-elapsed, 2) * this.accelConst)
if (this.open && this.dirType == "-") d = -d
else if (this.open && this.dirType == "+") d = -d
else if (!this.open && this.dirType == "-") d = -this.dim + d
else d = this.dim + d
this.moveTo(d)
}
}
ypSlideOutMenu.prototype.endSlide = function() {
this.aniTimer = window.clearTimeout(this.aniTimer)
this.moveTo(this.open ? this.outPos : this.homePos)
if (!this.open) this.setVisibility(false)
if ((this.open && !this.over) || (!this.open && this.over)) {
this.startSlide(this.over)
}
}
ypSlideOutMenu.prototype.setVisibility = function(bShow) { 
var s = this.ns4 ? this.container : this.container.style
s.visibility = bShow ? "visible" : "hidden"
}
ypSlideOutMenu.prototype.moveTo = function(p) { 
this.style[this.orientation == "h" ? "left" : "top"] = this.ns4 ? p : p + "px"
}
ypSlideOutMenu.prototype.getPos = function(c) {
return parseInt(this.style[c])
}
ypSlideOutMenu.prototype.onactivate = function() { }
ypSlideOutMenu.prototype.ondeactivate = function() { }