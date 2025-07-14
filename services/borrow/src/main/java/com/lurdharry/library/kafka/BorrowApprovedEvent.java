package com.lurdharry.library.kafka;

import com.lurdharry.library.borrow.BorrowOrder;

public record BorrowApprovedEvent(String orderId, BorrowOrder order) {
}
