/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unico.common;

import java.io.Serializable;

/**
 *
 * @author S.Shah
 */
public class Gcd implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    private Integer i1;
    private Integer i2;

    public Gcd(Integer i1, Integer i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    public Gcd() {
    }

    public Integer getI1() {
        return i1;
    }

    public void setI1(Integer i1) {
        this.i1 = i1;
    }

    public Integer getI2() {
        return i2;
    }

    public void setI2(Integer i2) {
        this.i2 = i2;
    }

    public String toString() {
        return i1 + " " + i2;
    }
}
