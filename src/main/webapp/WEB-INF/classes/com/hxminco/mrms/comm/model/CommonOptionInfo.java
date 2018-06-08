package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.RsCommonOption;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Employee on 2017/7/12.
 */
@XmlRootElement
public class CommonOptionInfo {
    private List<RsCommonOption> lstRsCommonOptions;

    public List<RsCommonOption> getLstRsCommonOptions() {
        return lstRsCommonOptions;
    }

    public void setLstRsCommonOptions(List<RsCommonOption> lstRsCommonOptions) {
        this.lstRsCommonOptions = lstRsCommonOptions;
    }
}
