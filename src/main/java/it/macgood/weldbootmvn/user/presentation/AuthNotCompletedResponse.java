package it.macgood.weldbootmvn.user.presentation;

public record AuthNotCompletedResponse(
        Long userId,
        String phone,
        String sentCode,
        String roleAttachUrl
) {
}
