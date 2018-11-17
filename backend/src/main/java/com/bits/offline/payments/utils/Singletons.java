package com.bits.offline.payments.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public enum Singletons {

    INSTANCE;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss");

    public ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public DateFormat getDateFormat() {
        return DATE_FORMAT;
    }
}
