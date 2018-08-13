package test;

import java.io.*;

public class DirForTestUtils {

    public static final String TEST_CONTAINER = "test" + File.separatorChar;

    public static void createTestContainer() {
        File testContainer = new File(TEST_CONTAINER);
        testContainer.mkdir();
    }

    public static void removeTestContainer(){
        File testContainer = new File(TEST_CONTAINER);
        removeDir(testContainer);
        removeDir(new File("test"));
        removeDir(testContainer);
    }


    public File tryToCreateDirectoryWithFiles(Long... linesInFiles) {
        try {
            return createDirectoryWithFiles(linesInFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long countArraySum(Long... array) {
        Long sum = 0L;
        for (Long elem : array)
            sum += elem;
        return sum;
    }

    private File createDirectoryWithFiles(Long... linesInFiles) throws IOException {
        Long lines = countArraySum(linesInFiles);
        File directory = createDirectory(linesInFiles.length, lines);
        fillDirectoryWithFiles(directory, linesInFiles);
        return directory;
    }

    private File createDirectory(int length, Long lines) {
        File directory = new File(TEST_CONTAINER + "testDirWith" + length + "FilesAnd" + lines + "Lines");
        if (!directory.isDirectory())
            directory.delete();
        removeDir(directory);
        directory.mkdirs();
        return directory;
    }

    private static void removeDir(File directory) {
        if (directory.listFiles() != null)
            for (File f : directory.listFiles())
                if (f.isDirectory())
                    removeDir(f);
                else
                    f.delete();
        directory.delete();
    }


    private FileForTestUtils fileUtils = new FileForTestUtils();

    private void fillDirectoryWithFiles(File directory, Long... linesInFiles) throws IOException {
        for (Long lines : linesInFiles) {
            fileUtils.tryToCreateFileWithSpecificAmountOfLines(lines, directory);
        }
    }
}
