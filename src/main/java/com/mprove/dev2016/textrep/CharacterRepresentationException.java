package com.mprove.dev2016.textrep;

import org.apache.commons.codec.binary.Hex;

import java.util.Optional;

public class CharacterRepresentationException extends Exception {

    private final Optional<Character> unrepresentableCharacter;
    private final Optional<byte[]> unrepresentableData;

    private static String messageForUnrepChar(char c) {
        return "Cannot represent character '" + c + "' for some reason, likely because of a lack of inclusion in an alphabet.";
    }

    private static String messageForUnrepData(byte[] data) {
        return "Cannot represent data " + Hex.encodeHexString(data) + "for some reason because, uh, I donno";
    }

    private CharacterRepresentationException(String message) {
        super(message);
        this.unrepresentableCharacter = Optional.empty();
        this.unrepresentableData = Optional.empty();
    }

    private CharacterRepresentationException(char c) {
        super(messageForUnrepChar(c));
        this.unrepresentableCharacter = Optional.of(c);
        this.unrepresentableData = Optional.empty();
    }

    private CharacterRepresentationException(byte[] data) {
        super(messageForUnrepData(data));
        this.unrepresentableData = Optional.of(data);
        this.unrepresentableCharacter = Optional.empty();
    }

    public static CharacterRepresentationException from(byte dat) {
        byte data[] = new byte[1];
        data[0] = dat;
        return CharacterRepresentationException.from(data);
    }
    public static CharacterRepresentationException from(byte[] data) { return new CharacterRepresentationException(data); }
    public static CharacterRepresentationException from(char c) { return new CharacterRepresentationException(c); }
    public static CharacterRepresentationException withDescription(String mesg) { return new CharacterRepresentationException(mesg); }

}
