package com.mprove.dev2016.textrep;

/**
 * Created by justin on 8/24/16.
 */
public interface RepresentsSomeCodepoints {

    boolean canRepresent(int codepoint);

    default boolean canRepresent(String string) {
        return string.codePoints().allMatch(x -> canRepresent(x));
    }

}
