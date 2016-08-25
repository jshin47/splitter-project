package com.mprove.dev2016.textrep;

import com.google.common.collect.BiMap;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Optional;


public class NaiveEnumeratedAlphabet extends AbsCharCompliantAlphabet  {

    private final BiMap<Character, Byte> charToByte;
    private final BiMap<Byte, Character> byteToChar;

    public NaiveEnumeratedAlphabet(BiMap<Character, Byte> forward) {
        this.charToByte = forward;
        this.byteToChar = forward.inverse();
    }

    public byte byteRepresentation(char c) throws CharacterRepresentationException {
        if (!canRepresent(c))
            throw CharacterRepresentationException.from(c);

        return this.charToByte.get(c);
    }

    public char charRepresentation(byte b) throws CharacterRepresentationException {
        if (!canRepresent(b))
            throw CharacterRepresentationException.from(b);
        return this.byteToChar.get(b);
    }

    public boolean canRepresent(byte b) {
        return this.byteToChar.containsKey(b);
    }

    @Override
    public boolean canRepresent(char c) {
        return this.charToByte.containsKey(c);
    }

    @Override
    public void append(char c, OutputStream outputStream) throws IOException, CharacterRepresentationException {
        outputStream.write(byteRepresentation(c));
    }

    @Override
    public char readFrom(InputStream stream) throws EOFException, IOException, CharacterRepresentationException {
        int next = stream.read();

        if (next == -1)
            throw new EOFException();

        return this.charRepresentation((byte)next);

    }

    @Override
    public short maxBitLength() {
        Optional<Integer> maxValueO = this.charToByte.values().stream().max((x, y) -> x.intValue() - y.intValue()).map(x -> x.intValue());

        if (!maxValueO.isPresent())
            return  0;

        int maxValue = maxValueO.get();

        if (maxValue > 127)
            return 8;
        else if (maxValue > 63)
            return 7;
        else if (maxValue > 31)
            return 6;
        else if (maxValue > 15)
            return 5;
        else if (maxValue > 7)
            return 4;
        else if (maxValue > 3)
            return 3;
        else if (maxValue > 1)
            return 2;
        else if (maxValue > 0)
            return  1;

        return  0;

    }

    public ShiftExtendedAlphabet extendedAlphabet(byte shiftByte, AbsCharCompliantAlphabet extension) {
        return new ShiftExtendedAlphabet(this, shiftByte, extension);
    }

    public static NaiveEnumeratedAlphabet fromOrderingOfCharacters(Character... characters) {
        return new NaiveEnumeratedAlphabet(CharacterValueEnumerationUtils.mappingInducedFromOrdering(Arrays.asList(characters)));
    }
}
