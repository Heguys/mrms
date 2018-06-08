package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.RsInstitution;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Employee on 2017/7/13.
 */
@XmlRootElement
public class InstitutionInfo {

    private List<RsInstitution> lstRsInstitutions;

    public List<RsInstitution> getLstRsInstitutions() {
        return lstRsInstitutions;
    }

    public void setLstRsInstitutions(List<RsInstitution> lstRsInstitutions) {
        this.lstRsInstitutions = lstRsInstitutions;
    }

    @Override
    public String toString() {
        return "InstitutionInfo{" +
                "lstRsInstitutions=" + lstRsInstitutions +
                '}';
    }
}
