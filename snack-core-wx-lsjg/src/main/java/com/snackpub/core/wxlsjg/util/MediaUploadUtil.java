package com.snackpub.core.wxlsjg.util;

import com.alibaba.fastjson.JSONObject;
import com.snackpub.core.tools.utils.SpringUtil;
import com.snackpub.core.wxlsjg.props.WxProperties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author snackpub
 * @date 2020/10/8
 */
@Slf4j
@UtilityClass
public class MediaUploadUtil {

    /**
     * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
     *
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterialEver(String fileUrl, String type, String token) throws IOException {
        String mediaUrl = PropsUtil.getPropsKey("wx.sanck.media_url");
        File file = new File(fileUrl);
        //上传素材
        String path = /*mediaUrl*/"https://api.weixin.qq.com/cgi-bin/media/upload" + "?access_token=" + token + "&type=" + type;
        String result = connectHttpsByPost(path, null, file);
        result = result.replaceAll("[\\\\]", "");
        log.info("result:" + result);
        JSONObject resultJSON = JSONObject.parseObject(result);
        if (resultJSON != null) {
            if (resultJSON.get("media_id") != null) {
                log.info("上传" + type + "永久素材成功");
                return resultJSON;
            } else {
                log.info("上传" + type + "永久素材失败");
            }
        }
        return null;
    }

    private static String connectHttpsByPost(String path, String KK, File file) throws IOException {
        URL urlObj = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        String result = null;
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);

        // 请求正文信息
        // 第一部分：
        String sb = "--" + // 必须多两道线
                BOUNDARY +
                "\r\n" +
                "Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\"" + file.getName() + "\"\r\n" +
                "Content-Type:application/octet-stream\r\n\r\n";
        byte[] head = sb.getBytes(StandardCharsets.UTF_8);
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件以流文件的方式 推入到url中

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8);// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            // 定义BufferedReader输入流来读取URL的响应
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            log.info("发送POST请求出现异常！" + e);
            log.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * 测试上传图片至微信服务器获得media_id
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            //获得Access_token的方法，请自行百度
            String token = "38__YDLfbCmEZ2i8U6foatBF6MZQ-WCQZSWCuffxIo8sxIzDTk4bj2TA1jHj3RAOoJN-RzgnL27Oj5UDlFeJC0FhPruvbxJMRncsszUS9VvsAKfGnLMSOCvoZ7VCmwQJZzL-Fx4FHF_z2CkapuINVLbAJACKA";
            String path = "E:\\学习文档\\域名备案资料\\TIM图片20180911130437.jpg";
            JSONObject object = addMaterialEver(path, "image", token);
            assert object != null;
            boolean b = object.containsKey("media_id");
            if (b) {
                String media_id = object.getString("media_id");
                System.out.println("media_id:" + media_id);
            }
            log.info(object.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
