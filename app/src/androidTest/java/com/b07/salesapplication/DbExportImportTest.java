package com.b07.salesapplication;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseHelper;
import com.b07.services.DbExporter;
import com.b07.services.DbImporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DbExportImportTest {


    @Test
    public void testFileIO() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext(); //?
        File directory = appContext.getFilesDir();
        String filename = "database_copy.ser";
        String fileContents = "Hello world!";
        try (FileOutputStream fos = appContext.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = appContext.openFileInput(filename);
             InputStreamReader inputStreamReader =
                     new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = reader.readLine();
                }
            }
        }
        appContext.deleteFile(filename);
        Assert.assertEquals(fileContents, stringBuilder.toString());

        Assert.assertEquals(0, appContext.fileList().length);
 //       File file = new File(directory, filename);
    //    System.out.println("--directory->" + directory.getAbsolutePath());
     //   Assert.assertNull(directory.getAbsolutePath(),directory.getAbsolutePath());
    }

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext);
        dbDriver.onUpgrade(dbDriver.getWritableDatabase(), 0, 1);
    }

    @Test
    public void testExport() throws  Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DbExporter exporter = new DbExporter(appContext);
        exporter.exportData(DatabaseHelper.SERIALIZED_DATABASE_NAME);
    }

    @Test
    public void testImport() throws  Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DbImporter importer = new DbImporter(appContext);
        importer.importData(DatabaseHelper.SERIALIZED_DATABASE_NAME);
    }
}
