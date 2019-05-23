package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.BookingServiceDtoIn;
import com.course.PhotoNetwork.model.dto.BookingServiceDtoOut;
import com.course.PhotoNetwork.model.dto.BookingUserInfoDtoIn;
import com.course.PhotoNetwork.model.types.BookingEnum;
import com.course.PhotoNetwork.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        booking.setStatus(BookingEnum.NEW);

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

    /**
     * Find bookings that @param user made
     */
    public List<BookingModel> findUserBookedServices(UserModel user) {

        return bookingRepository.findByCustomer(user);
    }

    /**
     * Find bookings that @param user has as provided services
     */
    public List<BookingModel> findUserUpcomingServices(UserModel user) {
        return bookingRepository.findByMaster(user);
    }

    @Transactional
    public void changeStatus(BookingUserInfoDtoIn dtoIn) {
        Optional<BookingModel> bookingOptional = bookingRepository.findById(Long.parseLong(dtoIn.getBookingId()));

        BookingModel booking = bookingOptional.orElseThrow(IllegalArgumentException::new);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = userService.findByEmail(auth.getName());

        switch (dtoIn.getPrevStatusId()) {
            case "1":
                booking.setStatus(BookingEnum.PAID_CLIENT);
                break;
            case "2":
                booking.setStatus(BookingEnum.PAID_MASTER);
                break;
            case "3":
                booking.setStatus(BookingEnum.FINISH_AWAITS);
                if(booking.getMaster() == currentUser)
                    booking.setFinishedByMaster(true);
                else
                    booking.setFinishedByClient(true);
                break;
            case "4":
                    booking.setStatus(BookingEnum.FINISHED);
                break;
        }
        bookingRepository.save(booking);
    }

    public List<BookingServiceDtoOut> toDto(List<BookingModel> userBookedServices) {
        List<BookingServiceDtoOut> res = new ArrayList<>();

        for (BookingModel b : userBookedServices) {
            BookingServiceDtoOut service = new BookingServiceDtoOut(b.getId(),
                    b.getMaster().getId(), b.getMaster().getUsername(),
                    b.getCustomer().getId(), b.getCustomer().getUsername(),
                    b.getService().getId(), b.getService().getName(), b.getService().getPrice(),
                    b.getBookingDate().toString(),
                    b.getStatus(),
                    b.isFinishedByMaster(), b.isFinishedByClient(), b.isDeletedByMaster(), b.isDeletedByClient());
            res.add(service);
        }

        return res;
    }

    @Transactional
    public void removeBooking(Long bookingId, Authentication auth) {

        Optional<BookingModel> bookingOptional = bookingRepository.findById(bookingId);
        BookingModel booking = bookingOptional.orElseThrow(IllegalArgumentException::new);

        if(booking.getStatus() != BookingEnum.DELETE_AWAITS)
            booking.setStatus(BookingEnum.DELETE_AWAITS);

        UserModel currentUser = userService.findByEmail(auth.getName());

        if(booking.getMaster() == currentUser)
            booking.setDeletedByMaster(true);
        else if(booking.getCustomer() == currentUser)
            booking.setDeletedByClient(true);

        if(booking.isDeletedByClient() && booking.isDeletedByMaster())
            booking.setStatus(BookingEnum.DELETED);

        bookingRepository.save(booking);
    }

    public List<BookingModel> findByClientAndStatus(UserModel user, BookingEnum status) {
        return bookingRepository.findByCustomerAndStatus(user, status);
    }
}
