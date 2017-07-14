package com.vector.myokhttputils.learncar;

import java.util.List;

/**
 * Created by Vector
 * on 2017/7/12 0012.
 */

public class JsonModel1 {

    /**
     * flag : 1
     * msg : 处理成功
     * datas : [{"id":"be5d113f20594ae2a50a1c2fa4da1751","photoid":"96f2fdee849e4802aeff193992e22782"}]
     */

    public String flag;
    public String msg;
    public List<DatasBean> datas;

    @Override
    public String toString() {
        return "JsonModel1{" +
                "flag='" + flag + '\'' +
                ", msg='" + msg + '\'' +
                ", datas=" + datas +
                '}';
    }

    public static class DatasBean {
        /**
         * id : be5d113f20594ae2a50a1c2fa4da1751
         * photoid : 96f2fdee849e4802aeff193992e22782
         */

        public String id;
        public String photoid;
    }
}
