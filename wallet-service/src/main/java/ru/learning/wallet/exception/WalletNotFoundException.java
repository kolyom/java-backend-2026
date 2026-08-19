package ru.learning.wallet.exception;

import lombok.Getter;

@Getter
public class WalletNotFoundException extends RuntimeException {
    private final Long id;

    public WalletNotFoundException(Long id) {
        super("Wallet not found with id" + id);
        this.id = id;
    }

}
