package com.soap.app.utils;

import com.soap.app.Exception.PlatformUncheckException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 日期时间格式
     */
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式
     */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(DateUtils.class);

    /**
     * 将日期字符串解析为日期对象。<br/>
     * 默认采用 yyyy-MM-dd 格式。
     *
     * @param str
     *            需解析的字符串
     * @return 解析后的日期对象。<br/>
     *         失败返回null
     * @throws PlatformUncheckException
     *             if argument str is null
     */
    public static Date parseDate(String str) {
        return parseDate(str, YYYY_MM_DD);
    }

    /**
     * 将日期字符串解析为日期对象。
     *
     * @param str
     *            需解析的字符串
     * @param format
     *            格式
     * @return 返回解析后的日期对象。<br/>
     *         失败返回null
     * @throws PlatformUncheckException
     *             if argument str or format is null
     *
     */
    public static Date parseDate(String str, String format) {
        try {
            AppAssert.notNull(str,
                    "this argument str is required; it must not be null");
            AppAssert.notNull(format,
                    "this argument format is required; it must not be null");
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        return null;
    }

    /**
     * 将日期格式化为字符串
     *
     * @param date
     *            需要格式化的日期
     * @param format
     *            格式
     * @return 格式化后的日期字符串
     * @throws PlatformUncheckException
     *             if argument date or format is null
     */
    public static String formatDate(Date date, String format) {
        AppAssert.notNull(date,
                "this argument date is required; it must not be null");
        AppAssert.notNull(format,
                "this argument format is required; it must not be null");
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将日期格式化为字符串<br/>
     * 默认采用 yyyy-MM-dd 格式。
     *
     * @param date
     *            需要格式化的日期
     * @return 格式化后的日期字符串
     * @throws PlatformUncheckException
     *             if argument date is null
     */
    public static String formatDate(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

    /**
     * 将字符串转换为时间对象<br/>
     * 默认采用“yyyy-MM-dd HH:mm:ss”格式
     *
     * @param str
     *            需要解析的时间字符串
     * @return 解析后的时间对象。<br/>
     *         转换失败返回null
     * @throws PlatformUncheckException
     *             if argument str is null
     */
    public static Date parseDateTime(String str) {
        return parseDateTime(str, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将字符串转换为时间对象<br/>
     *
     * @param str
     *            需要解析的时间字符串
     * @param format
     *            格式
     * @return 解析后的时间对象。<br/>
     *         转换失败返回null
     * @throws PlatformUncheckException
     *             if argument str or format is null
     */
    public static Date parseDateTime(String str, String format) {
        try {
            AppAssert.notNull(str,
                    "this argument str is required; it must not be null");
            AppAssert.notNull(format,
                    "this argument format is required; it must not be null");
            return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(str);
        } catch (ParseException e) {
            LOGGER.error(str);
        }
        return null;
    }

    /**
     * 将时间对象格式化为字符串。<br/>
     * 默认采用“yyyy-MM-dd HH:mm:ss”格式
     *
     * @param date
     *            需要格式化的时间对象
     * @return 格式化后的字符串
     * @throws PlatformUncheckException
     *             if params date is null
     */
    public static String formatDateTime(Date date) {
        return formatDateTime(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将时间对象格式化为字符串。<br/>
     *
     * @param date
     *            需要格式化的时间对象
     * @param format
     *            格式
     * @return 格式化后的字符串
     * @throws PlatformUncheckException
     *             if params date or format is null
     */
    public static String formatDateTime(Date date, String format) {
        AppAssert.notNull(date,
                "this argument date is required; it must not be null");
        AppAssert.notNull(format,
                "this argument format is required; it must not be null");
        return new SimpleDateFormat(format).format(date);
    }
}
