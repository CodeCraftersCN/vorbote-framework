package cn.vorbote.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>
 * Calculation Util can help you with high precision math calculation.
 * </p>
 *
 * <p>
 * This util is made by Github User @sunzsh, and the source code location is <a
 * href="https://gist.github.com/sunzsh/ddaaa065335f4c4b94aba7a1ea47b5ee">Github Gist</a>
 * </p>
 *
 * <p>
 * Note: Some changes was made by Github user @vorbote because of the deprecation of method
 * {@link BigDecimal#setScale(int, int)}, all call to this method was moved to
 * {@link BigDecimal#setScale(int, RoundingMode)}.
 * </p>
 * Created at 5/26/2022 11:31 PM
 *
 * @author sunzsh
 * @author vorbote
 */
public class CalculationUtil {

    private BigDecimal value;

    /**
     * Make the constructor private and protect it being initialized from outside.
     *
     * @param value The number value.
     */
    private CalculationUtil(Number value) {
        this.value = convertBigDecimal(value, null);
    }

    /**
     * The initial value of start.
     *
     * @param value The initial value.
     * @return The {@code CalculationUtil} instance.
     */
    public static CalculationUtil startOf(Number value) {
        return new CalculationUtil(value);
    }

    /**
     * Add a number to the original figure.
     *
     * @param other The other number.
     * @return The calculated instance.
     */
    public CalculationUtil add(Number other) {
        return operator(BigDecimal::add, other);
    }

    /**
     * Calc: the original value <b>add</b> the other number.
     *
     * @param other              The other number to be added.
     * @param beforeOperateScale Round {@code other} to {@code beforeOperateScale} decimal places before doing adding.
     * @return The calculated instance.
     */
    public CalculationUtil add(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::add, other, beforeOperateScale);
    }

    /**
     * Calc: the original value <b>subtract</b> the other number.
     *
     * @param other The value to be subtracted.
     * @return The calculated instance.
     */
    public CalculationUtil subtract(Number other) {
        return operator(BigDecimal::subtract, other);
    }

    /**
     * Calc: the original value <b>subtract</b> the other number.
     *
     * @param other              The value to be subtracted.
     * @param beforeOperateScale Round {@code other} to {@code beforeOperateScale} decimal places before doing
     *                           subtracting.
     * @return The calculated instance.
     */
    public CalculationUtil subtract(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::subtract, other, beforeOperateScale);
    }

    /**
     * Calc: the original value <b>multiply</b> the other number.
     *
     * @param other The value to be multiplied.
     * @return The calculated instance.
     */
    public CalculationUtil multiply(Number other) {
        return operator(BigDecimal::multiply, other);
    }

    /**
     * Calc: the original value <b>multiply</b> the other number.
     *
     * @param other              The value to be multiplied.
     * @param beforeOperateScale Round {@code other} to {@code beforeOperateScale} decimal places before doing
     *                           multiplication.
     * @return The calculated instance.
     */
    public CalculationUtil multiply(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::multiply, other, beforeOperateScale);
    }

    /**
     * Calc: the original value <b>divide</b> the other number.
     *
     * @param other The value to be divided.
     * @return The calculated instance.
     */
    public CalculationUtil divide(Number other) {
        return operator(BigDecimal::divide, other);
    }

    /**
     * Calc: the original value <b>divide</b> the other number.
     *
     * @param other              The value to be multiplied.
     * @param beforeOperateScale Round {@code other} to {@code beforeOperateScale} decimal places before doing
     *                           multiplication.
     * @return The calculated instance.
     */
    public CalculationUtil divide(Number other, Integer beforeOperateScale) {
        return operator(BigDecimal::divide, other, beforeOperateScale);
    }

    /**
     * Calc: the original value <b>divide</b> the other number.
     *
     * @param other The value to be multiplied.
     * @param scale The result is rounded to {@code scale} decimal places.
     * @return The calculated instance.
     */
    public CalculationUtil divideWithScale(Number other, Integer scale) {
        return baseOperator(otherValue ->
                this.value.divide(otherValue, scale, RoundingMode.HALF_UP), other, null);
    }

    /**
     * Calc: the original value <b>divide</b> the other number.
     *
     * @param other              The value to be multiplied.
     * @param scale              The result is rounded to {@code scale} decimal places.
     * @param beforeOperateScale Round {@code other} to {@code beforeOperateScale} decimal places before doing dividing.
     * @return The calculated instance.
     */
    public CalculationUtil divideWithScale(Number other, Integer scale, Integer beforeOperateScale) {
        return baseOperator(otherValue -> this.value.divide(otherValue, scale, RoundingMode.HALF_UP), other, beforeOperateScale);
    }

    /**
     * Get the value of the calculation in {@code BigDecimal}.
     *
     * @return The result of the figure.
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Get the value of the calculation in {@code BigDecimal}.
     *
     * @param scale The result is rounded to {@code scale} decimal places.
     * @return The result of the figure.
     */
    public BigDecimal getValue(int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Get the value of the calculation in {@code Double}.
     *
     * @return The result of the figure.
     */
    public Double getDouble() {
        return getValue().doubleValue();
    }

    /**
     * Get the value of the calculation in {@code Double}.
     *
     * @param scale The result is rounded to {@code scale} decimal places.
     * @return The result of the figure.
     */
    public Double getDouble(int scale) {
        return getValue(scale).doubleValue();
    }

    /**
     * Get the value of the calculation in {@code Long}.
     *
     * @return The result of the figure.
     */
    public Long getLong() {
        return getValue().longValue();
    }

    /**
     * Get the value of the calculation in {@code Integer}.
     *
     * @return The result of the figure.
     */
    public Integer getInteger() {
        return getValue().intValue();
    }

    private CalculationUtil operator(BiFunction<BigDecimal, BigDecimal, BigDecimal> operator, Object other) {
        return operator(operator, other, 9);
    }

    private CalculationUtil operator(BiFunction<BigDecimal, BigDecimal, BigDecimal> operator, Object other, Integer beforeOperateScale) {
        return baseOperator((otherValue) -> operator.apply(this.value, otherValue), other, beforeOperateScale);
    }

    private synchronized CalculationUtil baseOperator(Function<BigDecimal, BigDecimal> operatorFunction, Object other, Integer beforeOperateScale) {
        if (other == null) {
            return this;
        }
        if (other instanceof CalculationUtil) {
            this.value = operatorFunction.apply(((CalculationUtil) other).getValue());
            return this;
        }
        this.value = operatorFunction.apply(convertBigDecimal(other, beforeOperateScale));
        return this;
    }

    private BigDecimal convertBigDecimal(Object value, Integer scale) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal res;
        if (value instanceof Number) {
            res = BigDecimal.valueOf(((Number) value).doubleValue());
        } else {
            try {
                res = new BigDecimal(value.toString());
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }

        if (scale != null) {
            res = BigDecimal.valueOf(res.setScale(scale, RoundingMode.HALF_UP).doubleValue());
        }
        return res;
    }

}
