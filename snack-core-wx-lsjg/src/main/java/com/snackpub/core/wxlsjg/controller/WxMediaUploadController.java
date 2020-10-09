package com.snackpub.core.wxlsjg.controller;

import com.snackpub.core.tools.utils.SpringUtil;
import com.snackpub.core.wxlsjg.constant.MsgTypeConstant;
import com.snackpub.core.wxlsjg.props.WxProperties;
import com.snackpub.core.wxlsjg.util.MediaUploadUtil;
import com.snackpub.core.wxlsjg.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

/**
 * 素材上传
 *
 * @author snackpub
 * @date 2020/10/7
 */

@RestController
@RequestMapping("/media")
@AllArgsConstructor
public class WxMediaUploadController {

    private WxProperties wxProperties;


    @PostMapping("/image/upload")
    public String mediaUpload(HttpServletRequest request,
                              HttpServletResponse response,
                              MultipartFile multipartFile) throws IOException {
        int n;
        File f = null;
        // 输出文件的新name 就是指上传后的文件名称
        System.out.println("getName:" + multipartFile.getName());
        // 输出源文件名称 就是指上传前的文件名称
        System.out.println("Oriname:" + multipartFile.getOriginalFilename());
        // 创建文件
        f = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println(f.getAbsolutePath());
        try (InputStream in = multipartFile.getInputStream(); OutputStream os = new FileOutputStream(f)) {
            // 得到文件流。以文件流的方式输出到新文件
            // 可以使用byte[] ss = multipartFile.getBytes();代替while
            byte[] buffer = new byte[4096];
            while ((n = in.read(buffer, 0, 4096)) != -1) {
                os.write(buffer, 0, n);
            }
            // 读取文件第一行
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            System.out.println(bufferedReader.readLine());
            // 输出路径
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaUploadUtil.addMaterialEver(f.getAbsolutePath(), MsgTypeConstant.RESP_MESSAGE_TYPE_IMAGE, TokenUtil.getToken().getToken());
        // 输出file的URL
        System.out.println(f.toURI().toURL().toString());
        // 输出文件的绝对路径
        System.out.println(f.getAbsolutePath());
        // 操作完上的文件 需要删除在根目录下生成的文件
        File file = new File(f.toURI());
        if (file.delete()) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");

        }
        return null;
    }


}
