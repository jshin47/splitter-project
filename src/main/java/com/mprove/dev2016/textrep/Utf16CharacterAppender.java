package com.mprove.dev2016.textrep;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Utf16CharacterAppender {

    public static final AppendsSomeChars UTF_16_APPENDER;

    static {
        UTF_16_APPENDER = new AppendsSomeChars() {
            @Override
            public void append(char c, OutputStream outputStream) throws IOException, CharacterRepresentationException {
                String s = Character.toString(c);
                int value = Character.getNumericValue(c);

                byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();

                byte[] output = new byte[2];
                output[0] = bytes[2];
                output[1] = bytes[3];

                outputStream.write(output);
            }
        };
    }
}
