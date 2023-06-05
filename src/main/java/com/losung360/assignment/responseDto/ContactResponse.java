package com.losung360.assignment.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
