///usr/bin/env jbang "$0" "$@" ; exit $?

import java.io.*;

import java.util.*;

public class MessageFileWriterEAM {

    private FileWriter writer;

    private MessageFileWriterEAM(String filename) throws IOException {
        this.writer = new FileWriter(filename);
    }

    public void writeMessage(final String message) throws IOException {
        this.writer.write(message);
    }

    private void closeFile() throws IOException {
        this.writer.close();
    }

    @FunctionalInterface
    public interface UseMessageFileWriter<T, E extends Throwable> {
        void accept(T instance) throws E;
    }

    public static void use(final String filename,
            final UseMessageFileWriter<MessageFileWriterEAM, IOException> block) throws IOException {
        final MessageFileWriterEAM messageFileWriterEAM = new MessageFileWriterEAM(filename);
        try {
            block.accept(messageFileWriterEAM);
        } finally {
            messageFileWriterEAM.closeFile();
        }
    }

    public static void main(String[] args) throws IOException {
        MessageFileWriterEAM.use(
                "messages.txt",
                writerEAM -> writerEAM.writeMessage("Hello folks!"));
    }

}