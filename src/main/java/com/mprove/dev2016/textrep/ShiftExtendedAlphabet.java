package com.mprove.dev2016.textrep;

import com.sun.xml.internal.rngom.parse.compact.EOFException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ShiftExtendedAlphabet extends AbsCharCompliantAlphabet {

    private final NaiveEnumeratedAlphabet baseAlphabet;
    private final byte shiftByte;
    private final AbsCharCompliantAlphabet extensionAlphabet;

    public ShiftExtendedAlphabet(NaiveEnumeratedAlphabet baseAlphabet, byte shiftByte, AbsCharCompliantAlphabet extensionAlphabet) {
        this.baseAlphabet = baseAlphabet;
        this.shiftByte = shiftByte;
        this.extensionAlphabet = extensionAlphabet;
    }

    @Override
    public boolean canRepresent(char c) {
        return baseAlphabet.canRepresent(c) || extensionAlphabet.canRepresent(c);
    }

    @Override
    public void append(char c, OutputStream outputStream) throws IOException, CharacterRepresentationException {

        if (baseAlphabet.canRepresent(c)) {
            baseAlphabet.append(c, outputStream);
        } else {
            outputStream.write(shiftByte);
            extensionAlphabet.append(c, outputStream);
        }

    }

    @Override
    public char readFrom(InputStream stream) throws EOFException, IOException, CharacterRepresentationException {
        int next = stream.read();

        if (next == -1)
            throw new EOFException();
        else if (shiftByte == next)
            return extensionAlphabet.readFrom(stream);

        return baseAlphabet.charRepresentation((byte)next);
    }

    @Override
    public short maxBitLength() {
        int baseMax = baseAlphabet.maxBitLength();
        int exttMax = extensionAlphabet.maxBitLength();

        return (short) Math.max(baseMax, exttMax);
    }
}
