//Expects an iframe to query server to populate new lists
//<iframe src='empty.html' id='server_commands' name='server_commands' style='visibility:hidden' height=0></iframe>

var item_machine;
var taxrates = new Array(1);
function page_unload() {
if (window.ischanged)
return('You have not yet submitted this transaction.') }
function page_init() {
window.warndupe = false;
window.isvalid = true;
window.ischanged = false;
window.isinited = false;
window.dateformat ="MM/DD/YYYY";
window.weekstart =1;
var item_elems = new Array('itemid','itemtype','quantity','description','price_display','price','rate','amount','taxable','taxableamt','custcol1','custcol2','custcol3','onhand','reorder','noprint');
var item_elem_types = new Array('select','text','float','text','text', 'slaveselect','rate','currency','checkbox','text','integer','text','text','text','text','text');
var item_elem_required = new Array(true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false);
var item_table_labels = new Array('Item','','Quantity','Description','Price Level','','Unit Price','Amount','Tax','','Serves','Unit of Measure','Preparation','','','');
item_machine = new Machine('item', item_elems, item_elem_types, item_elem_required, document.forms['main_form'],document.forms['item_form'],document.getElementById('item_splits'), item_table_labels,true);
item_machine.buildtable();
item_machine.recalc = Item_Machine_Recalc;
item_machine.validateline = Item_Machine_ValidateLine;
item_machine.validatedelete = Item_Machine_ValidateDelete;
item_machine.postprocessline = Item_Machine_PostProcessLine;
item_machine.syncQtyReceived = Item_Machine_Sync_QtyReceived;

page_init2();
page_init3();
page_init4();
window.isinited = true;
window.status = '';
window.warndupe = false;
var base_t = GetCookie('base_t');
if (base_t != 0) {
document.forms[0].elements['elap_t'].value = new Date().getTime() - base_t;
document.cookie = 'base_t=0; path=/';
}
}
function restore_splits() {
document.forms[0].elements['nextitemidx'].value='1';
document.forms[0].elements['itemdata'].value='';
item_machine.clearline();
item_machine.buildtable();
}
function clear_splits() {
document.forms[0].elements['nextitemidx'].value='1';
document.forms[0].elements['itemdata'].value='';
item_machine.clearline(true);
item_machine.buildtable();
synctotal();
}
function page_reset() {
document.forms[0].reset();
page_init3();
var savechanged = window.ischanged;
restore_splits();
window.ischanged = savechanged;
window.isvalid = true;
}
function synctotal() {
var total = 0.0;
var haslines = false;
var tax_total = 0.0;
var taxrate = document.forms[0].elements['taxrate'].value;
var dotax = document.forms[0].elements['taxable'].checked;
var item_total = 0.0;
var linecount = document.forms[0].elements['nextitemidx'].value-1, numapplied=0;
for (var i=1; i <= linecount; i++) {
var linetype = getEncodedValue('item',i,'itemtype');
if (linetype != 'Subtotal' && linetype != 'EndGroup' && linetype != 'Description' && linetype != 'Group') {
  var amount = getEncodedValue('item',i,'amount');
amount = amount.replace(/,/g,"");
  if (amount.length) {
  amount = (Math.floor(parseFloat(amount)*100+0.5)/100);
  item_total += amount;  //  WAS HERE
  if (dotax && getEncodedValue('item',i,'taxable') == 'T') {
    var basis = parseFloat(getEncodedValue('item',i,'taxableamt'));
    if (isNaN(basis)) basis = amount;
    var tax = parseFloat(basis)*(taxrate.length  ? parseFloat(taxrate)/100.0 : 0.0);
    tax_total += tax; }
  haslines = true;
  numapplied++;
  }
}
}
item_total =(Math.floor(item_total*100+0.5)/100);
total += item_total;
if (dotax) {
tax_total = Math.floor(parseFloat(tax_total)*100+0.5)/100;
total += tax_total
}
document.forms[0].elements['taxtotal'].value = dotax ? format_currency(tax_total) : '';
document.forms[0].elements['total'].value = format_currency(total);
document.forms[0].elements['haslines'].value = haslines ? 'T' : 'F';
}

