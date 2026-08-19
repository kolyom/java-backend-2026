package ru.learning.wallet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.learning.wallet.dto.AmountRequest;
import ru.learning.wallet.dto.OperationResponse;
import ru.learning.wallet.dto.TransferRequest;
import ru.learning.wallet.dto.TransferResponse;
import ru.learning.wallet.dto.WalletRequest;
import ru.learning.wallet.dto.WalletResponse;
import ru.learning.wallet.entity.OperationType;
import ru.learning.wallet.entity.Wallet;
import ru.learning.wallet.entity.WalletOperation;
import ru.learning.wallet.exception.InsufficientFundsException;
import ru.learning.wallet.exception.SameWalletTransferException;
import ru.learning.wallet.exception.WalletNotFoundException;
import ru.learning.wallet.repository.WalletOperationRepository;
import ru.learning.wallet.repository.WalletRepository;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletOperationRepository walletOperationRepository;

    private WalletResponse toDto(Wallet wallet) {
        WalletResponse walletResponse = new WalletResponse();
        walletResponse.setBalance(wallet.getBalance());
        walletResponse.setOwner(wallet.getOwner());
        walletResponse.setId(wallet.getId());
        return walletResponse;
    }

    private OperationResponse toDto(WalletOperation walletOperation) {
        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setId(walletOperation.getId());
        operationResponse.setType(walletOperation.getType());
        operationResponse.setAmount(walletOperation.getAmount());
        operationResponse.setCounterpartyWalletId(walletOperation.getCounterpartyWalletId());
        operationResponse.setCreatedAt(walletOperation.getCreatedAt());
        return operationResponse;
    }

    private Wallet fromDto(WalletRequest walletRequest) {
        Wallet wallet = new Wallet();
        wallet.setOwner(walletRequest.getOwner());
        wallet.setBalance(walletRequest.getBalance());
        return wallet;
    }

    private WalletResponse applyBalanceChange(Long walletId, Long delta, OperationType type, Long counterpartyId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        if ((wallet.getBalance() + delta) >= 0) {
            wallet.setBalance(wallet.getBalance() + delta);
            Wallet saved = walletRepository.save(wallet);
            WalletOperation walletOperation = new WalletOperation();
            walletOperation.setWalletId(walletId);
            walletOperation.setType(type);
            walletOperation.setAmount((Long) Math.abs(delta));
            walletOperation.setCounterpartyWalletId(counterpartyId);
            walletOperation.setCreatedAt(LocalDateTime.now());
            WalletOperation savedWalletOperation = walletOperationRepository.save(walletOperation);
            return toDto(saved);
        } else
            throw new InsufficientFundsException(walletId);

    }

    public WalletResponse create(WalletRequest walletRequest) {
        Wallet wallet = fromDto(walletRequest);
        walletRepository.save(wallet);
        WalletResponse walletResponse = toDto(wallet);
        return walletResponse;
    }

    public WalletResponse findById(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        WalletResponse walletResponse = toDto(wallet);
        return walletResponse;
    }

    @Transactional
    public WalletResponse withdraw(Long id, AmountRequest amount) {
        return applyBalanceChange(id, -amount.getAmount(), OperationType.WITHDRAW, null);
    }

    @Transactional
    public WalletResponse deposit(Long id, AmountRequest amount) {

        return applyBalanceChange(id, +amount.getAmount(), OperationType.DEPOSIT, null);
    }

    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {
        if (transferRequest.getFromId().equals(transferRequest.getToId()))
            throw new SameWalletTransferException(transferRequest.getFromId());
        TransferResponse transferResponse = new TransferResponse();
        WalletResponse from = applyBalanceChange(transferRequest.getFromId(), -transferRequest.getAmount(),
                OperationType.TRANSFER_OUT,
                transferRequest.getToId());
        WalletResponse to = applyBalanceChange(transferRequest.getToId(), +transferRequest.getAmount(),
                OperationType.TRANSFER_IN,
                transferRequest.getFromId());
        transferResponse.setFrom(from);
        transferResponse.setTo(to);
        return transferResponse;

    }

    public List<OperationResponse> getOperations(Long id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        List<WalletOperation> operations = walletOperationRepository.findByWalletIdOrderByCreatedAtDesc(id);
        ArrayList<OperationResponse> result = new ArrayList<>();
        for (WalletOperation o : operations) {
            result.add(toDto(o));
        }
        return result;
    }
}
