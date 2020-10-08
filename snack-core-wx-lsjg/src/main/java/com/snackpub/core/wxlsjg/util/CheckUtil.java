package com.snackpub.core.wxlsjg.util;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 微信Token校验
 *
 * @author snack
 */
@UtilityClass
public class CheckUtil {
    /**
     * 开发者自行定义Token,与接口配置信息中Token一致
     */
    private static final String TOKEN = "snack";

    /**
     * 签名验证
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return bool
     */
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = {TOKEN, timestamp, nonce};
        //将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        //将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        String temp = getSha1(sb.toString());
        //开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return temp.equals(signature);
    }

    private String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(System.out::println);
    }
}
