package com.vector.myokhttputils;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static com.vector.myokhttputils.Encode.FORM_ENCODE_SET;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

//        System.out.println(MyUrlUtils.decode("+++", "UTF-8"));
        //
//
        System.out.println(Encode.canonicalize("+ +%20", FORM_ENCODE_SET, false, true, true, true));
        System.out.println(MyUrlUtils.encode("aA0 -_.*", "UTF-8"));

        System.out.println(URLEncoder.encode(" +  ", "UTF-8"));
        System.out.println(URLDecoder.decode("+", "UTF-8"));


        assertEquals(4, 2 + 2);
    }
}