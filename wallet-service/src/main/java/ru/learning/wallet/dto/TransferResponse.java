package ru.learning.wallet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferResponse {
    private WalletResponse from;
    private WalletResponse to;
}
