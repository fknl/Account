package techagi;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.List;
import java.util.Map.Entry;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * Prerequisites: You must have a valid Amazon Web Services developer account,
 * and be signed up to use Amazon SQS. For more information about Amazon SQS,
 * see https://aws.amazon.com/sqs
 * <p>
 * Make sure that your credentials are located in ~/.aws/credentials
 */
public class SQSSimpleJavaClientExample {
    public  void sqs() {
        /*
         * Create a new instance of the builder with all defaults (credentials
         * and region) set automatically. For more information, see
         * Creating Service Clients in the AWS SDK for Java Developer Guide.
         */
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        System.out.println("===============================================");
        System.out.println("Getting Started with Amazon SQS Standard Queues");
        System.out.println("===============================================\n");

        try {
            // Create a queue.
            System.out.println("Creating a new SQS queue called MyQueue.\n");
            final CreateQueueRequest createQueueRequest =
                    new CreateQueueRequest("XXXX");
            final String myQueueUrl = sqs.createQueue(createQueueRequest)
                    .getQueueUrl();

            // List all queues.
            System.out.println("Listing all queues in your account.\n");
            for (final String queueUrl : sqs.listQueues().getQueueUrls()) {
                System.out.println("  QueueUrl leri bunlar " + queueUrl);
            }
            System.err.println("AaAAAAAA");
            final GetQueueUrlResult ufukSQS = sqs.getQueueUrl("UfukSQS");

            System.out.println();

            // Send a message.
            System.out.println("Sending a message to MyQueue.\n");
            sqs.sendMessage(new SendMessageRequest(ufukSQS.getQueueUrl(),
                    "This is my message text."));

            // Receive messages.
            System.out.println("Receiving messages from MyQueue.\n");
            final ReceiveMessageRequest receiveMessageRequest =
                    new ReceiveMessageRequest(ufukSQS.getQueueUrl());
            final List<Message> messages = sqs.receiveMessage(receiveMessageRequest)
                    .getMessages();
            for (final Message message : messages) {
                System.out.println("Message");
                System.out.println("  MessageId:     "
                        + message.getMessageId());
                System.out.println("  ReceiptHandle: "
                        + message.getReceiptHandle());
                System.out.println("  MD5OfBody:     "
                        + message.getMD5OfBody());
                System.out.println("  Body:          "
                        + message.getBody());
                for (final Entry<String, String> entry : message.getAttributes()
                        .entrySet()) {
                    System.out.println("Attribute");
                    System.out.println("  Name:  " + entry
                            .getKey());
                    System.out.println("  Value: " + entry
                            .getValue());
                }
            }
            System.out.println();

            // Delete the messages.
            System.out.println("Deleting the messages that we received.\n");

            for (final Message message : messages) {
                sqs.deleteMessage(new DeleteMessageRequest(ufukSQS.getQueueUrl(),
                        message.getReceiptHandle()));
            }

            // Delete the queue.
            System.out.println("Deleting the test queue.\n");
            sqs.deleteQueue(new DeleteQueueRequest(ufukSQS.getQueueUrl()));
        } catch (final AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (final AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means " +
                    "the client encountered a serious internal problem while " +
                    "trying to communicate with Amazon SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}