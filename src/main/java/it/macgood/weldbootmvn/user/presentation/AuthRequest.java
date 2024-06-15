package it.macgood.weldbootmvn.user.presentation;

public record AuthRequest(
        String phone,
        String name
) {
}
