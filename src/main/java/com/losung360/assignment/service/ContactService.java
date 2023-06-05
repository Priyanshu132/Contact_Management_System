package com.losung360.assignment.service;

import com.losung360.assignment.exception.DuplicateData;
import com.losung360.assignment.exception.InValidPhoneNumber;
import com.losung360.assignment.model.Contact;
import com.losung360.assignment.requestDto.ContactRequest;
import com.losung360.assignment.responseDto.ContactResponse;

import java.util.List;

public interface ContactService {

    ContactResponse addContact(ContactRequest contact) throws DuplicateData, InValidPhoneNumber;

    List<ContactResponse> getAllContact();

    ContactResponse updateContact(ContactRequest contact,Integer id);

    ContactResponse deleteContact(ContactRequest contact);

    List<Contact> getContactByFilter(String filter, String value);

    List<ContactResponse> getContactBy(String filter,String value);
}
