package test;

import java.io.*;

import static test.DirForTestUtils.TEST_CONTAINER;

public class FileForTestUtils {

    public static final String EXTENSION = ".ext";

    public File tryToCreateFileWithSpecificAmountOfLines(long linesAmount, File parentDir) {
        try {
            return createFileWithSpecificAmountOfLines(linesAmount, parentDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private File createFileWithSpecificAmountOfLines(long linesAmount, File parentDir) throws IOException {
        File file = createFile(linesAmount, parentDir);
        BufferedWriter writer = initWriter(file);
        writeSpecificAmountOfLinesToFile(linesAmount, writer);
        return file;
    }

    private File createFile(long linesAmount, File parentDir) throws IOException {
        String fileNamePrefix = parentDir == null ? "" : parentDir.getName() + "/";
        String fileName = TEST_CONTAINER + fileNamePrefix + "testFileWith" + linesAmount + "Lines" + EXTENSION;
        File file = new File(fileName);
        file.delete();
        file.createNewFile();
        return file;
    }

    private BufferedWriter initWriter(File file) throws FileNotFoundException {
        FileOutputStream fileOutStream = new FileOutputStream(file);
        OutputStreamWriter osWriter = new OutputStreamWriter(fileOutStream);
        BufferedWriter writer = new BufferedWriter(osWriter);
        return writer;
    }

    private void writeSpecificAmountOfLinesToFile(long linesAmount, BufferedWriter writer) throws IOException {
        for (int i = 0; i < linesAmount; ++i) {
            writer.write("something");
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }
}
