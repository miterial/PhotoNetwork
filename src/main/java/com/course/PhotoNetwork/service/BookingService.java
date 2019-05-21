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

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void bookService(BookingServiceDtoIn bookingServiceDtoIn) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = userService.findByEmail(auth.getName());

        if(findByMasterAndDate(bookingServiceDtoIn.getMasterId(), bookingServiceDtoIn.getDatetime()))
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
        booking.setBookingDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(dto.getDatetime()));

        return booking;
    }

    /**
     * Find if master is busy one hour before and one hour after the @param date
     * @param masterId
     * @param date
     * @return
     * @throws ParseException
     */
    private boolean findByMasterAndDate(String masterId, String date) throws ParseException {
        long id = Long.parseLong(masterId);
        Optional<UserModel> master = userService.findById(id);

        Date bookingDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(date);

        return bookingRepository.findByMasterAndBookingDate(master.get(), bookingDate) != null;

    }
}
