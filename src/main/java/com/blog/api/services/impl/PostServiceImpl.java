package com.blog.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.entities.Category;
import com.blog.api.entities.Post;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.payloads.PostDto;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepository;
import com.blog.api.repositories.PostRepository;
import com.blog.api.repositories.UserRepository;
import com.blog.api.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		// Converting PostDto into Post
		Post post = modelMapper.map(postDto, Post.class);

		post.setImageName("default.png");
		post.setCreatedDate(new Date());
		post.setCategory(category);
		post.setUser(user);

		// saving post into database.
		Post newPost = postRepository.save(post);

		// Converting Post into PostDto in return it.
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = postRepository.save(post);
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String order) {

		// sorting the data into ascending and descending order according to use input
		Sort sort = null;

		sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		// Dividing Data Into Pages.
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePosts = postRepository.findAll(page);

		// Converting Data of per page into List.
		List<Post> posts = pagePosts.getContent();

		for (Post post : posts) {
			System.out.println(post.getPostId());
		}

		// Converting Post into PostDto and store into a List.
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		// Generating response for posts.
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setIsLastPage(pagePosts.isLast());

		return postResponse;
	}

	@Override
	public PostDto getSinglePost(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));

		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePosts = postRepository.findByCategory(category, page);

		List<Post> posts = pagePosts.getContent();

		List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		// Generating response for posts.
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setIsLastPage(pagePosts.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePosts = postRepository.findByUser(user, page);

		List<Post> posts = pagePosts.getContent();

		List<PostDto> postDto = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		// Generating response for posts.
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setIsLastPage(pagePosts.isLast());

		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepository.findByTitleContaining(keyword);

		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

}
