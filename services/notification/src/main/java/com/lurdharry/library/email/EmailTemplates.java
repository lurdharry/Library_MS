package com.lurdharry.library.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplates {

    BORROW_CONFIRMATION("borrow-confirmation.html","Borrow order processed"),
    STATUS_UPDATE("status-confirmation.html","Order status updated");

    private final String template;
    private final String subject;
}
