package com.blog.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.ApiResponse;
import com.blog.api.payloads.CommentDto;
import com.blog.api.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComments(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		CommentDto createComment = commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deletedComments(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Comment With Id " + commentId + " Deleted Successfully!!");
		apiResponse.setStatus(true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

}
