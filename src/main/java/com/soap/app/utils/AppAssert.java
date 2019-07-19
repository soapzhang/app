package com.soap.app.utils;

import com.soap.app.Exception.PlatformUncheckException;
import org.apache.commons.lang3.StringUtils;


public class AppAssert {
    /**
     * Assert that an object is <code>null</code> .
     *
     * <pre class="code">
     * FpiAssert.isNull(value, &quot;The value must be null&quot;);
     * </pre>
     *
     * @param object
     *            the object to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws PlatformUncheckException
     *             if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new PlatformUncheckException(message);
        }
    }

    /**
     * Assert that an object is <code>null</code> .
     *
     * <pre class="code">
     * FpiAssert.isNull(value);
     * </pre>
     *
     * @param object
     *            the object to check
     * @throws PlatformUncheckException
     *             if the object is not <code>null</code>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     *
     * <pre class="code">
     * FpiAssert.notNull(clazz, &quot;The class must not be null&quot;);
     * </pre>
     *
     * @param object
     *            the object to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws PlatformUncheckException
     *             if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new PlatformUncheckException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     *
     * <pre class="code">
     * FpiAssert.notNull(clazz);
     * </pre>
     *
     * @param object
     *            the object to check
     * @throws PlatformUncheckException
     *             if the object is <code>null</code>
     */
    public static void notNull(Object object) {
        notNull(object,
                "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that str is not empty.
     *
     * <pre class="code">
     * FpiAssert.notEmpty(str, &quot;The str must not be empty&quot;);
     * </pre>
     *
     * @param str
     *            the str to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws PlatformUncheckException
     *             if the str is <code>empy</code>
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new PlatformUncheckException(message);
        }
    }

    /**
     * Assert that str is not empty.
     *
     * <pre class="code">
     * FpiAssert.empty(str, &quot;The str must not be empty&quot;);
     * </pre>
     *
     * @param str
     *            the str to check
     * @throws PlatformUncheckException
     *             if the str is <code>empy</code>
     */
    public static void notEmpty(String str) {
        notEmpty(str,
                "[Assertion failed] - this argument is required;it must not be empty");
    }

    /**
     * Assert that str is not blank.
     *
     * <pre class="code">
     * FpiAssert.notBlank(str, &quot;The str must not be blank&quot;);
     * </pre>
     *
     * @param str
     *            the str to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws PlatformUncheckException
     *             if the str is <code>blank</code>
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new PlatformUncheckException(message);
        }
    }

    /**
     * Assert that str is not blank.
     *
     * <pre class="code">
     * FpiAssert.notBlank(str, &quot;The str must not be blank&quot;);
     * </pre>
     *
     * @param str
     *            the str to check
     * @throws PlatformUncheckException
     *             if the str is <code>blank</code>
     */
    public static void notBlank(String str) {
        notEmpty(str,
                "[Assertion failed] - this argument is required;it must not be blank");
    }
}
