package com.blog.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	Page<Post> findByUser(User user, Pageable pageable);

	Page<Post> findByCategory(Category category, Pageable pageable);

	List<Post> findByTitleContaining(String title);

}
