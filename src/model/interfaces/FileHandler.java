package model.interfaces;

import model.FileUtils.ExtensionDetails;

import java.io.File;

public interface FileHandler {

    void handleFileIfAppropriate();

    boolean isAppropriateFile();

    ExtensionDetails getReuslt();
}
