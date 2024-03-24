package blisgo.domain.dictionary.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    IRON("고철"),
    METAL("금속캔"),
    BULKY("대형"),
    STYROFOAM("발포합성"),
    NON_FLAMMABLE("불연성-종량제"),
    VINYL("비닐"),
    GLASS("유리병"),
    PROG("음식물"),
    CLOTHES("의류"),
    ONLY_BOX("전용함"),
    PAPER("종이"),
    CARTON("종이팩"),
    HOME_APPLIANCES("가전제품"),
    PLASTIC("플라스틱"),
    PAY_AS_YOU_GO_BAG("종량제봉투"),
    SEPARATION_BY_MATERIAL("재질별분리"),
    CAUTION("주의"),
    PRO_FACILITY("전문시설");

    final String tag;
}
