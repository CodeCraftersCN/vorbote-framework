package cn.vorbote.ical.values;

import cn.vorbote.ical.standards.ITimezone;

/**
 * America<br>
 * Created at 2022/9/20 16:32
 *
 * @author vorbote
 */
public enum America implements ITimezone {
    RIO_BRANCO("America/Rio_Branco"),
    ;

    America(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    private final String timezoneId;

    @Override
    public String getTimezoneId() {
        return timezoneId;
    }
}
