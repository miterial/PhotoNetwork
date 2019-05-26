package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.ReviewModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.ReviewDtoIn;
import com.course.PhotoNetwork.model.dto.ReviewDtoOut;
import com.course.PhotoNetwork.model.types.BookingEnum;
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
    @Autowired
    private BookingService bookingService;

    @Transactional
    public ReviewModel addReview(ReviewDtoIn dtoIn) {

        UserModel author = userRepository.findById(dtoIn.getAuthorId()).orElseThrow(IllegalAccessError::new);
        UserModel master = userRepository.findById(dtoIn.getMasterId()).orElseThrow(IllegalAccessError::new);

        if(bookingService.findByClientAndMasterAndStatus(author, master, BookingEnum.FINISHED) == null)
            throw new IllegalArgumentException("Данный пользователь не может добавлять комментарии исполнителю с id=" + dtoIn.getMasterId());

        ReviewModel review = new ReviewModel(dtoIn.getContent(), dtoIn.getRate(), author);
        review = reviewRepository.save(review);

        master.getReviews().add(review);
        master.setAvgRate((master.getAvgRate()+dtoIn.getRate())/master.getReviews().size()-1);
        userRepository.save(master);

        return review;
    }

    public List<ReviewDtoOut> toDto(List<ReviewModel> entities) {
        List<ReviewDtoOut> res = new ArrayList<>();

        if(entities != null)
            for(ReviewModel r: entities) {
                ReviewDtoOut out = new ReviewDtoOut(r.getAuthor().getId(),r.getAuthor().getUsername(),r.getRate(),r.getContent());
                res.add(out);
            }
        return res;
    }

    public List<ReviewModel> findAll() {
        return reviewRepository.findAll();
    }

    public ReviewDtoOut toDto(ReviewModel entity) {
        return new ReviewDtoOut(entity.getId(),entity.getAuthor().getUsername(),entity.getRate(),entity.getContent());
    }

    public void deleteByAuthor(Long authId) {
        UserModel author = userRepository.findById(authId).get();

        reviewRepository.deleteByAuthor(author);
    }
}
