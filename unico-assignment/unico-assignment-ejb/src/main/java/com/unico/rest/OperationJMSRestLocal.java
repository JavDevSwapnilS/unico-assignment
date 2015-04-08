package com.unico.rest;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author S.Shah
 */
@Local
public interface OperationJMSRestLocal {

    String push(int i1, int i2);

    public List<Integer> list() throws Exception;

}
