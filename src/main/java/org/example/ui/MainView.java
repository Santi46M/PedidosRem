package org.example.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.controller.MainController;
import org.example.dto.ImportResult;
import org.example.model.EstadoPedido;
import org.example.model.Pedido;

import java.io.File;

import java.io.File;
import java.util.List;

public class MainView {

    private final MainController controller;
    private final FilteredList<Pedido> pedidosFiltrados;

    private final BorderPane root = new BorderPane();
    private final TableView<Pedido> table = new TableView<>();
    private final Label statusLabel = new Label("Listo.");

    private final TextField buscarField = new TextField();
    private final ComboBox<EstadoPedido> estadoFilter = new ComboBox<>();

    private final Label detalleId = new Label("-");
    private final Label detalleOptica = new Label("-");
    private final Label detalleRut = new Label("-");
    private final Label detallePaciente = new Label("-");
    private final Label detalleLente = new Label("-");
    private final Label detalleMaterial = new Label("-");
    private final Label detalleEstado = new Label("-");
    private final Label detalleFecha = new Label("-");
    private final Label detalleObs = new Label("-");

    public MainView(MainController controller) {
        this.controller = controller;
        this.pedidosFiltrados = new FilteredList<>(controller.getPedidos(), p -> true);

        root.setTop(buildTopBar());
        root.setCenter(buildCenter());
        root.setBottom(buildBottomBar());
    }

    public Parent getRoot() {
        return root;
    }

    private VBox buildTopBar() {
        Button importarBtn = new Button("Importar CSV");
        importarBtn.setOnAction(e -> importarCsv());

        Button demoBtn = new Button("Cargar demo");
        demoBtn.setOnAction(e -> cargarDemoImportacion());

        Button limpiarFiltrosBtn = new Button("Limpiar filtros");
        limpiarFiltrosBtn.setOnAction(e -> {
            buscarField.clear();
            estadoFilter.setValue(null);
            aplicarFiltros();
        });

        buscarField.setPromptText("Buscar por óptica, paciente, ID, RUT, material...");
        buscarField.textProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        buscarField.setPrefWidth(320);

        estadoFilter.setPromptText("Estado");
        estadoFilter.getItems().addAll(EstadoPedido.values());
        estadoFilter.valueProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
        estadoFilter.setPrefWidth(180);

        Label titulo = new Label("Pedidos de lentes");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        HBox row1 = new HBox(12, titulo);
        row1.setAlignment(Pos.CENTER_LEFT);

        HBox row2 = new HBox(
                10,
                importarBtn,
                demoBtn,
                new Separator(),
                new Label("Buscar:"), buscarField,
                new Label("Filtrar:"), estadoFilter,
                limpiarFiltrosBtn
        );
        row2.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(10, row1, row2);
        box.setPadding(new Insets(16, 16, 10, 16));
        return box;
    }

    private SplitPane buildCenter() {
        configureTable();

        VBox left = new VBox(10, buildStatsBar(), table);
        left.setPadding(new Insets(0, 8, 8, 16));
        VBox.setVgrow(table, Priority.ALWAYS);

        VBox right = buildDetailPanel();
        right.setPadding(new Insets(0, 16, 8, 8));

        SplitPane splitPane = new SplitPane(left, right);
        splitPane.setDividerPositions(0.74);
        return splitPane;
    }

