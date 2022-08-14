package io.dpopkov.pomidor;

import java.io.*;

public class BufferedFile {
    private final byte[] bytes;
    private final long length;

    public BufferedFile(String pathToFile) {
        File file = new File(pathToFile);
        length = file.length();
        bytes = new byte[(int) length];
        readAllBytes(file);
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    private void readAllBytes(File file) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int numRead = in.read(bytes);
            if (numRead != length) {
                throw new PomidorException("Failed to read all bytes");
            }
        } catch (IOException e) {
            throw new PomidorException("Failed to read file " + file, e);
        }
    }
}
