package com.vector.myokhttputils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/7
 *     desc  : 编码解码相关工具类
 * </pre>
 */
public class CoderUtils {

    private static String UTF8 = "UTF-8";

    private CoderUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 以UTF-8编码字符串
     * <p>若想自己指定字符集,可以使用encode(String string, String charset)方法</p>
     *
     * @param string 要编码的字符
     * @return 编码为UTF-8的字符串
     */
    public static String urlEncodeUTF8(String string) {
        return urlEncode(string, UTF8);
    }

    /**
     * 字符编码
     * <p>若系统不支持指定的编码字符集,则直接将string原样返回</p>
     *
     * @param string  要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(String string, String charset) {
        try {
            return URLEncoder.encode(string, charset);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    /**
     * 以UTF-8解码字符串
     * <p>若想自己指定字符集,可以使用# {urlDecode(String string, String charset)}方法</p>
     *
     * @param string 要解码的字符
     * @return 解码为UTF-8的字符串
     */
    public static String urlDecodeUTF8(String string) {
        return urlDecode(string, UTF8);
    }

    /**
     * 字符解码
     * <p>若系统不支持指定的解码字符集,则直接将string原样返回</p>
     *
     * @param string  要解码的字符
     * @param charset 字符集
     * @return 解码为字符集的字符串
     */
    public static String urlDecode(String string, String charset) {
        try {
            return URLDecoder.decode(string, charset);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String getMD5(String data) {
        return getMD5(data.getBytes());
    }

    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 密文
     */
    public static String getMD5(String data, String salt) {
        return bytes2Hex(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String getMD5(byte[] data) {
        return bytes2Hex(encryptMD5(data));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 密文
     */
    public static String getMD5(byte[] data, byte[] salt) {
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2Hex(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param filePath 文件路径
     * @return 文件的MD5校验码
     */
    public static String getFileMD5(String filePath) {
        return getFileMD5(new File(filePath));
    }

    /**
     * 获取文件的MD5校验码
     *
     * @param file 文件
     * @return 文件的MD5校验码
     */
    public static String getFileMD5(File file) {
        if (!file.exists()) {
            return "";
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buffer);
            return bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
        return "";
    }

    /**
     * SHA加密
     *
     * @param data 明文字符串
     * @return 密文
     */
    public static String getSHA(String data) {
        return getSHA(data.getBytes());
    }

    /**
     * SHA加密
     *
     * @param data 明文字节数组
     * @return 密文
     */
    public static String getSHA(byte[] data) {
        return bytes2Hex(encryptSHA(data));
    }

    /**
     * SHA加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 获取文件的SHA校验码
     *
     * @param filePath 文件路径
     * @return 文件的SHA校验码
     */
    public static String getFileSHA(String filePath) {
        return getFileSHA(new File(filePath));
    }

    /**
     * 获取文件的SHA校验码
     *
     * @param file 文件
     * @return 文件的SHA校验码
     */
    public static String getFileSHA(File file) {
        if (!file.exists()) {
            return "";
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel channel = in.getChannel();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(buffer);
            return bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
        return "";
    }

    /**
     * 一个byte转为2个hex字符
     *
     * @param src byte数组
     * @return 16进制大写字符串
     */
    public static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length << 1];
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }
        return new String(res);
    }


    /**
     * 加密后解密
     *
     * @param inStr
     * @return
     */
    public static String jm(String inStr) {
        final char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);
    }

    public static String getCRC32(String str) {
        try {
            CRC32 c32 = new CRC32();
            c32.update(str.getBytes());
            return Long.toHexString(c32.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFileCRC32(File file) {
        if (!file.exists()) {
            return "";
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            CRC32 c32 = new CRC32();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                c32.update(buffer, 0, len);
            }
            return Long.toHexString(c32.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static String getFileCRC32(String filePath) {
        return getFileCRC32(new File(filePath));
    }
}
