package com.snackpub.core.wxlsjg.service;

import com.alibaba.fastjson.JSONObject;
import com.snackpub.core.wxlsjg.props.WxProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author snackpub
 * @date 2020/10/7
 */
@Slf4j
@Service
@AllArgsConstructor
public class WxMediaUploadService {

    private static WxProperties wxProperties;


}
