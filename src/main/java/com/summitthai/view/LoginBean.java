/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.summitthai.view;

import com.summitthai.bonita.domain.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Wittakarn
 */
@Getter
@Setter
@ToString
@Slf4j
@SessionScoped
@ManagedBean(name="loginBean")
public class LoginBean implements Serializable{
    private User user;
    
    public LoginBean(){
        user = new User();
    }
    
    public String login(){
        return "menu";
    }
}
