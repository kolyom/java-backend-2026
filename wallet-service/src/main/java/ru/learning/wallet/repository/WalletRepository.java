package ru.learning.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.learning.wallet.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