function save_record() {
if (!window.isinited) return false;
var form=document.forms[0];
var labels = '';
var val;
val=getSelectValue(document.forms['main_form'].elements['entity'])
if (val.length==0 || val=='-1')
labels+=(labels.length?',':'')+'Customer:Job'
if (isempty(document.forms['main_form'].elements['tran_date'],'Date')) 
labels+=(labels.length?',':'')+'Date'
if (labels.length>0) {
alert('Please enter ' + ((labels.indexOf(',') != -1) ? 'values' : 'a value') + ' for ' + labels);
return false;
}
if (form.elements['haslines'].value=='F') {
alert('You must enter at least one line item for this transaction.');
return false;
}
form.elements['taxtotal_send'].value=form.elements['taxtotal'].value
form.elements['total_send'].value=form.elements['total'].value
if (document.forms[0].elements['to_be_emailed'].checked) {
if (document.forms[0].elements['email'].value.length == 0) {alert('Please specify an e-mail address to send to'); return false; } }
if (document.forms[0].elements['taxable'].checked) {
if (document.forms[0].elements['taxitem'].selectedIndex == 0 || document.forms[0].elements['taxrate'].value.length == 0) {
alert('Please specify a tax item and tax rate for this customer'); return false; }
}
if (window.warndupe) {
if (confirm('Sales Order # '+document.forms[0].elements['refnum'].value+' is already used.  Click OK to replace with the next available number'))
document.forms[0].elements['refnum'].value=document.forms[0].elements['nextrefnum'].value; window.warndupe = false; return false; }
window.ischanged=false;
return true;
}
function delete_record(url) {
var verb = (url == 'void.jsp' ? 'void' : 'delete');
var prompt = 'Are you sure you want to ' + verb+ ' this transaction?'
if (document.forms[0].linked.value == 'T')
prompt = 'There are other transactions linked to this one.  Do you really want to ' + verb + ' it?';
if (confirm(prompt)) {
if (url == 'void.jsp'){
document.forms[0].action = addParamToURL(document.forms[0].action,'void','Void');
}else{
document.forms[0].action = addParamToURL(document.forms[0].action,'delete','Delete');}
document.forms[0].setAttribute('onsubmit', null);
window.ischanged=false;
return true;
}
else return false;
}
function Synctemplate(fieldflag, linenum, onlySlaveSelect)
{
var sel = document.forms['main_form'].elements['template'];
var value = getSelectValue(sel);
if (value == -1) {document.location = '/pages/accounting/lists/formtemplateaddform.jsp'; }
}

function Syncdefstatus(fieldflag, linenum, onlySlaveSelect)
{
}

function Syncentity(fieldflag, linenum, onlySlaveSelect)
{
var sel = document.forms['main_form'].elements['entity'];
var value = getSelectValue(sel);
if (value == -1) {window.open('/pages/accounting/lists/entityaddform.jsp?target=main:entity&label=Customer:Job&type=CustJob', 'newentity', 'scrollbars=no,width=350,height=325');
resetlist(sel);return; }
window.isinited = false;var serverUrl = '/accounting/transactions/salesord.nl?cf=89&q=entity&si='+encode(value)+(fieldflag ? '&f=T' : '');
	document.getElementById('server_commands').src= serverUrl;
}

function Syncdepartment(fieldflag, linenum, onlySlaveSelect)
{
var sel = document.forms['main_form'].elements['department'];
var value = getSelectValue(sel);
if (value == -1) {window.open('/pages/accounting/lists/departmentaddform.jsp?target=main:department&label=Department', 'newdepartment', 'scrollbars=no,width=400,height=175');
resetlist(sel);return; }
}

function Syncclass(fieldflag, linenum, onlySlaveSelect)
{
var sel = document.forms['main_form'].elements['class'];
var value = getSelectValue(sel);
if (value == -1) {window.open('/pages/accounting/lists/classaddform.jsp?target=main:class&label=Class', 'newclass', 'scrollbars=no,width=400,height=175');
resetlist(sel);return; }
}

function Syncship_via(fieldflag, linenum, onlySlaveSelect)
{
}

function Syncterms(fieldflag, linenum, onlySlaveSelect)
{
}

function Syncrep(fieldflag, linenum, onlySlaveSelect)
{
}

