package com.elib.userclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Integer id;
    private String content;
    private Integer rate;
    private Integer bookId;
    private String userEmail;
}
