package com.yanwei.platform.common.utils;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;

/**
 * 日志处理工具类
 * @author luojianhong
 * @version $Id: LogUtils.java, v 0.1 2017年7月10日 下午5:30:32 luojianhong Exp $
 */
public abstract class LogUtils {

    /**
     * 使用logger将日志信息按照trace级别输出
     *
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void trace(Logger logger, String messageOrFormat, Object... messageParams) {
        trace(null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照trace级别输出
     *
     * @param throwable       异常堆栈
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void trace(Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        if (logger.isTraceEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }
            if (null == throwable) {
                logger.trace(message);
            } else {
                logger.trace(message, throwable);
            }
        }
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void debug(Logger logger, String messageOrFormat, Object... messageParams) {
        debug(null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param throwable       异常堆栈
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void debug(Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        if (logger.isDebugEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            if (null == throwable) {
                logger.debug(message);
            } else {
                logger.debug(message, throwable);
            }
        }
    }

    /**
     * 使用logger将日志信息按照info级别输出
     *
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void info(Logger logger, String messageOrFormat, Object... messageParams) {
        info(null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照info级别输出
     *
     * @param throwable       异常堆栈
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void info(Throwable throwable, Logger logger, String messageOrFormat,
                            Object... messageParams) {
        if (logger.isInfoEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            if (null == throwable) {
                logger.info(message);
            } else {
                logger.info(message, throwable);
            }
        }
    }

    /**
     * 使用logger将日志信息按照warn级别输出
     *
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void warn(Logger logger, String messageOrFormat, Object... messageParams) {
        warn(null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照warn级别输出
     *
     * @param throwable       异常堆栈
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void warn(Throwable throwable, Logger logger, String messageOrFormat,
                            Object... messageParams) {
        if (logger.isWarnEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            if (null == throwable) {
                logger.warn(message);
            } else {
                logger.warn(message, throwable);
            }
        }
    }

    /**
     * 使用logger将日志信息按照error级别输出
     *
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void error(Logger logger, String messageOrFormat, Object... messageParams) {
        error(null, logger, messageOrFormat, messageParams);
    }

    /**
     * 使用logger将日志信息按照debug级别输出
     *
     * @param throwable       异常堆栈
     * @param logger          输出日志的logger
     * @param messageOrFormat 日志格式.如:"这是一个测试日志%3d".如果没有日志参数messageParams,则直接输出本日志
     * @param messageParams   日志格式的参数
     */
    public static void error(Throwable throwable, Logger logger, String messageOrFormat,
                             Object... messageParams) {
        if (logger.isErrorEnabled()) {
            String message = messageOrFormat;
            if (!ArrayUtils.isEmpty(messageParams)) {
                message = String.format(messageOrFormat, messageParams);
            }

            if (null == throwable) {
                logger.error(message);
            } else {
                logger.error(message, throwable);
            }
        }
    }

}