package model.FileUtils;

import model.interfaces.LinesInFileCounter;

import java.io.*;

public class LinesInFileCounterImpl implements LinesInFileCounter{

    private static final String ENCODING = "UTF-8";

    private File file;

    public long count(File file) {
        this.file = file;
        try {
            return countLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private long countLines() throws IOException {
        BufferedReader fileReader = initReader();
        long linesCount = countLines(fileReader);
        fileReader.close();
        return linesCount;
    }

    private BufferedReader initReader() throws UnsupportedEncodingException, FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, ENCODING);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        return reader;
    }

    private long countLines(BufferedReader fileReader) throws IOException {
        long linesCount = 0;
        while (fileReader.readLine() != null) {
            linesCount++;
        }
        return linesCount;
    }

}
