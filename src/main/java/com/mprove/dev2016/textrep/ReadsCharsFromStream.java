package com.mprove.dev2016.textrep;

import com.sun.xml.internal.rngom.parse.compact.EOFException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;

/**
 * Created by justin on 8/24/16.
 */
public interface ReadsCharsFromStream {

    char readFrom(InputStream stream) throws EOFException, IOException, CharacterRepresentationException;

    default String readAll(InputStream inputStream) throws IOException, CharacterRepresentationException {
        StringBuilder builder = new StringBuilder();

        do {
            try {
                builder.append(this.readFrom(inputStream));
            } catch (java.io.EOFException finished) {
                break;
            }
        } while (true);

        return builder.toString();
    }

    default String fromByteArray(byte[] data) throws IOException, CharacterRepresentationException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);

        return readAll(bais);
    }
}
