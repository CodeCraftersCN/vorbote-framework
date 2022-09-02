package cn.vorbote.message.sender.aliyun;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AliyunRegion<br>
 * Created at 02/09/2022 14:06
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
    CHENGDU("chengdu"),
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
