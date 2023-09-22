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
package org.devtoolbox.gui.util.fxml.tab;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;


/**
 * @author Arnaud Lecollaire
 */
public class FXMLTabFactory {

	private final Class<?> moduleClass;


	/**
	 * @param moduleClass any class of the module that should be used to load FXML files
	 */
	public FXMLTabFactory(Class<?> moduleClass) {
		super();
		this.moduleClass = moduleClass;
	}

	public <ControllerType> LoadedTabData<ControllerType> openTab(final String tabTitle, final String viewPath) {
        return openTab(tabTitle, viewPath, null);
    }

    public <ControllerType> LoadedTabData<ControllerType> openTab(final String tabTitle, final String fxmlPath, final Consumer<IOException> errorHandler) {
        URL fxmlPathURL = moduleClass.getResource(requireNonNull(fxmlPath));
        if (fxmlPathURL == null) {
        	throw new IllegalArgumentException("FXML file [" + fxmlPath + "] not found in classpath.");
        }

    	try (InputStream input = fxmlPathURL.openStream()) {
    		FXMLLoader loader = new FXMLLoader(fxmlPathURL);
            final Node content = (Node) loader.load(input);
            final Tab newTab = new Tab(tabTitle);
            newTab.setContent(content);
            return new LoadedTabData<ControllerType>(newTab, loader.<ControllerType> getController());
        } catch (final IOException error) {
            if (errorHandler != null) {
                errorHandler.accept(error);
                return null;
            }

            throw new IllegalStateException("Failed to initialize view [" + fxmlPath + "].", error);
        }
    }
}