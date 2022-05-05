package com.cdz.javacode.fsm;

import com.github.oxo42.stateless4j.triggers.TriggerWithParameters1;

/**
 * event：
 * 1. 创建订单
 * 2. 锁定资金
 * 3. PDT发单
 * 4. 更新资金
 * 5. 取消订单
 */
public enum SwapEvent  {

    //check order
    CHECK_ORDER,
    // 创建订单
    CREATE_ORDER,
    // 锁定资金
    LOCK_FUNDS,
    // PDT发单
    PDT_SEND_ORDER,
    // 更新资金
    UPDATE_ASSET,
    // 取消订单
    CANCEL_ORDER,
    ;
}
