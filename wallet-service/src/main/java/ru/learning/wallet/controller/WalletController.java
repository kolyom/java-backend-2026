package ru.learning.wallet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.learning.wallet.dto.AmountRequest;
import ru.learning.wallet.dto.OperationResponse;
import ru.learning.wallet.dto.TransferRequest;
import ru.learning.wallet.dto.TransferResponse;
import ru.learning.wallet.dto.WalletRequest;
import ru.learning.wallet.dto.WalletResponse;
import ru.learning.wallet.service.WalletService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/create")
    public WalletResponse createWallet(@RequestBody WalletRequest request) {
        return walletService.create(request);
    }

    @GetMapping("/{id}")
    public WalletResponse getWalletById(@PathVariable Long id) {
        return walletService.findById(id);
    }

    @PostMapping("/{id}/deposit")
    public WalletResponse depositWallet(@PathVariable Long id, @Valid @RequestBody AmountRequest amount) {
        return walletService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public WalletResponse withdrawWallet(@PathVariable Long id, @Valid @RequestBody AmountRequest amount) {
        return walletService.withdraw(id, amount);
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(@Valid @RequestBody TransferRequest transferRequest) {
        return walletService.transfer(transferRequest);
    }

    @GetMapping("/{id}/operations")
    public List<OperationResponse> getOperations(@PathVariable Long id) {
        return walletService.getOperations(id);
    }
}
