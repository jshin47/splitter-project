package com.mprove.dev2016.textrep;

import com.google.common.collect.Maps;

import java.util.Map;

public class Gsm0338CharCompliantAlphabets {
    private Gsm0338CharCompliantAlphabets() {

    }

    public static final ShiftExtendedAlphabet GSM_0338_STANDARD_ALPHABET;
    public static final byte GSM_0338_STANDARD_SHIFT_BYTE = (byte) 0x1B;
    private static final NaiveEnumeratedAlphabet _GSM_0338_BASE;
    private static final AbsCharCompliantAlphabet _GSM_0338_EXT;

    static {

        _GSM_0338_BASE = NaiveEnumeratedAlphabet.fromOrderingOfCharacters(
                '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'Ç', '\n', 'Ø', 'ø', '\r', 'Å', 'å', 'Δ', '_', 'Φ', 'Γ', 'Λ', 'Ω', 'Π', 'Ψ', 'Σ', 'Θ', 'Ξ', null, 'Æ', 'æ', 'ß', 'É', ' ', '!', '"', '#', '¤', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '¡', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§', '¿', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à');

        Map<Character, Byte> extMap = Maps.newHashMapWithExpectedSize(9);

        extMap.put('^', (byte) 0x14);
        extMap.put('{', (byte) 0x28);
        extMap.put('}', (byte) 0x29);
        extMap.put('\\', (byte) 0x2F);
        extMap.put('[', (byte) 0x3C);
        extMap.put('~', (byte) 0x3D);
        extMap.put(']', (byte) 0x3E);
        extMap.put('|', (byte) 0x40);
        extMap.put('€', (byte) 0x65);

        _GSM_0338_EXT = new NaiveEnumeratedAlphabet(CharacterValueEnumerationUtils.mappingFromMap(extMap));

        GSM_0338_STANDARD_ALPHABET = new ShiftExtendedAlphabet(_GSM_0338_BASE, GSM_0338_STANDARD_SHIFT_BYTE, _GSM_0338_EXT);

    }

}
