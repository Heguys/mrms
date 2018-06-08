package com.hxminco.mrms.ioc.s;

import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by Employee on 2017/6/29.
 */
@WebService
public interface MrmsService {
    @WebMethod
    public String examInfoDownLoad(String examPlanUid);
    @WebMethod
    public String institutionIfoDownLoad();
    @WebMethod
    public String commonOptionDownLoad();
    @WebMethod
    public String examPlanDataSync(String institutionUid);
    @WebMethod
    public String examInfoUpLoad(String xml);

}
