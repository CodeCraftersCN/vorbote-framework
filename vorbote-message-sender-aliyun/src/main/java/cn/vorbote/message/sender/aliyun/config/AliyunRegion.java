package cn.vorbote.message.sender.aliyun.config;

/**
 * AliyunRegion<br>
 * Created at 04/12/2022 19:50
 *
 * @author theod
 */
public enum AliyunRegion {

    QINGDAO("cn-qingdao", AliyunConfig.ENDPOINT_CN),
    BEIJING("cn-beijing", AliyunConfig.ENDPOINT_CN),
    ZHANGJIAKOU("zhangjiakou", AliyunConfig.ENDPOINT_CN),
    HOHHOT("cn-huhehaote", AliyunConfig.ENDPOINT_CN),
    ULANQAB("cn-wulanchabu", AliyunConfig.ENDPOINT_CN),
    HANGZHOU("cn-hangzhou", AliyunConfig.ENDPOINT_CN),
    SHANGHAI("cn-shanghai", AliyunConfig.ENDPOINT_CN),
    SHENZHEN("cn-shenzhen", AliyunConfig.ENDPOINT_CN),
    CHENGDU("cn-chengdu", AliyunConfig.ENDPOINT_CN),
    HONG_KONG("cn-hongkong", AliyunConfig.ENDPOINT_CN),
    TOKYO("ap-northeast-1", AliyunConfig.ENDPOINT),
    SINGAPORE("ap-southeast-1", AliyunConfig.ENDPOINT),
    SYDNEY("ap-southeast-2", AliyunConfig.ENDPOINT),
    KUALA_LUMPUR("ap-southeast-3", AliyunConfig.ENDPOINT),
    JAKARTA("ap-southeast-5", AliyunConfig.ENDPOINT),
    VIRGINIA("us-east-1", AliyunConfig.ENDPOINT),
    SAN_FRANCISCO("us-west-1", AliyunConfig.ENDPOINT),
    LONDON("eu-west-1", AliyunConfig.ENDPOINT),
    FRANKFORT("eu-central-1", AliyunConfig.ENDPOINT),
    BOMBAY("ap-south-1", AliyunConfig.ENDPOINT),
    DUBAI("me-east-1", AliyunConfig.ENDPOINT),
    HANGZHOU_FINANCE("cn-hangzhou-finance", AliyunConfig.ENDPOINT_CN),
    SHANGHAI_FINANCE("cn-shanghai-finance", AliyunConfig.ENDPOINT_CN),
    SHENZHEN_FINANCE("cn-shenzhen-finance", AliyunConfig.ENDPOINT_CN),
    BEIJING_FINANCE("cn-beijing-finance", AliyunConfig.ENDPOINT_CN),
    ;



    private final String region;

    private final String endpoint;

    AliyunRegion(String region, String endpoint) {
        this.region = region;
        this.endpoint = endpoint;
    }

    public String getRegion() {
        return region;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
