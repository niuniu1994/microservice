package com.elib.userclient.service;

import com.elib.userclient.dao.CustomUserRepository;
import com.elib.userclient.model.AccountInfo;
import com.elib.userclient.model.Address;
import com.elib.userclient.model.CustomUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerUserServiceTest {

    @Autowired
    CustomUserRepository customUserRepository;

    @Test
    public void addUserTest() {
        CustomUser customUser = new CustomUser();
        customUser.addBook(1);
        customUser.addBook(2);
        customUser.addBook(3);
        Address address = new Address();
        address.setCountry("daw");
        address.setStreetName("12");
        address.setStreetNumber("12");
        address.setZipCode("12");
        customUser.setLastName("dw");
        customUser.setFirstName("Dwa");
        customUser.setAddress(address);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setEmail("dwa");
        accountInfo.setPassword("dwa");
        customUser.setAccountInfo(accountInfo);

        CustomUser c = customUserRepository.save(customUser);
        Assertions.assertNotNull(c.getId());
    }

    @Test
    public void findUserByUserNamePassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        CustomUser customUser = customUserRepository.findUserByAccountInfo_Email("abc@gmail.com");
        Assertions.assertTrue(bCryptPasswordEncoder.matches("123", customUser.getAccountInfo().getPassword()));
        Assertions.assertNotNull(customUser);
    }
}
