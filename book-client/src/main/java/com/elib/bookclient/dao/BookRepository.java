package com.elib.bookclient.dao;

import com.elib.bookclient.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kainingxin
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b where b.category = ?1")
    List<Book> findByCategory(String category);

    @Query("select b from Book b where b.title like %?1%")
    List<Book> findByTitle(String title);
}
