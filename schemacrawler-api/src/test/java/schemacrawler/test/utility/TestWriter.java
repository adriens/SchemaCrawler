/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2018, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.test.utility;


import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public final class TestWriter
  extends Writer
  implements TestOutputCapture
{

  private static final String lineSeparator = System
    .getProperty("line.separator");

  private final TestOutputStream out;

  public TestWriter()
    throws IOException
  {
    out = new TestOutputStream();
  }

  @Override
  public void close()
    throws IOException
  {
    out.close();
  }

  @Override
  public void flush()
    throws IOException
  {
    out.flush();
  }

  @Override
  public Path getFilePath()
  {
    return out.getFilePath();
  }

  @Override
  public String getFileContents()
  {
    return out.getFileContents();
  }

  public void println()
  {
    writeout(lineSeparator);
  }

  public void println(final Object x)
  {
    if (x == null)
    {
      println("null");
    }
    else
    {
      println(x.toString());
    }
  }

  public void println(final String x)
  {
    writeout(x);
    println();
  }

  @Override
  public String toString()
  {
    return out.toString();
  }

  @Override
  public void write(final char[] cbuf, final int off, final int len)
    throws IOException
  {
    writeout(new String(cbuf, off, len));
  }

  private void writeout(final String x)
  {
    try
    {
      if (x == null)
      {
        out.write("null".getBytes(UTF_8));
      }
      else
      {
        out.write(x.getBytes(UTF_8));
      }
    }
    catch (final IOException e)
    {
      // Ignore
    }
  }

}
