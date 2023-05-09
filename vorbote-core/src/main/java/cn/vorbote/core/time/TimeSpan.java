package cn.vorbote.core.time;

import cn.vorbote.core.utils.CalculationUtil;

import java.util.Objects;

/**
 * Represents a time interval. We use this class to show diffs from 2 {@code DateTime} instances.
 *
 * @author vorbote
 */
public final class TimeSpan {

    static final long SECONDS_IN_A_DAY = 86400L;
    static final long SECONDS_IN_A_HOUR = 3600L;
    static final long SECONDS_IN_A_MINUTE = 60L;

    private long totalSeconds;

    /**
     * No-args constructor, you can use this to build a {@code TimeSpan} with 0 days, 0 hours, 0 minutes and 0 seconds.
     */
    public TimeSpan() {
    }

    /**
     * This constructor will build a {@code TimeSpan} with specific total seconds.
     *
     * @param totalSeconds The total seconds of this {@code TimeSpan}
     */
    public TimeSpan(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    /**
     * This constructor will build a {@code TimeSpan} with specific data.
     *
     * @param days    The days of this {@code TimeSpan}.
     * @param hours   The hours of this {@code TimeSpan}.
     * @param minutes The minutes of this {@code TimeSpan}.
     * @param seconds The seconds of this {@code TimeSpan}.
     */
    public TimeSpan(int days, int hours, int minutes, int seconds) {
        this.addDays(days);
        this.addHours(hours);
        this.addMinutes(minutes);
        this.addSeconds(seconds);
    }

    /**
     * Get total seconds.
     *
     * @return Total seconds.
     */
    public long getTotalSeconds() {
        return totalSeconds;
    }

    /**
     * Set total seconds.
     *
     * @param totalSeconds Total seconds.
     */
    public void setTotalSeconds(long totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    /**
     * Get the number of days between two times.
     *
     * @return The number of days between two times.
     */
    public int getDays() {
        return (int) (totalSeconds / SECONDS_IN_A_DAY);
    }

    /**
     * Get the number of hours between two times.
     *
     * @return The number of hours between two times.
     */
    public int getHours() {
        return ((int) ((totalSeconds - (getDays() * SECONDS_IN_A_DAY)) / SECONDS_IN_A_HOUR));
    }

    /**
     * Get the number of minutes between two times.
     *
     * @return The number of minutes between two times.
     */
    public int getMinutes() {
        return ((int) ((totalSeconds -
                (getDays() * SECONDS_IN_A_DAY) -
                (getHours() * SECONDS_IN_A_HOUR)) / SECONDS_IN_A_MINUTE));
    }

    /**
     * Get the number of seconds between two times.
     *
     * @return The number of seconds between two times.
     */
    public int getSeconds() {
        return (int) (totalSeconds % 60);
    }

    @Override
    public String toString() {
        return String.format("%d.%02d:%02d:%02d",
                getDays(), getHours(), getMinutes(), getSeconds());
    }

    /**
     * Get the total seconds in this {@code TimeSpan}.
     *
     * @return The total seconds in this {@code TimeSpan}.
     */
    public long totalSeconds() {
        return totalSeconds;
    }

    /**
     * Set total seconds of this time span.
     *
     * @param totalSeconds total seconds of this time span
     * @return the {@code TimeSpan} instance itself
     */
    public TimeSpan totalSeconds(long totalSeconds) {
        this.totalSeconds = totalSeconds;
        return this;
    }

    /**
     * Get the total hours in this {@code TimeSpan}.
     *
     * @return The total milliseconds in this {@code TimeSpan}.
     * @see #totalSeconds()
     */
    public double totalHours() {
        return CalculationUtil.startOf(totalSeconds()).divide(SECONDS_IN_A_HOUR).getDouble();
    }

    /**
     * Check that the given {@code Object} instance is equal to this instance.
     *
     * @param o The given {@code Object} instance,
     * @return Value {@code true} will be returned when the {@code Object} o is equal to this instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSpan timeSpan = (TimeSpan) o;
        return getDays() == timeSpan.getDays() && getHours() == timeSpan.getHours() && getMinutes() == timeSpan.getMinutes() && getSeconds() == timeSpan.getSeconds();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDays(), getHours(), getMinutes(), getSeconds());
    }

    /**
     * Generate a builder of {@code TimeSpan}.
     *
     * @return A builder of {@code TimeSpan}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Inner class {@code Builder} of {@code TimeSpan}.
     */
    public static class Builder {
        /**
         * The build result.
         */
        private final TimeSpan target;

        /**
         * Generate a new {@code Builder}, use protected to prevent other coders use it.
         */
        protected Builder() {
            target = new TimeSpan();
        }

        /**
         * Set the number of days between two times.
         *
         * @param days The number of days between two times.
         * @return {@code Builder} instance itself.
         */
        public Builder days(int days) {
            target.addDays(days);
            return this;
        }

        /**
         * Set the number of hours between two times.
         *
         * @param hours The number of hours between two times.
         * @return {@code Builder} instance itself.
         */
        public Builder hours(int hours) {
            target.addHours(hours);
            return this;
        }

        /**
         * Set the number of minutes between two times.
         *
         * @param minutes The number of minutes between two times.
         * @return {@code Builder} instance itself.
         */
        public Builder minutes(int minutes) {
            target.addMinutes(minutes);
            return this;
        }

        /**
         * Set the number of seconds between two times.
         *
         * @param seconds The number of seconds between two times.
         * @return {@code Builder} instance itself.
         */
        public Builder seconds(int seconds) {
            target.addSeconds(seconds);
            return this;
        }

        /**
         * Build a {@code TimeSpan} instance.
         *
         * @return A {@code TimeSpan} instance with specified data.
         */
        public TimeSpan build() {
            return target;
        }
    }

    /**
     * Add seconds to this {@code TimeSpan}.<br>
     * Note: This method support chain call.
     *
     * @param seconds Seconds need to be added to this {@code TimeSpan}.
     * @return The {@code TimeSpan} instance itself.
     */
    public TimeSpan addSeconds(int seconds) {
        this.totalSeconds += seconds;
        return this;
    }

    /**
     * Add minutes to this {@code TimeSpan}.<br>
     * Note: This method support chain call.
     *
     * @param minutes Minutes need to be added to this {@code TimeSpan}.
     * @return The {@code TimeSpan} instance itself.
     */
    public TimeSpan addMinutes(int minutes) {
        this.totalSeconds += minutes * SECONDS_IN_A_MINUTE;
        return this;
    }

    /**
     * Add hours to this {@code TimeSpan}.<br>
     * Note: This method support chain call.
     *
     * @param hours Hours need to be added to this {@code TimeSpan}.
     * @return The {@code TimeSpan} instance itself.
     */
    public TimeSpan addHours(int hours) {
        this.totalSeconds += hours * SECONDS_IN_A_HOUR;
        return this;
    }

    /**
     * Add days to this {@code TimeSpan}.<br>
     * Note: This method support chain call.
     *
     * @param days Days need to be added to this {@code TimeSpan}.
     * @return The {@code TimeSpan} instance itself.
     */
    public TimeSpan addDays(int days) {
        this.totalSeconds += days * SECONDS_IN_A_DAY;
        return this;
    }
}
