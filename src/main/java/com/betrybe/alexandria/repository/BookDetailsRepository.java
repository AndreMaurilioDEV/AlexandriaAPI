package com.betrybe.alexandria.repository;

import com.betrybe.alexandria.entity.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {
}
