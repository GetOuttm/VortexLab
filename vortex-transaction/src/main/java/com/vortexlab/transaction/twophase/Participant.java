package com.vortexlab.transaction.twophase;

public interface Participant {

    /**
     * 第一阶段
     */
    boolean prepare();

    /**
     * 第二阶段
     */
    void commit();

    /**
     * 回滚
     */
    void rollback();
}
