# Configuration of message queue for wildfly server as below.

1) Queue for rest operation
```html
	<jms-queue name="gcdRestQueue">
			<entry name="jms/queue/gcdRestQueue"/>
			<entry name="java:jboss/exported/jms/queue/gcdRestQueue"/>
	</jms-queue>
```

2) Queue for soap operation

```html
	<jms-queue name="gcdSoapQueue">
        		<entry name="jms/queue/gcdSoapQueue"/>
        		<entry name="java:jboss/exported/jms/queue/gcdSoapQueue"/>
  	</jms-queue>
```

-----------------------------------------------------------------------------------
Exposed web services

1. Rest web services

	(i) Exposed stateless session bean (Ejb module)
			
			Bean Name: 	com.unico.rest.OperationJMSRest
			URL		 :	http://localhost:8080/unico/resources/operationjmsrest	
      

	(ii) Exposed via jax_rs (Web module)
	
			Class Name:	unico.rest.JMSRest
			URL		  :http://localhost:8080/unico/resources/operationjmsrestweb

2. Soap web services

	(i) Exposed stateless session bean (Ejb module)

			Bean Name: 	com.unico.soap.OperationJMSSoap
			URL		 :	http://localhost:8080/unico-ejb/OperationJMSSoap?wsdl
			
	(ii) Exposed via jax_rs (Web module)
			
			Class Name:	unico.rest.JMSRest
			URL		  :	http://localhost:8080/unico/JMSSoap?wsdl
			
