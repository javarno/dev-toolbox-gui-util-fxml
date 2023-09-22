<!--
  - MIT License
  -
  - Copyright © 2020-2023 dev-toolbox.org
  -
  - Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
  - (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
  - distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
  - following conditions:
  -
  - The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
  -
  - THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
  - MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
  - CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  - OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
  -->

dev-toolbox-gui-util-fxml
=========================

Utility classes to make fxml utilization easier.

[FXMLApplication](https://github.com/javarno/dev-toolbox-gui-util-fxml/blob/master/src/main/java/org/devtoolbox/gui/util/fxml/FXMLApplication.java)
---------------

This is an extension of *javafx.application.Application* designed to easily start a javaFX application using a main XML file.

Example :

```
package my.fabulous.application;

import org.devtoolbox.gui.util.fxml.FXMLApplication;

public class MyFabulousApplication extends FXMLApplication<MainController> {

    public static void main(final String[] args) {
        launch(args);
    }

    public MyFabulousApplication() {
        super("FabulousApplication.fxml", "My Fabulous application", 1000, 800);
    }

    // optional, you can override this method if you need to init some variables in your controller
    // (for example from the main methods args)
    @Override
    protected void initMainController(MainController mainController) {
        super.initMainController(mainController);
        mainController.initMyFabulousParameter(getParameters().getRaw().stream().findFirst().orElse(null));
    }
}
```

For applications using java modules, the package containing the fxml file must be opened so that util-fxml can access it.
In this case, for a maven project, the FXML file is in *src/main/resources/my/fabulous/application* and *my.fabulous.application* is opened to the dev-toolbox-gui-util-fxml module

module-info.java :

```
module my.fabulous.application {

    exports my.fabulous.application to javafx.graphics, javafx.fxml;

    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires org.devtoolbox.gui.util.fxml;

    opens my.fabulous.application to org.devtoolbox.gui.util.fxml;

}
```

[TabFactory](https://github.com/javarno/dev-toolbox-gui-util-fxml/blob/master/src/main/java/org/devtoolbox/gui/util/fxml/tab/TabFactory.java)
----------

Factory class to easily initialize a tab from a FXML file.

Example :

```
package my.fabulous.application;

import org.devtoolbox.gui.util.fxml.tab.FXMLTabFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;


public class MainController {

    @FXML private TabPane testPane;

    private final FXMLTabFactory tabFactory = new FXMLTabFactory(getClass());


    @FXML public void addTab() {
        tabFactory.<TabController> openTab("My Fabulous tab", "Tab.fxml")
            .useTab(testPane.getTabs()::add)
            .useController(controller -> controller.doSomeFabulousStuff());
    }
}
```


history
-------
- v0.5.0 2023-09-23
  * changed project name to dev-toolbox-gui-util-fxml
  * java 21
  * upgraded javaFX to v21 and slf4j-api to v2.0.9
  * maven plugin versions upgrade
  * added FXMLApplication
  * changed LoadedTabData to record
- v0.4.1 2023-02-03 : upgraded all gui-util projects to use the same version of dev-toolbox-gui-util
- v0.4.0 2020-04-23 : java 14
- v0.3.1 2020-02-07 : upgraded slf4j-api tp match logback version
- previous versions : history lost :)