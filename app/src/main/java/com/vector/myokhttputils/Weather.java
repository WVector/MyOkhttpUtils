package com.vector.myokhttputils;

/**
 * Created by Vector
 * on 2017/5/31 0031.
 */

public class Weather {

    /**
     * weatherinfo : {"city":"西安","cityid":"101110101","temp":"20","WD":"西南风","WS":"1级","SD":"14%","WSE":"1","time":"17:00","isRadar":"1","Radar":"JC_RADAR_AZ9290_JB","njd":"暂无实况","qy":"970","rain":"0"}
     */

    public WeatherinfoBean weatherinfo;

    public static class WeatherinfoBean {
        /**
         * city : 西安
         * cityid : 101110101
         * temp : 20
         * WD : 西南风
         * WS : 1级
         * SD : 14%
         * WSE : 1
         * time : 17:00
         * isRadar : 1
         * Radar : JC_RADAR_AZ9290_JB
         * njd : 暂无实况
         * qy : 970
         * rain : 0
         */

        public String city;
        public String cityid;
        public String temp;
        public String WD;
        public String WS;
        public String SD;
        public String WSE;
        public String time;
        public String isRadar;
        public String Radar;
        public String njd;
        public String qy;
        public String rain;

        @Override
        public String toString() {
            return "WeatherinfoBean{" +
                    "city='" + city + '\'' +
                    ", cityid='" + cityid + '\'' +
                    ", temp='" + temp + '\'' +
                    ", WD='" + WD + '\'' +
                    ", WS='" + WS + '\'' +
                    ", SD='" + SD + '\'' +
                    ", WSE='" + WSE + '\'' +
                    ", time='" + time + '\'' +
                    ", isRadar='" + isRadar + '\'' +
                    ", Radar='" + Radar + '\'' +
                    ", njd='" + njd + '\'' +
                    ", qy='" + qy + '\'' +
                    ", rain='" + rain + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Weather{" +
                "weatherinfo=" + weatherinfo +
                '}';
    }
}
