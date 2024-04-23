package blisgo.infrastructure.external.search;

import java.util.List;

public interface SearchMapper<D, I> {

    I toIndex(D domain);

    default List<I> toIndexes(List<D> domains) {
        return domains.stream().map(this::toIndex).toList();
    }

}
