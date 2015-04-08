package unico.rest;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
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
import com.unico.common.Gcd;
import com.unico.common.MessageQueueOperation;

/**
 *
 * @author S.Shah
 */
@Path("operationjmsrestweb")
public class JMSRest {

    @Resource(mappedName = "java:jboss/exported/jms/queue/gcdRestQueue")
    private Queue myQueue;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory myQueueFactory;

    private MessageQueueOperation messageQueueOperation = MessageQueueOperation.getInstance();

    @Path("push")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public String push(@FormParam("i1") int i1, @FormParam("i2") int i2) {
        Gcd gcd = new Gcd(i1, i2);
        try {
            messageQueueOperation.sendMessage(gcd, myQueue, myQueueFactory);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @Path("list")
    @GET
    @Produces("application/json")

    public List<Integer> list() throws Exception {
        List result = new ArrayList();
        for (Message message : messageQueueOperation.readAllMessages(myQueue, myQueueFactory)) {
            Gcd gcd = (Gcd) ((ObjectMessage) message).getObject();
            result.add(gcd.getI1());
            result.add(gcd.getI2());
        }
        return result;
    }
}
