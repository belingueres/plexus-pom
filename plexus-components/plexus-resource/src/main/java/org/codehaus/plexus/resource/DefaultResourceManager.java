package org.codehaus.plexus.resource;

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

import java.util.Iterator;
import java.io.InputStream;

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.resource.manager.ResourceLoaderManager;
import org.codehaus.plexus.resource.loader.ResourceLoader;
import org.codehaus.plexus.resource.loader.ResourceNotFoundException;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class DefaultResourceManager
    extends AbstractLogEnabled
    implements ResourceManager, Initializable
{
    /** @requirement */
    private ResourceLoaderManager resourceLoaderManager;

    // ----------------------------------------------------------------------
    // Component Lifecycle
    // ----------------------------------------------------------------------

    public void initialize()
    {
    }

    // ----------------------------------------------------------------------
    // ResourceManager Implementation
    // ----------------------------------------------------------------------

    public InputStream getResourceAsInputStream( String name )
        throws ResourceNotFoundException
    {
        InputStream is = null;

        for ( Iterator it = resourceLoaderManager.getResourceLoaders(); it.hasNext(); )
        {
            ResourceLoader resourceLoader = (ResourceLoader) it.next();

            try
            {
                is = resourceLoader.getResourceAsInputStream( name );

                break;
            }
            catch ( ResourceNotFoundException e )
            {
                // ignore and continue to the next loader
            }
        }

        if ( is == null )
        {
            throw new ResourceNotFoundException( name );
        }

        return is;
    }
}
