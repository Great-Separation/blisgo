package blisgo.infrastructure.external.search;

public interface SearchMapper<D, I> {

    I toIndex(D domain);
}
