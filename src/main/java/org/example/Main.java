package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.service.PedidoService;
import org.example.ui.MainView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        PedidoService pedidoService = new PedidoService();
        MainController controller = new MainController(pedidoService);
        controller.cargarDemoInicial();

        MainView mainView = new MainView(controller);

        Scene scene = new Scene(mainView.getRoot(), 1320, 760);
        stage.setTitle("Pedidos REM - Fase 1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}