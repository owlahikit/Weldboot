package it.macgood.weldbootmvn.user.presentation;

public record AuthResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        String role,
        String phone
) {
}
