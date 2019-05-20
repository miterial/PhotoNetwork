package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.BookingModel;
import com.course.PhotoNetwork.model.RoleModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.*;
import com.course.PhotoNetwork.repository.BookingRepository;
import com.course.PhotoNetwork.repository.RoleRepository;
import com.course.PhotoNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserModel user = userRepository.findByEmail(email);
        if(user != null) {
            //log.info("user with email" +email+ " was found!");
            Set<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("email not found");
        }
    }
    private Set<GrantedAuthority> getUserAuthority(List<RoleModel> userRoles) {

        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRolename()));
        });

        return new HashSet<>(roles);
    }

    private UserDetails buildUserForAuthentication(UserModel user, Set<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public void registerUser(UserDtoIn newUser) {
        UserModel user = new UserModel();
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        List<RoleModel> roles = new ArrayList<>();
        roles.add(roleRepository.findByRolename("USER"));
        user.setRoles(roles);

        userRepository.save(user);
    }

    public void updateUser(UserDtoIn newUser) throws IOException {
        UserModel user = userRepository.findByEmail(newUser.getEmail());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setUsername(newUser.getUsername());
        user.setBirthday(newUser.getBirthday());
        user.setProvideServices(newUser.isProvideServices());
        user.setDescription(newUser.getDescription());
        user.setAvatar("data:image/jpeg;base64," + Base64.getEncoder().
                encodeToString(newUser.getAvatar().getBytes()));

        List<RoleModel> roles = new ArrayList<>();
        roles.add(roleRepository.findByRolename("USER"));
        if(newUser.isProvideServices())
            roles.add(roleRepository.findByRolename("MASTER"));

        user.setRoles(roles);

        userRepository.save(user);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDtoOut toDto(UserModel usr) {
        ArrayList<ServiceDto> services = new ArrayList<>();
        for (ServiceModel r : usr.getServices()) {
            services.add(new ServiceDto(r.getId(), r.getName(), r.getPrice()));
        }
        return new UserDtoOut(usr.getId(), usr.getName(), usr.getSurname(),
                usr.getBirthday(), usr.getRegdate(),
                usr.getUsername(), usr.getDescription(), usr.getEmail(),
                usr.getAvatar(), services, usr.isProvideServices(), photoService.toDtoSmall(usr.getPhotos()));
    }

    @Deprecated
    public void changeAvatar(MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = findByEmail(auth.getName());
        user.setAvatar("data:image/jpeg;base64," + Base64.getEncoder().
                encodeToString(file.getBytes()));

        userRepository.save(user);
    }

    public Optional<UserModel> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void subscribeToUser(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = findByEmail(auth.getName());
        Optional<UserModel> usertoSubscribeOptional = findById(userId);
        if(usertoSubscribeOptional.isPresent()) {

            UserModel userToSubscribe = usertoSubscribeOptional.get();

            if(currentUser.getSubscribers() == null) {
                List<UserModel> subscribers = new ArrayList<>();
                subscribers.add(currentUser);
                userToSubscribe.setSubscribers(subscribers);
            }
            else
                userToSubscribe.getSubscribers().add(currentUser);
        }

        else throw new IllegalArgumentException("Пользователь " + userId + " не найден");
    }

    public List<UserModel> findSubscribes() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = findByEmail(auth.getName());
        return userRepository.findAll().stream().filter(u -> u.getSubscribers().contains(currentUser)).collect(Collectors.toList());
    }

    public void bookService(BookingServiceDtoIn bookingServiceDtoIn) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = findByEmail(auth.getName());

        if(servicesService.findByMasterAndDate(bookingServiceDtoIn.getMasterId(), bookingServiceDtoIn.getDate()) != null)
            throw new IllegalStateException("Дата уже зарезервирована");

        ServiceModel service = servicesService.findById(bookingServiceDtoIn.getServiceId()).get();

        BookingModel booking = new BookingModel();
        booking.setCustomer(currentUser);
        booking.setMaster(userRepository.findById(bookingServiceDtoIn.getMasterId()).get());
        booking.setService(service);
        booking.setBookingDate(bookingServiceDtoIn.getDate());

        bookingRepository.save(booking);

    }
}
