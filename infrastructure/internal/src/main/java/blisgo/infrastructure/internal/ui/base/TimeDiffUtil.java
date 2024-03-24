package blisgo.infrastructure.internal.ui.base;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalDateTime;

@UtilityClass
public class TimeDiffUtil {
    public static String calcTimeDiff(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(localDateTime, now);

        long diffSeconds = duration.getSeconds();
        if (diffSeconds < 60) {
            return diffSeconds + "초 전";
        }

        long diffMinutes = diffSeconds / 60;
        if (diffMinutes < 60) {
            return diffMinutes + "분 전";
        }

        long diffHours = diffMinutes / 60;
        if (diffHours < 24) {
            return diffHours + "시간 전";
        }

        long diffDays = diffHours / 24;
        if (diffDays < 30) {
            return diffDays + "일 전";
        }

        long diffMonths = diffDays / 30;
        if (diffMonths < 12) {
            return diffMonths + "개월 전";
        }

        long diffYears = diffMonths / 12;
        return diffYears + "년 전";
    }
}
