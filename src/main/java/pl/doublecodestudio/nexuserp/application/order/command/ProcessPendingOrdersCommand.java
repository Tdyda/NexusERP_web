package pl.doublecodestudio.nexuserp.application.order.command;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ProcessPendingOrdersCommand {
    String status;
    LocalTime time;
}