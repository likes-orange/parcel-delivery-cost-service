package xyz.mynt.parceldeliverycost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "weight",
    "length",
    "width",
    "height",
    "voucherCode",
})
public class ParcelDto {

    @NotNull(message = "'weight' must not be null.")
    @JsonProperty("weight")
    private Double weight;

    @NotNull(message = "'length' must not be null.")
    @JsonProperty("length")
    private Double length;

    @NotNull(message = "'width' must not be null.")
    @JsonProperty("width")
    private Double width;

    @NotNull(message = "'height' must not be null.")
    @JsonProperty("height")
    private Double height;

    @JsonProperty("voucherCode")
    private String voucherCode;

    public Double getVolume() {
        return length * width * height;
    }

}
