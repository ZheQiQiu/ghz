package com.ghz.utils;

import com.alibaba.fastjson.JSONObject;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DistUtils {

    /**
     * sha512算法
     * @author qzq
     * @date 2020.5.14
     * @param str 待加密字符串
     * @return sha512后的结果
     */
    public static String sha512(String str){
        return SHA(str,"SHA-512");
    }

    public static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte[] byteBuffer = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuilder strHexString = new StringBuilder();
                // 遍歷 byte buffer
                for (byte b : byteBuffer) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    /**
     * 异常堆栈跟踪转换成字符串
     * @author qzq
     * @date 2020.4.8
     * @param e 异常
     * @return String
     */
    public static String getErrorInfoFromException(Exception e) {
        e.printStackTrace();
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String s = sw.toString();
            sw.close();
            pw.close();
            return "\r\n" + s + "\r\n";
        } catch (Exception ex) {
            return "获得Exception信息的工具类异常!";
        }
    }

    /**
     * Object转String函数
     * @author qzq
     * @date 2020.4.23
     * @param obj 对象
     * @return 字符串
     */
    public static String obj2String(Object obj){
        try{
            return (String)obj;
        } catch (ClassCastException ignored){
            try{
                return obj.toString();
            } catch (Exception ignored1){

            }
        }

        if(obj instanceof Integer){
            return Integer.toString((Integer) obj);
        }
        if(obj instanceof Double){
            return Double.toString((Double) obj);
        }
        if(obj instanceof Float){
            return Float.toString((Float) obj);
        }
        if(obj instanceof Short){
            return Short.toString((Short) obj);
        }
        if(obj instanceof Long){
            return Long.toString((Long) obj);
        }
        return "";
    }

    /**
     * JSON字符串转Map
     * @author qzq
     * @date 2020.4.8
     * @param json JSON字符串
     * @return JSONObject
     */
    public static JSONObject json2Map(String json){

        return JSONObject.parseObject(json);

    }

    /**
     * 将对象或集合转换为JsonString
     * @author qzq
     * @date 2020.5.15
     * @param orc 对象或集合
     * @return string
     */
    public static String orc2Str(Object orc){
        return JSONObject.toJSON(orc).toString();
    }

    public static String orc2Str(Collection<?> orc){
        return JSONObject.toJSON(orc).toString();
    }

    /**
     * @author qzq
     * @date 2020.5.13
     * @param data 数据传入
     * @return 打包code、msg、数据后转换为JSONString返回
     */
    public static String successAndRenderData(Object data){
        Map<String,Object> res = new HashMap<>();
        putCode2ResMap(res,StatusCodeEnum.SUCCESS);
        res.put("data",data);
        return orc2Str(res);
    }

    public static String successAndRenderData(Collection<?> data){
        Map<String,Object> res = new HashMap<>();
        putCode2ResMap(res,StatusCodeEnum.SUCCESS);
        res.put("data",data);
        return orc2Str(res);
    }

    public static String successAndRender(){
        Map<String,Object> res = new HashMap<>();
        putCode2ResMap(res,StatusCodeEnum.SUCCESS);
        return orc2Str(res);
    }

    /**
     * 打包状态码至map
     * @author qzq
     * @date 2020-4-22
     * @param map map对象
     * @param sc 状态码枚举常量
     * @param e 异常对象（可为空）
     * @return map对象
     */
    public static Map<String,Object> putCode2ResMap(Map<String,Object> map, StatusCodeEnum sc, Exception e){
        map.put("code",sc.getCode());
        map.put("msg",sc.getMsg());
        if(e != null){
            map.put("stack",getErrorInfoFromException(e));
        }
        return map;
    }

    public static Map<String,Object> putCode2ResMap(Map<String,Object> map, StatusCodeEnum sc){
        return putCode2ResMap(map,sc,null);
    }

    public static Map<String,Object> putCode2ResMap(StatusCodeEnum sc, Exception e){
        Map<String,Object> res = new HashMap<>();
        return putCode2ResMap(res,sc,e);
    }

    public static Map<String,Object> putCode2ResMap(StatusCodeEnum sc){
        return putCode2ResMap(sc,null);
    }

    public static Map<String,Object> putCode2ResMap(int code,String msg,Exception e){
        Map<String,Object> res = new HashMap<>();
        res.put("code",code);
        res.put("msg",msg);
        if(e != null){
            res.put("stack",getErrorInfoFromException(e));
        }
        return res;
    }

    public static Map<String,Object> putCode2ResMap(GhzException ge){
        return putCode2ResMap(ge.getCode(),ge.getMsg(),ge);
    }

    public static String putCode2JsonStr(Map<String,Object> map, StatusCodeEnum sc){
        return orc2Str(putCode2ResMap(map,sc));
    }

    public static String putCode2JsonStr(StatusCodeEnum sc, Exception e){
        return orc2Str(putCode2ResMap(sc,e));
    }

    public static String putCode2JsonStr(StatusCodeEnum sc){
        return orc2Str(putCode2ResMap(sc));
    }

    public static String putCode2JsonStr(int code,String msg,Exception e){
        return orc2Str(putCode2ResMap(code,msg,e));
    }

    public static String putCode2JsonStr(GhzException ge){
        return orc2Str(putCode2ResMap(ge));
    }

}
