package model.FileUtils;

import model.enums.ExtUseCase;
import model.interfaces.ExtensionDetailsMap;
import model.interfaces.FileHandler;
import model.interfaces.LinesCounter;

import java.io.File;
import java.util.*;

public class LinesCounterImpl implements LinesCounter {

    private File directory;
    private Queue<File> dirQueue;
    private ExtensionDetailsMap extensionsMap;
    private ArrayList<Thread> threads = new ArrayList<>();
    private Set<String> specificExtensions = new HashSet<>();
    private ExtUseCase useCase = ExtUseCase.IGNORE_THIS;

    @Override
    public ExtensionDetailsMap countLines(File file) {
        extensionsMap = new SynchronizedExtensionDetailsMap();
        if (file == null)
            return extensionsMap;

        if (file.isDirectory())
            countLinesInDirectory(file);
        else
            countLinesInSingleFile(file);

        return extensionsMap;
    }

    @Override
    public ExtensionDetailsMap countLinesOnlyForExtensions(File file, Set<String> extensions) {
        this.specificExtensions = extensions;
        this.useCase = ExtUseCase.LOOK_FOR_THIS;
        return countLines(file);
    }

    @Override
    public ExtensionDetailsMap countLinesIgnoreExtensions(File file, Set<String> extensionsToIgnore) {
        this.specificExtensions = extensionsToIgnore;
        this.useCase = ExtUseCase.IGNORE_THIS;
        return countLines(file);
    }


    private void countLinesInDirectory(File directory) {
        dirQueue = new PriorityQueue<>();
        dirQueue.offer(directory);
        handleDirectoriesRecursively();
    }

    private void countLinesInSingleFile(File file) {
        handleFileIfAppropriateAndIncExtensionDetails(file);
    }

    private void handleDirectoriesRecursively() {
        while (!dirQueue.isEmpty()) {
            directory = dirQueue.poll();
            handleDirectory();
        }
        for(Thread t : threads)
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void handleDirectory() {
        if (isCorrectDirectory())
            handleCorrectDirectory();
    }

    private void handleCorrectDirectory() {
        File[] filesInDirectory = directory.listFiles();
        for (File file : filesInDirectory)
            if (file.isDirectory())
                dirQueue.offer(file);
            else handleFileIfAppropriateAndIncExtensionDetails(file);
    }
    
    private void handleFileIfAppropriateAndIncExtensionDetails(File file) {
        FileHandler fileHandler = new FileHandlerImpl(file, specificExtensions, useCase);
        if(fileHandler.isAppropriateFile()) {
            handleAppropriateFileAndIncExtensionDetails(fileHandler);
        }
    }

    private void handleAppropriateFileAndIncExtensionDetails(FileHandler fileHandler){
        fileHandler.handleFileIfAppropriate();
        ExtensionDetails extensionDetails = fileHandler.getReuslt();
        extensionsMap.incExtensionDetails(extensionDetails);
    }

    private void handleAppropriateFileAndIncExtensionDetailsInAnotherThread(FileHandler handler){
        Thread t = new Thread() {
            @Override
            public void run() {
                System.out.println("thread " + Thread.currentThread().getName() + " invoked");
                handleAppropriateFileAndIncExtensionDetails(handler);
                System.out.println("thread " + Thread.currentThread().getName() + " finished");
            }
        };
        t.start();
    }

    private boolean isCorrectDirectory() {
        return directory.listFiles() != null;
    }
}

