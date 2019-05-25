package com.course.PhotoNetwork.controller.rest;

import com.course.PhotoNetwork.model.CategoryModel;
import com.course.PhotoNetwork.model.ServiceModel;
import com.course.PhotoNetwork.model.dto.UserServicesDtoIn;
import com.course.PhotoNetwork.service.CategoryService;
import com.course.PhotoNetwork.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/service")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @PostMapping
    public ResponseEntity addService(@RequestParam String service_name){

        ServiceModel service = new ServiceModel();

        service.setName(service_name);
        service.setPrice(0);

        try {
            servicesService.save(service);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/services")
    @ResponseBody
    public ResponseEntity changeServices(@RequestBody UserServicesDtoIn services) {
        try {
            servicesService.changeServices(services);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity deleteService(@PathVariable Long serviceId) {
        try {
            servicesService.deleteById(serviceId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(ServicesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
