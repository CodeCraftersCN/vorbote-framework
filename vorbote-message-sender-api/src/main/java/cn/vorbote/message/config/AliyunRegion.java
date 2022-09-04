package cn.vorbote.message.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This is the class that specifies the regions in Aliyun.<br>
 * Created at 04/09/2022 14:58
 *
 * @author theod
 */
@Getter
@AllArgsConstructor
public enum AliyunRegion {

    QINGDAO("cn-qingdao"),
    BEIJING("cn-beijing"),
    ZHANGJIAKOU("zhangjiakou"),
    HOHHOT("cn-huhehaote"),
    ULANQAB("cn-wulanchabu"),
    HANGZHOU("cn-hangzhou"),
    SHANGHAI("cn-shanghai"),
    SHENZHEN("cn-shenzhen"),
    CHENGDU("cn-chengdu"),
    HONG_KONG("cn-hongkong"),
    TOKYO("ap-northeast-1"),
    SINGAPORE("ap-southeast-1"),
    SYDNEY("ap-southeast-2"),
    KUALA_LUMPUR("ap-southeast-3"),
    JAKARTA("ap-southeast-5"),
    VIRGINIA("us-east-1"),
    SAN_FRANCISCO("us-west-1"),
    LONDON("eu-west-1"),
    FRANKFORT("eu-central-1"),
    BOMBAY("ap-south-1"),
    DUBAI("me-east-1"),
    HANGZHOU_FINANCE("cn-hangzhou-finance"),
    SHANGHAI_FINANCE("cn-shanghai-finance"),
    SHENZHEN_FINANCE("cn-shenzhen-finance"),
    BEIJING_FINANCE("cn-beijing-finance"),
    ;

    private final String regionId;
}
