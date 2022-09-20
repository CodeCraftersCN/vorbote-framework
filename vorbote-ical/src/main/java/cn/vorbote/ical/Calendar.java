package cn.vorbote.ical;

import cn.vorbote.core.utils.CollectionUtil;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Calendar Instance, implements the document of RFC 5545.<br>
 * Created at 2022/9/20 10:37
 * <p>
 *
 * @author vorbote
 */
public final class Calendar {

    //
    // Constants
    //

    // Tag
    private final static String TAG = "VCALENDAR";

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

    private List<CalendarNode> nodes;

    //
    // Constructors
    //
    public Calendar() {
        this.nodes = new ArrayList<>();
    }

    //
    // Methods
    //

    /**
     * Set the name for this calendar.
     *
     * @param name The name for the calendar.
     * @return The calendar instance.
     */
    public Calendar setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the company name for this calendar.
     *
     * @param companyName The company name for the calendar.
     * @return The calendar instance.
     */
    public Calendar setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * Set the product name for this calendar.
     *
     * @param productName The product name for the calendar.
     * @return The calendar instance.
     */
    public Calendar setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    /**
     * Set the method for this calendar.
     *
     * @param method The product name for the calendar.
     * @return The calendar instance.
     */
    public Calendar setMethod(String method) {
        this.method = method;
        return this;
    }

    /**
     * Add a calendar node to this calendar.
     *
     * @param node Any calendar node.
     * @return The calendar instance.
     */
    public Calendar addEvent(CalendarNode node) {
        this.nodes.add(node);
        return this;
    }

    /**
     * Resolve the calendar instance to a text that implements RFC-5545.
     *
     * @return A string includes all events in this calendar.
     */
    public String resolve() {
        var events = new StringBuilder();
        if (CollectionUtil.isNotEmpty(nodes)) {
            nodes.forEach(item -> events.append(item.resolve()));
        }

        return "BEGIN:" + TAG + "\n" +
                "PRODID:-//" + companyName + "//" + productName + "//EN\n" +
                "VERSION:" + version + "\n" +
                "X-WR-CALNAME:" + name + "\n" +
                events + "\n" +
                "END:" + TAG;
    }

}
