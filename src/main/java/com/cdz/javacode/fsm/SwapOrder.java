package com.cdz.javacode.fsm;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SwapOrder {
    private String orderId;
    private String userId;
    private String error;
    private BigDecimal lockAmount;
    private BigDecimal quantity;
    private SwapState status;
    private String fromCoin;
    private String toCoin;
}
