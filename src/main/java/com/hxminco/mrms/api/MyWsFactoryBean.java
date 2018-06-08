package com.hxminco.mrms.api;

import java.util.HashMap;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.beans.factory.FactoryBean;

public class MyWsFactoryBean<T> implements FactoryBean<T> {

    Class<T> type;

    String url;

    @Override
    public T getObject() throws Exception {
        JaxWsProxyFactoryBean jpb = new JaxWsProxyFactoryBean();
        jpb.setAddress(url);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        hashMap.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
        hashMap.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
        hashMap.put(WSHandlerConstants.USER, "username");
        WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(hashMap);
        jpb.getOutInterceptors().add(wss4jOutInterceptor);
        T create = jpb.create(type);
        Client client = ClientProxy.getClient(create);
        HTTPConduit conduit = (HTTPConduit)client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(25000); //连接超时时间  经测试可用
        policy.setReceiveTimeout(50000);//请求超时时间.经测试可用
        conduit.setClient(policy);
        return create;
    }

    @Override
    public Class<?> getObjectType() {
        // TODO Auto-generated method stub
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        // TODO Auto-generated method stub
        return false;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
