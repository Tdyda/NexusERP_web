package pl.doublecodestudio.nexuserp.application.user.command;

public record ChangeUserPasswordCommand(String oldP, String newP) {
}