function Synccustbody1(fieldflag, linenum, onlySlaveSelect)
{
var sel = document.forms['main_form'].elements['custbody1'];
var value = getSelectValue(sel);
if (value == -1) {window.open('/pages/accounting/lists/customlistdataaddform.jsp?target=main:custbody1&label=Occasion&listid=1&label=Occasion', 'newcustomlistdata', 'scrollbars=no,width=350,height=125');
resetlist(sel);return; }
}

function Syncmessage(fieldflag, linenum, onlySlaveSelect)
{
}

function Synctaxitem(fieldflag, linenum, onlySlaveSelect)
{
var sel = document.forms['main_form'].elements['taxitem'];
var value = getSelectValue(sel);
document.forms['main_form'].elements['taxrate'].value = ((value.length != 0 && taxrates.length > value) ? taxrates[eval(value)+1000] : '');
eval(document.forms['main_form'].elements['taxrate'].getAttribute('onchange'));
}

function create_memorized_transaction(create) {
    var actionsave = document.forms[0].action;
    document.forms[0].action = addParamToURL(document.forms[0].action,'memorize','T');
    if (!create)
    {
        document.forms[0].action = addParamToURL(document.forms[0].action,'memupdate','T');
    }
    if (window.isvalid && save_record())  {
    window.ischanged=true;
    return true; }
    document.forms[0].action = actionsave;
    return false;
}
function do_memorize() {
  window.ischanged=true;
  var prompt = 'You have already memorized this transaction.\n\nClick OK to create another memorized transaction.\n\nClick Cancel to update this memorized transaction.';
  if (document.forms[0].elements['memdoc'] && !confirm(prompt))
      create_memorized_transaction(false);
  else
      create_memorized_transaction(true);
}
function hot_print() {
document.forms[0].action = addParamToURL(document.forms[0].action,'hotprint','T'); return true;}
function CheckCheckForHotPrint() {
  if (document.forms[0].elements['toprint'].checked) {
    alert('In order to print directly from this page, you must clear the To Be Printed checkbox and enter the number of the check you are printing.'); return false;}
return true; }
function InsertGroup(groupid, linenum, quantity) {
document.getElementById('server_commands').src='/accounting/transactions/salesord.nl?cf=89&gr='+groupid+'&ln='+linenum+'&q='+quantity+'&c='+getSelectValue(document.forms[0].elements.entity); }
function InsertGroupData(newdata, groupidx, grouplen) {
item_machine.insertdata(newdata, groupidx+1);
SetupGroup(groupidx+1,groupidx+grouplen);
item_machine.recalc();
item_machine.buildtable(); }
function CheckDuplicate(num) {
document.getElementById('server_commands').src='/accounting/transactions/checkduplicate.nl?type=salesord&num='+num
}
function changeForm(id) {
if (id==-1) {resetlist(document.forms[0].elements['template']);return;}
if (document.location.href.indexOf('cf=') == -1)
document.location = document.location.href + (document.location.href.indexOf('?') == -1 ? '?' : '&') + 'cf=' + id;
else document.location = document.location.href.replace(/cf=[0-9]+/,'cf='+id); }
function syncItemMachinePrice(){
var url = '';
var maxline = parseInt(item_machine.mainform.elements['nextitemidx'].value);
url += '&icount='+(maxline-1);
for (var linenum=1; linenum < maxline; linenum++)
{
if (getEncodedValue('item',linenum,'price') != 'ZNEW'){
url +='&i'+(linenum-1)+'='+getEncodedValue('item',linenum,'itemid');}
}
if ((maxline-1)>0 && document.forms['main_form'].elements['entity'].value != null && document.forms['main_form'].elements['entity'].value.length >0){
document.getElementById('server_commands').src='/accounting/transactions/syncItemMachinePrice.nl?custjob='+document.forms['main_form'].elements['entity'].value+url;
}
return url;
}
function addShortcut() {
window.open('/elements/simple/addShortcut.nl?label=Sales%20Order&url=/accounting/transactions/salesord.nl?cf%3D89&perm=TRAN_SALESORD&level=2&feature=SALESORDERS','addshortcut','scrollbars=no,width=300,height=150');
}
function doFind() {
window.open('/accounting/transactions/quickfind.nl?type=salesord&label=Sales Order','find','scrollbars=no,height=200,width=400')
}
function goRegister() {
var acctid;
window.location='/elements/reporting/reportbuilder.nl?reporttype=REGISTER&specacct=SalesOrd&acctid=125';
}
function localEval(s) {
return eval(s);
}
function refreshmachine(targetmachines,key,title) {
var sTarget = '';
var targets = targetmachines.split(';');
for (var i = 0; i<targets.length; i++) {
sTarget += (targets[i].indexOf(':') == -1 ? '&machine=' : '&target=')+ targets[i];
}
document.getElementById('server_commands').src='/accounting/transactions/salesord.nl?cf=89&r=T' + (sTarget.indexOf('target') != -1?'&optionid=' + key + '&optionname=' + title :') +sTarget;
}
function showlayer(lname) {
if (window.isinited) {
}
}
function show_preview(id) {
document.location = '/pages/accounting/transactions/hotprint.jsp?sethotprinter=T&id='+id+'&label=Sales%20Order&printtype=transaction&trantype=salesord'
}
function send_email(id) {
document.location = '/pages/accounting/transactions/email.jsp?id='+id;
}
function send_fax(id) {
document.location = '/pages/accounting/transactions/email.jsp?fax=T&id='+id;
}
function Syncitemid(fieldflag, linenum, onlySlaveSelect)
{
  var sel = document.forms['item_form'].elements['itemid'];
  var value = getSelectValue(sel);
  var value_entity = getSelectValue(document.forms['main_form'].elements['entity']);
  if (value == -1) {window.open('/pages/accounting/lists/extinvtitemaddform.jsp?target=item:itemid&label=Item&payment=T&sale=T', 'newextinvtitem', 'scrollbars=yes,width=800,height=500');
  resetlist(sel);return; }
  window.isinited = false;
  var serverUrl = '/accounting/transactions/salesord.nl?cf=89&q=itemid&si='+encode(value)+'&si_entity='+encode(value_entity)+(fieldflag ? '&f=T' : '');
  if (linenum != null) {
  var linearray = item_machine.mainform.elements['itemdata'].value.split(String.fromCharCode(2));
  var linedata = linearray[linenum].split(String.fromCharCode(1));
  for (var i = 0; i >< item_machine.form_elems.length; i++) {
  if (item_machine.form_elems[i] == 'price')
    serverUrl += '&price=' + linedata[i] ;
  }
  if (onlySlaveSelect == true) 
    serverUrl += '&view=T';
  }
  document.getElementById('server_commands').src= serverUrl;
}

function Searchprice(value)
{
if (value.length == 0) {
document.forms['item_form'].elements['price'].value = '';document.forms['item_form'].elements['price_display'].isvalid = true;window.isvalid = true;eval(document.forms['item_form'].elements['price'].getAttribute('onchange'));return; }
	document.getElementById('server_commands').src= '/accounting/transactions/salesord.nl?cf=89&&q=price&st='+value+'&f=T';
}
function Listprice() {
window.open('/accounting/transactions/salesord.nl?cf=89&&q=price&n=Price%20Level&l=T&t=item:price','selnames','scrollbars=yes,width=300,height=300') }
function Syncprice(fieldflag, linenum, onlySlaveSelect)
{
if (onlySlaveSelect == true) {
return eval(Syncitemid(fieldflag, linenum, onlySlaveSelect));
}
var frm=document.forms['item_form'];
if (document.forms['item_form'].elements['price'].selectedIndex != -1 ) {
frm.elements['price_display'].value = getSelectText(frm.elements['price']);}
else
 {frm.elements['price_display'].value = '';}
if (document.forms['item_form'].elements['itemid'].selectedIndex == 0) {
while (document.forms['item_form'].elements['price'].length>0)
{document.forms['item_form'].elements['price'].options[0] = null;}
}
var sel = document.forms['item_form'].elements['price'];
var value = getSelectValue(sel);
window.isinited = false;var serverUrl = '/accounting/transactions/salesord.nl?cf=89&q=price&si='+encode(value)+(fieldflag ? '&f=T' : '')+ '&itemid=' +encode(  document.forms['item_form'].elements['itemid'].selectedIndex != -1 ? document.forms['item_form'].elements['itemid'].options[document.forms['item_form'].elements['itemid'].selectedIndex].value : '');
	document.getElementById('server_commands').src= serverUrl;
}



