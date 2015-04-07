package com.unico.rest;

import com.unico.common.Gcd;
import com.unico.jms.JMSOperation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author S.Shah
 */
@Path("operationjmsrest")
@Stateless
@LocalBean
public class OperationJMSRest implements OperationJMSRestLocal {

    @EJB
    private JMSOperation jMSOperation;

    @Resource(mappedName = "java:jboss/exported/jms/queue/gcdRestQueue")
    private Queue myQueue;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory myQueueFactory;

    @Path("push")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public String push(@FormParam("i1") int i1, @FormParam("i2") int i2) {
        Gcd gcd = new Gcd(i1, i2);        
        try {
            jMSOperation.sendMsg(gcd, myQueue, myQueueFactory);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @Path("list")
    @GET
    @Produces("application/json")
    @Override
    public List<Integer> list() throws Exception {
        List result = new ArrayList();
        for (Message message : jMSOperation.readAllMsg(myQueue, myQueueFactory, 2)) {
            Gcd gcd = (Gcd) ((ObjectMessage) message).getObject();
            result.add(gcd.getI1());
            result.add(gcd.getI2());
        }
        return result;
    }

}
