package br.gov.sp.fatec.pagination;

public record Page<T>(
    Iterable<T> items,
    int currentPage,
    int totalPages,
    long totalItems
) {
}
