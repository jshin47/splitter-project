package com.mprove.dev2016;

import com.mprove.dev2016.textrep.CharacterRepresentationException;
import com.mprove.dev2016.textrep.Utf16CharacterAppender;
import junit.framework.TestCase;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

public class MessageSplitterImplTest extends TestCase {

    String input = "Lorem psum dolor sit amet, ʆ consectetur adipiscing elit. ʆ Nunc risus quam, ʆ malesuada eget erat a, varius pellentesque lectus. In tempus viverra dolor nec blandit. Maecenas fermentum  ʆ pretium scelerisque. Fusce vitae elit facilisis, tempus risus gravida, sagittis diam. Cras tortor odio, luctus et metus ac, malesuada interdum orci. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Quisque augue tortor, tincidunt non nibh vitae, finibus ullamcorper justo. Aenean commodo eu leo ac tincidunt. In elementum turpis ac fermentum fringilla.";

    private MessageSplitterImpl splitter = new MessageSplitterImpl();

    @Test
    public void testFoo() throws Exception {
        final String result[] = new MessageSplitterImpl().split( "A" );
        assertNotNull( result );
        assertEquals( 1, result.length );
        assertEquals( "A", result[0] );
    }

    @Test
    public void testResplitWithoutSpaces() throws Exception {

        final String message = repeat( 'x', 161 );
        final String parts[] = splitter.split( message );
        assertEquals( 2, parts.length );
        assertEquals( repeat( 'x', 160 ), parts[0] );
        assertEquals( "x", parts[1] );

    }

    @Test
    public void testResplitWithSpaces() throws Exception {

        final String message = repeat( 'x', 150 ) + " " + repeat( 'x', 10 );
        final String parts[] = splitter.split( message );
        assertEquals( 2, parts.length );
        assertEquals( repeat( 'x', 150 ), parts[0] );
        assertEquals( repeat( 'x', 10 ), parts[1] );

    }

    @Test
    public void testResplitWithNonGsm() throws Exception {

        final String message = repeat( 'á', 71 );
        final String parts[] = splitter.split( message );
        assertEquals( 2, parts.length );
        assertEquals( repeat( 'á', 70 ), parts[0] );
        assertEquals( "á", parts[1] );

    }

    @Test
    public void testResplitWithDoubleWidthGsm() throws Exception {

        final String message = "^" + repeat( 'x', 159 );
        final String parts[] = splitter.split( message );
        assertEquals( 2, parts.length );
        assertEquals( "^" + repeat( 'x', 158 ), parts[0] );
        assertEquals( "x", parts[1] );

    }

    @Test
    public void testResplitChoosingPerSegmentBetweenGsmAndUtf16() throws Exception {

        final String message = repeat( 'x', 71 ) + repeat( 'á', 71 );
        final String parts[] = splitter.split( message );
        assertEquals( 3, parts.length );
        assertEquals( repeat( 'x', 71 ), parts[0] );
        assertEquals( repeat( 'á', 70 ), parts[1] );
        assertEquals( "á", parts[2] );

    }

    @Test
    public void testDontSplitUtf16SurrogatePairs() throws Exception {

        final String message = repeat( 'á', 69 ) + "\uD808\uDC00";
        final String parts[] = splitter.split( message );
        assertEquals( 2, parts.length );
        assertEquals( repeat( 'á', 69 ), parts[0] );
        assertEquals( "\uD808\uDC00", parts[1] );

    }

    private String repeat( final char c, final int count ) {
        final char chars[] = new char[count];
        Arrays.fill( chars, 0, count, c );
        return new String( chars );
    }

    @Test
    public void testWhitespaceCorrection() throws Exception {

        String word = "word";
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 39; i++) {
            input.append(word);
            input.append(" ");
        }

        final String result[] = new MessageSplitterImpl().split(input.toString());

        assertEquals(2, result.length);
    }

    @Test
    public void testGsmEscapeSequenceNotSplitApartSegments() throws Exception {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 159; i++) {
            builder.append('a');
        }
        builder.append(' ');
        builder.append('}');

        final String result[] = new MessageSplitterImpl().split(builder.toString());

        assertEquals(2, result.length);
        assertEquals(159, result[0].length());
    }

}
