package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingModel, Long> {
    BookingModel findByMasterAndBookingDate(UserModel user, Date bookedDate);

    List<BookingModel> findByMaster(UserModel userModel);
}
