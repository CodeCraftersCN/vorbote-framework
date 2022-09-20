package cn.vorbote.ical.values;

/**
 * Classification<br>
 * Created at 20/09/2022 13:45
 *
 * @author theod
 */
public enum Classification {

    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE"),
    CONFIDENTIAL("CONFIDENTIAL");

    private final String value;

    Classification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
