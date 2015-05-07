/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.saiku.service.util.export;

import org.olap4j.CellSet;
import org.saiku.olap.dto.SaikuDimensionSelection;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.util.OlapResultSetUtil;
import org.saiku.olap.util.formatter.FlattenedCellSetFormatter;
import org.saiku.olap.util.formatter.HierarchicalCellSetFormatter;
import org.saiku.olap.util.formatter.ICellSetFormatter;
import org.saiku.service.util.export.excel.ExcelBuilderOptions;
import org.saiku.service.util.export.excel.ExcelWorksheetBuilder;

import java.util.List;

public class ExcelExporter {

  public static byte[] exportExcel(String saikuQueryName, CellSet cellSet, List<SaikuDimensionSelection> filters, int show_sums) {
    return exportExcel( saikuQueryName, cellSet, new HierarchicalCellSetFormatter(), filters, show_sums);
  }

  public static byte[] exportExcel( String saikuQueryName,
                                    CellSet cellSet,
                                    ICellSetFormatter formatter,
                                    List<SaikuDimensionSelection> filters,
                                    int show_sums) {
    CellDataSet table = OlapResultSetUtil.cellSet2Matrix( cellSet, formatter );
    ExcelBuilderOptions exb = new ExcelBuilderOptions();
    exb.repeatValues = ( formatter instanceof FlattenedCellSetFormatter );
    return getExcel( saikuQueryName, table, filters, exb, show_sums);
  }

  private static byte[] getExcel( String saikuQueryName, CellDataSet table, List<SaikuDimensionSelection> filters,
                                  ExcelBuilderOptions options, int show_sums) {
    // TBD Sheet name is parametric. Useful for future ideas or improvements
    ExcelWorksheetBuilder worksheetBuilder = new ExcelWorksheetBuilder( saikuQueryName, table, filters, options, show_sums);
    return worksheetBuilder.build();
  }
}