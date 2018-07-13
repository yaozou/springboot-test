package com.yaozou.platform.common.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据库主键生成器
 * @author luojianhong
 * @version $Id: sequence.java, v 0.1 2017年11月17日 下午1:59:18 luojianhong Exp $
 */
public class GeneratorId {

    private static final long ONE_STEP  = 10;
    private static final Lock LOCK      = new ReentrantLock();
    private static long       lastTime  = System.currentTimeMillis();
    private static short      lastCount = 0;
    private static int        count     = 0;

    @SuppressWarnings("finally")
    public static String nextStringId() {
        LOCK.lock();
        try {
            if (lastCount == ONE_STEP) {
                boolean done = false;
                while (!done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        try {
                            Thread.currentThread();
                            Thread.sleep(1);
                        } catch (java.lang.InterruptedException e) {
                        }
                        continue;
                    } else {
                        lastTime = now;
                        lastCount = 0;
                        done = true;
                    }
                }
            }
            count = lastCount++;
        } finally {
            LOCK.unlock();
            return lastTime + "" + String.format("%03d", count);
        }
    }

    public static Long nextLongId() {
        return Long.parseLong(nextStringId());
    }

}