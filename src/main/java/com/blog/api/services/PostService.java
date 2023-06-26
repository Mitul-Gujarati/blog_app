package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String order);

	PostDto getSinglePost(Integer postId);

	PostResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

	PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);

	List<PostDto> searchPosts(String keyword);
}