    private HBox buildStatsBar() {
        Label totalLbl = new Label();
        totalLbl.textProperty().bind(Bindings.concat(
                "Total pedidos visibles: ",
                Bindings.size(pedidosFiltrados)
        ));

        Label totalGlobalLbl = new Label();
        totalGlobalLbl.textProperty().bind(Bindings.concat(
                " | Total general: ",
                Bindings.size(controller.getPedidos())
        ));

        HBox box = new HBox(8, totalLbl, totalGlobalLbl);
        box.setPadding(new Insets(0, 0, 8, 0));
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private void configureTable() {
        TableColumn<Pedido, String> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("idExterno"));
        colId.setPrefWidth(120);

        TableColumn<Pedido, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getFechaAltaFormateada())
        );
        colFecha.setPrefWidth(135);

        TableColumn<Pedido, String> colOptica = new TableColumn<>("Óptica");
        colOptica.setCellValueFactory(new PropertyValueFactory<>("optica"));
        colOptica.setPrefWidth(170);

        TableColumn<Pedido, String> colRut = new TableColumn<>("RUT");
        colRut.setCellValueFactory(new PropertyValueFactory<>("rut"));
        colRut.setPrefWidth(130);

        TableColumn<Pedido, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        colPaciente.setPrefWidth(150);

        TableColumn<Pedido, String> colOjo = new TableColumn<>("Ojo");
        colOjo.setCellValueFactory(new PropertyValueFactory<>("ojo"));
        colOjo.setPrefWidth(60);

        TableColumn<Pedido, String> colFormula = new TableColumn<>("Fórmula");
        colFormula.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getFormulaResumen())
        );
        colFormula.setPrefWidth(220);

        TableColumn<Pedido, String> colMaterial = new TableColumn<>("Material");
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
        colMaterial.setPrefWidth(150);

        TableColumn<Pedido, EstadoPedido> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(120);

        table.getColumns().setAll(
                colId, colFecha, colOptica, colRut, colPaciente, colOjo, colFormula, colMaterial, colEstado
        );
        table.setItems(pedidosFiltrados);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> mostrarDetalle(newV));

        if (!controller.getPedidos().isEmpty()) {
            table.getSelectionModel().selectFirst();
        }
    }

    private VBox buildDetailPanel() {
        Label titulo = new Label("Detalle del pedido");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.addRow(0, strong("ID:"), detalleId);
        grid.addRow(1, strong("Óptica:"), detalleOptica);
        grid.addRow(2, strong("RUT:"), detalleRut);
        grid.addRow(3, strong("Paciente:"), detallePaciente);
        grid.addRow(4, strong("Lente:"), detalleLente);
        grid.addRow(5, strong("Material:"), detalleMaterial);
        grid.addRow(6, strong("Estado:"), detalleEstado);
        grid.addRow(7, strong("Fecha alta:"), detalleFecha);

        Label obsTitle = strong("Observaciones:");
        TextArea obsArea = new TextArea();
        obsArea.setEditable(false);
        obsArea.setWrapText(true);
        obsArea.setPrefRowCount(8);
        obsArea.textProperty().bind(detalleObs.textProperty());

        Button marcarImpresoBtn = new Button("Marcar como IMPRESO");
        marcarImpresoBtn.setOnAction(e -> {
            Pedido seleccionado = table.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                showInfo("No hay pedido seleccionado.");
                return;
            }

            controller.marcarComoImpreso(seleccionado);            table.refresh();
            mostrarDetalle(seleccionado);
            statusLabel.setText("Pedido " + seleccionado.getIdExterno() + " marcado como IMPRESO.");
            marcarImpresoBtn.setDisable(true);
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            marcarImpresoBtn.setDisable(newV == null || newV.getEstado() != EstadoPedido.PENDIENTE);
        });

        Pedido seleccionado = table.getSelectionModel().getSelectedItem();
        marcarImpresoBtn.setDisable(seleccionado == null || seleccionado.getEstado() != EstadoPedido.PENDIENTE);

        VBox panel = new VBox(14, titulo, new Separator(), grid, obsTitle, obsArea, new Separator(), marcarImpresoBtn);
        panel.setPadding(new Insets(16));
        panel.setPrefWidth(360);
        panel.setStyle("-fx-background-color: #f7f7f7; -fx-background-radius: 10; -fx-border-color: #dddddd; -fx-border-radius: 10;");
        return panel;
    }

    private Label strong(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-weight: bold;");
        return lbl;
    }

    private HBox buildBottomBar() {
        HBox box = new HBox(statusLabel);
        box.setPadding(new Insets(10, 16, 14, 16));
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private void aplicarFiltros() {
        String texto = buscarField.getText() == null ? "" : buscarField.getText().trim().toLowerCase();
        EstadoPedido estadoSeleccionado = estadoFilter.getValue();

        pedidosFiltrados.setPredicate(p -> {
            boolean coincideTexto = texto.isBlank() || contieneTexto(p, texto);
            boolean coincideEstado = estadoSeleccionado == null || p.getEstado() == estadoSeleccionado;
            return coincideTexto && coincideEstado;
        });
    }

    private boolean contieneTexto(Pedido p, String texto) {
        return safe(p.getIdExterno()).contains(texto)
                || safe(p.getOptica()).contains(texto)
                || safe(p.getRut()).contains(texto)
                || safe(p.getPaciente()).contains(texto)
                || safe(p.getOjo()).contains(texto)
                || safe(p.getMaterial()).contains(texto)
                || safe(p.getObservaciones()).contains(texto)
                || safe(p.getFormulaResumen()).contains(texto);
    }

    private String safe(String value) {
        return value == null ? "" : value.toLowerCase();
    }

    private void mostrarDetalle(Pedido pedido) {
        if (pedido == null) {
            detalleId.setText("-");
            detalleOptica.setText("-");
            detalleRut.setText("-");
            detallePaciente.setText("-");
            detalleLente.setText("-");
            detalleMaterial.setText("-");
            detalleEstado.setText("-");
            detalleFecha.setText("-");
            detalleObs.setText("-");
            return;
        }

        detalleId.setText(pedido.getIdExterno());
        detalleOptica.setText(pedido.getOptica());
        detalleRut.setText(pedido.getRut());
        detallePaciente.setText(pedido.getPaciente());
        detalleLente.setText(pedido.getFormulaLarga());
        detalleMaterial.setText(pedido.getMaterial());
        detalleEstado.setText(pedido.getEstado().name());
        detalleFecha.setText(pedido.getFechaAltaFormateada());
        detalleObs.setText(pedido.getObservaciones());
    }

    private void importarCsv() {
        Window window = root.getScene() != null ? root.getScene().getWindow() : null;

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar CSV de pedidos");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        File file = chooser.showOpenDialog(window);
        if (file == null) {
            return;
        }

        try {
            ImportResult result = controller.importarCsv(file);
            aplicarFiltros();
            table.refresh();

            statusLabel.setText("Importación completada. Nuevos: "
                    + result.getNuevos()
                    + " | Duplicados ignorados: "
                    + result.getDuplicados());

            if (!controller.getPedidos().isEmpty() && table.getSelectionModel().getSelectedItem() == null) {
                table.getSelectionModel().selectFirst();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Error al importar CSV: " + ex.getMessage());
        }
    }

    private void cargarDemoImportacion() {
        int[] result = controller.cargarDemoImportacion();

        aplicarFiltros();
        table.refresh();

        statusLabel.setText("Demo importada. Nuevos: " + result[0] + " | Duplicados: " + result[1]);

        if (!controller.getPedidos().isEmpty() && table.getSelectionModel().getSelectedItem() == null) {
            table.getSelectionModel().selectFirst();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ocurrió un problema");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}