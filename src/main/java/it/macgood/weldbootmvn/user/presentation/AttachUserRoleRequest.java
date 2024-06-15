package it.macgood.weldbootmvn.user.presentation;

public record AttachUserRoleRequest(
        Long userId,
        String role
) {
}
