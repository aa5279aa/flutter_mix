package com.lxl;

import android.net.Uri;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiangleiliu on 2017/8/5.
 */
public class StringUtil {
    static final int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    static final int[] vi = new int[]{1, 0, 88, 9, 8, 7, 6, 5, 4, 3, 2};
    public static final String EMPTY = "";
    private static final Pattern URI_FILE_PATTERN = Pattern.compile("\\.(zip|rar|tar|apk|gz|z|exe|dmg|wav|mp3|mpeg|rm|avi|ram|doc|ppt|pdf|xls|xlsx|rtf|tmp|bat|shell|swf)$");

    public StringUtil() {
    }

    public static String getUnNullString(String inStr) {
        if(emptyOrNull(inStr)) {
            inStr = "";
        }

        return inStr;
    }

    public static boolean checkIp(String str) {
        boolean flag = false;
        String[] s = new String[4];
        if(!str.matches("[0-9[\\.]]{1,16}")) {
            flag = false;
        } else {
            s = str.split("\\.");

            for(int i = 0; i < s.length; ++i) {
                int a = Integer.parseInt(s[i]);
                if(!Integer.toBinaryString(a).matches("[0-1]{1,8}")) {
                    flag = false;
                    break;
                }

                flag = true;
            }
        }

        return flag;
    }

    public static boolean checkEmptyOrNull(String[][] valueAndNameArr, StringBuilder errorInfo) {
        boolean flag = true;
        String[][] arr$ = valueAndNameArr;
        int len$ = valueAndNameArr.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String[] valueAndName = arr$[i$];
            if(emptyOrNull(valueAndName[0])) {
                errorInfo.append(valueAndName[1] + ",");
                flag = false;
            }
        }

        if(!flag) {
            errorInfo.append(" can\'t be emptyOrNull!");
        }

