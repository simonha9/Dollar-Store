package com.b07.exporter;

import com.b07.exceptions.DataExportException;
import com.b07.exceptions.ValidationFailedException;
import java.util.List;

public interface DbRecordExporter<T> {

  List<T> exportRecords() throws DataExportException;
}
