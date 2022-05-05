package com.cdz.javacode.fsm.action;

import com.cdz.javacode.fsm.SwapOrder;
import com.cdz.javacode.fsm.SwapState;
import com.github.oxo42.stateless4j.delegates.Action1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwapCreateBeforeOrderCheckAction implements Action1<SwapOrder> {
    @Override
    public void doIt(SwapOrder order) {
        log.info("SwapCreateBeforeOrderCheck: start");
        order.setStatus(SwapState.INIT);
        log.info("order = {}", order);
        log.info("SwapCreateBeforeOrderCheck: end");
    }
}
