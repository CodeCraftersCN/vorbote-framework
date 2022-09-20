package cn.vorbote.ical.values;

import cn.vorbote.ical.standards.ITimezone;

/**
 * Africa<br>
 * Created at 2022/9/20 16:31
 *
 * @author vorbote
 */
public enum Africa implements ITimezone {

    BUJUMBURA("Africa/Bujumbura"),

    GABORONE("Africa/Gaborone"),

    LUBUMBASHI("Africa/Lubumbashi"),

    MAPUTO("Africa/Maputo");

    Africa(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    private final String timezoneId;

    @Override
    public String getTimezoneId() {
        return timezoneId;
    }
}
