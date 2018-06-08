package com.hxminco.mrms.api;

import com.hxminco.mrms.comm.model.CxfFileWrapper;
import com.hxminco.mrms.comm.model.MyException;

import javax.jws.WebService;

/**
 * Created by Employee on 2017/6/29.
 */
@WebService
public interface MrmsService {

    public String examInfoDownLoad(String examPlanUid) throws MyException;

    public String institutionIfoDownLoad();

    public String commonOptionDownLoad();

    public String examPlanDataSync(String institutionUid);

    public String examInfoUpLoad(String xml);

    public String updateTrainPlanUploadStatus(String trainPlanUid);

    public CxfFileWrapper download(String version);

    public String checkCommonOptionDataUpdate(String createTime);

    public String checkInstitutionDataUpdate(String createTime);

    public String checkProjectVersionUpdate(String version);

}
