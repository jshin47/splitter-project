package com.mprove.dev2016.textrep;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.w3c.dom.ranges.RangeException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

public class CharacterValueEnumerationUtils {

    public static BiMap<Character, Byte> mappingInducedFromOrdering(List<Character> characters) {
        BiMap<Character, Byte> result = HashBiMap.create();

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            byte induced = (byte)i;
            result.put(c, induced);
        }

        return result;
    }

    public static BiMap<Character, Byte> mappingFromMap(Map<Character, Byte> map) {
        BiMap<Character, Byte> result = HashBiMap.create();

        for (char c : map.keySet()) {
            byte b = map.get(c);
            result.put(c, b);
        }

        return result;
    }

    public static BiMap<Character, Byte> mappingFromInverse(Map<Byte, Character> inverse) {
        BiMap<Character, Byte> result = HashBiMap.create();

        for (byte b : inverse.keySet()) {
            char c = inverse.get(b);
            result.put(c, b);
        }

        return result;
    }

}
