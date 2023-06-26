package com.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.api.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
