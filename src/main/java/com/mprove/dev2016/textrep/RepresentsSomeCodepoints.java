package com.mprove.dev2016.textrep;

public interface RepresentsSomeCodepoints {

    boolean canRepresent(int codepoint);

    default boolean canRepresent(String string) {
        return string.codePoints().allMatch(x -> canRepresent(x));
    }

}
