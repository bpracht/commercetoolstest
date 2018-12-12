package com.pracht.commercetools.common.service;

public class ChangeLoggerService {
    protected String quotedDescription(String description) {
        final char quote='"'; // Used to avoid ugly escaping
        return quote+description+quote;
    }
}
