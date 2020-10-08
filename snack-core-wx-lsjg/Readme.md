# 微信公众号开发

## 注册微信公众号

[微信工作平台](https://mp.weixin.qq.com/) <https://mp.weixin.qq.com/>

## 内外穿透工具
* NATAPP
* ngrok  

#### 端口使用  
免费试用的80 or 8080；购买可任意
#### 相关配置
 config.ini（Windows）文件配置
* authtoken  免费购买的隧道token(核心)
* 同下载的穿透工具可执行文件同目录

其它操作系统可根据官方文档配置config文件


## 公众号开发配置
### 开发
公众号开发信息
* IP白名单配置(服务器IP)

服务器配置
1. 服务器地址(URL)  
  <http://zyrg6g.natappfree.cc/wx/wxt>  内外穿透的IP(你需要用你的穿透工具的域名)
2. 令牌(Token) 可随意，程序中一致便可
3. 消息加解密密钥(EncodingAESKey) 随机生成

4. 验证的确来自微信服务器(*/wx/wxt*是我接口url，开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求
，具体校验查看微信接入指南！！！)

    | 参数 | 描述 |
    | :---- | :---- |
    | signature | 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。 |
    | timestamp | 时间戳 |
    | nonce | 随机数 |
    | echostr | 随机字符串 |

开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：
    1）将token、timestamp、nonce三个参数进行字典序排序 2）将三个参数字符串拼接成一个字符串进行sha1加密 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

### 自定义菜单
[自定义菜单官方文档](https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html)

#### 注意事项

* 自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。
* 一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替
* 创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，如果发现上一次拉取菜单的请求在5分钟以前，就会拉取一下菜单，如果菜单有更新，就会刷新客户端的菜单。测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果

#### 自定义按钮类型

1. click 
2. view  跳转用户的URL,适用于网页开发
3. ... ...


#### 接口请求示例

##### 创建菜单接口 

http请求方式：POST（请使用https协议）
<https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN>

数据格式 click和view的请求示例
```
{
     "button":[
     {	
          "type":"click",
          "name":"今日歌曲",
          "key":"V1001_TODAY_MUSIC"
      },
      {
           "name":"菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"搜索",
               "url":"http://www.soso.com/"
            },
            {
                 "type":"miniprogram",
                 "name":"wxa",
                 "url":"http://mp.weixin.qq.com",
                 "appid":"wx286b93c14bbf93aa",
                 "pagepath":"pages/lunar/index"
             },
            {
               "type":"click",
               "name":"赞一下我们",
               "key":"V1001_GOOD"
            }]
       }]
 }  
```

**数据格式参数说明**

| 参数 | 是否必须 | 说明 | 
| :---- | :---- | :----|
| button| 是 | 一级菜单数组，个数应为1~3个 |
| sub_button | 否 | 二级菜单数组，个数应为1~5个|
| type | 是  | 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型|
| name | 是 | 菜单标题，不超过16个字节，子菜单不超过60个字节|
| key | click等点击类型必须 | 菜单KEY值，用于消息接口推送，不超过128字节|
| url | view、miniprogram类型必须 | 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url|
| appid | miniprogram类型必须 | 小程序的appid（仅认证公众号可配置）|
| ...其他关于小程序的参数 | ...自行查看 | ...创建接口文档 |

通过HttpClient发起post请求
```java
HttpClient hc = new HttpClient();
PostMethod pm = new PostMethod(url);
RequestEntity re = new StringRequestEntity(menuData, "application/json", "UTF-8");
pm.setRequestEntity(re);
hc.executeMethod(pm);
String respBody = pm.getResponseBodyAsString();
```
返回结果

正确返回如下 
 
    {"errcode":0,"errmsg":"ok"}

错误时的返回JSON数据包如下（示例为无效菜单名长度）

    {"errcode":40018,"errmsg":"invalid button name size"}

注意数据结构错误！！！

 ##### 查询菜单接口
 
 .... 其他
 
 
  
 
### 开发者工具
* 开发者文档（过一遍，注意相关事项）
* 公众平台测试账号（体验一波，更加丝滑）
* 其他文档... ... （对症下药）

### 运维中心


### 接口权限
  
今天是难过的一天，心情无比的郁闷
 
2020/9/11 唉，烦死了啊