package ru.learning.wallet.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.learning.wallet.entity.OperationType;

@Data
@NoArgsConstructor
public class OperationResponse {
    private Long id;
    private OperationType type;
    private Long amount;
    private Long counterpartyWalletId;
    private LocalDateTime createdAt;
}
