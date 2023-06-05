package com.losung360.assignment.controller;


import com.losung360.assignment.exception.DuplicateData;
import com.losung360.assignment.exception.InValidPhoneNumber;
import com.losung360.assignment.requestDto.ContactRequest;

import com.losung360.assignment.responseDto.Response;
import com.losung360.assignment.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/")
    public ResponseEntity<Response> addContact(@RequestBody ContactRequest contactRequest, BindingResult bindingResult) throws DuplicateData, InValidPhoneNumber {

        if(bindingResult.hasErrors())
            return new ResponseEntity<>(new Response(true,bindingResult.toString(),null),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new Response(false,"Created",contactService.addContact(contactRequest)),HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllContact(){
        return new ResponseEntity<>(new Response(false,"Fetched",contactService.getAllContact()),HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<Response> deleteContact(@RequestParam(required = false) Integer id,@RequestParam(required = false) String phoneNumber){


        if(Objects.isNull(id) && Objects.isNull(phoneNumber))
            return new ResponseEntity<>(new Response(true,"Both Id and PhoneNumber Can't be Null !!!",null),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new Response(false,"Deleted",contactService.deleteContact(new ContactRequest(id,phoneNumber))),HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateContact(@PathVariable Integer id,@RequestBody ContactRequest contactRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return new ResponseEntity<>(new Response(true,bindingResult.toString(),null),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new Response(false,"Updated",contactService.updateContact(contactRequest,id)),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Response> getContactByFilter(@RequestParam String filterName,@RequestParam String value){

        return new ResponseEntity<>(new Response(false,"Fetched",contactService.getContactBy(filterName,value)),HttpStatus.OK);
    }
}
