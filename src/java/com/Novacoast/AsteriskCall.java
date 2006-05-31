package com.Novacoast;

import net.sf.asterisk.manager.AuthenticationFailedException;
import net.sf.asterisk.manager.ManagerConnection;
import net.sf.asterisk.manager.ManagerConnectionFactory;
import net.sf.asterisk.manager.TimeoutException;
import net.sf.asterisk.manager.action.OriginateAction;
import net.sf.asterisk.manager.response.ManagerResponse;
import org.aspcfs.utils.StringUtils;

import java.io.IOException;

public class AsteriskCall {
  private ManagerConnection managerConnection;

  public AsteriskCall(String server, String username, String password) throws IOException {
    ManagerConnectionFactory factory = new ManagerConnectionFactory();
    this.managerConnection = factory.getManagerConnection(server, username, password);
  }

  public String call(String extension, String phoneNumber, String context) throws IOException, AuthenticationFailedException,
      TimeoutException {
    OriginateAction originateAction;
    ManagerResponse originateResponse;

    originateAction = new OriginateAction();
    originateAction.setChannel("SIP/" + extension);
    originateAction.setContext(context);
    originateAction.setExten(StringUtils.getNumbersOnly(phoneNumber));
    originateAction.setCallerId(extension);
    originateAction.setPriority(new Integer(1));
    originateAction.setTimeout(new Integer(30000));

    // connect to Asterisk and log in
    managerConnection.login();

    // send the originate action and wait for a maximum of 30 seconds for Asterisk
    // to send a reply
    originateResponse = managerConnection.sendAction(originateAction, 30000);

    // print out whether the originate succeeded or not
    String response = originateResponse.getResponse();

    // and finally log off and disconnect
    managerConnection.logoff();

    return response;
  }
}

