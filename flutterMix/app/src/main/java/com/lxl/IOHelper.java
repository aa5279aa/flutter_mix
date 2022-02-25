package com.lxl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IOHelper {

    /**
     * @return
     */
    public static void fromIsToOsByCode(InputStream is, OutputStream os,
                                        String incode, String outcode) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, incode));
            writer = new BufferedWriter(new OutputStreamWriter(os, outcode));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String readStrByCode(InputStream is, String code)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;

        reader = new BufferedReader(new InputStreamReader(is, code));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        try {
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String fromIputStreamToString(InputStream is) {
        return fromIputStreamToString(is, "utf-8");
    }

    //byte数组到图片
    public static void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) return;
        try {
            FileOutputStream imageOutput = new FileOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static String fromIputStreamToString(InputStream is, String code) {
        if (is == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        try {
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] lens = baos.toByteArray();
        String result = null;
        try {
            result = new String(lens, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> readListStrByCode(InputStream is, String code) {
        List<String> list = new ArrayList<>();
        BufferedReader reader = null;

        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(is, code));
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return list;
    }

    public static String listToStr(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (String line : list) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static boolean fromIputStreamToFile(InputStream is,
                                               String outfilepath) {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;

        try {
            inBuff = new BufferedInputStream(is);

            outBuff = new BufferedOutputStream(
                    new FileOutputStream(outfilepath));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (inBuff != null)

                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static InputStream fromFileToIputStream(String infilepath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(infilepath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fis;
    }

    public static InputStream fromFileToIputStream(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fis;
    }

    public static InputStream fromStringToIputStream(String s) {
        if (s != null && !s.equals("")) {
            try {

                ByteArrayInputStream stringInputStream = new ByteArrayInputStream(
                        s.getBytes());
                return stringInputStream;
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    public static InputStream getInputStreamFromUrl(String urlstr) {
        try {
            InputStream is = null;
            HttpURLConnection conn = null;
            System.out.println("urlstr:" + urlstr);
            URL url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return is;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static void writerStrByCode(OutputStream os, String outcode,
                                       String str) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(os, outcode));
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writerStrByCodeToFile(File file, String outcode,
                                             boolean type, String str) {
        BufferedWriter writer = null;
        try {
            FileOutputStream out = new FileOutputStream(file, type);
            writer = new BufferedWriter(new OutputStreamWriter(out, outcode));
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public static byte[] bitMap2byte(Bitmap bmp) {
        int bytes = bmp.getByteCount();
        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);
        byte[] byteArray = buf.array();
        return byteArray;

    }

    public static Bitmap byte2bitMap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }


    public static byte[] getBytesByBitmap2(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        return buffer.array();
    }


    public static byte[] getNV21(int inputWidth, int inputHeight, Bitmap scaled) {

        int[] argb = new int[inputWidth * inputHeight];
        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
        byte[] yuv = new byte[inputWidth * inputHeight * 3 / 2];
        conver_argb_to_i420(yuv, argb, inputWidth, inputHeight);
        scaled.recycle();
        return yuv;
    }

    public static void conver_argb_to_i420(byte[] i420, int[] argb, int width, int height) {
        final int frameSize = width * height;
        int length = i420.length;
        int yIndex = 0;                   // Y start index   0到235105
        int uIndex = frameSize;           // U statt index   235106到235106*(5/4)       = 293882.5
        int vIndex = (int) Math.ceil((frameSize * 5.0) / 4.0); // V start index:    235106*(5/4)到235106*(6/4) = 352659

        int a, R, G, B, Y, U, V;
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                try {
                    a = (argb[index] & 0xff000000) >> 24; //  is not used obviously
                    R = (argb[index] & 0xff0000) >> 16;
                    G = (argb[index] & 0xff00) >> 8;
                    B = (argb[index] & 0xff) >> 0;

                    // well known RGB to YUV algorithm
                    Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                    U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                    V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                    i420[yIndex] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
                    yIndex++;
                    if (j % 2 == 0 && i % 2 == 0) {
                        i420[uIndex++] = (byte) ((U < 0) ? 0 : ((U > 255) ? 255 : U));
                        i420[vIndex++] = (byte) ((V < 0) ? 0 : ((V > 255) ? 255 : V));
                    }
                    index++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
