package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.entity.BookDetails;

public record BookDetailsDto(
        Long id,
        String summary,
        Integer pageCount,
        String year,
        String isbn) {

  public static BookDetailsDto fromEntity(BookDetails bookDetail) {
    return new BookDetailsDto(
            bookDetail.getId(),
            bookDetail.getSummary(),
            bookDetail.getPageCount(),
            bookDetail.getYear(),
            bookDetail.getIsbn()
    );
  }
}
