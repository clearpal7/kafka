package kafka.tools;


import joptsimple.*;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.common.utils.Exit;
import org.apache.kafka.common.utils.Utils;

import java.io.IOException;
import java.util.Properties;

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
        Exit.exit(new StreamsCreate().run(args));
    }

    public int run(final String[] args) {

        KafkaAdminClient kafkaAdminClient = null;
        try {
            parseArguments(args);

            final boolean dryRun = options.has(dryRunOption);
            final String groupId = options.valueOf(applicationIdOption);
            final Properties properties = new Properties();

            if(options.has(commandConfigOption)) {
                properties.putAll(Utils.loadProps(options.valueOf(commandConfigOption)));
            }




        } catch (IOException e) {

        }
    }

    private void parseArguments(final String[] args) throws IOException {

    }


}
