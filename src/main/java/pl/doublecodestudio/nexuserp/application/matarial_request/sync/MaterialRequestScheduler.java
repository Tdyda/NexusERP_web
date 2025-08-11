package pl.doublecodestudio.nexuserp.application.matarial_request.sync;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.matarial_request.command.CreateMaterialRequestCommandHandler;

@RequiredArgsConstructor
@Slf4j
@Component
public class MaterialRequestScheduler {
    private final CreateMaterialRequestCommandHandler commandHandler;

    @Scheduled(cron = "0 0 3 * * *")
//@Scheduled(initialDelay = 3000, fixedDelay = Long.MAX_VALUE)
    public void syncMaterialRequests() {
        try {
            commandHandler.handle();
            log.info("MaterialRequest sync completed successfully");
        } catch (Exception e) {
            log.error("MaterialRequest sync failed", e);
        }
    }
}
