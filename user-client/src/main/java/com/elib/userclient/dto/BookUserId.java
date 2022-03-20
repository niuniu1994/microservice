package com.elib.userclient.dto;


import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class BookUserId {

    @NonNull String email;
    @NonNull Integer bookId;
}
