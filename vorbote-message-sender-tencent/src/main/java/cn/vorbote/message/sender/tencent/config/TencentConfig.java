package cn.vorbote.message.sender.tencent.config;

import cn.vorbote.message.config.BasicConfig;

/**
 * Config<br>
 * Created at 03/12/2022 19:02
 *
 * @author vorbote
 */
public final class TencentConfig {

    /**
     * Public param, value is "SendSms" in this api.
     */
    public final static String ACTION = "SendSms";

    public final static String ALGORITHM = "TC3-HMAC-SHA256";

    public final static String HOST = "sms.tencentcloudapi.com";

    public final static String CANONICAL_HEADERS = "content-type:" + BasicConfig.CONTENT_TYPE + "\nhost:" + HOST + "\n";

    public final static String CANONICAL_QUERY_STRING = "";

    public final static String CANONICAL_URI = "/";

    public final static String SEND_METHOD = "POST";

    public final static String HEADER_PREFIX = "X-TC-";

    public final static String SIGNED_HEADERS = "content-type;host";

    public final static String SERVICE = "sms";

    public final static String VERSION = "2021-01-11";

}
