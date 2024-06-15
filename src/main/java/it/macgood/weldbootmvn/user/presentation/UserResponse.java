package it.macgood.weldbootmvn.user.presentation;

public record UserResponse(
        Long id,
        String firstname,
        String lastname,
        String email,
        String password,
        String role,
        String phone
        ) {
}
