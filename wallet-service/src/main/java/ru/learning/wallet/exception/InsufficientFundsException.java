package ru.learning.wallet.exception;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {
    private final Long id;

    public InsufficientFundsException(Long id) {
        super("Wallet don't have need balance with id " + id);
        this.id = id;
    }
}
