package application;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VitrineApp extends Application {

    private AnchorPane pane;
    private Scene scene;
    private TextField txPesquisa;
    private TableView<ItensProperty> tbVitrine;
    private TableColumn<ItensProperty, String> columnProduto;
    private TableColumn<ItensProperty, Double> columnPreco;
    private static ObservableList<ItensProperty> listItens = FXCollections.observableArrayList();
    private static Carrinho carinho;

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initItens();
        initListeners();

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Vitrine - Loja");
        stage.setResizable(false);
        stage.show();

        initLayout();
        tbVitrine.setItems(listItens);
        tbVitrine.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        pane = new AnchorPane();
        pane.setPrefSize(800, 600);

        txPesquisa = new TextField();
        txPesquisa.setPromptText("Digite o item para pesquisar ...");
        txPesquisa.setPrefWidth(250);

        tbVitrine = new TableView<>();
        tbVitrine.setPrefSize(780, 500);

        columnProduto = new TableColumn<>("Nome");
        columnPreco = new TableColumn<>("Preço");

        tbVitrine.getColumns().addAll(columnProduto, columnPreco);

        pane.getChildren().addAll(txPesquisa, tbVitrine);

        carinho = new Carrinho();

        columnProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    private void initLayout() {
        txPesquisa.setLayoutX(10);
        txPesquisa.setLayoutY(10);

        tbVitrine.setLayoutX(10);
        tbVitrine.setLayoutY(60);

        pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, black 0%, silver 100%);");
    }

    private void initItens() {
        Produto p1 = new Produto("Bola", 10.0);
        Produto p2 = new Produto("Raquete", 100.0);
        Produto p3 = new Produto("Chuteira", 250.0);
        Produto p4 = new Produto("Capacete", 60.0);
        Produto p5 = new Produto("Luva", 35.0);
        Produto p6 = new Produto("Camisa", 40.0);

        Vitrine vitrine = new Vitrine();
        vitrine.add(p1, p2, p3, p4, p5, p6);

        for (Produto produto : vitrine.getProdutos()) {
            VitrineApp.listItens.add(new ItensProperty(produto.getNome(), produto.getPreco()));
        }
    }

    private ObservableList<ItensProperty> findItens() {
        ObservableList<ItensProperty> itensEncontrados = FXCollections.observableArrayList();

        for (ItensProperty item : listItens) {
            if (item.getProduto().contains(txPesquisa.getText())) {
                itensEncontrados.add(item);
            }
        }
        return itensEncontrados;
    }

    private void initListeners() {
        txPesquisa.setOnAction(event -> {
            if (!txPesquisa.getText().equalsIgnoreCase("")) {
                tbVitrine.setItems(findItens());
            } else {
                tbVitrine.setItems(listItens);
            }
        });

        tbVitrine.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItensProperty>() {
            @Override
            public void changed(ObservableValue<? extends ItensProperty> observable, ItensProperty oldItem, ItensProperty newItem) {
                if (newItem != null) {
                    // indicando os itens para nossa tela ItemApp
                    ItemApp.setProduto(new Produto(newItem.getProduto(), newItem.getPreco()));
                    ItemApp.setIndex(tbVitrine.getSelectionModel().getSelectedIndex());

                    // Chamando nossa tela
                    try {
                        new ItemApp().start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static Carrinho getCarrinho() {
        return carinho;
    }

    public static void setCarrinho(Carrinho carinho) {
        VitrineApp.carinho = carinho;
    }

    public class ItensProperty {
        private SimpleStringProperty produto;
        private SimpleDoubleProperty preco;

        public ItensProperty(String produto, Double preco) {
            this.produto = new SimpleStringProperty(produto);
            this.preco = new SimpleDoubleProperty(preco);
        }

        public String getProduto() {
            return produto.get();
        }

        public void setProduto(String produto) {
            this.produto.set(produto);
        }

        public double getPreco() {
            return preco.get();
        }

        public void setPreco(double preco) {
            this.preco.set(preco);
        }
    }
}
