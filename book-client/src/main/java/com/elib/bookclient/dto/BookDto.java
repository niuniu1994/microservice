package com.elib.bookclient.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kainingxin
 */
@Value
public class BookDto {
    String title;
    String isbn;
    String description;
    String author;
    String category;
    String date;
    MultipartFile thumbnail;
    MultipartFile ebook;
}
