package com.course.PhotoNetwork.service;

import com.course.PhotoNetwork.model.RoleModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.*;
import com.course.PhotoNetwork.repository.RoleRepository;
import com.course.PhotoNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.awt.print.Book;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private ReviewService reviewService;

    @PostConstruct
    public void init() {
        if(findByEmail("admin@adm.com") == null) {
            UserDtoIn admin = new UserDtoIn();
            admin.setEmail("admin@adm.com");
            admin.setPassword("verystrongpass");
            admin.setUsername("admin");

            UserModel userModel = registerUser(admin);
            List<RoleModel> roles = roleRepository.findAll();
            if(roles.isEmpty()) {
                RoleModel role = new RoleModel();
                role.setRolename("USER");
                roles.add(role);

                role = new RoleModel();
                role.setRolename("MASTER");
                roles.add(role);

                role = new RoleModel();
                role.setRolename("ADMIN");
                roles.add(role);
                roleRepository.saveAll(roles);
            }
            userModel.setRoles(roles);
            userRepository.save(userModel);
        }
    }

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

    public UserModel registerUser(UserDtoIn newUser) {
        UserModel user = new UserModel();
        user.setEmail(newUser.getEmail());

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setUsername(newUser.getUsername());
        List<RoleModel> roles = new ArrayList<>();
        roles.add(roleRepository.findByRolename("USER"));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(UserDtoIn newUser) throws IOException, ParseException {
        UserModel user = userRepository.findByEmail(newUser.getEmail());
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setSurname(newUser.getSurname());
        user.setUsername(newUser.getUsername());
        user.setDescription(newUser.getDescription());

        if(newUser.getAvatar() != null && !newUser.getAvatar().isEmpty())
            user.setAvatar("data:image/jpeg;base64," + Base64.getEncoder().
                encodeToString(newUser.getAvatar().getBytes()));

        if(newUser.getBirthday() != null && !newUser.getBirthday().isEmpty())
            user.setBirthday(new SimpleDateFormat("dd.MM.yyyy").parse(newUser.getBirthday().split( " ")[0]));

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

    public UserDtoOut toDto(UserModel usr) throws ParseException {
        ArrayList<ServiceDto> services = new ArrayList<>();
        if(usr.getServices() != null)
            for (ServiceModel r : usr.getServices()) {
                services.add(new ServiceDto(r.getId(), r.getName(), r.getPrice()));
            }
        return new UserDtoOut(usr.getId(), usr.getName(), usr.getSurname(),
                usr.getBirthday(), usr.getRegdate(),
                usr.getUsername(), usr.getDescription(), usr.getEmail(),
                usr.getAvatar(), usr.getAvgRate(), services, isMaster(usr), reviewService.toDto(usr.getReviews()), photoService.toDtoSmall(usr.getPhotos()));
    }

    private List<UserDtoOut> toDto(List<UserModel> entities) throws ParseException {
        List<UserDtoOut> res = new ArrayList<>();

        for(UserModel u: entities) {
            res.add(toDto(u));
        }

        return res;
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
    public void subscribeToUser(UserModel newSubscriber, Long userIdToSubscribe) {
        Optional<UserModel> usertoSubscribeOptional = findById(userIdToSubscribe);
        if(usertoSubscribeOptional.isPresent()) {

            UserModel userToSubscribe = usertoSubscribeOptional.get();

            if(newSubscriber.getSubscribers() == null) {
                List<UserModel> subscribers = new ArrayList<>();
                subscribers.add(newSubscriber);
                userToSubscribe.setSubscribers(subscribers);
            }
            else if(!newSubscriber.getSubscribers().contains(newSubscriber)) {
                userToSubscribe.getSubscribers().add(newSubscriber);
            }
            else throw new IllegalArgumentException("Текущий пользователь уже подписан");
            userRepository.save(userToSubscribe);
        }

        else throw new IllegalArgumentException("Пользователь " + userIdToSubscribe + " не найден");
    }

    public void unsubscribeFromUser(UserModel subscriber, Long userIdToUnsubscribe) {
        Optional<UserModel> usertoUnsubscribeOptional = findById(userIdToUnsubscribe);
        if(usertoUnsubscribeOptional.isPresent()) {

            UserModel userToUnsbscribe = usertoUnsubscribeOptional.get();

            userToUnsbscribe.getSubscribers().removeIf(s -> s.getId() == subscriber.getId());
            userRepository.save(userToUnsbscribe);

        }

        else throw new IllegalArgumentException("Пользователь " + userIdToUnsubscribe + " не найден");
    }

    public List<UserModel> findSubscribes(UserModel user) {
        return userRepository.findAll().stream().filter(u -> u.getSubscribers().contains(user)).collect(Collectors.toList());
    }

    public boolean isAdmin(UserModel user) {
        for(RoleModel userRole : user.getRoles()) {
            if(userRole.getRolename().equals("ADMIN"))
                return true;
        }
        return false;
    }

    public boolean isMaster(UserModel user) {
        for(RoleModel userRole : user.getRoles()) {
            if(userRole.getRolename().equals("MASTER"))
                return true;
        }
        return false;
    }

    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public Page<UserDtoOut> findPaginated(String service, PageRequest pageRequest) throws ParseException {

        int pageSize = pageRequest.getPageSize();
        int currentPage = pageRequest.getPageNumber();
        int startItem = currentPage * pageSize;

        List<UserModel> allPhotographers = userRepository.findAll();

        allPhotographers.removeIf(u->u.getPhotos().isEmpty());

        if(service != null && !service.isEmpty())
            allPhotographers.removeIf(user -> {

                if(user.getServices().isEmpty())
                    return true;

                for(ServiceModel s: user.getServices()) {
                   if(s.getName().equals(service)) {
                       return false;
                   }
                }
               return true;
            });

        List<UserDtoOut> res;

        if (allPhotographers.size() < startItem) {
            res = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allPhotographers.size());
            res = toDto(allPhotographers.subList(startItem, toIndex));
        }

        return new PageImpl<>(res, PageRequest.of(currentPage, pageSize), allPhotographers.size());
    }
}
