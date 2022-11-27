package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private  String username;
    private  String password;
    private  String role;
}