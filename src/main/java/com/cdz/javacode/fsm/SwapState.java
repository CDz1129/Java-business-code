package com.cdz.javacode.fsm;

/**
 * status：
 * 1. 待锁定资金
 * 2. 待取消
 * 3. 待PDT发单
 * 4. 待更新资金
 * 5. 已取消订单
 * 6. 已完成订单
 */
public enum SwapState {

    //准备状态
    WAITING,
    // 订单初始化
    INIT,
    // 待锁定资金
    WAIT_LOCK,
    // 待取消
    WAIT_CANCEL,
    // 待PDT发单
    WAIT_PDT_SEND,
    // 待更新资金
    WAIT_UPDATE_MONEY,
    // 已取消订单
    CANCELED,
    // 已完成订单
    COMPLETED
}
