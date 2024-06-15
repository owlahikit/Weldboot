package it.macgood.weldbootmvn.user.presentation;

public record AuthExtra(
        String smsHttpCode,
        String generatedCode
) {
}
