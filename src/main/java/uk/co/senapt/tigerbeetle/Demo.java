package uk.co.senapt.tigerbeetle;

import com.tigerbeetle.AccountBatch;
import com.tigerbeetle.Client;
import com.tigerbeetle.RequestException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Demo {
    public static void main(final String[] args) {

        System.out.println("Running on " + System.getProperty("os.name"));
        runCommand("uname");
        runCommand("uname -r");

        String rawConnection = System.getenv().getOrDefault("TIGERBEETLE", "127.0.0.1:3000");
        String[] connection = rawConnection.split(",");
        System.out.println("Connecting to cluster at: " +
                Arrays.stream(connection).collect(Collectors.joining(",")));
        Client client = new Client(0, connection);

        AccountBatch accountBatch = new AccountBatch(1);
        accountBatch.add();
        accountBatch.setCode(123);
        accountBatch.setLedger(123);
        UUID accountId = UUID.randomUUID();
        accountBatch.setId(accountId.getLeastSignificantBits(), accountId.getMostSignificantBits());
        try {
            client.createAccounts(accountBatch);
        } catch (RequestException e) {
            e.printStackTrace();
        }
        System.out.println("Created new account with id: " + accountId.getLeastSignificantBits() +
                "," + accountId.getMostSignificantBits());
    }


    private static void runCommand(String cmd) {
        // Oh my God, how I HATE Java at times. Long live Kotlin :)
        try {
            Process process = Runtime.getRuntime().exec(cmd);

            System.out.print(cmd + ": " );
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);

            int exitCode = process.waitFor();
            assert exitCode == 0;
            future.get();
        } catch (Exception ex) {
            System.err.println("Failed to run command '" + cmd + "' " + ex.getMessage());
        }
    }


    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }
}
