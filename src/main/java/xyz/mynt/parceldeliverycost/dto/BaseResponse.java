package xyz.mynt.parceldeliverycost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mynt.parceldeliverycost.constant.ResponseCode;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "responseCode",
    "data",
    "error"
})
public class BaseResponse<T> {

    @JsonProperty("responseCode")
    private ResponseCode responseCode;

    @JsonProperty("data")
    private T data;

    @JsonProperty("error")
    private ErrorResponse error;

}
