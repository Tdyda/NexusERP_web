package pl.doublecodestudio.nexuserp.application.user.command;

public record ChangeUserPasswordCommand(String username, String oldPassword, String newPassword) {
}
