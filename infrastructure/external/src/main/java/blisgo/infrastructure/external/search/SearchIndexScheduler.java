package blisgo.infrastructure.external.search;

import blisgo.domain.common.Picture;
import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.infrastructure.external.database.WasteDirectDBAdapter;
import blisgo.infrastructure.external.extract.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Description("검색 인덱스를 갱신하는 스케줄러")
public class SearchIndexScheduler {
    private final WasteDirectDBAdapter adapter;
    private final AlgoliaClient client;
    private final JsonParser jsonParser;
    private final Locale[] locales = {Locale.KOREAN, Locale.ENGLISH};

    @Scheduled(zone = "UTC", cron = "0 0 0 * * *")
    public void updateSearchIndex() {
        List<Map<String, String>> maps = adapter.findAll();

        for (var locale : locales) {
            List<Waste> localizedWastes = new ArrayList<>();
            for (var map : maps) {
                WasteId wasteId = WasteId.of(map.get("waste_id"));
                String localizedName = jsonParser.getLocalizedString(map.get("name"), locale);
                List<String> localizedHashtags = jsonParser.getLocalizedList(map.get("hashtags"), locale);
                Picture picture = Picture.of(map.get("picture"));

                Waste waste = Waste.builder()
                        .wasteId(wasteId)
                        .picture(picture)
                        .name(localizedName)
                        .hashtags(localizedHashtags)
                        .build();

                localizedWastes.add(waste);
            }
            client.batchUpdate(localizedWastes, locale);
        }
    }
}
