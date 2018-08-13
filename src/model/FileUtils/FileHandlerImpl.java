package model.FileUtils;

import model.enums.ExtUseCase;
import model.interfaces.FileHandler;
import model.interfaces.LinesInFileCounter;

import java.io.File;
import java.util.Set;

/**
    Handles passed file.
    getResult returns ExtensionDetails object (null if file was inappropriate)
    isAppropriateFile - tells if file is appropriate according to @specificExtension and @useCase
 */
public class FileHandlerImpl implements FileHandler{

    private File file;
    private ExtensionDetails extensionDetails;
    private Set<String> specificExtensions;
    private ExtUseCase useCase;

    private LinesInFileCounter linesCounter = new LinesInFileCounterImpl();

    public FileHandlerImpl(File file, Set<String> specificExtensions, ExtUseCase useCase) {
        this.file = file;
        this.specificExtensions = specificExtensions;
        this.useCase = useCase;
    }

    public void handleFileIfAppropriate() {
        if (isAppropriateFile()) {
            extensionDetails = new ExtensionDetails();
            handleAppropriateFile();
        }
    }

    public boolean isAppropriateFile() {
        return hasExtension() && isAppropriateExtension();
    }

    @Override
    public ExtensionDetails getReuslt() {
        return extensionDetails;
    }

    private void handleAppropriateFile() {
        extensionDetails.extension = getExtension();
        extensionDetails.files++;
        extensionDetails.lines += linesCounter.count(file);
    }

    private String getExtension() {
        int dotIndex = file.getName().lastIndexOf('.');
        return dotIndex < 0 ? "" : file.getName().substring(dotIndex);
    }



    private boolean hasExtension() {
        return file.getName().contains(".");
    }

    private boolean isAppropriateExtension() {
        boolean extensionContainsInSet = specificExtensions.contains(getExtension());
        boolean lookForThis = useCase == ExtUseCase.LOOK_FOR_THIS && extensionContainsInSet;
        boolean dontIgnoreThis = useCase == ExtUseCase.IGNORE_THIS && !extensionContainsInSet;
        return lookForThis || dontIgnoreThis;
    }
}
