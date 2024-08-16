package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.entity.BookDetails;

public record BookDetailsCreationDto(
        String summary,
        Integer pageCount,
        String year,
        String isbn) {

  public BookDetails toEntity() {
    return new BookDetails(summary, pageCount, year, isbn);
  }
}