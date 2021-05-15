package xyz.mynt.parceldeliverycost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mynt.parceldeliverycost.constant.ResponseCode;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "responseCode",
        "traceId",
        "messages",
        "timestamp"
})
public class ErrorResponse {

    @JsonProperty("responseCode")
    private ResponseCode responseCode;

    @JsonProperty("traceId")
    private String traceId;

    @JsonProperty("messages")
    private List<String> messages;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

}
