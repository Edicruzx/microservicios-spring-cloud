package com.mitocode.microservices.saga.model;

public enum SagaStatus { STARTED, STOCK_RESERVED, COMPLETED, COMPENSATING, COMPENSATED, FAILED }
