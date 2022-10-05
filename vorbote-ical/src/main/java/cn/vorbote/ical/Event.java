package cn.vorbote.ical;

import cn.vorbote.core.time.DateTime;
import cn.vorbote.core.time.TimeSpan;
import cn.vorbote.ical.config.CalendarConfig;
import cn.vorbote.ical.standards.ITimezone;
import cn.vorbote.ical.values.Classification;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Event<br>
 * Created at 2022/9/20 10:38
 *
 * @author vorbote
 */
public final class Event extends CalendarNode {

    //
    // Constant values
    //
    private final static String TAG = "VEVENT";

    //
    // Methods
    //

    /**
     * Add a batch of categories.
     *
     * @param categories A batch of categories.
     * @return The Event instance.
     */
    public Event addCategories(String... categories) {
        this.categories.addAll(Arrays.asList(categories));
        return this;
    }

    /**
     * Add a batch of categories.
     *
     * @param categories A batch of categories.
     * @return The Event instance.
     */
    public Event addCategories(Collection<String> categories) {
        this.categories.addAll(categories);
        return this;
    }

    /**
     * Add a category.
     *
     * @param category A category.
     * @return The Event instance.
     */
    public Event addCategory(String category) {
        this.categories.add(category);
        return this;
    }

    /**
     * Set the classification.
     *
     * @param classification The specified classification value.
     * @return The Event instance.
     */
    public Event setClassification(Classification classification) {
        this.classification = classification;
        return this;
    }

    /**
     * Set the comment.
     *
     * @param comment The comment.
     * @return The Event instance.
     */
    public Event setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Set the description.
     *
     * @param description The description.
     * @return The Event instance.
     */
    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set the location.
     *
     * @param location The location.
     * @return The Event instance.
     */
    public Event setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Set the percent complete value.
     *
     * @param percentComplete The percent complete value.
     * @return The Event instance.
     */
    public Event setPercentComplete(Integer percentComplete) {
        if (percentComplete < 0 || percentComplete > 100) {
            throw new IllegalArgumentException("Percent out of range (0 ~ 100)");
        }
        this.percentComplete = percentComplete;
        return this;
    }

    /**
     * Set a priority.
     *
     * @param priority The priority to be set.
     * @return The Event instance.
     */
    public Event setPriority(Integer priority) {
        if (priority < 0 || priority > 9) {
            throw new IllegalArgumentException("The priority you provide is out of range (0 ~ 9).");
        }
        this.priority = priority;
        return this;
    }

    /**
     * Set the summary.
     *
     * @param summary The summary (you can also call it as a title).
     * @return The Event instance.
     */
    public Event setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * Set the end of this node.
     *
     * @param end The end time of this event.
     * @return The Event instance.
     */
    public Event setEnd(DateTime end) {
        if (this.duration != null) {
            throw new IllegalStateException("You have set the field DURATION before, please remove it or remove setEnd.");
        }
        this.end = end;
        return this;
    }

    /**
     * Set the start of this event.
     *
     * @param start The date time specify the start time of this event.
     * @return The Event instance.
     */
    public Event setStart(DateTime start) {
        this.start = start;
        return this;
    }

    /**
     * Set the duration of this event.
     *
     * @param duration The duration of this event.
     * @return The Event instance.
     */
    public Event setDuration(TimeSpan duration) {
        if (this.end != null) {
            throw new IllegalStateException("You have set the field END before, please remove it or remove setDuration.");
        }
        this.duration = duration;
        return this;
    }

    /**
     * Set the URL.
     *
     * @param url The URL.
     * @return The Event instance.
     */
    public Event setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Set the uid of this event.
     *
     * @param uid The uid.
     * @return The Event instance.
     */
    public Event setUid(Long uid) {
        this.uid = uid;
        return this;
    }

    /**
     * Set the domain name of this event.
     *
     * @param domainName The domain name.
     * @return The Event instance.
     */
    public Event setDomainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    /**
     * Set the timezone of this event.
     *
     * @param timezone The time zone to set.
     * @return The Event instance.
     */
    public Event setTimezone(ITimezone timezone) {
        this.timezone = timezone;
        return this;
    }

    @Override
    public String resolve() {
        return "\nBEGIN:" + TAG + "\n" +
                "UID:" + uid + "@" + domainName + "\n" +
                "SUMMARY:" + summary + "\n" +
                "DTSTART" + Optional.ofNullable(timezone).map(item -> ";TZID=" + item.getTimezoneId()).orElse("") + ":" + start.pattern(CalendarConfig.UTC_FORMAT) + "\n" +
                Optional.ofNullable(categories)
                        .map((item) -> {
                            if (!item.isEmpty()) {
                                return "CATEGORIES:" + resolveCategories() + "\n";
                            }
                            return null;
                        }).orElse("") +
                Optional.ofNullable(duration)
                        .map((item) -> "DURATION:PT" + item.totalSeconds() + "S\n").orElse("") +
                Optional.ofNullable(end)
                        .map((item) -> "DTEND:" + end.pattern(CalendarConfig.UTC_FORMAT) + "\n").orElse("") +
                Optional.ofNullable(classification)
                        .map((item) -> "CLASS:" + item.getValue() + "\n").orElse("") +
                Optional.ofNullable(comment).map((item) -> "COMMENT:" + item + "\n").orElse("") +
                Optional.ofNullable(description).map((item) -> "DESCRIPTION:" + item + "\n").orElse("") +
                Optional.ofNullable(location).map((item) -> "LOCATION:" + item + "\n").orElse("") +
                Optional.ofNullable(percentComplete).map((item) -> "PERCENT-COMPLETE:" + item + "\n").orElse("") +
                Optional.ofNullable(priority).map((item) -> "PRIORITY:" + item + "\n").orElse("") +
                "END:" + TAG + "\n";
    }
}
