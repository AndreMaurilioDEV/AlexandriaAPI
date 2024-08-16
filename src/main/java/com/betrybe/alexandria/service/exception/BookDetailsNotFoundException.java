package com.betrybe.alexandria.service.exception;

public class BookDetailsNotFoundException extends RuntimeException {
  public BookDetailsNotFoundException() {
    super("Detalhes de livro não encontrado!");
  }
}
