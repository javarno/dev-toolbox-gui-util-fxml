/*
 * MIT License
 *
 * Copyright © 2020-2023 dev-toolbox.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.devtoolbox.gui.util.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Extension of {@link javafx.application.Application} that starts the primary stage using a fxml file on the module classpath.
 *
 * @param <ControllerType> type of the controller for the main FXML file
 */
public class FXMLApplication<ControllerType> extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(FXMLApplication.class);

	private final String mainFXMLPath;
	private final String title;
	private final Integer width;
	private final Integer height;


	/**
	 * Creates a new JavaFX application using FXML. In case the main FXML file can not be loaded, application will stop (System.exit(-1)).
	 *
	 * @param mainFXMLPath the path of the main FXML path inside the module classloader.
	 * @throws NullPointerException if mainFXMLPath is null
	 */
	public FXMLApplication(String mainFXMLPath) {
		this(mainFXMLPath, null, null, null);
	}

	/**
	 * Creates a new JavaFX application using FXML. In case the main FXML file can not be loaded, application will stop (System.exit(-1)).
	 *
	 * @param mainFXMLPath the path of the main FXML path inside the module classloader.
	 * @param title window title (optional)
	 * @throws NullPointerException if mainFXMLPath is null
	 */
	public FXMLApplication(String mainFXMLPath, String title) {
		this(mainFXMLPath, title, null, null);
	}

	/**
	 * Creates a new JavaFX application using FXML. In case the main FXML file can not be loaded, application will stop (System.exit(-1)).
	 *
	 * @param mainFXMLPath the path of the main FXML path inside the module classloader.
	 * @param title window title (optional)
	 * @param width starting width for the scene (optional)
	 * @param height starting height for the scene (optional)
	 * @throws NullPointerException if mainFXMLPath is null
	 */
	public FXMLApplication(String mainFXMLPath, String title, Integer width, Integer height) {
		super();

		this.mainFXMLPath = Objects.requireNonNull(mainFXMLPath);
		this.title = title;
		this.width = width;
		this.height = height;
	}

    @Override
	public void start(Stage primaryStage) {
        primaryStage.setTitle(title);
        URL mainFXML = getClass().getResource(mainFXMLPath);
        if (mainFXML == null) {
            LOGGER.error("Main FXML file [{}] not found by module class path.", mainFXMLPath);
            System.exit(-1);
        }

        final FXMLLoader loader = new FXMLLoader(mainFXML);
        Parent parent = null;
        try {
            parent = (Parent) loader.load();
        } catch (final IOException error) {
            LOGGER.error("Failed to initialize application.", error);
            System.exit(-2);
        }
    	primaryStage.setScene(new Scene(parent, (width == null) ? -1 : width, (height == null) ? -1 : height));
        primaryStage.show();
        ControllerType mainController = loader.<ControllerType>getController();
        initMainController(mainController);
    }

    /**
     * Initializes the main controller (default implementation does nothing).
     */
	protected void initMainController(ControllerType mainController) {
	}
}