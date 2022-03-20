package com.elib.userclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
     private Integer id;
     private String isbn;
     private String title;
     private String author;
     private String category;
     private String publishDate;
     private String description;
     private String thumbnail;
     private String ebookUrl;
     private Integer rate;
     private List<CommentDto> comments;
}
