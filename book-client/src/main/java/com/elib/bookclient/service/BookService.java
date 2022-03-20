package com.elib.bookclient.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.elib.bookclient.dto.BookDto;
import com.elib.bookclient.dao.BookRepository;
import com.elib.bookclient.mapper.BookMapper;
import com.elib.bookclient.model.Book;
import com.elib.bookclient.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BookService {
    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;


    void create(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Integer id){
        bookRepository.deleteById(id);
    }

    public boolean addComment(Comment comment) {
        if (commentRepository.save(comment).getId() != null) return true;
        return false;
    }

    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer uploadBook(BookDto bookDto) throws IOException {
        Book book = bookMapper.bookDto2Book(bookDto);
        try {
            bookRepository.save(book);
            ObjectMetadata meta = new ObjectMetadata();
            //add pdf
            meta.setContentType("application/pdf");
            PutObjectRequest putObjectRequest = new PutObjectRequest("my-demo-bucket-123432134", String.format("books/%s", book.getEbookUrl()), bookDto.getEbook().getInputStream(), meta);
            s3client.putObject(putObjectRequest);
            //add pdf
            meta = new ObjectMetadata();
            putObjectRequest = new PutObjectRequest("my-demo-bucket-123432134", String.format("image/%s", book.getThumbnail()), bookDto.getThumbnail().getInputStream(), meta);
            s3client.putObject(putObjectRequest);
            return book.getId();
        } catch (AmazonServiceException e) {
         log.error("Upload file failed: {}",e.getMessage());
        }
        return null;
    }

    public List<Book> findBookByCategory(String category){
        Assert.notNull(category,"Category can't be empty");
        List<Book> bookList = bookRepository.findByCategory(category);
        if (bookList.size() > 10){
            bookList = bookList.subList(0,10);
        }
        return  bookList;
    }

    public Book findBookById(Integer id){
        Assert.notNull(id,"Category can't be empty");
        return bookRepository.findById(id).orElse(null);
    }


    public List<Book> findBookByTitle(String title) {
        return  bookRepository.findByTitle(title);
    }
}
