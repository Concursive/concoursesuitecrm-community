function getHeight(tbId) {
  this.ie = document.all ? 1 : 0;
  this.ns4 = document.layers ? 1 : 0;
  this.dom = document.getElementById ? 1 : 0;
  var tbObj = this.dom ? document.getElementById(tbId) : this.ie ? document.all[tbId] : document.layers[tbId];
  return (!this.ns4 ? tbObj.offsetHeight : tbObj.height ? tbObj.height : tbObj.clip.height);
}