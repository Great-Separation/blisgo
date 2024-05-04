package blisgo.infrastructure.external.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Description("Temp 에 저장된 리소스를 일괄 삭제한느 스케줄러")
public class StorageScheduler {

    private final CloudinaryClient client;

    @Scheduled(zone = "UTC", cron = "0 0 0 * * *")
    public void deleteTempResources() {
        client.deleteResourcesWhereTagIsTemp();
    }
}
