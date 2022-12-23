package cn.vorbote.core.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * BranchUtil<br>
 * Created at 20/09/2022 15:07
 *
 * @author theod
 */
public final class BranchUtil<T> {

    private BranchUtil(boolean result) {
        this.result = result;
    }

    private final boolean result;

    /**
     * If any of the {@code booleans} has a value of {@code true}, the <b>if</b>
     * branch of the subsequent method will be called, otherwise the else branch.
     * This method is equivalent to Java's native logical <b>or</b> operation.
     *
     * @param booleans The boolean data or boolean expression to be checked.
     * @param <T>      The type of data that will be returned to the if or else
     *                 branch.
     * @return A BranchUtil instance that supports chain calls.
     */
    public static <T> BranchUtil<T> anyMatch(Boolean... booleans) {
        boolean result = Arrays.stream(booleans)
                .filter(Objects::nonNull)
                .anyMatch(Boolean::booleanValue);
        return new BranchUtil<>(result);
    }

    /**
     * If all of the {@code booleans} is value of {@code true}, the <b>if</b>
     * branch of the subsequent method will be called, otherwise the else branch.
     * This method is equivalent to Java's native logical <b>and</b> operation.
     *
     * @param booleans The boolean data or boolean expression to be checked.
     * @param <T>      The type of data that will be returned to the if or else
     *                 branch.
     * @return A BranchUtil instance that supports chain calls.
     */
    public static <T> BranchUtil<T> allMatch(Boolean... booleans) {
        boolean result = Arrays.stream(booleans)
                .filter(Objects::nonNull)
                .allMatch(Boolean::booleanValue);
        return new BranchUtil<>(result);
    }

    /**
     * If any of the {@code booleans} has a value of {@code true}, the <b>if</b>
     * branch of the subsequent method will be called, otherwise the else branch.
     * This method is equivalent to Java's native logical <b>or</b> operation.
     *
     * @param booleanSuppliers The method which of each provides a boolean value.
     * @param <T>              The type of data that will be returned to the if
     *                         or else branch.
     * @return A BranchUtil instance that supports chain calls.
     */
    public static <T> BranchUtil<T> anyMatch(BooleanSupplier... booleanSuppliers) {
        boolean result = Arrays.stream(booleanSuppliers)
                .filter(Objects::nonNull)
                .anyMatch(BooleanSupplier::getAsBoolean);
        return new BranchUtil<>(result);
    }

    /**
     * If all of the {@code booleans} is value of {@code true}, the <b>if</b>
     * branch of the subsequent method will be called, otherwise the else branch.
     * This method is equivalent to Java's native logical <b>and</b> operation.
     *
     * @param booleanSuppliers The methods which of each provides a boolean value.
     * @param <T>              The type of data that will be returned to the if
     *                         or else branch.
     * @return A BranchUtil instance that supports chain calls.
     */
    public static <T> BranchUtil<T> allMatch(BooleanSupplier... booleanSuppliers) {
        boolean result = Arrays.stream(booleanSuppliers)
                .filter(Objects::nonNull)
                .allMatch(BooleanSupplier::getAsBoolean);
        return new BranchUtil<>(result);
    }

    /**
     * This method provides the methods that will be executed by each branch.
     *
     * @param ifSupplier   A method will be called when the condition is reached.
     * @param elseSupplier A method will be called when the condition is not
     *                     reached.
     * @return The return value of the method.
     */
    public T handle(Supplier<T> ifSupplier, Supplier<T> elseSupplier) {
        if (this.result && null != ifSupplier) {
            return ifSupplier.get();
        }

        if (null == elseSupplier) {
            return null;
        }

        return elseSupplier.get();
    }

    /**
     * This method provides the methods that will be executed by each branch.
     *
     * @param ifSupplier A method will be called when the condition is reached.
     * @return The return value of the method.
     */
    public T handle(Supplier<T> ifSupplier) {
        return handle(ifSupplier, null);
    }

    /**
     * This method provides the methods that will be executed by each branch.
     *
     * @param ifRunnable   A method will be called when the condition is reached.
     * @param elseRunnable A method will be called when the condition is not
     *                     reached.
     */
    public void handle(Runnable ifRunnable, Runnable elseRunnable) {
        if (this.result && null != ifRunnable) {
            ifRunnable.run();
            return;
        }

        if (null == elseRunnable) {
            return;
        }

        elseRunnable.run();
    }

    /**
     * This method provides the methods that will be executed by each branch.
     *
     * @param ifRunnable A method will be called when the condition is reached.
     */
    public void handle(Runnable ifRunnable) {
        handle(ifRunnable, null);
    }

}
