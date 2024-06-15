package it.macgood.weldbootmvn.user.presentation;

import lombok.Builder;
import lombok.Data;

@Data
@Deprecated
@Builder
public class SmsAuthResponse {
    private Long statusCode;
    private Double balance;
    private String sms;
    private String status;
}
