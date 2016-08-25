package com.mprove.dev2016.textrep;


public interface RepresentsSomeChars extends RepresentsSomeCodepoints {

    boolean canRepresent(char c);

    @Override
    default boolean canRepresent(int codepoint) {
        char[] chars = Character.toChars(codepoint);

        for (char c : chars) {
            if (!canRepresent(c))
                return false;
        }

        return true;
    }

    default boolean canRepresent(String s) {
        for (char c : s.toCharArray()) {
            if (!canRepresent(c))
                return false;
        }

        return true;
    }

}