        return flag;
    }

    public static boolean emptyOrNull(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null?str2 == null:str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null?str2 == null:str1.equalsIgnoreCase(str2);
    }

    public static boolean emptyOrNull(String... arrStr) {
        String[] arr$ = arrStr;
        int len$ = arrStr.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String str = arr$[i$];
            if(emptyOrNull(str)) {
                return true;
            }
        }

        return false;
    }

    public static String replaceBlank(String str) {
        return emptyOrNull(str)?"":str.replace(" ", "");
    }

    public static int toInt(String s) {
        boolean i = false;

        int i1;
        try {
            i1 = Integer.parseInt(s);
        } catch (Exception var3) {
            i1 = -1;
        }

        return i1;
    }

    public static int toInt(String str, int defaultValue) {
        int i;
        try {
            i = Integer.parseInt(str);
        } catch (Exception var4) {
            i = defaultValue;
        }

        return i;
    }

    public static int compareStrToInt(String s1, String s2) {
        int i = 0;

        try {
            i = Integer.parseInt(s1) - Integer.parseInt(s2);
        } catch (Exception var4) {
            ;
        }

        return i;
    }

    public static int cityIDToInt(String s) {
        boolean i = false;

        int i1;
        try {
            i1 = Integer.parseInt(s);
        } catch (Exception var3) {
            i1 = 0;
        }

        return i1;
    }

    public static String toDecimalString(float value) {
        if(value < 0.0F) {
            return "";
        } else if(value == 0.0F) {
            return "0";
        } else {
            String result = Float.toString(value);
            if(result != null) {
                int pointIdx = result.indexOf(".");
                if(pointIdx <= 0) {
                    return "0";
                }

                int number = toInt(result.substring(pointIdx + 1));
                if(number == 0) {
                    return result.substring(0, pointIdx);
                }
            }

            return result;
        }
    }

    public static String toDecimalString(int value) {
        return value < 0?"":(value == 0?"0":(value % 100 == 0?String.valueOf(value / 100):Float.toString((float)value / 100.0F)));
    }

    public static String toDecimalString(long value) {
        return value < 0L?"":(value == 0L?"0":(value % 100L == 0L?String.valueOf(value / 100L):Double.toString((double)value / 100.0D)));
    }

    public static String halfUpToDecimalString(int value) {
        if(value < 0) {
            return "";
        } else if(value == 0) {
            return "0";
        } else if(value % 100 == 0) {
            return String.valueOf(value / 100);
        } else {
            StringBuffer strBuff = new StringBuffer();
            strBuff.append((new BigDecimal(String.valueOf((float)value / 100.0F))).setScale(0, 4));
            return strBuff.toString();
        }
    }

    public static long toLong(String s) {
        long i = 0L;

        try {
            i = Long.parseLong(s);
        } catch (Exception var4) {
            i = -1L;
        }

        return i;
    }

    public static float toFloat(String s) {
        float i = 0.0F;

        try {
            i = Float.parseFloat(s);
        } catch (Exception var3) {
            i = -1.0F;
        }

        return i;
    }

    public static double toDouble(String s) {
        double i = 0.0D;

        try {
            i = Double.parseDouble(s);
        } catch (Exception var4) {
            i = -1.0D;
        }

        return i;
    }

    public static String formatAirportName(String airportName) {
        return emptyOrNull(airportName)?"":(airportName.contains("国际")?airportName.replace("国际", ""):airportName);
    }

    public static String getFlightOrderState(String code) {
        if(emptyOrNull(code)) {
            return null;
        } else {
            String c = code.toLowerCase();
            return c.equals("c")?"已取消":(c.equals("p")?"处理中":(c.equals("r")?"全部退票":(c.equals("s")?"成交":(c.equals("t")?"部分退票":(c.equals("w")?"未处理":(c.equals("u")?"未提交":"提交中"))))));
        }
    }

    public static byte checkIsBackMoney(String giftType) {
        if(!emptyOrNull(giftType)) {
            if(giftType.equalsIgnoreCase("L") || giftType.equalsIgnoreCase("U") || giftType.equalsIgnoreCase("R") || giftType.equalsIgnoreCase("T") || giftType.equalsIgnoreCase("S")) {
                return (byte)0;
            }

            if(giftType.equalsIgnoreCase("C") || giftType.equalsIgnoreCase("D")) {
                return (byte)1;
            }
        }

        return (byte)-1;
    }

    public static String shortAirportName(String airportName) {
        if(airportName != null) {
            int len = airportName.length();
            if(airportName.endsWith("国际机场")) {
                return airportName.substring(0, len - 4);
            }

            if(airportName.endsWith("机场")) {
                return airportName.substring(0, len - 2);
            }
        }

        return "";
    }

    public static String toIntString(String s) {
        String intString = "";

        try {
            if(s.contains(".")) {
                intString = s.substring(0, s.indexOf("."));
            } else {
                intString = s;
            }
        } catch (Exception var3) {
            ;
        }

        return intString;
    }

    public static String changeNullStr(String str) {
        return str == null?"":str;
    }

    public static int isNumString(String str) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        return m.matches()?1:0;
    }

    public static boolean isConSpeCharacters(String string) {
        return string.replaceAll("[一-龥]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0;
    }

    public static String formatDateString(int _year, int _month, int _day, int _hour, int _minute) {
        String value = String.valueOf(_year);
        if(_month < 10) {
            value = value + "0" + _month;
        } else {
            value = value + _month;
        }

        if(_day < 10) {
            value = value + "0" + _day;
        } else {
            value = value + _day;
        }

        if(_hour < 10) {
            value = value + "0" + _hour;
        } else {
            value = value + _hour;
        }

        if(_minute < 10) {
            value = value + "0" + _minute;
        } else {
            value = value + _minute;
        }

        value = value + "00";
        return value;
    }

    public static String formatDateString(int year, int month, int day) {
        String value;
        for(value = String.valueOf(year); value.length() < 4; value = "0" + value) {
            ;
        }

        if(month < 10) {
            value = value + "0" + month;
        } else {
            value = value + month;
        }

        if(day < 10) {
            value = value + "0" + day;
        } else {
            value = value + day;
        }

        return value;
    }

    public static String formatFlightRate(String oriStr) {
        if(emptyOrNull(oriStr)) {
            return "null";
        } else {
            StringBuffer rateText = new StringBuffer();
            if(toFloat(oriStr) >= 10.0F) {
                rateText.append("全价");
            } else {
                DecimalFormat df = new DecimalFormat("########.0");
                rateText.append("");
                rateText.append(toDouble(df.format((double)toFloat(oriStr))));
                rateText.append("折");
            }

            return rateText.toString();
        }
    }

    public static String getMD5(byte[] source) {
        StringBuilder sb = new StringBuilder();
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(source);
        } catch (NoSuchAlgorithmException var7) {
            ;
        }

        if(md5 != null) {
            byte[] arr$ = md5.digest();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                byte b = arr$[i$];
                sb.append(String.format("%02X", new Object[]{Byte.valueOf(b)}));
            }
        }

        return sb.toString();
    }

    public static String processString(String s) {
        String r = "";
        if(s != null && !"".equals(s)) {
            r = s.replaceAll("、", "\n");
        }

        return r;
    }

    public static String formatInfo(String str) {
        String strInfo = str.replace("<BR/>", "\n");
        strInfo = strInfo.replace("<br/>", "\n");
        strInfo = strInfo.replace("<br>", "\n");
        strInfo = strInfo.replace("&nbsp", "");
        strInfo = strInfo.replace("<t>", "    ");
        return strInfo;
    }

    public static String replaceStr(String str, String src, String aim) {
        return !emptyOrNull(str) && !emptyOrNull(src)?(str.contains(src)?str.replace(src, aim):str):"";
    }

    public static String getBackMoneyString(String giftValue) {
        float value = Float.valueOf(giftValue).floatValue();
        if(value > 1.0F) {
            return (int)value + "元";
        } else {
            int value1 = (int)(value * 100.0F);
            return value1 + "%";
        }
    }

    public static int getSeekBarProgress(int value, int minValue, int midValue, int maxValue) {
        return value < 0?0:(value <= midValue?50 * (value - minValue) / (midValue - minValue):50 + 50 * (value - midValue) / (maxValue - midValue));
    }

    public static int getSeekBarValue(int progress, int minValue, int midValue, int maxValue) {
        if(progress < 0) {
            return 0;
        } else if(progress <= 50) {
            return (int)((double)(midValue - minValue) * ((double)progress / 50.0D)) + minValue;
        } else {
            int overValue = (int)((double)((progress - 50) * (maxValue - midValue)) / 50.0D);
            return midValue + overValue;
        }
    }

    public static boolean isValidStr(String inputStr) {
        char[] charArray = inputStr.toCharArray();
        int length = charArray.length;

        for(int i = 0; i < length; ++i) {
            if((charArray[i] < 48 || charArray[i] > 57) && (charArray[i] < 65 || charArray[i] > 122) && charArray[i] != 95) {
                return false;
            }
        }

        return true;
    }

    public static SpannableString getMidLineStr(String s) {
        SpannableString ss = new SpannableString(s);
        ss.setSpan(new StrikethroughSpan(), 0, s.length(), 33);
        return ss;
    }

    public static String dateToString(int year, int month, int day) {
        return year >= 0 && month >= 0 && day >= 0?year + "-" + month + "-" + day:"";
    }

    public static String parseTude(String d) {
        String r = "";
        if(d != null && !"".equals(d)) {
            int i = d.indexOf(":");
            r = r + d.substring(0, i) + ".";
            String ss = d.substring(i + 1, d.length());
            int si = ss.indexOf(":");
            r = r + ss.substring(0, si) + ".";
            r = r + ss.substring(si + 1);
        }

        return r;
    }

    public static String getFlightClassName(String code) {
        return "Y".equalsIgnoreCase(code)?"经济舱":("F".equalsIgnoreCase(code)?"头等舱":("A".equalsIgnoreCase(code)?"不限":("C".equalsIgnoreCase(code)?"公务舱":"")));
    }

    public static String getFlightClassName2(String code) {
        return "Y".equalsIgnoreCase(code)?"经济舱":("F".equalsIgnoreCase(code)?"公务舱/头等舱":("A".equalsIgnoreCase(code)?"不限":("C".equalsIgnoreCase(code)?"公务舱/头等舱":"")));
    }

    public static String getFlightClassNameByFlag(int flag) {
        String subClassName = "";
        if(flag == 0) {
            subClassName = "经济舱";
        } else if(flag == 2) {
            subClassName = "公务舱";
        } else if(flag == 3) {
            subClassName = "头等舱";
        }

        return subClassName;
    }

    public static String getFlightSubClassLabelByFlag(int flag) {
        String subClassName = "";
        switch(flag) {
            case 1:
                subClassName = "高端";
                break;
            case 2:
                subClassName = "超值";
                break;
            case 3:
                subClassName = "豪华";
            case 4:
            case 5:
            case 6:
            default:
                break;
            case 7:
                subClassName = "空中";
        }

        return subClassName;
    }

    public static String getFlightSubClassNameByFlag(int flag) {
        String subClassName = "";
        switch(flag) {
            case 1:
                subClassName = "高端经济舱";
                break;
            case 2:
                subClassName = "超值头等舱";
                break;
            case 3:
                subClassName = "豪华经济舱";
                break;
            case 4:
                subClassName = "公务舱";
                break;
            case 5:
                subClassName = "头等舱";
                break;
            case 6:
                subClassName = "经济舱";
        }

        return subClassName;
    }

    public static String insertSymbolInStrPotion(String srcStr, String insertStr, int position) {
        if(emptyOrNull(srcStr)) {
            return "";
        } else {
            String str = "";
            if(position > srcStr.length()) {
                return str;
            } else {
                StringBuffer showStringBuffer = new StringBuffer(srcStr.length() + insertStr.length());
                String tmep = srcStr.substring(position, srcStr.length());
                showStringBuffer.append(srcStr.substring(0, position));
                showStringBuffer.append(insertStr);
                showStringBuffer.append(tmep);
                str = showStringBuffer.toString();
                return str;
            }
        }
    }

    public static boolean isDateRight(String date) {
        if(date.length() == 8) {
            boolean year = true;
            boolean month = true;
            boolean day = true;
            boolean isLeapYear = false;
            int year1 = toInt(date.substring(0, 4));
            int month1 = toInt(date.substring(4, 6));
            int day1 = toInt(date.substring(6, 8));
            if(year1 / 4 == 0 && year1 / 100 != 0) {
                isLeapYear = true;
            }

            if(year1 / 400 == 0) {
                isLeapYear = true;
            }

            switch(month1) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if(day1 <= 31 && day1 >= 1) {
                        return true;
                    }
                    break;
                case 2:
                    if(isLeapYear) {
                        if(day1 <= 29 && day1 >= 1) {
                            return true;
                        }
                    } else if(day1 <= 28 && day1 >= 1) {
                        return true;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if(day1 <= 30 && day1 >= 1) {
                        return true;
                    }
                    break;
                default:
                    return false;
            }

            return false;
        } else {
            return false;
        }
    }

    public static int calcTwoDateUnsign(String dateStr1, String dateStr2) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        long nd = 86400000L;

        try {
            long diff = sd.parse(dateStr2).getTime() - sd.parse(dateStr1).getTime();
            long e = diff / nd;
            return Math.abs((int)e);
        } catch (ParseException var9) {
            var9.printStackTrace();
            return -1;
        }
    }

    public static int plusGlobalTwoTime(String time1, String time2) {
        int timeA = 0;
        int timeB = 0;
        if(!emptyOrNull(time1)) {
            timeA = toInt(time1);
        }

        if(!emptyOrNull(time2)) {
            timeB = toInt(time2);
        }

        return timeA + timeB;
    }

    public static String fromatGrade(String originGrade, boolean isShowZero) {
        String grade = "0.0";
        DecimalFormat decimalFormat = new DecimalFormat("########.0");
        grade = decimalFormat.format((double)toFloat(originGrade));
        double d = toDouble(grade);
        if(d <= 0.0D) {
            if(!isShowZero) {
                grade = "";
            } else {
                grade = "0.0";
            }
        }

        return grade;
    }

    public static String kilometreToMetre(String kilometre) {
        String retStr = "";
        if(emptyOrNull(kilometre)) {
            return retStr;
        } else {
            float floatData = toFloat(kilometre) * 1000.0F;
            if(floatData == -1000.0F) {
                return retStr;
            } else {
                retStr = toIntString(floatData + "");
                return retStr;
            }
        }
    }

    public static String metreToKilometre(String metre) {
        String retStr = "";
        if(emptyOrNull(metre)) {
            return retStr;
        } else if(toInt(metre) == -1) {
            return retStr;
        } else {
            float floatData = (float)toInt(metre) / 1000.0F;
            retStr = floatData + "";
            return retStr;
        }
    }

    public static String subString(String content, int length) {
        return content.length() < length?content:content.substring(0, length);
    }

    public static int getSBCCaseLength(String text) {
        if(text != null && text.length() != 0) {
            try {
                return text.getBytes("GBK").length;
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static float parseToHalfInteger(float value) {
        if(value > 0.0F) {
            int valueInt = (int)value;
            return value > (float)valueInt + 0.5F?(float)(valueInt + 1):(value > (float)valueInt?(float)valueInt + 0.5F:value);
        } else {
            return 0.0F;
        }
    }

    public static String getFormatCurrency(String currency) {
        return currency != null && currency.length() != 0?(!"RMB".equalsIgnoreCase(currency) && !"CNY".equalsIgnoreCase(currency)?currency:"￥"):"";
    }

    public static String[] getStringArray(String value, String splitChar) {
        String[] array = null;
        if(!emptyOrNull(value)) {
            array = value.split(splitChar);
        }

        return array;
    }

    public static Map<String, String> handleExtensionStrToMap(String extensionStr) {
        HashMap retMap = new HashMap();
        if(emptyOrNull(extensionStr)) {
            return retMap;
        } else {
            String[] content = extensionStr.split("\\|");
            if(content != null && content.length > 0) {
                String[] arr$ = content;
                int len$ = content.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String temp = arr$[i$];
                    if(!emptyOrNull(temp)) {
                        int index = temp.indexOf("=");
                        if(index > 0) {
                            try {
                                String e = temp.substring(0, index);
                                String value = temp.substring(index + 1, temp.length());
                                retMap.put(e.trim(), value.trim());
                            } catch (Exception var10) {
                                ;
                            }
                        }
                    }
                }

                return retMap;
            } else {
                return retMap;
            }
        }
    }

    public static String handleExtensionStrToGetValue(String extensionStr, String key) {
        Map retMap = handleExtensionStrToMap(extensionStr);
        return retMap.containsKey(key)?(String)retMap.get(key):"";
    }

    public static String getShortPriceDate(String priceDate) {
        if(emptyOrNull(priceDate)) {
            return "";
        } else {
            String[] strArray = priceDate.split("、");
            if(null != strArray && strArray.length > 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(strArray[0]).append("、").append(strArray[1]).append("...");
                return sb.toString();
            } else {
                return priceDate;
            }
        }
    }

    public static boolean isContainChinese(String str) {
        if(emptyOrNull(str)) {
            return false;
        } else {
            char[] charArr = str.toCharArray();
            char[] arr$ = charArr;
            int len$ = charArr.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                char c = arr$[i$];
                if(isContainChinese(c)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean isContainChinese(char c) {
        Character.UnicodeBlock cu = Character.UnicodeBlock.of(c);
        return cu == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || cu == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || cu == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || cu == Character.UnicodeBlock.GENERAL_PUNCTUATION || cu == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || cu == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static String getTalkTime(int time) {
        int hour = time / 3600;
        int minu = time % 3600 / 60;
        int sec = time % 60;
        String str = "";
        if(hour == 0) {
            str = String.format("%02d:%02d", new Object[]{Integer.valueOf(minu), Integer.valueOf(sec)});
        } else {
            str = String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(minu), Integer.valueOf(sec)});
        }

        return str;
    }

    public static String cutStringByNum(String content, int num, String endString) {
        String end = endString;
        if(endString == null) {
            end = "...";
        }

        String str = "";
        if(content.length() > num) {
            str = content.substring(0, num) + end;
        } else {
            str = content;
        }

        return str;
    }

    public static String convertDispatchFee(int value) {
        return value == 0?"0":(value % 100 == 0?String.valueOf(value / 100):Float.toString((float)value / 100.0F));
    }

    public static int convertStringToIntOnlyForFocusFlight(String string) {
        byte[] b = string.getBytes();
        int value = 0;

        for(int i = 0; i < b.length; ++i) {
            int n = (b[i] < 0?b[i] + 256:b[i]) << 8 * i;
            value += n;
        }

        return Math.abs(value);
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));
        s *= 6378.137D;
        s = (double)Math.round(s * 10000.0D);
        s /= 10000.0D;
        return s;
    }

    private static double rad(double d) {
        return d * 3.141592653589793D / 180.0D;
    }

    public static String getShowStar(String[] hotelStar, int level) {
        if(hotelStar != null && hotelStar.length >= level) {
            String str = "";
            if(level > 0 && level < 6) {
                str = hotelStar[level - 1];
            }

            return str;
        } else {
            return "";
        }
    }

    public static String decimalToPercent(String decimal) {
        float value = -1.0F;
        if(!emptyOrNull(decimal)) {
            try {
                value = Float.parseFloat(decimal);
            } catch (NumberFormatException var3) {
                ;
            }
        }

        if(value == -1.0F) {
            return "";
        } else {
            DecimalFormat format = new DecimalFormat("0.#%");
            return format.format((double)value);
        }
    }

    public static String getCeilPriceString(int price) {
        String priceText = "";
        if(price <= 0) {
            return "0";
        } else {
            if(price % 100 != 0) {
                priceText = String.valueOf(price / 100 + 1);
            } else {
                priceText = String.valueOf(price / 100);
            }

            return priceText;
        }
    }

    public static String toOneDecimal(String value) {
        Double huashi = Double.valueOf(0.0D);

        try {
            huashi = Double.valueOf(Double.parseDouble(value));
        } catch (Exception var4) {
            ;
        }

        DecimalFormat format = new DecimalFormat("#0.0");
        String buf = format.format(huashi).toString();
        return buf;
    }

    public static boolean isDateTimeEmpty(String dateTime) {
        String emptyDate = "00010101";
        String emptyDateTime = "00010101000000";
        return emptyOrNull(dateTime)?true:(dateTime.equals("00010101000000")?true:dateTime.equals("00010101"));
    }

    public static String autoLineFeed(String s, int num) {
        StringBuilder sb = new StringBuilder();
        float size = 0.0F;

        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if(size >= (float)num) {
                sb.append("\n");
                size = 0.0F;
            }

            Character.UnicodeBlock cUB = Character.UnicodeBlock.of(c);
            if(cUB != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                sb.append(c);
                size = (float)((double)size + 0.5D);
            } else {
                sb.append(c);
                ++size;
            }
        }

        return sb.toString();
    }

    public static String getTextBySplitStr(String text) {
        String[] arr = getStringArray(text, "\\|");
        String result = "";
        if(arr != null) {
            if(arr.length == 1) {
                result = arr[0];
            } else if(arr.length == 2) {
                result = arr[0] + "(" + arr[1] + ")";
            }
        }

        return result;
    }

    public static Boolean isNotBlank(String text) {
        return text != null && !"".equals(text)?Boolean.TRUE:Boolean.FALSE;
    }

    public static String ifBlankDefault(String text, String defaultString) {
        return text != null && !"".equals(text)?text:defaultString;
    }

    public static boolean isDate(String date) {
        if(emptyOrNull(date)) {
            return false;
        } else {
            String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(date);
            return m.matches();
        }
    }

    public static boolean isCtripURL(String url) {
        if(!emptyOrNull(url)) {
            String host = Uri.parse(url.toLowerCase()).getHost();
            if(!TextUtils.isEmpty(host)) {
                if(host.endsWith("ctrip.com") || host.endsWith("ctrip.cn") || host.endsWith("ctripcorp.com") || host.endsWith("xiecheng.com") || host.endsWith("lvping.com") || host.endsWith("toursforfun.com") || host.endsWith("eztravel.com.tw") || host.endsWith("csshotel.com.cn") || host.endsWith("wingontravel.com") || host.endsWith("tieyou.com") || host.endsWith("tujia.com") || host.endsWith("hhtravel.com") || host.endsWith("ctripqa.com") || host.endsWith("iwanoutdoor.com") || host.endsWith("youctrip.com") || host.endsWith("ctripqa.com") || host.endsWith("qunar.com") || host.endsWith("qunarzz.com") || host.endsWith("qua.com") || host.endsWith("c-ctrip.com")) {
                    return true;
                }

                if(url.toLowerCase().contains("ct_s_url_wl=2")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isFileForUrl(String strUrl) {
        String lastSegment = Uri.parse(strUrl).getLastPathSegment();
        return TextUtils.isEmpty(lastSegment)?false:URI_FILE_PATTERN.matcher(lastSegment.toLowerCase()).find();
    }

    public static String trimXSSString(String string) {
        return string.replace("<", "").replace(">", "");
    }

    public static String getSplitTextWithinPosition(String content, String splitContent, int position) {
        String result = null;
        if(!emptyOrNull(content) && !emptyOrNull(splitContent)) {
            String[] temp = content.split(splitContent);
            if(temp != null && temp.length >= position + 1) {
                result = temp[position];
            }
        }

        return result;
    }

    public static boolean isIntegerString(String input) {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    public static String getDescriptionFromException(Exception e) {
        if(e == null) {
            return "";
        } else {
            try {
                StringWriter e2 = new StringWriter();
                PrintWriter pw = new PrintWriter(e2);
                e.printStackTrace(pw);
                String ret = e2.toString();
                return ret;
            } catch (Exception var4) {
                return "Bad getDescriptionFromException:" + var4.toString();
            }
        }
    }

    public static String escapeSql(String str) {
        if(emptyOrNull(str)) {
            return "";
        } else {
            str = str.replaceAll("\'", "\'\'");
            str = str.replaceAll("\"", "\"\"");
            str = str.replaceAll("\\\\", "");
            return str;
        }
    }

    public static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static String subStringBetween(String source, String start, String end) {
        if(source != null && start != null && end != null) {
            int indexOf = source.indexOf(start);
            if(indexOf == -1) {
                return null;
            } else {
                int indexOf2 = source.indexOf(end, start.length() + indexOf);
                return indexOf2 != -1?source.substring(start.length() + indexOf, indexOf2):null;
            }
        } else {
            return null;
        }
    }

    public static String subStringAfter(String source, String prefix) {
        if(isEmpty(source)) {
            return source;
        } else if(prefix == null) {
            return "";
        } else {
            int indexOf = source.indexOf(prefix);
            return indexOf != -1?source.substring(indexOf + prefix.length()):"";
        }
    }

    public static String trim(String str) {
        return str == null?null:str.trim();
    }

    public static boolean isBlank(String str) {
        if(str != null) {
            int length = str.length();
            if(length != 0) {
                for(int i = 0; i < length; ++i) {
                    if(!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }

                return true;
            }
        }

        return true;
    }

    public static String join(Object[] objArr, String str) {
        return objArr == null?null:join(objArr, str, 0, objArr.length);
    }

    public static String join(Object[] objArr, String str, int i, int i2) {
        if(objArr == null) {
            return null;
        } else {
            if(str == null) {
                str = "";
            }

            int i3 = i2 - i;
            if(i3 <= 0) {
                return "";
            } else {
                StringBuilder stringBuilder = new StringBuilder(((objArr[i] == null?128:objArr[i].toString().length()) + str.length()) * i3);

                for(int i4 = i; i4 < i2; ++i4) {
                    if(i4 > i) {
                        stringBuilder.append(str);
                    }

                    if(objArr[i4] != null) {
                        stringBuilder.append(objArr[i4]);
                    }
                }

                return stringBuilder.toString();
            }
        }
    }
}
