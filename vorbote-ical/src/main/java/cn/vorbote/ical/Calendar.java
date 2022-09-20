package cn.vorbote.ical;

import lombok.experimental.Accessors;

import java.util.List;

/**
 * Calendar Instance, implements the document of RFC 5545.<br>
 * Created at 2022/9/20 10:37
 *
 * BEGIN:VCALENDAR
 * PRODID:-//iStudiez Team//CalDAV Client//EN
 * VERSION:2.0
 * X-WR-CALNAME:iStudiez Pro Calendar
 *
 * @author vorbote
 */
public final class Calendar {

    //
    // Fields
    //

    private String name;

    /**
     * Company name. This value is to specify the {@code productIdentifier}
     * property.
     */
    private String companyName;

    /**
     * Product name. This value is to specify the {@code productIdentifier}
     * property.
     */
    private String productName;

    /**
     * Calendar scale, referenced from <a href="https://icalendar.org/iCalendar-RFC-5545/3-7-1-calendar-scale.html"
     * >RFC 5545 - 3.7.1. Calendar Scale</a>.
     */
    private final String scale = "GREGORIAN";

    /**
     * Method, referenced from <a href="https://icalendar.org/iCalendar-RFC-5545/3-7-2-method.html"
     * >RFC 5545 - 3.7.2. Method</a>.
     */
    private String method;

    private final String version = "2.0";

    //
    // Constructors
    //


    //
    // Methods
    //
    public void addNode(CalendarNode node) {

    }

    public void addEvent(Event event) {

    }

    public String resolve() {
        return
                "BEGIN:VCALENDAR\n" +
                "PRODID:-//" + "";
    }

}
