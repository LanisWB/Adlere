package adlere.train;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.core.JsonParseException;

import adlere.train.commun.CommunOperations;
import adlere.train.commun.Constants;
import adlere.train.exceptions.FunctionalException.JsonEntryNotFoundException;
import adlere.train.exceptions.FunctionalException.JsonEntryTypeException;
import adlere.train.exceptions.TechnicalException.FileAccessException;
import adlere.train.exceptions.TechnicalException.FileEmptyException;
import adlere.train.exceptions.TechnicalException.FileTypeException;

public class TestFileProcessing {
	/* local variables */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void inputFileExceptions() throws FileAccessException, FileEmptyException, IOException {

		File subfolder = folder.newFolder("test");
		String fileName = "notReallyAFile.txt";
		File emptyFile = folder.newFile("empty.txt");

		// File is directory
		FileTypeException typeException = assertThrows(FileTypeException.class,
				() -> CommunOperations.readFileNotEmpty(subfolder.getAbsolutePath()));
		assertEquals(Constants.FILE_TYPE_ERROR, typeException.getMessage());

		// Empty file error
		FileEmptyException emptyException = assertThrows(FileEmptyException.class,
				() -> CommunOperations.readFileNotEmpty(emptyFile.getAbsolutePath()));
		assertEquals(Constants.FILE_EMPTY_ERROR, emptyException.getMessage());

		// File not exists
		String path = Paths.get(subfolder.getAbsolutePath(), fileName).toFile().getAbsolutePath();
		FileAccessException notFoundException = assertThrows(FileAccessException.class,
				() -> CommunOperations.readFileNotEmpty(path));
		assertEquals(Constants.FILE_PROTECTED_OR_NOT_FOUND_ERROR, notFoundException.getMessage());
	}

	@Test
	public void nonExistingOutputFile() throws FileAccessException, FileEmptyException, IOException {
		File subfolder = folder.newFolder("test");
		String fileName = "notReallyAFileOutput.txt";

		String path = Paths.get(subfolder.getAbsolutePath(), fileName).toFile().getAbsolutePath();
		assertTrue(CommunOperations.checkFile(path));
		Boolean fileCreated = Arrays.asList(subfolder.listFiles()).stream().anyMatch(f -> f.getName().equals(fileName));
		assertTrue(fileCreated);
	}

	@Test
	public void testJsonFormat() throws JsonParseException, IOException {
		String json = new String("{\"test\":\"\",\"test2\":}");
		assertThrows(JsonParseException.class, () -> CommunOperations.checkJsonFormat(json));

		String json2 = new String("{\"test\":\"\",\"test2\":\"\"}test");
		assertThrows(JsonParseException.class, () -> CommunOperations.checkJsonFormat(json2));

		String json3 = new String("{\"test\":\"\",\"test2\":\"\"}");
		assertTrue(CommunOperations.checkJsonFormat(json3));
	}

	@Test
	public void getJsonValues() throws JsonEntryNotFoundException, JsonEntryTypeException {
		String jsonString = new String("{\"string\":\"string test\",\"int\":2}");
		JSONObject jsonObject = new JSONObject(jsonString);

		/**** JSON read String ************/
		assertEquals("string test", CommunOperations.getStringValue(jsonObject, "string"));

		JsonEntryTypeException typeException = assertThrows(JsonEntryTypeException.class,
				() -> CommunOperations.getStringValue(jsonObject, "int"));
		assertEquals(Constants.JSON_ENTRY_TYPE_MISMATCH + "int", typeException.getMessage());

		JsonEntryNotFoundException notFoundException = assertThrows(JsonEntryNotFoundException.class,
				() -> CommunOperations.getStringValue(jsonObject, "notAKey"));
		assertEquals(Constants.JSON_ENTRY_NOT_FOUND + "notAKey", notFoundException.getMessage());

		/**** JSON read int ************/
		assertEquals(2, CommunOperations.getInteger(jsonObject, "int"));
		JsonEntryTypeException typeException2 = assertThrows(JsonEntryTypeException.class,
				() -> CommunOperations.getInteger(jsonObject, "string"));
		assertEquals(Constants.JSON_ENTRY_TYPE_MISMATCH + "string", typeException2.getMessage());
		JsonEntryNotFoundException notFoundException2 = assertThrows(JsonEntryNotFoundException.class,
				() -> CommunOperations.getInteger(jsonObject, "notAKey"));
		assertEquals(Constants.JSON_ENTRY_NOT_FOUND + "notAKey", notFoundException2.getMessage());
	}

}
