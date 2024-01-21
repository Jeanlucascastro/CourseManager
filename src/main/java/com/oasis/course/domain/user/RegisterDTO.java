package com.oasis.course.domain.user;

public record RegisterDTO(String login, String password, UserRole role, Long companyId) {
}
