package com.losung360.assignment.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private Integer id;

    private String firstName;

    private String lastName;
    private String email;

    @NotBlank(message = "Phone Number Can't be Null")
    private String phoneNumber;

    public ContactRequest(Integer id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }
}
