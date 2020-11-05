package com.henrique.bookshelf.repository;

import java.util.List;

import com.henrique.bookshelf.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(long authorId);

    @Modifying
    @Transactional
    @Query(value = "delete from Book b where b.author.id=:#{#authorId}", nativeQuery = true)
    void deleteByAuthorId(@Param("authorId")long authorId);

    List<Book> findByCategoryId(long category);

    @Modifying
    @Transactional
    @Query(value = "delete from Book b where b.category.id=:#{#categoryId}", nativeQuery = true)
    void deleteByCategoryId(@Param("categoryId")long categoryId);
}
