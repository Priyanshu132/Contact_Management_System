package com.losung360.assignment.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private boolean error;
    private String message;
    private Object object;
}
