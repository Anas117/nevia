package com.pds.neviauser.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserCreateDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;

}
