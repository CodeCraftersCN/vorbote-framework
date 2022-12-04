package cn.vorbote.message.sender.tencent.config;

import cn.vorbote.message.configurations.IRegion;

/**
 * Regions available in Tencent Cloud platform SMS Services.<br>
 * Created at 03/12/2022 18:26
 *
 * @author vorbote
 * @since 4.1.0
 */
public enum TencentRegion implements IRegion {

    NANJING("ap-nanjing"),
    GUANGZHOU("ap-guangzhou"),
    BEIJING("ap-beijing");

    private final String region;

    TencentRegion(String region) {
        this.region = region;
    }

    /**
     * Get the region id in the form of Tencent Cloud standards.
     *
     * @return the region id
     */
    @Override
    public String getRegion() {
        return region;
    }
}
