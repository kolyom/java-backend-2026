package ru.learning.wallet.exception;

import lombok.Getter;

@Getter
public class SameWalletTransferException extends RuntimeException {
    private final Long id;

    public SameWalletTransferException(Long id) {
        super("Cannot transfer to the same wallet: id " + id);
        this.id = id;
    }
}
