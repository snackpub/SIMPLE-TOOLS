package com.snackpub.core.wxlsjg.constant;

/**
 * 系统常量
 *
 * @author snack
 */
public interface SnackConstant {

    /**
     * 编码
     */
    String UTF_8 = "UTF-8";

    /**
     * contentType
     */
    String CONTENT_TYPE_NAME = "Content-type";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json;charset=utf-8";

    String APP_PROPERTIES_FILENAME = "wxApplication.properties";

    String WX_PROPS_PREFIX = "wx.snack.";

    String WX_PROPS_GRANT_TYPE = WX_PROPS_PREFIX + "grant_type";

    String WX_PROPS_APPID = WX_PROPS_PREFIX + "appid";

    String WX_PROPS_SECRET = WX_PROPS_PREFIX + "secret";

    String WX_TOKEN_URL = WX_PROPS_PREFIX + "token_url";

    String WX_MENU_CREATE =WX_PROPS_PREFIX + "menu_create";
    String WX_MENU_SELECT =WX_PROPS_PREFIX + "menu_select";
}
