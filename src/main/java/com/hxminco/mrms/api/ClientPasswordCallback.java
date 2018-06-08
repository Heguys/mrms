package com.hxminco.mrms.api;

import com.hxminco.mrms.comm.utils.PCSerialNumberManager;
import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * Created by Employee on 2017/9/4.
 */
public class ClientPasswordCallback implements CallbackHandler {
    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        String ident = PCSerialNumberManager.getDiskNumber();
        String passwd = PCSerialNumberManager.getSerialNumber("C");
        pc.setIdentifier(ident);
        pc.setPassword(passwd);

    }
}