package com.cdz.javacode.fsm;

import com.cdz.javacode.fsm.action.*;
import com.github.oxo42.stateless4j.StateConfiguration;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action1;
import com.github.oxo42.stateless4j.delegates.Func2;
import com.github.oxo42.stateless4j.transitions.Transition;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters1;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

@Slf4j
public class StateMachineDemo {

    public static void main(String[] args) {
        SwapOrder order = new SwapOrder();
        order.setOrderId("123");
        order.setUserId("123");
        order.setStatus(SwapState.WAITING);
        order.setLockAmount(BigDecimal.ZERO);
        order.setQuantity(BigDecimal.TEN);
        StateMachineConfig<SwapState, SwapEvent> swapSMConfig = new StateMachineConfig<>();

        swapSMConfig.configure(SwapState.WAITING)
                .permitDynamic(new TriggerWithParameters1(SwapEvent.CHECK_ORDER,SwapOrder.class),o -> SwapState.INIT,new SwapCreateBeforeOrderCheckAction());
        swapSMConfig.configure(SwapState.INIT)
                .permitDynamic(new TriggerWithParameters1(SwapEvent.CREATE_ORDER,SwapOrder.class), o -> SwapState.WAIT_LOCK,
                        new SwapCreateOrderAction());
        swapSMConfig.configure(SwapState.WAIT_LOCK)
                .permitDynamic(new TriggerWithParameters1(SwapEvent.LOCK_FUNDS,SwapOrder.class), o -> SwapState.WAIT_PDT_SEND,
                                new SwapLockFundAction());
        swapSMConfig.configure(SwapState.WAIT_PDT_SEND)
                .permitDynamic(new TriggerWithParameters1(SwapEvent.PDT_SEND_ORDER,SwapOrder.class), o -> SwapState.WAIT_UPDATE_MONEY,
        new SwapSendPdtAction());
        swapSMConfig.configure(SwapState.WAIT_UPDATE_MONEY)
                .permitDynamic(new TriggerWithParameters1(SwapEvent.UPDATE_ASSET,SwapOrder.class), o -> SwapState.COMPLETED,
                       new SwapUpdateMoneyAction());
        swapSMConfig.configure(SwapState.WAIT_CANCEL)
                .permitDynamic(new TriggerWithParameters1(SwapEvent.CANCEL_ORDER,SwapOrder.class), o -> SwapState.CANCELED,
            new SwapCancelOrderAction());


        StateMachine<SwapState, SwapEvent> machine = new StateMachine<>(order.getStatus(), swapSMConfig);

        machine.fire(new TriggerWithParameters1(SwapEvent.CHECK_ORDER,SwapOrder.class),order);
        machine.fire(new TriggerWithParameters1(SwapEvent.CREATE_ORDER,SwapOrder.class),order);
        machine.fire(new TriggerWithParameters1(SwapEvent.LOCK_FUNDS,SwapOrder.class),order);
        machine.fire(new TriggerWithParameters1(SwapEvent.PDT_SEND_ORDER,SwapOrder.class),order);
        machine.fire(new TriggerWithParameters1(SwapEvent.UPDATE_ASSET,SwapOrder.class),order);

        log.info("{}",order);
        StateMachine<SwapState, SwapEvent> machine1 = new StateMachine<>(SwapState.WAIT_CANCEL, swapSMConfig);
        order.setStatus(SwapState.WAIT_CANCEL);
        machine1.fire(new TriggerWithParameters1(SwapEvent.CANCEL_ORDER,SwapOrder.class),order);
        log.info("{}",order);
    }

}
