function delay(millis){
  var then,now; 
  then=new Date().getTime();
  now=then;
  while((now-then)<millis)
  {now=new Date().getTime();}
}
