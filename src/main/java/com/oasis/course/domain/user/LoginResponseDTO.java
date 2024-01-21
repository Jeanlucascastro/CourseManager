package com.oasis.course.domain.user;

public record LoginResponseDTO(String token, UserDTO user) {
}
