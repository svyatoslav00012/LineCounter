package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FileUtils.ExtensionDetails;
import model.FileUtils.LinesCounterImpl;
import model.interfaces.ExtensionDetailsMap;
import model.interfaces.LinesCounter;

import java.io.File;
import java.util.Collections;
import java.util.Set;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sample.fxml"))));
		primaryStage.setTitle("LineCount");
		primaryStage.show();
	}


	public static void main(String[] args) {
		//launch(args);
		LinesCounter counter = new LinesCounterImpl();
        File code = new File("file").getAbsoluteFile().getParentFile() // LineCounter
                .getParentFile();    //JavaFX
//                .getParentFile()    //MyProjects
//                .getParentFile()    //Java
//                .getParentFile();   //Coding
        Set<String> lookOnlyFor = Collections.singleton(".java");
        Long timeStart = System.currentTimeMillis();
        ExtensionDetailsMap ext = counter.countLinesOnlyForExtensions(code, lookOnlyFor);
        printDuration(timeStart);
        System.out.println(ext);
        System.exit(0);
    }

    private static void printDuration(Long timeStart) {
        Long timeFinish = System.currentTimeMillis();
        Long duration = timeFinish - timeStart;
        int millis = (int) (duration % 1000);
        int secs = (int) (duration / 1000 % 60);
        int mins = (int) (duration / 1000 / 60 % 60);
        int hours = (int) (duration / 1000 / 60 / 60);
        System.out.println(hours + ":" + mins + ":" + secs + "." + millis);
    }
}
