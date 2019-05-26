package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.UserModel;
import com.course.PhotoNetwork.model.dto.UserServicesDto;
import com.course.PhotoNetwork.service.ServicesService;
import com.course.PhotoNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/service")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity addService(@RequestParam String service_name){

        ServiceModel service = new ServiceModel();

        service.setName(service_name);
        service.setPrice(0);

        try {
            return ResponseEntity.ok(servicesService.toDto(servicesService.save(service)));
        } catch (Exception ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/services")
    @ResponseBody
    public ResponseEntity changeServices(@RequestBody UserServicesDto services, Authentication auth) {
        try {
            UserModel user = userService.findByEmail(auth.getName());
            if(!userService.isMaster(user) && user.getId() != services.getUserId())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Данный пользователь не может добавлять услуги в профиль пользователя с id=" + services.getUserId());

            Set<ServiceModel> res = servicesService.changeServices(services);
            return ResponseEntity.ok(servicesService.toListDto(res,services.getUserId()));
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity deleteService(@PathVariable Long serviceId, Authentication auth) {
        try {
            UserModel curUser = userService.findByEmail(auth.getName());
            if(!userService.isAdmin(curUser))
                throw new IllegalArgumentException("Для данного действия нужны права администратора");
            servicesService.deleteById(serviceId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
