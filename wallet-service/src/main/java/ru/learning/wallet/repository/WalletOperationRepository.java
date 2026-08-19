package ru.learning.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.learning.wallet.entity.WalletOperation;

public interface WalletOperationRepository extends JpaRepository<WalletOperation, Long> {
    public List<WalletOperation> findByWalletIdOrderByCreatedAtDesc(Long walletId);
}
