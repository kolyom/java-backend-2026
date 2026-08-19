package ru.learning.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWalletNotFound(WalletNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInsufficientFundsException(InsufficientFundsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SameWalletTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSameWalletTransferException(SameWalletTransferException ex) {
        return ex.getMessage();
    }
}
