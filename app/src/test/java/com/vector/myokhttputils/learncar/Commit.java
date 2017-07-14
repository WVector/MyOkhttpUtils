package com.vector.myokhttputils.learncar;

import com.vector.myokhttputils.TimeUtil;

import java.util.Date;

/**
 * Created by Vector
 * on 2017/7/12 0012.
 */

public class Commit {

    /**
     * datatype : 2
     * idcard : 150222199001272634
     * itemcode : 1-1
     * coachfaceid :
     * inphotoid : d4a0430d1d7e4b1eb507aa357c1ad93a
     * coachidcard :
     * faceid : be5d113f20594ae2a50a1c2fa4da1751
     * trainid : 771502221990012726341038393
     * outfaceid :
     * coachname :
     * starttime : 2017-07-12 10:38:39
     * roomtype : 2
     * subject : 4
     * traintimes : 240
     * coachoutfaceid :
     * endtime : 2017-07-12 14:51:14
     */

    public String datatype = "2";
    public String idcard = "150222199001272634";
    public String itemcode = "1-1";
    public String coachfaceid;
    public String inphotoid;
    public String coachidcard;
    public String faceid;
    public String trainid;
    public String outfaceid;
    public String coachname;
    public String starttime;
    public String roomtype = "2";
    public String subject = "4";
    public String traintimes;
    public String coachoutfaceid;
    public String endtime = TimeUtil.myyyy_MM_dd_HH_mm_ss.format(new Date());

}
