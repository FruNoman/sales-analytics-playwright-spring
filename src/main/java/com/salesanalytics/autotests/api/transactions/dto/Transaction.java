package com.salesanalytics.autotests.api.transactions.dto;

public record Transaction(String id, String date, String region, String category, int revenue) {}
