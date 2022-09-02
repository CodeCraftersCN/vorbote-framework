package cn.vorbote.core.utils;


import cn.vorbote.core.exceptions.SnowFlakeException;

/**
 * Distributed Sequence Generator.
 */
public final class SnowFlake {

    // region Fields

    /**
     * 开始时间截 (2022-6-1)
     */
    private static final long DEFAULT_START_EPOCH = 1_654_012_800_000L;

    private final long startEpoch;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long dataCentreIdBits = 5L;

    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private final long dataCentreId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;
    // endregion

    // region Constructor

    /**
     * Constructor can build a new SnowFlake instance.
     *
     * @param workerId     The id of the server. (Should between 0 and 31)
     * @param dataCentreId The id of the data centre. (Should between 0 and 31)
     */
    public SnowFlake(long workerId, long dataCentreId) {
        this(DEFAULT_START_EPOCH, workerId, dataCentreId);
    }

    /**
     * Constructor can build a new SnowFlake instance.
     *
     * @param startEpoch   The start epoch to calculate.
     * @param workerId     The id of the server. (Should between 0 and 31)
     * @param dataCentreId The id of the data centre. (Should between 0 and 31)
     */
    public SnowFlake(long startEpoch, long workerId, long dataCentreId) {
        if (startEpoch > currentTimestamp()) {
            throw new IllegalArgumentException("Start Epoch can not be greater than current timestamp!");
        }

        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }

        long maxDataCentreId = ~(-1L << dataCentreIdBits);
        if (dataCentreId > maxDataCentreId || dataCentreId < 0) {
            throw new IllegalArgumentException(String.format("Data Centre Id can't be greater than %d or less than 0",
                    maxDataCentreId));
        }

        this.startEpoch = startEpoch;
        this.workerId = workerId;
        this.dataCentreId = dataCentreId;
    }
    // endregion

    // region Methods

    /**
     * Get next unique id.
     *
     * @return A unique id.
     */
    public synchronized long nextId() {
        long timestamp = currentTimestamp();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new SnowFlakeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = awaitToNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        long timestampLeftShift = sequenceBits + workerIdBits + dataCentreIdBits;
        long dataCentreIdShift = sequenceBits + workerIdBits;
        return ((timestamp - startEpoch) << timestampLeftShift)
                | (dataCentreId << dataCentreIdShift)
                | (workerId << sequenceBits)
                | sequence;
    }

    /**
     * Block until the next millisecond until a new timestamp is obtained.
     *
     * @param lastTimestamp The timestamp when id was created last time.
     * @return Current timestamp.
     */
    private long awaitToNextMillis(long lastTimestamp) {
        long timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    /**
     * Return current timestamp with the unit of millisecond.
     *
     * @return Current time.
     */
    private long currentTimestamp() {
        return System.currentTimeMillis();
    }
    // endregion
}