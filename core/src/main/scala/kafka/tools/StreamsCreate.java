package kafka.tools;


import joptsimple.*;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.utils.Exit;
import org.apache.kafka.common.utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class StreamsCreate {

    private static OptionSpec<String> bootstrapServerOption;
    private static OptionSpec<String> applicationIdOption;
    private static OptionSpec<String> inputTopicsOption;
    private static OptionSpec<String> outputTopicsOption;
    private static OptionSpec helpOption;
    private static OptionSpec versionOption;
    private static OptionSpecBuilder dryRunOption;
    private static OptionSpecBuilder executionOption;
    private static OptionSpec<String> commandConfigOption;


    private OptionSet options = null;

    public static void main(final String[] args) {
        Exit.exit(new StreamsCreate().run(args, null));
    }

    public int run(final String[] args, final Properties config) {

        KafkaAdminClient kafkaAdminClient = null;
        try {
            parseArguments(args);

            final boolean dryRun = options.has(dryRunOption);
            final String groupId = options.valueOf(applicationIdOption);
            final Properties properties = new Properties();

            if(options.has(commandConfigOption)) {
                properties.putAll(Utils.loadProps(options.valueOf(commandConfigOption)));
            }

            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, options.valueOf(bootstrapServerOption));
            kafkaAdminClient = AdminClient.createStream(config);
            validateActiveTopology(groupId, kafkaAdminClient);

            final HashMap<Object, Object> consumerConfig = new HashMap<>(config);
            consumerConfig.putAll(properties);

            final KafkaS


        } catch (final Throwable e) {

        }
    }

    private void validateActiveTopology(String groupId, AdminClient adminClient) throws InterruptedException, ExecutionException {
        final DescribeConsumerGroupsResult describeConsumerGroupsResult =
                adminClient.describeConsumerGroups(Collections.singleton(groupId));
        List<MemberDescription> members =
                new ArrayList<>(describeConsumerGroupsResult.describedGroups().get(groupId).get().members());
        if(!members.isEmpty()) {
            throw new IllegalStateException("Consumer group '" + groupId + "' is still active "
                    + "and has following members: " + members + ". "
                    + "Make sure to stop all running stream application instances before making new streams.");
        }

    }

    private void parseArguments(final String[] args) throws IOException {

    }


}
