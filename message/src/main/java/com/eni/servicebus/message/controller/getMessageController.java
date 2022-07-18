package com.eni.servicebus.message.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.util.BinaryData;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

@RestController
public class getMessageController {
	
	//String connectionString = "Endpoint={NamespaceAsb.servicebus.windows.net};SharedAccessKeyName={RootManageSharedAccessKey};"
      //      + "SharedAccessKey={Endpoint=sb://namespaceasb.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=95xYR3iL4hji/Lyr5AkYIucr4B4Uo9GueGGZ2sIvNVQ=}";
	
	String connectionString = "Endpoint=sb://namespaceasb.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=95xYR3iL4hji/Lyr5AkYIucr4B4Uo9GueGGZ2sIvNVQ=";
	String queueName="queue01";
	
	 @GetMapping("/talkToMe")
	    public String talking()
	    {
	        return("Hello from GetMessage");

	    }
	
	@GetMapping("/getMessage")
	public List<String> readMessage() {
		
		List<String> messageList=new ArrayList<String>();  
		
		ServiceBusReceiverClient receiver = new ServiceBusClientBuilder()
	            .connectionString(connectionString)
	            .receiver()
	            .maxAutoLockRenewDuration(Duration.ofMinutes(1))
	            .queueName(queueName)
	            .buildClient();
		
		//for (int i = 0; i < 10; i++) {

            receiver.receiveMessages(2).stream().forEach(message -> {
                // Process message. The message lock is renewed for up to 1 minute.
                
            	System.out.printf("Sequence #: %s. Contents: %s%n", message.getSequenceNumber(), message.getBody());
                messageList.add("Sequence #: " + message.getSequenceNumber() + " Contents: " + message.getBody());
                
                // Messages from the sync receiver MUST be settled explicitly.
                receiver.complete(message);
            });
        //}

        // Close the receiver.
        receiver.close();
		
		
		//return("Message read complete");
		return(messageList);
	}

}
