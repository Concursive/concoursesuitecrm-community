var base = "images/";
var p1 = new Array();
var p2 = new Array();
var lib = new Array();
function loadImages(newLib) {
  if (document.images) {
    lib = new Array(newLib);
    for (i=0;i<lib.length;i++) {
      p1[i] = new Image;
      p1[i].src = base + lib[i] + ".gif";
      p2[i] = new Image;
      p2[i].src = base + lib[i] + "-on.gif";
    }
  }
}
function over(i, c) {
  if (document.images) {
    document.images[lib[i] + c].src = p2[i].src;
  }
}
function out(i, c) {
  if (document.images) {
    document.images[lib[i] + c].src = p1[i].src;
  }
}
