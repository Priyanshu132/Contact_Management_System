package com.losung360.assignment.serviceImpl;

import com.losung360.assignment.exception.DuplicateData;
import com.losung360.assignment.exception.InValidPhoneNumber;
import com.losung360.assignment.model.Contact;
import com.losung360.assignment.repository.ContactRepo;
import com.losung360.assignment.requestDto.ContactRequest;
import com.losung360.assignment.responseDto.ContactResponse;
import com.losung360.assignment.service.ContactService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContactResponse addContact(ContactRequest contactRequest) throws DuplicateData,InValidPhoneNumber  {
        if(!isValidContact(contactRequest.getPhoneNumber()))
            throw new InValidPhoneNumber("Phone Number is  Invalid !!!");
        List<Contact> contact = getContactByFilter("PhoneNumber",contactRequest.getPhoneNumber());
        if(Objects.nonNull(contact))
            throw new DuplicateData("Contact Already Exists !!!");
        if(Objects.isNull(contactRequest.getFirstName())){
            if(Objects.isNull(contactRequest.getLastName())){
                contactRequest.setFirstName(contactRequest.getPhoneNumber());
            }
            else
                contactRequest.setFirstName(contactRequest.getLastName());
        }
        Contact contact1 = modelMapper.map(contactRequest,Contact.class);
        contact1 = contactRepo.save(contact1);

        return modelMapper.map(contact1,ContactResponse.class);
    }

    @Override
    public List<ContactResponse> getAllContact() {
        return contactRepo.findAll().stream().map(x -> modelMapper.map(x,ContactResponse.class)).collect(Collectors.toList());
    }

    @Override
    public ContactResponse updateContact(ContactRequest contactRequest,Integer id) throws NullPointerException {
        List<Contact> contact = getContactByFilter("Id",String.valueOf(id));;
        if(Objects.isNull(contact))
            throw new NullPointerException("Contact Doesn't exist !!!");

        Contact contact1 = modelMapper.map(contactRequest,Contact.class);
        contact1 = contactRepo.save(contact1);
        return modelMapper.map(contact1,ContactResponse.class);
    }

    @Override
    public ContactResponse deleteContact(ContactRequest contactRequest) {
        List<Contact> contact;
        if(Objects.nonNull(contactRequest.getId()))
            contact = getContactByFilter("Id",String.valueOf(contactRequest.getId()));
        else
            contact = getContactByFilter("PhoneNumber",contactRequest.getPhoneNumber());

        if(Objects.isNull(contact))
            throw new NullPointerException("Contact Doesn't exist !!!");

        contactRepo.deleteById(contact.get(0).getId());
        return modelMapper.map(contact.get(0),ContactResponse.class);
    }

    @Override
    public List<Contact> getContactByFilter(String filter, String value) {
        List<Contact> contact;
        switch (filter){
            case "phoneNumber":
                contact = Collections.singletonList(contactRepo.findByPhoneNumber(value));
                break;
            case "firstName":
                contact = contactRepo.findByFirstName(value);
                break;
            case "lastName":
                contact = contactRepo.findByLastName(value);
                break;
            case "email":
                contact = contactRepo.findByEmail(value);
                break;
            case "Id":
                Optional<Contact> contactOptional = contactRepo.findById(Integer.parseInt(value));
                contact = Collections.singletonList(contactOptional.orElse(null));
                break;
            default:
                contact = null;
        }


        return contact;
    }

    @Override
    public List<ContactResponse> getContactBy(String filter, String value) throws NullPointerException{
        List<Contact> contact = getContactByFilter(filter,value);
        return contact.stream().map(x->modelMapper.map(x,ContactResponse.class)).collect(Collectors.toList());
    }

    private boolean isValidContact(String phoneNumber){
        Pattern p = Pattern.compile("^\\d{10}$");  /// For Indian Numbers
        Matcher m = p.matcher(phoneNumber);
        return (m.matches());
    }
}
