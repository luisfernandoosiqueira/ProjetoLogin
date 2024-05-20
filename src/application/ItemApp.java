package application;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ItemApp extends Application {

    private AnchorPane pane;
    private ImageView imagem;
    private Label lbNome, lbPreco;
    private Button btAddCarrinho;
    public static Stage stage;
    private Scene scene;
    private static Produto produto;
    private static int index;
    private String[] imagens = {
        "http://icons.iconarchive.com/icons/iconshock/soccer/128/soccer-3-icon.png",
        "http://icons.iconarchive.com/icons/kidaubis-design/olympic-games/128/Table-Tennis-icon.png",
        "http://icons.iconarchive.com/icons/ergosign/soccer-worldcup-2010/128/soccer-shoe-grass-icon.png",
        "http://icons.iconarchive.com/icons/iconshock/real-vista-sports/128/formula-1-helmet-icon.png",
        "http://icons.iconarchive.com/icons/iconshock/real-vista-sports/128/boxing-gloves-icon.png",
        "http://icons.iconarchive.com/icons/giannis-zographos/liverpool-fc/128/European-Shirt-2010-2011-icon.png"
    };

    public void initComponents() {
        pane = new AnchorPane();
        pane.setPrefSize(600, 400);
        pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, black 0%, silver 100%);");

        lbNome = new Label("Nome: " + ItemApp.getProduto().getNome());
        lbPreco = new Label("Preço: " + ItemApp.getProduto().getPreco());

        imagem = new ImageView(imagens[index]);
        imagem.setEffect(new Reflection());

        btAddCarrinho = new Button("Adicionar");
        InnerShadow effectInnerShadow = new InnerShadow();
        effectInnerShadow.setColor(Color.RED);
        btAddCarrinho.setEffect(effectInnerShadow);

        pane.getChildren().addAll(lbNome, lbPreco, imagem, btAddCarrinho);
    }

    public void initLayout() {
        lbNome.setLayoutX(20);
        lbNome.setLayoutY(50);
        lbNome.setTextFill(Color.WHITE);
        lbNome.setFont(Font.font(null, FontWeight.BOLD, 20));

        lbPreco.setLayoutX(20);
        lbPreco.setLayoutY(100);
        lbPreco.setTextFill(Color.WHITE);
        lbPreco.setFont(Font.font(null, FontWeight.BOLD, 20));

        imagem.setLayoutX(200);
        imagem.setLayoutY(50);

        btAddCarrinho.setLayoutX(20);
        btAddCarrinho.setLayoutY(150);
        btAddCarrinho.setFont(Font.font(null, FontWeight.BOLD, 20));
    }

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListener();
        ItemApp.stage = stage;
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Item");
        stage.setResizable(false);
        stage.show();

        initLayout();
        initTransition();
        initTimeline();
    }

    private void initTimeline() {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(imagem.opacityProperty(), 0.0);
        KeyFrame kf = new KeyFrame(Duration.millis(3000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    private void initTransition() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), imagem);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(3000), btAddCarrinho);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setAutoReverse(true);

        SequentialTransition transicaoSequencial = new SequentialTransition();
        transicaoSequencial.getChildren().addAll(fadeIn, scaleTransition);
        transicaoSequencial.play();
    }

    private void initListener() {
        btAddCarrinho.setOnAction(event -> {
            VitrineApp.getCarrinho().add(produto);
            try {
                new CarrinhoApp().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        ItemApp.index = index;
    }

    public static Produto getProduto() {
        return produto;
    }

    public static void setProduto(Produto produto) {
        ItemApp.produto = produto;
    }

    public static Stage getStage() {
        return stage;
    }
}
