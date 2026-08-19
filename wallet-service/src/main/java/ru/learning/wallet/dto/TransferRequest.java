package ru.learning.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferRequest {
    @NotNull
    private Long fromId;
    @NotNull
    private Long toId;

    @NotNull
    @Positive
    private Long amount;

}
