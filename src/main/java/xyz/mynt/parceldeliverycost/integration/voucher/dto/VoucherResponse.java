package xyz.mynt.parceldeliverycost.integration.voucher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "code",
    "discount",
    "expiry"
})
public class VoucherResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("discount")
    private Double discount;

    @JsonProperty("expiry")
    private LocalDate expiry;

}
