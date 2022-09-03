package cn.vorbote.message.sender.tencent;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TencentRegion<br>
 * Created at 02/09/2022 16:53
 *
 * @author theod
 */
@Getter
@AllArgsConstructor
public enum TencentRegion {

    GUANGZHOU("ap-guangzhou"),
    NANJING("ap-nanjing");

    private final String regionId;



}
