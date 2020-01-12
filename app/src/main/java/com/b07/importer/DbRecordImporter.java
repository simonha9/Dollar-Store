package com.b07.importer;

import com.b07.exceptions.DataImportException;
import java.util.List;

public interface DbRecordImporter {

  void importRecords(List<Object> data) throws DataImportException;
}
