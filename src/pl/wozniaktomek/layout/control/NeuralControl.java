package pl.wozniaktomek.layout.control;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.wozniaktomek.ThesisApp;
import pl.wozniaktomek.layout.widget.NeuralNetworkWidget;
import pl.wozniaktomek.neural.NeuralNetwork;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class NeuralControl implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private HBox titleContainer;
    @FXML private HBox progressWidgetContainer;
    @FXML private VBox networkWidgetContainer;

    private NeuralNetwork neuralNetwork;
    private NeuralNetworkWidget neuralNetworkWidget;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNetworkWidget();
        initializeSizeListener();
    }

    private void initializeNetworkWidget() {
        neuralNetwork = new NeuralNetwork();
        neuralNetwork.addLayer(2);
        neuralNetwork.addLayer(12);
        neuralNetwork.addLayer(3);
        neuralNetwork.addBias();
        neuralNetworkWidget = new NeuralNetworkWidget(neuralNetwork);
    }

    private void initializeSizeListener() {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            titleContainer.setPrefWidth(ThesisApp.windowControl.getContentPane().getWidth() - 82);
            progressWidgetContainer.setPrefWidth(ThesisApp.windowControl.getContentPane().getWidth());
            networkWidgetContainer.setPrefWidth(ThesisApp.windowControl.getContentPane().getWidth());

            if (isScrollBarVisible())
                refreshNetworkWidget(ThesisApp.windowControl.getContentPane().getWidth() - 18);
            else refreshNetworkWidget(ThesisApp.windowControl.getContentPane().getWidth());
        };

        ThesisApp.windowControl.getContentPane().widthProperty().addListener(stageSizeListener);
        ThesisApp.windowControl.getContentPane().heightProperty().addListener(stageSizeListener);
    }

    private void refreshNetworkWidget(Double width) {
        networkWidgetContainer.getChildren().clear();
        neuralNetworkWidget.drawNetwork(width - 42);
        networkWidgetContainer.getChildren().add(neuralNetworkWidget.getWidget());
    }

    private boolean isScrollBarVisible() {
        Set<Node> nodes = scrollPane.lookupAll(".scroll-bar");
        for (final Node node : nodes) {
            if (node instanceof ScrollBar) {
                ScrollBar scrollBar = (ScrollBar) node;
                if (scrollBar.getOrientation().equals(Orientation.VERTICAL))
                    return true;
            }
        }

        return false;
    }
}
