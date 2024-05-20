package application;

import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CarrinhoApp extends Application {

    private AnchorPane pane;
    private static Stage stage;
    private TableView<ItensProperty> tbCarrinho;
    private TableColumn<ItensProperty, String> columnProduto;
    private TableColumn<ItensProperty, Double> columnPreco;
    private Button btExcluir, btComprar, btVoltar;
    private static ObservableList<ItensProperty> listaItens;

    public void initItens() {
        listaItens = FXCollections.observableArrayList();
        for (Produto produto : VitrineApp.getCarrinho().getProdutos()) {
            ItensProperty item = new ItensProperty(produto.getNome(), produto.getPreco());
            listaItens.add(item);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        initItens();
        initComponents();
        initListener();

        Scene scene = new Scene(pane);
        CarrinhoApp.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Carrinho");
        stage.setResizable(false);
        stage.show();

        initLayout();
        tbCarrinho.setItems(listaItens);
    }

    private void initLayout() {
        tbCarrinho.setLayoutX(10);
        tbCarrinho.setLayoutY(20);

        btComprar.setLayoutX(250);
        btComprar.setLayoutY(530);
        btComprar.setFont(Font.font(null, FontWeight.BOLD, 16));

        btExcluir.setLayoutX(350);
        btExcluir.setLayoutY(530);
        btExcluir.setFont(Font.font(null, FontWeight.BOLD, 16));

        btVoltar.setLayoutX(440);
        btVoltar.setLayoutY(530);
        btVoltar.setFont(Font.font(null, FontWeight.BOLD, 16));

        pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, black 0%, silver 100%);");
    }

    private void initListener() {
        btComprar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "Compra Realizada com Sucesso !!!", "Fechamento De Compra", JOptionPane.INFORMATION_MESSAGE);
                        VitrineApp.getCarrinho().removerAll();
                        Platform.runLater(() -> {
                            CarrinhoApp.getStage().close();
                            ItemApp.getStage().close();
                        });
                    }
                };
                thread.start();
            }
        });

        btExcluir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                ItensProperty selectedItem = tbCarrinho.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    String nome = selectedItem.getProduto();
                    Double preco = selectedItem.getPreco();
                    VitrineApp.getCarrinho().remover(new Produto(nome, preco));
                    tbCarrinho.getItems().remove(selectedItem);
                }
            }
        });

        btVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                CarrinhoApp.getStage().close();
                ItemApp.getStage().close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        pane = new AnchorPane();
        pane.setPrefSize(800, 600);

        tbCarrinho = new TableView<>();
        tbCarrinho.setPrefSize(780, 500);

        columnProduto = new TableColumn<>("Produto");
        columnPreco = new TableColumn<>("Preço");

        tbCarrinho.getColumns().setAll(columnProduto, columnPreco);

        btComprar = new Button("Comprar");
        btExcluir = new Button("Excluir");
        btVoltar = new Button("Voltar");

        pane.getChildren().addAll(tbCarrinho, btComprar, btExcluir, btVoltar);

        columnProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    }

    public static Stage getStage() {
        return stage;
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
