package com.vector.myokhttputils;

import android.app.Application;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.*;
import com.zhy.http.okhttp.BuildConfig;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.InputStream;

import okio.Buffer;

/**
 * Created by zhy on 15/8/25.
 */
public class MyApplication extends Application {
    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";
    private String CER_123061 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICsTCCAhqgAwIBAgIIODtw6bZEH1kwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UE\n" +
            "BhMCQ04xKTAnBgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5\n" +
            "MQ0wCwYDVQQDEwRTUkNBMB4XDTE0MDUyNjAxNDQzNloXDTE5MDUyNTAxNDQzNlow\n" +
            "azELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24g\n" +
            "QXV0aG9yaXR5MRkwFwYDVQQLHhCUwY3vW6JiN2cNUqFOLV/DMRYwFAYDVQQDEw1r\n" +
            "eWZ3LjEyMzA2LmNuMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8Cxlz+V/4\n" +
            "KkUk8YTxVxzii7xp2gZPWuuVBiwQ6iwL98it75WNGiYCUasDXy3O8wY+PtZFvgEK\n" +
            "kpHqQ1U6uemiHStthUS1xTBsU/TuXF6AHc+oduP6zCGKcUnHRAksRb8BGSgzBA/X\n" +
            "3B9CUKnYa9YA2EBIYccrzIh6aRAjDHbvYQIDAQABo4GBMH8wHwYDVR0jBBgwFoAU\n" +
            "eV62d7fiUoND7cdRiExjhSwAQ1gwEQYJYIZIAYb4QgEBBAQDAgbAMAsGA1UdDwQE\n" +
            "AwIC/DAdBgNVHQ4EFgQUj/0m74jhq993ItPCldNHYLJ884MwHQYDVR0lBBYwFAYI\n" +
            "KwYBBQUHAwIGCCsGAQUFBwMBMA0GCSqGSIb3DQEBBQUAA4GBAEXeoTkvUVSeQzAx\n" +
            "FIvqfC5jvBuApczonn+Zici+50Jcu17JjqZ0zEjn4HsNHm56n8iEbmOcf13fBil0\n" +
            "aj4AQz9hGbjmvQSufaB6//LM1jVe/OSVAKB4C9NUdY5PNs7HDzdLfkQjjDehCADa\n" +
            "1DH+TP3879N5zFoWDgejQ5iFsAh0\n" +
            "-----END CERTIFICATE-----";


    private String CER_NM139 = "-----BEGIN CERTIFICATE-----\n" +
            "MIIErjCCA5agAwIBAgIQNlUs/JTc0Areu8b3RdqoJDANBgkqhkiG9w0BAQsFADBP\n" +
            "MQswCQYDVQQGEwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQxJDAiBgNV\n" +
            "BAMMG0NBIOayg+mAmuWFjei0uVNTTOivgeS5piBHMjAeFw0xNjA2MjgwODA5NDRa\n" +
            "Fw0xODA2MjgwODA5NDRaMBgxFjAUBgNVBAMMDWFwcC5ubTEzOS5jb20wggEiMA0G\n" +
            "CSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDNyHhikmxioClQ7DgBxDHMuckGcVGb\n" +
            "GTKmB2z297nUi/mfHuWca8QPtHKnc4czeen3zV1X70m0IbrwJE1L5mRBaxRCWI4W\n" +
            "KrnuFJIJ6VpvFa1K4j3BZzACU1zEm4zH5On36AKHT2DxlDw/+N83WtWcyca9rdXG\n" +
            "S497/aoftDLv6SL+h9VADWb1mBDS9CKa7Zb0994yhoNofmb4o5IaWjeiIrpuJhCt\n" +
            "FPJBAVRiBAqkYrDRFFNSJ2tIXrEYnBL30WXzMwzf4xOvnlkBu5sOmyUV/XmD9Wxp\n" +
            "9yUKDvojOmG/z5hbSxfG28pIsvDQXqQGB87N1UBETyIa/ft+h/ANpTITAgMBAAGj\n" +
            "ggG7MIIBtzAOBgNVHQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsG\n" +
            "AQUFBwMBMAkGA1UdEwQCMAAwHQYDVR0OBBYEFKf1xxLKHcUDIhwCi02EMCBPz/0B\n" +
            "MB8GA1UdIwQYMBaAFDDadIbzKJBWntcxMcK9Wc2TEjkdMH8GCCsGAQUFBwEBBHMw\n" +
            "cTA1BggrBgEFBQcwAYYpaHR0cDovL29jc3AyLndvc2lnbi5jbi9jYTJnMi9zZXJ2\n" +
            "ZXIxL2ZyZWUwOAYIKwYBBQUHMAKGLGh0dHA6Ly9haWEyLndvc2lnbi5jbi9jYTJn\n" +
            "Mi5zZXJ2ZXIxLmZyZWUuY2VyMD4GA1UdHwQ3MDUwM6AxoC+GLWh0dHA6Ly9jcmxz\n" +
            "Mi53b3NpZ24uY24vY2EyZzItc2VydmVyMS1mcmVlLmNybDApBgNVHREEIjAggg1h\n" +
            "cHAubm0xMzkuY29tgg9xdWFzaS5ubTEzOS5jb20wTwYDVR0gBEgwRjAIBgZngQwB\n" +
            "AgEwOgYLKwYBBAGCm1EBAQIwKzApBggrBgEFBQcCARYdaHR0cDovL3d3dy53b3Np\n" +
            "Z24uY29tL3BvbGljeS8wDQYJKoZIhvcNAQELBQADggEBACZbATri0ZDLvqJL85ZV\n" +
            "LUWw5Q1NUK5C3YzkU8gEs0OIZzZn9KsdYazvY0ulEdQWrsD+DHSXETtbSv3OBihB\n" +
            "1VB0wvBFcIWcqBm4OB/FuQSsGuVWzXicPfa1AVaevCAag3v+7zEzr4CKAVNL6oun\n" +
            "AfM29cLqkuifFjAaD/XYehjuK3pTujB5CaVn0CJnPddvu3IP4+szRRFRFqTonXqo\n" +
            "Tf/S/v1I4eP0RjSSq0K/kyx4iscEFn3fmA65fKA0l03ay4WbV2DFcDbTZZ1RCjH3\n" +
            "hQsyWNX+kkmMy5VNFey+RFODh5+YJ9KVqv4JqY9C99us0MbTAi3YHM3xdIKsp8u+\n" +
            "IP8=\n" +
            "-----END CERTIFICATE-----\n";

    @Override
    public void onCreate() {
        super.onCreate();

        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{new Buffer().writeUtf8(CER_123061).inputStream()}, null, null);

//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .addInterceptor(loggingInterceptor)
//                .cookieJar(cookieJar1)
//
////                .hostnameVerifier(new HostnameVerifier()
////                {
////                    @Override
////                    public boolean verify(String hostname, SSLSession session)
////                    {
////                        return true;
////                    }
////                })
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                .build();


//        OkHttpUtils.initClient(okHttpClient);


        OkHttpUtils.getInstance()
                .debug(BuildConfig.DEBUG, "okhttp")
                .timeout(20 * 1000L)
                .sslSocketFactory(new InputStream[]{new Buffer().writeUtf8(CER_123061).inputStream()}, null, null);
    }
}
