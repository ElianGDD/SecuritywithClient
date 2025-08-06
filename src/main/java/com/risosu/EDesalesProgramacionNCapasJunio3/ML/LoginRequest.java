/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.ML;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Alien 13
 */
public class LoginRequest {

    @JsonProperty("userName")
    private String userName;
     @JsonProperty("password")
    private String password;

    // Constructor vacío
    public LoginRequest() {
    }

    // Constructor con parámetros
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getters y setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
