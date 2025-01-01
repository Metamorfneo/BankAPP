package com.grupito.springbank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {
    @Schema(
            name = "Nombre de la cuenta"
    )
    private String accountName;
    @Schema(
            name = "Balance de la cuenta"
    )
    private BigDecimal accountBalance;
    @Schema(
            name = "Numero de la cuenta"
    )
    private String accountNumber;
}
