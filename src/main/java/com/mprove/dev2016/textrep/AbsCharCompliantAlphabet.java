package com.mprove.dev2016.textrep;

import java.io.*;

public abstract class AbsCharCompliantAlphabet implements AppendsSomeChars, ReadsCharsFromStream, RepresentsSomeChars  {

    public byte[] getByteArray(String input, int maxSize) throws IOException, CharacterRepresentationException {
        return getByteArray(input.substring(0, getMaxLength(input, maxSize)));
    }

    public int getMaxLength(String input, int byteSize) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        char[] array = input.toCharArray();

        for (int i = 0; i < array.length; i++) {
            char c = array[i];

            try {
                append(c, outputStream);
            } catch (Exception e) {
                return i;
            }

            if (outputStream.size() > byteSize)
                return i;
        }

        return array.length;
    }

}
