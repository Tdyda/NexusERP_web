package pl.doublecodestudio.nexuserp.application.matarial_request.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.doublecodestudio.nexuserp.application.matarial_request.service.MaterialRequestSyncService;

@Component
@RequiredArgsConstructor
public class CreateMaterialRequestCommandHandler {
    private final MaterialRequestSyncService syncService;

    public void handle() {
        syncService.syncMissingMaterialRequests();
    }
}
