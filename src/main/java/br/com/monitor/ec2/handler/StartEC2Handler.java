package br.com.monitor.ec2.handler;


import br.com.monitor.ec2.response.MonitorResponse;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StartEC2Handler implements RequestHandler<Map<String,Object>,MonitorResponse> {


    private final static String AMI_ID = "{YOUR_AMI_ID}";
    private final static String KEY_NAME = "{YOUR_KEY_NAME}";
    private final static String SECURITY_GROUP_ID = "{YOUR_SECURITY_GROUP_ID}";
    private final static String SUBNET_ID = "{YOUR_SUBNET_ID}";

    public MonitorResponse handleRequest(Map<String, Object> input, Context context) {

        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("ec2.us-east-1.amazonaws.com", "us-east-1");
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

        TagSpecification tagSpecification = new TagSpecification();
        List<Tag> tags = new ArrayList<>();

        Tag tag = new Tag();
        tag.setKey("Name");
        tag.setValue("{YOUR_INSTANCE_NAME}");

        tags.add(tag);

        tagSpecification.setResourceType(ResourceType.Instance);
        tagSpecification.setTags(tags);

        runInstancesRequest.withImageId(AMI_ID)
                .withInstanceType(InstanceType.T2Micro)
                .withMinCount(1)
                .withMaxCount(1)
                .withKeyName(KEY_NAME)
                .withSubnetId(SUBNET_ID)
                .withTagSpecifications(tagSpecification)
                .withSecurityGroupIds(SECURITY_GROUP_ID);


        AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.standard().withEndpointConfiguration(endpoint).build();

        RunInstancesResult response = amazonEC2.runInstances(runInstancesRequest);

        return new MonitorResponse(response.getReservation(), response.getSdkHttpMetadata().getHttpStatusCode());
    }
}
