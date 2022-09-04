package cn.vorbote.message.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This is the class that specifies the regions in Tencent Cloud.<br>
 * Created at 04/09/2022 18:58
 *
 * @author theod
 */
@Getter
@AllArgsConstructor
public enum TencentRegion {

    BEIJING("ap-beijing"),
    GUANGZHOU("ap-guangzhou"),
    NANJING("ap-nanjing")
    ;

    private final String regionId;

}
