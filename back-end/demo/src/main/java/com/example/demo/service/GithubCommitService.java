package com.example.demo.service;

import com.example.demo.dto.response.GithubCommitResponse;
import com.example.demo.entity.GithubCommits;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.GithubCommitRepository;
import com.example.demo.repository.ProjectRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GithubCommitService {
    
    @Autowired
    private GithubCommitRepository githubCommitRepository;
    
    @Autowired
    private ProjectRepositoryRepository projectRepositoryRepository;
    
    // Get all commits
    public List<GithubCommitResponse> getAllCommits() {
        return githubCommitRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get commit by ID
    public GithubCommitResponse getCommitById(Integer id) {
        GithubCommits commit = githubCommitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GitHub Commit", "id", id));
        return convertToResponse(commit);
    }
    
    // Get commits by repository
    public List<GithubCommitResponse> getCommitsByRepository(Integer repoId) {
        if (!projectRepositoryRepository.existsById(repoId)) {
            throw new ResourceNotFoundException("Repository", "id", repoId);
        }
        return githubCommitRepository.findByRepoId(repoId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get commits by committer email
    public List<GithubCommitResponse> getCommitsByEmail(String email) {
        return githubCommitRepository.findByCommitterEmail(email).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get commits by date range
    public List<GithubCommitResponse> getCommitsByDateRange(Integer repoId, LocalDateTime startDate, LocalDateTime endDate) {
        if (!projectRepositoryRepository.existsById(repoId)) {
            throw new ResourceNotFoundException("Repository", "id", repoId);
        }
        return githubCommitRepository.findByRepoAndDateRange(repoId, startDate, endDate).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get commits by email and date range
    public List<GithubCommitResponse> getCommitsByEmailAndDateRange(String email, LocalDateTime startDate, LocalDateTime endDate) {
        return githubCommitRepository.findByEmailAndDateRange(email, startDate, endDate).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get recent commits by repository
    public List<GithubCommitResponse> getRecentCommits(Integer repoId, int limit) {
        if (!projectRepositoryRepository.existsById(repoId)) {
            throw new ResourceNotFoundException("Repository", "id", repoId);
        }
        return githubCommitRepository.findRecentCommitsByRepo(repoId).stream()
                .limit(limit)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Get commits since specific date
    public List<GithubCommitResponse> getCommitsSince(LocalDateTime since) {
        return githubCommitRepository.findCommitsSince(since).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    // Convert entity to response DTO
    private GithubCommitResponse convertToResponse(GithubCommits commit) {
        GithubCommitResponse response = new GithubCommitResponse();
        response.setCommitId(commit.getCommitId());
        response.setRepoId(commit.getRepoId());
        response.setSha(commit.getSha());
        response.setCommitterName(commit.getCommitterName());
        response.setCommitterEmail(commit.getCommitterEmail());
        response.setCommitMessage(commit.getCommitMessage());
        response.setAdditions(commit.getAdditions());
        response.setDeletions(commit.getDeletions());
        response.setCommitDate(commit.getCommitDate());
        response.setCommitUrl(commit.getCommitUrl());
        
        // Get repo name if available
        if (commit.getRepository() != null) {
            response.setRepoName(commit.getRepository().getRepoName());
        }
        
        return response;
    }
}
