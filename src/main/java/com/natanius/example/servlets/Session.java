package com.natanius.example.servlets;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Session {

    private final UUID sessionId;
    private String userName;

}
