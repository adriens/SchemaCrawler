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
package schemacrawler.tools.iosource;


import static java.nio.file.Files.exists;
import static java.nio.file.Files.newBufferedReader;
import static java.util.Objects.requireNonNull;
import static sf.util.IOUtility.isFileReadable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.logging.Level;

import sf.util.SchemaCrawlerLogger;
import sf.util.StringFormat;

public class FileInputResource
  implements InputResource
{

  private static final SchemaCrawlerLogger LOGGER = SchemaCrawlerLogger
    .getLogger(FileInputResource.class.getName());

  public static FileInputResource allowEmptyFileInputResource(final Path filePath)
    throws IOException
  {
    return new FileInputResource(filePath, true);
  }

  private final Path inputFile;
  private final boolean allowEmptyFile;

  public FileInputResource(final Path filePath)
    throws IOException
  {
    this(filePath, false);
  }

  private FileInputResource(final Path filePath, final boolean allowEmptyFile)
    throws IOException
  {
    inputFile = requireNonNull(filePath, "No file path provided").normalize()
      .toAbsolutePath();
    this.allowEmptyFile = allowEmptyFile;
    if (!allowEmptyFile && !isFileReadable(inputFile))
    {
      final IOException e = new IOException("Cannot read file, " + inputFile);
      LOGGER.log(Level.FINE, e.getMessage(), e);
      throw e;
    }
  }

  public Path getInputFile()
  {
    return inputFile;
  }

  @Override
  public Reader openNewInputReader(final Charset charset)
    throws IOException
  {
    requireNonNull(charset, "No input charset provided");

    if (allowEmptyFile && !exists(inputFile))
    {
      return new StringReader("");
    }

    final Reader reader = newBufferedReader(inputFile, charset);
    LOGGER.log(Level.INFO,
               new StringFormat("Opened input reader to file <%s>", inputFile));

    return new InputReader(getDescription(), reader, true);
  }

  @Override
  public String toString()
  {
    return inputFile.toString();
  }

}
