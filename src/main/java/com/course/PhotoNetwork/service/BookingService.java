package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.BookingServiceDtoIn;
import com.course.PhotoNetwork.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;

    public void bookService(BookingServiceDtoIn bookingServiceDtoIn) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = userService.findByEmail(auth.getName());

        if(findByMasterAndDate(bookingServiceDtoIn.getMasterId(), bookingServiceDtoIn.getDate()) != null)
            throw new IllegalStateException("Дата уже зарезервирована");

        BookingModel booking = toEntity(bookingServiceDtoIn, currentUser);

        bookingRepository.save(booking);

    }

    private BookingModel toEntity(BookingServiceDtoIn dto, UserModel currentUser) throws ParseException {
        BookingModel booking = new BookingModel();
        booking.setCustomer(currentUser);
        booking.setMaster(userService.findById(Long.parseLong(dto.getMasterId())).get());

        ServiceModel service = servicesService.findById(Long.parseLong(dto.getServiceId())).get();

        booking.setService(service);
        booking.setBookingDate(new SimpleDateFormat("dd.MM.yyyy").parse(dto.getDate().split(" ")[0]));

        return booking;
    }

    public BookingModel findByMasterAndDate(String masterId, String date) throws ParseException {
        long id = Long.parseLong(masterId);
        Optional<UserModel> master = userService.findById(id);
        return bookingRepository.findByMasterAndBookingDate(master.get(), new SimpleDateFormat("dd.MM.yyyy").parse(date.split(" ")[0]));
    }
}
