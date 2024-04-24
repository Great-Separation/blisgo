package blisgo.infrastructure.internal.persistence.base;

import java.util.List;

public interface PersistenceMapper<D, E, V> {

    E toEntity(D domain);

    D toDomain(E entity);

    default List<D> toDomains(List<E> entities) {
        return entities.stream().map(this::toDomain).toList();
    }

    V toDTO(D domain);

    default List<V> toDTOs(List<D> domains) {
        return domains.stream().map(this::toDTO).toList();
    }
}
