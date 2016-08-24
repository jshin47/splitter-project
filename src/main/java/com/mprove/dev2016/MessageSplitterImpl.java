package com.mprove.dev2016;

import com.mprove.dev2016.textrep.*;

import java.util.ArrayList;
import java.util.List;

public class MessageSplitterImpl implements MessageSplitter {

    public String[] split( final String messageIn ) {

        List<String> splitted = new ArrayList<>();

        int position = 0;

        while (position < messageIn.length()) {
            String remainder = messageIn.substring(position);
            int segmentLengthPrelim = longestEncodeableFittingSegment(remainder, 140);
            System.out.println("prelim length is " + segmentLengthPrelim);

            int correctedLength = segmentLengthWithWhitespaceCorrection(remainder, segmentLengthPrelim, 30);
            String segment = remainder.substring(0, correctedLength);
            splitted.add(segment);

            position += correctedLength;
            if (correctedLength < segmentLengthPrelim) {
                System.out.println("Corrected " + correctedLength);
                position += 1;
            }
        }

        return splitted.toArray(new String[splitted.size()]);
    }

    public AppendsSomeChars splitterEncoding(SmsCharacterEncoding encoding) {
        return (encoding == SmsCharacterEncoding.GSM0338) ? Gsm0338CharCompliantAlphabets.GSM_0338_STANDARD_ALPHABET : Utf16CharacterAppender.UTF_16_APPENDER;
    }

    public int longestEncodeableFittingSegment(String string, SmsCharacterEncoding encoding, int messageLengthInBytes) {

        AppendsSomeChars encodingForEncoding = splitterEncoding(encoding);

        return encodingForEncoding.lengthOfStringOfMaxSize(string, messageLengthInBytes);

    }

    public int longestEncodeableFittingSegment(String string, int messageLengthInBytes) {
        int gsm = longestEncodeableFittingSegment(string, SmsCharacterEncoding.GSM0338, messageLengthInBytes);
        int u16 = longestEncodeableFittingSegment(string, SmsCharacterEncoding.UTF16, messageLengthInBytes);

        int max = Math.max(gsm, u16);

        return max;
    }

    public int segmentLengthWithWhitespaceCorrection(String string, int segmentLength, int charsFromEnding) {

        if (segmentLength == string.length())
            return segmentLength;

        int charsToCheck = Math.min(charsFromEnding, string.length());

        for (int i = segmentLength - 1; i > 0; i--) {
            Character character = string.charAt(i);
            if (Character.isWhitespace(character))
                return i;
        }

        return segmentLength;

    }
}
