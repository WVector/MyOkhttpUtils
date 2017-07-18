package com.vector.myokhttputils;

import com.vector.myokhttputils.learncar.Pic;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.GzipSink;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by Vector
 * on 2016/11/9 0009.
 */

public class OKioUnitTest {
    private static final ByteString PNG_HEADER = ByteString.decodeHex("89504e470d0a1a0a");

    @Test
    public void readFile() {
        Source source = null;
        Sink sink = null;
        try {
            //读取
            source = Okio.source(new File("E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量.txt"));
            String s = Okio.buffer(source).readString(Charset.forName("GBK"));
            System.out.println(s);

            //写入
            File file = new File("E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量1.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            sink = Okio.sink(file);
            Okio.buffer(sink).writeString(s, Charset.forName("GBK")).flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Util.closeQuietly(source);
            Util.closeQuietly(sink);

        }
    }

    @Test
    public void readFile1() throws IOException {
        String srcPath = "E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量.txt";
        String destPath = "E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量1.txt";
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }


        Source source = null;
        Sink sink = null;
        try {
            //读取
            source = Okio.source(srcFile);
            //写入
            sink = Okio.sink(destFile);
            BufferedSink bufferedSink = Okio.buffer(sink);
            bufferedSink.writeAll(source);
            bufferedSink.emit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Util.closeQuietly(source);
            Util.closeQuietly(sink);

        }
    }

    @Test
    public void readFile2() throws IOException {
//        String srcPath = "E:\\视频\\科技\\[4K123.com] 暴风雨过后.mp4";
        String srcPath = "E:\\图片\\2015-03-27_18-34-35.png";
        String destPath = "C:\\Users\\wvector\\Desktop\\test.png";
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }


        Source source = null;
        Sink sink = null;
        try {
            //读取
            source = Okio.source(srcFile);
            //写入
            sink = Okio.sink(destFile);
            //监听写入的进度
            BufferedSink bufferedSink = Okio.buffer(new ForwardingSink(sink) {
                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    System.out.println("write :" + byteCount + "B");
                    super.write(source, byteCount);
                }
            });
            //监听读取的进度
            bufferedSink.writeAll(new ForwardingSource(source) {
                long sum = 0;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    final long readSize = super.read(sink, byteCount);
                    if (readSize != -1L) {
                        sum += readSize;
                    }
                    System.out.println("read :" + readSize + "B");
                    return readSize;
                }
            });
            bufferedSink.emit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Util.closeQuietly(source);
            Util.closeQuietly(sink);

        }
    }

    @Test
    public void gzipFile() {
        Source source = null;
        Sink sink = null;
        try {
            //读取
            source = Okio.source(new File("E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量.txt"));
            String s = Okio.buffer(source).readString(Charset.forName("GBK"));
            System.out.println(s);

            //写入
            File file = new File("E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量1.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            sink = Okio.sink(file);
            GzipSink gzipSink = new GzipSink(sink);
            Okio.buffer(gzipSink).writeString(s, Charset.forName("GBK")).flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Util.closeQuietly(source);
            Util.closeQuietly(sink);

        }
    }

    @Test
    public void readGzipFile() {
        Source source = null;
        try {
            //读取
            source = Okio.source(new File("E:\\软件\\Andriod\\TEMP\\钉钉\\签到工作量1.txt"));
            GzipSource gzipSource = new GzipSource(source);
            String s = Okio.buffer(gzipSource).readUtf8();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Util.closeQuietly(source);

        }
    }

    @Test
    public void createImage() {
        String destPath = "C:\\Users\\wvector\\Desktop\\test3.png";
        Sink sink = null;
        try {
            File file = new File(destPath);

            if (!file.exists()) {
                file.createNewFile();
            }
            sink = Okio.sink(file);

            BufferedSink bufferedSink = Okio.buffer(sink);

            bufferedSink.write(Base64Utils.decode(Pic.value4));

            bufferedSink.emit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.closeQuietly(sink);
        }


    }

    @Test
    public void readImgInfo() {
        File jpegFile = new File("D:\\我的文档\\我的相册\\DSCF1749.JPG");
//        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
//        ContactsContract.Directory exif = metadata.getDirectory(ExifDirectory.class);
//        Iterator tags = exif.getTagIterator();
//        while (tags.hasNext()) {
//            Tag tag = (Tag)tags.next();
//            System.out.println(tag);
//        }
    }

    @Test
    public void readImage() {
        Source source = null;
        try {
            //读取
            source = Okio.source(new File("E:\\图片\\2015-03-27_18-34-35.png"));
            ByteString byteString = Okio.buffer(source).readByteString();
//            System.out.println(byteString);
            System.out.println(byteString.base64());
//            System.out.println(byteString.md5());
//            System.out.println(byteString.sha256());
//            System.out.println(byteString.base64Url());
//            System.out.println(byteString.hex());

//            decodePng(new File("E:\\图片\\2015-03-27_18-34-35.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            Util.closeQuietly(source);

        }
    }

    public void decodePng(File in) throws IOException {
        BufferedSource pngSource = Okio.buffer(Okio.source(in));

        ByteString header = pngSource.readByteString(PNG_HEADER.size());
        if (!header.equals(PNG_HEADER)) {
            throw new IOException("Not a PNG.");
        }

        while (true) {
            Buffer chunk = new Buffer();

            // Each chunk is a length, type, data, and CRC offset.
            int length = pngSource.readInt();
            String type = pngSource.readUtf8(4);
            pngSource.readFully(chunk, length);
            int crc = pngSource.readInt();

            decodeChunk(type, chunk);
            if (type.equals("IEND")) break;
        }

        pngSource.close();
    }

    private void decodeChunk(String type, Buffer chunk) {
        if (type.equals("IHDR")) {
            int width = chunk.readInt();
            int height = chunk.readInt();
            System.out.printf("%08x: %s %d x %d%n", chunk.size(), type, width, height);
        } else {
            System.out.printf("%08x: %s%n", chunk.size(), type);
        }
    }
}
