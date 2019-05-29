package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.ReviewModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ReviewDtoIn;
import com.course.PhotoNetwork.service.ReviewService;
import com.course.PhotoNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity addReview(@ModelAttribute ReviewDtoIn dtoIn) {
        try {
            ReviewModel review = reviewService.addReview(dtoIn);
            return ResponseEntity.ok(reviewService.toDto(review));
        } catch (IllegalAccessError ex) {
            Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/form")
    public String addReviewForm(@ModelAttribute ReviewDtoIn dtoIn, Model model) {
        try {
            reviewService.addReview(dtoIn);
            return "redirect:/user/" + dtoIn.getMasterId();
        } catch (IllegalAccessError | Exception ex) {
            Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage","Не удалось оставить отзыв");
            return "/error";
        }
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity deleteMasterReviews(@PathVariable Long authorId, Authentication auth) {
        try {
            UserModel curUser = userService.findByEmail(auth.getName());
            if (!userService.isAdmin(curUser))
                throw new IllegalArgumentException("Для данного действия нужны права администратора");

            reviewService.deleteByAuthor(authorId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
