package com.cdz.javacode.fsm.action;

import com.cdz.javacode.fsm.SwapOrder;
import com.cdz.javacode.fsm.SwapState;
import com.github.oxo42.stateless4j.delegates.Action1;

public class SwapUpdateMoneyAction implements Action1<SwapOrder> {
    @Override
    public void doIt(SwapOrder order) {
        order.setStatus(SwapState.COMPLETED);
    }
}
