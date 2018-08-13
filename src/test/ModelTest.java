package test;

import model.FileUtils.LinesCounterImpl;
import model.FileUtils.LinesFiles;
import model.interfaces.ExtensionDetailsMap;
import model.interfaces.LinesCounter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import static test.FileForTestUtils.EXTENSION;

public class ModelTest {

    private LinesCounter counter = new LinesCounterImpl();

    private DirForTestUtils dirTestUtils = new DirForTestUtils();
    private FileForTestUtils fileTestUtils = new FileForTestUtils();

    @BeforeAll
    public static void createTestContainer() {
        DirForTestUtils.createTestContainer();
    }

    @AfterAll
    public static void deleteTestContainer() {
        DirForTestUtils.removeTestContainer();
    }

    @Test
    public void testCountLines_FileResultNotNull() {
        File someFile = fileTestUtils.tryToCreateFileWithSpecificAmountOfLines(3, null);
        ExtensionDetailsMap amount = counter.countLines(someFile);
        assert amount != null;
    }

    @Test
    public void testCountLines_DirectoryResultNotNull() {
        Long[] linesInFiles = {11L, 3L, 37L};
        File directory = dirTestUtils.tryToCreateDirectoryWithFiles(linesInFiles);
        ExtensionDetailsMap amount = counter.countLines(directory);
        assert amount != null;
    }

    @Test
    public void testCountLines_10LinesFile() {
        File file10 = fileTestUtils.tryToCreateFileWithSpecificAmountOfLines(10, null);
        ExtensionDetailsMap amount = counter.countLines(file10);
        LinesFiles linesFiles = amount.get(EXTENSION);
        assert linesFiles.lines == 10;
    }

    @Test
    public void testCountLines_90067LinesFile() {
        File file90067 = fileTestUtils.tryToCreateFileWithSpecificAmountOfLines(90067, null);
        ExtensionDetailsMap amount = counter.countLines(file90067);
        LinesFiles linesFiles = amount.get(EXTENSION);
        assert linesFiles.lines == 90067;
    }

    @Test
    public void testCountLines_DirectoryWith5FilesReturns756Lines() {
        Long[] linesInFiles = {20L, 4L, 577L, 122L, 33L};
        File directory = dirTestUtils.tryToCreateDirectoryWithFiles(linesInFiles);

        ExtensionDetailsMap map = counter.countLines(directory);
        LinesFiles linesFiles = map.get(EXTENSION);
        Long actualResult = linesFiles.lines;
        Long expectedResult = dirTestUtils.countArraySum(linesInFiles);

        assert actualResult.equals(expectedResult);
    }

    @Test
    public void testCountLines_ManyFilesDirectory() {
        Long[] linesInFiles = {354L, 684L, 7L, 9651L, 894651L, 85L, 99L, 112123L};
        File directory = dirTestUtils.tryToCreateDirectoryWithFiles(linesInFiles);

        ExtensionDetailsMap map = counter.countLines(directory);
        LinesFiles linesFiles = map.get(EXTENSION);
        Long actualResult = linesFiles.lines;
        Long expectedResult = dirTestUtils.countArraySum(linesInFiles);

        assert actualResult.equals(expectedResult);
    }

    @Test
    public void testCountLines_DirectoryWith5FilesReturns5Files() {
        Long[] linesInFiles = {20L, 4L, 577L, 122L, 33L};
        File directory = dirTestUtils.tryToCreateDirectoryWithFiles(linesInFiles);

        ExtensionDetailsMap map = counter.countLines(directory);
        LinesFiles linesFiles = map.get(EXTENSION);
        Long actualResult = linesFiles.files;
        Long expectedResult = (long) linesInFiles.length;

        assert actualResult.equals(expectedResult);
    }

    @Test
    public void testCountLinesOnlyForExtensions_FileWith500Lines_hasOneExtension() {
        File file500 = fileTestUtils.tryToCreateFileWithSpecificAmountOfLines(500, null);
        Set<String> extensionToLookFor = Collections.singleton(EXTENSION);
        ExtensionDetailsMap amount = counter.countLinesOnlyForExtensions(file500, extensionToLookFor);
        assert amount.getExtensions().size() == 1;
    }

    @Test
    public void testCountLinesOnlyForExtensions_FileWith500Lines_has500Lines() {
        File file500 = fileTestUtils.tryToCreateFileWithSpecificAmountOfLines(500, null);
        Set<String> extensionToLookFor = Collections.singleton(EXTENSION);
        ExtensionDetailsMap amount = counter.countLinesOnlyForExtensions(file500, extensionToLookFor);
        assert amount.get(EXTENSION).lines == 500;
    }

    @Test
    public void testCountLinesOnlyForExtensions_Directory() {
        Long[] linesInFiles = {354L, 684L, 7L, 9651L, 894651L, 85L, 99L, 112123L};
        File directory = dirTestUtils.tryToCreateDirectoryWithFiles(linesInFiles);
        Set<String> extensionToLookFor = Collections.singleton(EXTENSION);

        ExtensionDetailsMap map = counter.countLinesOnlyForExtensions(directory, extensionToLookFor);
        LinesFiles linesFiles = map.get(EXTENSION);
        Long actualResult = linesFiles.lines;
        Long expectedResult = dirTestUtils.countArraySum(linesInFiles);

        assert actualResult.equals(expectedResult);
    }

    @Test
    public void testCountLinesIgnoreExtensions_FileWith500Lines_noExtensionsFound() {
        File file500 = fileTestUtils.tryToCreateFileWithSpecificAmountOfLines(500, null);
        Set<String> extensionToLookFor = Collections.singleton(EXTENSION);
        ExtensionDetailsMap amount = counter.countLinesIgnoreExtensions(file500, extensionToLookFor);
        assert amount.getExtensions().size() == 0;
    }

}
