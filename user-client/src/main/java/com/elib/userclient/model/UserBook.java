package com.elib.userclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBook {
    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "book_id")
    private Integer bookId;
}
