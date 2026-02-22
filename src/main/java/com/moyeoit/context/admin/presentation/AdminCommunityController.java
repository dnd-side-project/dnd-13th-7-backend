package com.moyeoit.context.admin.presentation;

import com.moyeoit.context.admin.application.AdminCommunityService;
import com.moyeoit.context.community.domain.CommentStatus;
import com.moyeoit.context.community.domain.PostStatus;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/community")
@RequiredArgsConstructor
public class AdminCommunityController {

    private final AdminCommunityService adminCommunityService;

    @GetMapping("/posts")
    public String posts(@RequestParam(value = "keyword", required = false) String keyword,
                        @RequestParam(value = "status", required = false) PostStatus status,
                        @PageableDefault(size = 20) Pageable pageable,
                        Model model) {
        var posts = adminCommunityService.getPosts(keyword, status, pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("statuses", Arrays.stream(PostStatus.values())
                .filter(s -> s != PostStatus.REPORTED)
                .toArray(PostStatus[]::new));
        return "admin/community-posts";
    }

    @GetMapping("/posts/{postId}")
    public String postDetail(@PathVariable Long postId, Model model) {
        model.addAttribute("post", adminCommunityService.getPost(postId));
        model.addAttribute("statuses", PostStatus.values());
        return "admin/community-post-detail";
    }

    @PostMapping("/posts/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        adminCommunityService.deletePost(postId);
        return "redirect:/admin/community/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/restore")
    public String restorePost(@PathVariable Long postId) {
        adminCommunityService.restorePost(postId);
        return "redirect:/admin/community/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/blind")
    public String blindPost(@PathVariable Long postId,
                            @RequestParam(value = "memo", required = false) String memo) {
        adminCommunityService.blindPost(postId, memo);
        return "redirect:/admin/community/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/unblind")
    public String unblindPost(@PathVariable Long postId) {
        adminCommunityService.unblindPost(postId);
        return "redirect:/admin/community/posts/" + postId;
    }

    @GetMapping("/comments")
    public String comments(@RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "status", required = false) CommentStatus status,
                           @PageableDefault(size = 20) Pageable pageable,
                           Model model) {
        var comments = adminCommunityService.getComments(keyword, status, pageable);
        model.addAttribute("comments", comments);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("statuses", Arrays.stream(CommentStatus.values())
                .filter(s -> s != CommentStatus.REPORTED)
                .toArray(CommentStatus[]::new));
        return "admin/community-comments";
    }

    @GetMapping("/comments/{commentId}")
    public String commentDetail(@PathVariable Long commentId, Model model) {
        model.addAttribute("comment", adminCommunityService.getComment(commentId));
        model.addAttribute("statuses", CommentStatus.values());
        return "admin/community-comment-detail";
    }

    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId) {
        adminCommunityService.deleteComment(commentId);
        return "redirect:/admin/community/comments/" + commentId;
    }

    @PostMapping("/comments/{commentId}/restore")
    public String restoreComment(@PathVariable Long commentId) {
        adminCommunityService.restoreComment(commentId);
        return "redirect:/admin/community/comments/" + commentId;
    }

    @PostMapping("/comments/{commentId}/blind")
    public String blindComment(@PathVariable Long commentId,
                               @RequestParam(value = "memo", required = false) String memo) {
        adminCommunityService.blindComment(commentId, memo);
        return "redirect:/admin/community/comments/" + commentId;
    }

    @PostMapping("/comments/{commentId}/unblind")
    public String unblindComment(@PathVariable Long commentId) {
        adminCommunityService.unblindComment(commentId);
        return "redirect:/admin/community/comments/" + commentId;
    }

 
}
