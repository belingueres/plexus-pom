package org.codehaus.plexus.util;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Generated by JUnitDoclet, a tool provided by ObjectFab GmbH under LGPL.
 * Please see www.junitdoclet.org, www.gnu.org and www.objectfab.de for
 * informations about the tool, the licence and the authors.
 */
public class InterpolationFilterReaderTest
    extends TestCase
{
    public InterpolationFilterReaderTest( String name )
    {
        super( name );
    }

    /**
     * The JUnit setup method
     */
    protected void setUp()
        throws Exception
    {
    }

    /**
     * The teardown method for JUnit
     */
    protected void tearDown()
        throws Exception
    {
    }

    public void testDefaultInterpolationWithNonInterpolatedValueAtEnd()
        throws Exception
    {
        Map m = new HashMap();
        m.put( "name", "jason" );
        m.put( "noun", "asshole" );

        String foo = "${name} is an ${noun}. ${not.interpolated}";

        InterpolationFilterReader reader = new InterpolationFilterReader( new StringReader( foo ), m );

        StringWriter writer = new StringWriter();
        IOUtil.copy( reader, writer );

        String bar = writer.toString();
        assertEquals( "jason is an asshole. ${not.interpolated}", bar );
    }

    public void testDefaultInterpolationWithInterpolatedValueAtEnd()
        throws Exception
    {
        Map m = new HashMap();
        m.put( "name", "jason" );
        m.put( "noun", "asshole" );

        String foo = "${name} is an ${noun}";

        InterpolationFilterReader reader = new InterpolationFilterReader( new StringReader( foo ), m );

        StringWriter writer = new StringWriter();
        IOUtil.copy( reader, writer );

        String bar = writer.toString();
        assertEquals( "jason is an asshole", bar );
    }

    public void testInterpolationWithSpecifiedBoundaryTokens()
        throws Exception
    {
        Map m = new HashMap();
        m.put( "name", "jason" );
        m.put( "noun", "asshole" );

        String foo = "@name@ is an @noun@. @not.interpolated@ baby @foo@. @bar@";

        InterpolationFilterReader reader = new InterpolationFilterReader( new StringReader( foo ), m, "@", "@" );

        StringWriter writer = new StringWriter();
        IOUtil.copy( reader, writer );

        String bar = writer.toString();
        assertEquals( "jason is an asshole. @not.interpolated@ baby @foo@. @bar@", bar );
    }

    public void testInterpolationWithSpecifiedBoundaryTokensWithNonInterpolatedValueAtEnd()
        throws Exception
    {
        Map m = new HashMap();
        m.put( "name", "jason" );
        m.put( "noun", "asshole" );

        String foo = "@name@ is an @foobarred@";

        InterpolationFilterReader reader = new InterpolationFilterReader( new StringReader( foo ), m, "@", "@" );

        StringWriter writer = new StringWriter();
        IOUtil.copy( reader, writer );

        String bar = writer.toString();
        assertEquals( "jason is an @foobarred@", bar );
    }

    public void testInterpolationWithSpecifiedBoundaryTokensWithInterpolatedValueAtEnd()
        throws Exception
    {
        Map m = new HashMap();
        m.put( "name", "jason" );
        m.put( "noun", "asshole" );

        String foo = "@name@ is an @noun@";

        InterpolationFilterReader reader = new InterpolationFilterReader( new StringReader( foo ), m, "@", "@" );

        StringWriter writer = new StringWriter();
        IOUtil.copy( reader, writer );

        String bar = writer.toString();
        assertEquals( "jason is an asshole", bar );
    }
}
