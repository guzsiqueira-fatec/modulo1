package br.gov.sp.fatec.pagination;


import java.util.List;

public record Page<T>(
    List<T> items,
    int currentPage,
    int totalPages,
    long totalItems
) {
}
