package com.mprove.dev2016.textrep;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by justin on 8/24/16.
 */
public interface AppendsSomeChars {

    void append(char c, OutputStream outputStream) throws IOException, CharacterRepresentationException;

    default void write(String string, OutputStream outputStream) throws IOException, CharacterRepresentationException {
        for (char c : string.toCharArray()) {
            append(c, outputStream);
        }
    }

    default byte[] getByteArray(String string) throws IOException, CharacterRepresentationException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        write(string, baos);

        return baos.toByteArray();
    }

    default int byteSize(String string) throws IOException, CharacterRepresentationException {
        return getByteArray(string).length;
    }

    default int packedSize(int byteSize) {
        return byteSize;
    }

    default int lengthOfStringOfMaxSize(String string, int byteSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int count = 0;

        for (int codepoint : string.codePoints().toArray()) {
            char[] characters = Character.toChars(codepoint);

            try {
                for (char c : characters) {
                    append(c, baos);
                }
            } catch (Exception e) {
                return count;
            }

            if (packedSize(baos.size()) <= byteSize) {
                count += characters.length;
            } else {
                return count;
            }

        }

        return count;
    }

}
