package com.project.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Comment;
import com.project.entities.Issue;
import com.project.entities.Users;
import com.project.repositories.CommentRepository;
import com.project.repositories.IssueRepository;
import com.project.repositories.UsersRepository;
import com.project.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Override
	public Comment createComment(Long issueId, Long userId, String content) throws Exception {
		
		Optional<Issue> issueOptional = issueRepo.findById(issueId);
		Optional<Users> userOptional = userRepo.findById(userId);
		
		if(issueOptional.isEmpty()) {
			throw new Exception("Issue not found with this id :" + issueId);
		}
		if(userOptional.isEmpty()) {
			throw new Exception("User not found with this id :" + userId);
		}
		
		Issue issue = issueOptional.get();
		Users user = userOptional.get();
		
		Comment comment = new Comment();
		
		comment.setIssue(issue);
		comment.setUser(user);
		comment.setCreatedDateTime(LocalDateTime.now());
		comment.setContent(content);
		
		Comment savedComment = commentRepo.save(comment);
		
		issue.getComments().add(savedComment);
		
		return savedComment;
	}

	@Override
	public void deleteComment(Long commentId, Long userId) throws Exception {
		Optional<Comment> commentOptional = commentRepo.findById(commentId);
		Optional<Users> userOptional = userRepo.findById(userId);
		
		if(commentOptional.isEmpty()) {
			throw new Exception("comment not found with this id :" + commentId);
		}
		if(userOptional.isEmpty()) {
			throw new Exception("User not found with this id :" + userId);
		}
		
		Comment comment = commentOptional.get();
		Users user = userOptional.get();
		
		if(comment.getUser().equals(user)) {
			commentRepo.delete(comment);
		}
		else {
			throw new Exception("User does not have permission to delete this comment!");
		}
		
	}

	@Override
	public List<Comment> findCommentByIssueId(Long issueId) {
		
		return commentRepo.findByIssueId(issueId);
	}

}
