//package com.vector.myokhttputils;
//
//import com.alibaba.fastjson.JSON;
//import com.vector.myokhttputils.learncar.Api;
//import com.vector.myokhttputils.learncar.Commit;
//import com.vector.myokhttputils.learncar.Constant;
//import com.vector.myokhttputils.learncar.HttpUtil;
//import com.vector.myokhttputils.learncar.JsonModel1;
//import com.vector.myokhttputils.learncar.Pic;
//
//import org.junit.Test;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//public class ExampleUnitTest {
//    private String[] train_pics = {Pic.train_1, Pic.train_2, Pic.train_3, Pic.train_4, Pic.train_5, Pic.train_6};
//
//    @Test
//    public void learn1() throws Exception {
//        int number = 20;
//
//
//        String startTime = "2017-07-17 09:43:51";
//
//        String trainid = "3115022219900127263409435149";
//
//        train(number, trainid);
//
//
//        String photoid = "15ed4806755745d6b8f6fcedb77cdc1e";
//
//        String id = "be5d113f20594ae2a50a1c2fa4da1751";
//
//
//        commit(
//                photoid,
//                id,
//                trainid,
//                startTime,
//                number * 3 + ""
//        );
//
//        assertEquals(4, 2 + 2);
//
//    }
//
//    @Test
//    public void test1() throws IOException {
//
//    }
//
//    @Test
//    public void learn() throws Exception {
//        //验证
//        //上传头像
//        //上传信息
//        int number = 2;
//
//
//        JsonModel1 jsonModel1 = verifyPic();
//
//        long startTime = new Date().getTime();
//
//        String trainid = "41" + Constant.myId + TimeUtil.HHmmss.format(startTime) + "36";
//
//        train(number, trainid);
//
//        JsonModel1.DatasBean datasBean = jsonModel1.datas.get(0);
//
//        commit(
//                datasBean.photoid,
//                datasBean.id,
//                trainid,
//                TimeUtil.myyyy_MM_dd_HH_mm_ss.format(new Date(startTime)),
//                number * 3 + ""
//        );
//
//        assertEquals(4, 2 + 2);
//    }
//
////    @Test
////    public void addition_isCorrect() throws Exception {
////        //验证
////        //上传头像
////        //上传信息
////        int number = 20;
////
////
////        JsonModel1 jsonModel1 = verifyPic();
////
////
////        long startTime = new Date().getTime();
////
////
////        String trainid = "41" + Constant.myId + TimeUtil.HHmmss.format(startTime) + "36";
////
//////        Date lastTime = train1(number, startTime, trainid);
////
////        train(number, startTime, trainid);
////
////        JsonModel1.DatasBean datasBean = jsonModel1.datas.get(0);
////
//////        commit(
//////                datasBean.photoid,
//////                datasBean.id,
//////                trainid,
//////                TimeUtil.myyyy_MM_dd_HH_mm_ss.format(new Date(startTime)),
//////                number * 3 + "",
//////                TimeUtil.myyyy_MM_dd_HH_mm_ss.format(lastTime)
//////        );
////        commit(
////                datasBean.photoid,
////                datasBean.id,
////                trainid,
////                TimeUtil.myyyy_MM_dd_HH_mm_ss.format(new Date(startTime)),
////                number * 3 + ""
////        );
////
////        assertEquals(4, 2 + 2);
////    }
//
//    @Test
//    public void test2() {
//        verifyPic();
//    }
//
//
//    public JsonModel1 verifyPic() {
//        System.out.println("1，验证头像");
//        Map<String, String> params = new HashMap<>();
//        params.put(Constant.idcard, Constant.myId);
//        params.put(Constant.photo, Pic.value1);
//        String verifyPicJson = JSON.toJSONString(params);
//        String result = HttpUtil.post(Api.verifyPic, verifyPicJson);
//        JsonModel1 jsonModel1 = JSON.parseObject(result, JsonModel1.class);
//        System.out.println(jsonModel1);
//        return jsonModel1;
//    }
//
//    public void train(int number, String trainid) throws ParseException, IOException {
//
//
//        System.out.println("2，开始上传图片");
//
//
//        Train train = new Train();
//        train.idcard = Constant.myId;
//        train.itemcode = "1-1";
//        train.subject = "4";
//        train.trainid = trainid;
//
//
//        for (int i = 0; i < number; i++) {
//
//            System.out.println(i);
//
//            train.traintimes = i * 3 + "";
//
//            int nextInt = new Random().nextInt(6);
//            System.out.println("上传的图片" + nextInt);
//            train.photo = train_pics[nextInt];
//
//            Date now = new Date();
//            train.collecttime = TimeUtil.myyyy_MM_dd_HH_mm_ss.format(now);
//            System.out.println(train.collecttime);
//            String format = TimeUtil.HHmmss.format(now);
//
//            String s = (new Random().nextInt(100) + 1) + "";
//            train.recordid = s + train.idcard + format + s;
//
//            final String trainJson = JSON.toJSONString(train);
//
////            System.out.println(trainJson);
//
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    String result = HttpUtil.post(Api.uploadPic, trainJson);
//
//                    System.out.println(result);
//                }
//            }.start();
//            if (i == number - 1) {
//                break;
//            }
//
//            try {
//                Thread.sleep(3 * 60 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public Date train1(int number, long startTime, String trainid) throws ParseException, IOException {
//
//
//        System.out.println("2，开始上传图片" + TimeUtil.myyyy_MM_dd_HH_mm_ss.format(new Date(startTime)));
//        Date now = null;
//
//        Train train = new Train();
//        train.idcard = Constant.myId;
//        train.itemcode = "1-1";
//        train.subject = "4";
//        train.photo = Pic.value;
//        train.trainid = trainid;
//
//
//        for (int i = 0; i < number; i++) {
//
//            System.out.println(i);
//
//            train.traintimes = i * 3 + "";
//
//
//            now = new Date(startTime + 3 * 60 * 1000 * i);
//
//
//            train.collecttime = TimeUtil.myyyy_MM_dd_HH_mm_ss.format(now);
//
//            System.out.println(train.collecttime);
//
//            String format = TimeUtil.HHmmss.format(now);
//
//            train.recordid = "98" + train.idcard + format + "98";
//
//            final String trainJson = JSON.toJSONString(train);
//
////            System.out.println(trainJson);
//
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    String result = HttpUtil.post(Api.uploadPic, trainJson);
//
//                    System.out.println(result);
//                }
//            }.start();
//
////            try {
////                Thread.sleep(3 * 60 * 1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
//
//        return now;
//    }
//
//
//    public void commit(String inphotoid, String faceid, String trainid, String starttime, String traintimes, String lastTime) {
//        System.out.println("3，结束");
//        Commit commit = new Commit();
//        commit.inphotoid = inphotoid;
//        commit.faceid = faceid;
//        commit.trainid = trainid;
//        commit.starttime = starttime;
//        commit.traintimes = traintimes;
//        commit.endtime = lastTime;
//
//        String commitJson = JSON.toJSONString(commit);
//
//        String result = HttpUtil.post(Api.commit, commitJson);
//
//        System.out.println(result);
//
//    }
//
//    public void commit(String inphotoid, String faceid, String trainid, String starttime, String traintimes) {
//        System.out.println("3，结束");
//        Commit commit = new Commit();
//        commit.inphotoid = inphotoid;
//        commit.faceid = faceid;
//        commit.trainid = trainid;
//        commit.starttime = starttime;
//        commit.traintimes = traintimes;
//
//        String commitJson = JSON.toJSONString(commit);
//
//        String result = HttpUtil.post(Api.commit, commitJson);
//
//        System.out.println(result);
//
//    }
//}