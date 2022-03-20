package com.elib.bookclient.controller;

import com.elib.bookclient.dto.BookDto;
import com.elib.bookclient.dto.CustomResponseEntity;
import com.elib.bookclient.model.Book;
import com.elib.bookclient.model.Comment;
import com.elib.bookclient.service.BookService;
import com.elib.bookclient.util.ResponseEntityFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/api")
@Log4j2
public class BookController {

    @Autowired
    private BookService bookService;

    @DeleteMapping("/books/{id}")
    public CustomResponseEntity deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
        return ResponseEntityFactory.oK(null);
    }

    @GetMapping("/books/all")
    public CustomResponseEntity findBookByCategory() {
        return ResponseEntityFactory.oK(bookService.findAllBooks());
    }

    @GetMapping("/books")
    public CustomResponseEntity findBookByCategory(@RequestParam(name = "category",required = false) String category, @RequestParam(name = "title",required = false) String title) {
        if (category != null){
            return ResponseEntityFactory.oK(bookService.findBookByCategory(category));
        }else {
            return ResponseEntityFactory.oK(bookService.findBookByTitle(title));
        }

    }

    @GetMapping("/books/id/{id}")
    public Book findBookByIdInter(@PathVariable Integer id) {
        log.info("Get book with id {}", id);
        Book book = bookService.findBookById(id);
        log.info("Book found : {} ", book.getId());
        return book;
    }


    @GetMapping("/books/{id}")
    public CustomResponseEntity findBookById(@PathVariable Integer id) {
        log.info("Get book with id {}", id);
        Book book = bookService.findBookById(id);
        log.info("Book found : {} ", book.getId());
        return ResponseEntityFactory.oK(book);
    }


    @PostMapping("/books/comment")
    public CustomResponseEntity addComment(@RequestBody Comment comment) {
        if (bookService.addComment(comment)) {
            return ResponseEntityFactory.oK(null);
        }
        return ResponseEntityFactory.error(null);
    }

    @PostMapping("/books/upload")
    public CustomResponseEntity upload(@ModelAttribute BookDto bookDto) throws IOException {
        Integer id = bookService.uploadBook(bookDto);
        if (id != null){
            return ResponseEntityFactory.oK(id);
        }
        return ResponseEntityFactory.error(null);

    }

}
