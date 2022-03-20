package com.elib.bookclient.service;

import com.elib.bookclient.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> { }
