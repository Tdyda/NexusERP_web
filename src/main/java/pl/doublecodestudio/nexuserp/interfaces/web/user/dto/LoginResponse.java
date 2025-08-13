package pl.doublecodestudio.nexuserp.interfaces.web.user.dto;

public record LoginResponse(String accessToken, String refreshToken, UserDto user) {
}
