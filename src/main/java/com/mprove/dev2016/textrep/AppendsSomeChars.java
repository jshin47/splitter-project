package com.mprove.dev2016.textrep;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;

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

    default byte[] getPackedArray(byte[] data) {
        return packArrayWithMaxBitlength(data, maxBitLength());
    }

    default int getPackedArraySize(byte[] data) {
        return getPackedArray(data).length;
    }

    // A FAST AND ACCURATE WAY TO CHECK.
    default int getPackedArraySizeFast(int inputLength) {
        return (int) Math.ceil((double) inputLength * ( (double) maxBitLength() / 8.000 ) );
    }

    default short maxBitLength() {
        return 8;
    }

    default int byteSize(String string) throws IOException, CharacterRepresentationException {
        return getByteArray(string).length;
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

            // This is an odd-looking condition but the left part of the condition is strictly necessary for the right part, which takes time, so it saves us all those cases other than the ones we actually need to pack.
            if (getPackedArraySizeFast(baos.size()) <= byteSize) {
                count += characters.length;
            } else {
                return count;
            }

        }

        return count;
    }

    static byte[] packArrayWithMaxBitlength(byte[] data, short maxLength) {
        if (maxLength == 8)
            return data;

        System.out.println("packing original length " + data.length + " with bit max " + maxLength);

        BitSet originalBits = BitSet.valueOf(data);
        BitSet bits = new BitSet(data.length * maxLength);

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < maxLength; j++) {
                int originalPosition = i * 8 + j;
                boolean originalValue = originalBits.get(originalPosition);
                int newPosition = i   * maxLength + j;
                bits.set(newPosition, originalValue);
            }
        }

        byte[] packed = bits.toByteArray();

        System.out.println("the packed size is now " + packed.length);

        return packed;
    }

}
