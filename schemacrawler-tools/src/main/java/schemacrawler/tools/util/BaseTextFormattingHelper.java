/* 
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2008, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */

package schemacrawler.tools.util;


import schemacrawler.execute.QueryExecutorException;
import schemacrawler.tools.OutputFormat;
import sf.util.Utilities;

/**
 * Methods to format entire rows of output as HTML.
 * 
 * @author Sualeh Fatehi
 */
abstract class BaseTextFormattingHelper
  implements TextFormattingHelper
{
  final OutputFormat outputFormat;

  BaseTextFormattingHelper(OutputFormat outputFormat)
  {
    this.outputFormat = outputFormat;
  }

  /**
   * {@inheritDoc}
   * 
   * @see schemacrawler.tools.util.TextFormattingHelper#createDefinitionRow(java.lang.String)
   */
  public String createDefinitionRow(final String definition)
  {
    final TableRow row = new TableRow(outputFormat);
    row.add(new TableCell(outputFormat, "ordinal", ""));
    row.add(new TableCell(outputFormat, 2, "definition", definition));
    return row.toString();
  }

  /**
   * {@inheritDoc}
   * 
   * @see schemacrawler.tools.util.TextFormattingHelper#createDetailRow(java.lang.String,
   *      java.lang.String, java.lang.String)
   */
  public String createDetailRow(final String ordinal,
                                final String subName,
                                final String type)
  {
    final int subNameWidth = 32;
    final int typeWidth = 28;

    final TableRow row = new TableRow(outputFormat);
    if (Utilities.isBlank(ordinal))
    {
      row.add(new TableCell(outputFormat, "ordinal", ""));
    }
    else
    {
      row.add(new TableCell(outputFormat, "ordinal", format(ordinal, 2, true)));
    }
    row.add(new TableCell(outputFormat, "subname", format(subName,
                                                          subNameWidth,
                                                          true)));
    row.add(new TableCell(outputFormat, "type", format(type, typeWidth, true)));
    return row.toString();
  }

  /**
   * {@inheritDoc}
   * 
   * @see schemacrawler.tools.util.TextFormattingHelper#createEmptyRow()
   */
  public String createEmptyRow()
  {
    return new TableRow(outputFormat, 4).toString();
  }

  /**
   * {@inheritDoc}
   * 
   * @see schemacrawler.tools.util.TextFormattingHelper#createNameRow(java.lang.String,
   *      java.lang.String)
   */
  public String createNameRow(final String name, final String description)
  {
    final int nameWidth = 34;
    final int descriptionWidth = 36;

    final TableRow row = new TableRow(outputFormat);
    row.add(new TableCell(outputFormat,
                          2,
                          "name",
                          format(name, nameWidth, true)));
    row.add(new TableCell(outputFormat, "description", format(description,
                                                              descriptionWidth,
                                                              false)));
    return row.toString();
  }

  /**
   * {@inheritDoc}
   * 
   * @see schemacrawler.tools.util.TextFormattingHelper#createNameValueRow(java.lang.String,
   *      java.lang.String)
   */
  public String createNameValueRow(final String name, final String value)
  {
    final int nameWidth = 36;

    final TableRow row = new TableRow(outputFormat);
    row.add(new TableCell(outputFormat, "", format(name, nameWidth, true)));
    row.add(new TableCell(outputFormat, "", value));
    return row.toString();
  }

  private String format(final String text,
                        final int maxWidth,
                        final boolean alignLeft)
  {
    if (outputFormat == OutputFormat.text)
    {
      if (alignLeft)
      {
        return FormatUtils.padRight(text, maxWidth);
      }
      else
      {
        return FormatUtils.padLeft(text, maxWidth);
      }
    }
    else
    {
      return text;
    }
  }

  /**
   * Called to handle the row output. Handler to be implemented by
   * subclass.
   * 
   * @param columnNames
   *        Column names
   * @param columnData
   *        Column data
   * @throws QueryExecutorException
   *         On an exception
   */
  public String createRow(final String[] columnNames, final String[] columnData)
  {
    OutputFormat outputFormat = this.outputFormat;
    if (outputFormat == OutputFormat.text)
    {
      outputFormat = OutputFormat.csv;
    }
    final TableRow row = new TableRow(outputFormat);
    for (int i = 0; i < columnData.length; i++)
    {
      row.add(new TableCell(outputFormat, "", columnData[i]));
    }
    return row.toString();
  }

  /**
   * Called to handle the header output. Handler to be implemented by
   * subclass.
   * 
   * @param columnNames
   *        Column names
   * @throws QueryExecutorException
   *         On an exception
   */
  public String createRowHeader(final String[] columnNames)
  {
    OutputFormat outputFormat = this.outputFormat;
    if (outputFormat == OutputFormat.text)
    {
      outputFormat = OutputFormat.csv;
    }
    final TableRow row = new TableRow(outputFormat);
    for (int i = 0; i < columnNames.length; i++)
    {
      row.add(new TableCell(outputFormat, "name", columnNames[i]));
    }
    return row.toString();
  }

}
