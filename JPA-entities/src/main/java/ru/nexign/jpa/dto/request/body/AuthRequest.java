package ru.nexign.jpa.dto.request.body;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class AuthRequest implements Serializable {
    private String username;
    private String password;
}
