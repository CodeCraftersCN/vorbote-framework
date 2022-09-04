package cn.vorbote.message.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This is the class that specifies the regions.<br>
 * Created at 04/09/2022 14:58
 *
 * @author theod
 */
@Getter
@AllArgsConstructor
public enum Region {

    ALI_QINGDAO("cn-qingdao"),
    ALI_BEIJING("cn-beijing"),
    ALI_ZHANGJIAKOU("zhangjiakou"),
    ALI_HOHHOT("cn-huhehaote"),
    ALI_ULANQAB("cn-wulanchabu"),
    ALI_HANGZHOU("cn-hangzhou"),
    ALI_SHANGHAI("cn-shanghai"),
    ALI_SHENZHEN("cn-shenzhen"),
    ALI_CHENGDU("cn-chengdu"),
    ALI_HONG_KONG("cn-hongkong"),
    ALI_TOKYO("ap-northeast-1"),
    ALI_SINGAPORE("ap-southeast-1"),
    ALI_SYDNEY("ap-southeast-2"),
    ALI_KUALA_LUMPUR("ap-southeast-3"),
    ALI_JAKARTA("ap-southeast-5"),
    ALI_VIRGINIA("us-east-1"),
    ALI_SAN_FRANCISCO("us-west-1"),
    ALI_LONDON("eu-west-1"),
    ALI_FRANKFORT("eu-central-1"),
    ALI_BOMBAY("ap-south-1"),
    ALI_DUBAI("me-east-1"),
    ALI_HANGZHOU_FINANCE("cn-hangzhou-finance"),
    ALI_SHANGHAI_FINANCE("cn-shanghai-finance"),
    ALI_SHENZHEN_FINANCE("cn-shenzhen-finance"),
    ALI_BEIJING_FINANCE("cn-beijing-finance"),

    TENCENT_GUANGZHOU("ap-guangzhou"),
    TENCENT_NANJING("ap-nanjing")
    ;

    private final String regionId;
}
