package blisgo.domain.dictionary.vo;

import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

@AllArgsConstructor
public enum Category {
    IRON("고철", "Iron"),
    METAL("금속캔", "Metal"),
    BULKY("대형", "Bulky"),
    STYROFOAM("발포합성", "Styrofoam"),
    NON_FLAMMABLE("불연성-종량제", "Non-flammable"),
    VINYL("비닐", "Vinyl"),
    GLASS("유리병", "Glass"),
    PROG("음식물", "Food waste"),
    CLOTHES("의류", "Clothes"),
    ONLY_BOX("전용함", "Only box"),
    PAPER("종이", "Paper"),
    CARTON("종이팩", "Carton"),
    HOME_APPLIANCES("가전제품", "Home appliances"),
    PLASTIC("플라스틱", "Plastic"),
    PAY_AS_YOU_GO_BAG("종량제봉투", "Pay-as-you-go bag"),
    SEPARATION_BY_MATERIAL("재질별분리", "Separation by material"),
    CAUTION("주의", "Caution"),
    PRO_FACILITY("전문시설", "Professional facility");

    private final String ko;
    private final String en;

    public String tag() {
        String locale = LocaleContextHolder.getLocale().getLanguage();
        return switch (locale) {
            case "ko" -> ko;
            case "en" -> en;
            default -> en;
        };
    }

}
