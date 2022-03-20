package com.elib.userclient.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
public class AccountInfo {

    private String password;
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "auths", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();


    public AccountInfo() {
        roles.add("user");
    }
}
