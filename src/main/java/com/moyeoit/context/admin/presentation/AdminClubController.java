package com.moyeoit.context.admin.presentation;

import com.moyeoit.context.admin.application.AdminClubService;
import com.moyeoit.context.admin.presentation.request.AdminClubCreateRequest;
import com.moyeoit.context.admin.presentation.request.AdminClubUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/clubs")
@RequiredArgsConstructor
public class AdminClubController {

    private final AdminClubService adminClubService;

    @GetMapping
    public String clubs(@RequestParam(value = "keyword", required = false) String keyword,
                        @PageableDefault(size = 20) Pageable pageable,
                        Model model) {
        var clubs = adminClubService.getClubs(keyword, pageable);
        model.addAttribute("clubs", clubs);
        model.addAttribute("keyword", keyword);
        return "admin/clubs";
    }

    @GetMapping("/new")
    public String clubNew(Model model) {
        model.addAttribute("club", new AdminClubCreateRequest());
        return "admin/club-new";
    }

    @PostMapping
    public String createClub(@ModelAttribute AdminClubCreateRequest request,
                             RedirectAttributes redirectAttributes) {
        adminClubService.createClub(request);
        redirectAttributes.addFlashAttribute("message", "동아리가 등록되었습니다.");
        return "redirect:/admin/clubs";
    }

    @GetMapping("/{clubId}")
    public String clubDetail(@PathVariable Long clubId, Model model) {
        model.addAttribute("club", adminClubService.getClub(clubId));
        return "admin/club-detail";
    }

    @PostMapping("/{clubId}")
    public String updateClub(@PathVariable Long clubId,
                             AdminClubUpdateRequest request,
                             RedirectAttributes redirectAttributes) {
        adminClubService.updateClub(clubId, request);
        redirectAttributes.addFlashAttribute("message", "수정되었습니다.");
        return "redirect:/admin/clubs/" + clubId;
    }
}
