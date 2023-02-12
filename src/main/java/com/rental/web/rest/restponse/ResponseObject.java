package com.rental.web.rest.restponse;

import lombok.Data;

@Data
public class ResponseObject {
    private String status;
    private String message;
    private Object data;
}
