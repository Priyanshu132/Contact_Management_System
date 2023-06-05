package com.losung360.assignment.repository;

import com.losung360.assignment.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contact, Integer> {
    Contact findByPhoneNumber(String phoneNumber);

    List<Contact> findByFirstName(String value);

    List<Contact> findByLastName(String value);

    List<Contact> findByEmail(String value);

    void deleteByPhoneNumber(String phoneNumber);
}
