
package it.macgood.weldbootmvn.user.presentation;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallAuthResponse {

    @JsonProperty("balance")
    private Double balance;
    @JsonProperty("call_id")
    private String callId;
    @JsonProperty("code")
    private String code;
    @JsonProperty("cost")
    private Double cost;
    @JsonProperty("status")
    private String status;
    @JsonProperty("free_repeat")
    private Integer freeRepeat;
    private Long userId;

}
