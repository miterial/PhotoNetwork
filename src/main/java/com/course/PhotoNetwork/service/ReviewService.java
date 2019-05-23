package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.ReviewModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ReviewDtoIn;
import com.course.PhotoNetwork.model.dto.ReviewDtoOut;
import com.course.PhotoNetwork.repository.BookingRepository;
import com.course.PhotoNetwork.repository.ReviewRepository;
import com.course.PhotoNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addReview(ReviewDtoIn dtoIn) {

        UserModel author = userRepository.findById(dtoIn.getAuthorId()).orElseThrow(IllegalAccessError::new);

        ReviewModel review = new ReviewModel(dtoIn.getContent(), dtoIn.getRate(), author);
        reviewRepository.save(review);

        UserModel master = userRepository.findById(dtoIn.getMasterId()).orElseThrow(IllegalAccessError::new);

        master.getReviews().add(review);
        master.setAvgRate((master.getAvgRate()+dtoIn.getRate())/master.getReviews().size());
        userRepository.save(master);
    }

    public List<ReviewDtoOut> toDto(List<ReviewModel> entities) {
        List<ReviewDtoOut> res = new ArrayList<>();

        for(ReviewModel r: entities) {
            ReviewDtoOut out = new ReviewDtoOut(r.getAuthor().getId(),r.getAuthor().getUsername(),r.getRate(),r.getContent());
            res.add(out);
        }
        return res;
    }

    public List<ReviewModel> findAll() {
        return reviewRepository.findAll();
    }
}
