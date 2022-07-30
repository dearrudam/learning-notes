///usr/bin/env jbang "$0" "$@" ; exit $?

import java.io.*;

public class MessageFileWriter implements AutoCloseable {

    private FileWriter writer;

    public MessageFileWriter(String filename) throws IOException {
        this.writer = new FileWriter(filename);
    }

    public void writeMessage(final String message) throws IOException {
        this.writer.write(message);
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public static void main(String... args) throws IOException {
        try (var messageWriter = new MessageFileWriter("messages.txt")) {
            messageWriter.writeMessage("Hello folks!");
        }
    }
}