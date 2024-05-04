package blisgo.infrastructure.internal.ui.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToastStatus {
    SUCCESS("success"),
    INFO("info"),
    ERROR("danger");

    private final String status;
}
