/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.summitthai.bonita.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Wittakarn
 */
@Getter
@Setter
@ToString
public class User implements Serializable{
    private String name;
    private String password;
}
