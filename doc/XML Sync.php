<?
 // Site to post to
 $server = "www.your_darkhorse_crm_server.com"
 $path = "/ProcessPacket.do"
 // Begin the transfer
 $socket = fsockopen($server, 80);
 if (!$socket) {
   // error: could not connect to server
   // return
 } else {
   // Post the XML document
   $request = "POST " . $path . " HTTP/1.0\r\n" .
     "Host: " . $server . "\r\n" .
     "Content-Type: text/xml\r\n" .
     "Content-Length: " . strlen($xmlDocument) . "\r\n\r\n" .
     $xmlDocument;
   if (!fputs($socket, $request, strlen($request))) {
     // error: could not write to server
     // return
   }
   $response = '';
   while ($data = fread($socket, 32768)) {
     $response .= $data;
   }
   fclose($socket);
   if ($response = '') {
     // error: no response from server
     // return
   } else {
     // review the response for status
   }
 }
?>
