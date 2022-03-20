package com.elib.userclient.service;

import com.elib.userclient.dao.CustomUserRepository;
import com.elib.userclient.dto.*;
import com.elib.userclient.mapper.CustomUserMapper;
import com.elib.userclient.model.CustomUser;
import com.elib.userclient.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

;

@Service
public class CustomUserService {

    Logger logger = LoggerFactory.getLogger(CustomUserService.class);
    private final CustomUserRepository customUserRepository;

    @Autowired
    private CustomUserMapper customUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public CustomUserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Transactional
    public boolean addUser(SignUpUserDto customUserDto) {
        CustomUser customUser = customUserMapper.signUpUserDto2CustomUser(customUserDto);
        customUser.getAccountInfo().setPassword(bCryptPasswordEncoder.encode(customUser.getAccountInfo().getPassword()));

        if (customUserRepository.save(customUser).getId() != null) {
            return true;
        }
        return false;
    }

    public Map<String, String> login(LoginUserDto loginUserDto) {
        CustomUser customUser = customUserRepository.findUserByAccountInfo_Email(loginUserDto.getEmail());

        if (customUser != null && bCryptPasswordEncoder.matches(loginUserDto.getPassword(), customUser.getAccountInfo().getPassword())) {
            return Map.of("token", JwtUtil.generateToken(customUser));
        }
        return null;

    }


    public AuthUserDto loadUserByUsername(String email) {
        CustomUser customUser = customUserRepository.findByAccountInfo_Email(email);
        logger.info("CustomUser {} found", email);
        return new AuthUserDto(email, customUser.getAccountInfo().getRoles().get(0));
    }

    @Transactional
    public CustomUserDto findUserCompleteInfoByEmail(String email) {
        CustomUser customUser = this.customUserRepository.findUserByAccountInfo_Email(email);
        if (customUser != null) {
            logger.info("user borrowed {} books", customUser.getBooks().size());
            return customUserMapper.customUser2CustomUserDto(customUser);
        }
        logger.error("Username [{}] not found", email);
        throw new EntityNotFoundException("Username " + email + " not found");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean borrowBook(BookUserId bookUserId) {
        CustomUser customUser = customUserRepository.findUserByAccountInfo_Email(bookUserId.getEmail());
        if (customUser != null && customUser.getBooks() != null) {
            BookDto bookDto = restTemplate.getForObject("http://book-client/api/books/id/" + bookUserId.getBookId(), BookDto.class);
            if (bookDto == null) throw new IllegalArgumentException("Book not found");
            customUser.getBooks().add(bookDto.getId());
            customUserRepository.save(customUser);
            return true;
        }
        return false;
    }


    @Transactional
    public boolean returnBook(BookUserId bookUserId){
        CustomUser customUser = customUserRepository.findUserByAccountInfo_Email(bookUserId.getEmail());
        if (customUser != null){
            if (customUser.getBooks().contains(bookUserId.getBookId())){
                customUser.getBooks().remove(bookUserId.getBookId());
                customUserRepository.save(customUser);
                return true;
            }
        }
        return false;
    }

}
