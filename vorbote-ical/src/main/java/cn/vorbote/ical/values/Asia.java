package cn.vorbote.ical.values;

import cn.vorbote.ical.standards.ITimezone;

/**
 * Timezone<br>
 * Created at 2022/9/20 11:39
 *
 * @author vorbote
 */
public enum Asia implements ITimezone {

    SHANGHAI("Asia/Shanghai");

    private final String timezoneId;

    Asia(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    @Override
    public String getTimezoneId() {
        return timezoneId;
    }
}
