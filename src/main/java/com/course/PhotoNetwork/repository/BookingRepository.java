package com.course.PhotoNetwork.repository;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.types.BookingEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingModel, Long> {
    BookingModel findByMasterAndBookingDate(UserModel user, Date bookedDate);

    List<BookingModel> findByMaster(UserModel userModel);

    List<BookingModel> findByCustomer(UserModel user);

    List<BookingModel> findByCustomerAndStatus(UserModel user, BookingEnum status);

    void deleteById(Long id);

    BookingModel findByCustomerAndMasterAndStatus(UserModel author, UserModel master, BookingEnum status);
}
