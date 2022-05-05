package com.cdz.javacode.fsm.action;

import com.cdz.javacode.fsm.SwapOrder;
import com.cdz.javacode.fsm.SwapState;
import com.github.oxo42.stateless4j.delegates.Action1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapCreateOrderAction implements Action1<SwapOrder> {

    @Override
    public void doIt(SwapOrder order) {
        log.info("创建订单");
        order.setStatus(SwapState.WAIT_LOCK);
        log.info("创建订单end");
    }
}
