package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX App para demonstração de um sistema de login.
 */
public class LoginApp extends Application {

    private AnchorPane pane; // Painel base para adicionar outros componentes
    private TextField txLogin; // Campo de texto para inserir o login
    private PasswordField txSenha; // Campo de senha para inserir a senha
    private Button btEntrar, btSair; // Botões para entrar e sair
    private static Stage stage; // Palco principal da aplicação

    
    // Método start
    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();
        Scene scene = new Scene(pane); // Cria uma cena com o painel como raiz
        stage.setScene(scene); // Configura a cena no palco
        stage.setResizable(false); // Desabilita a redimensionamento da janela
        stage.setTitle("Login"); // Define o título da janela
        stage.show(); // Mostra a janela
        initLayout(); // Inicializa o layout dos componentes
        LoginApp.stage = stage; // Armazena a referência do palco
    }

    private void initComponents() {
        pane = new AnchorPane(); // Cria o painel
        pane.setPrefSize(400, 300); // Define o tamanho preferido do painel

        txLogin = new TextField(); // Cria o campo de texto para login
        txLogin.setPromptText("Digite aqui seu login"); // Texto fantasma

        txSenha = new PasswordField(); // Cria o campo de senha
        txSenha.setPromptText("Digite aqui sua senha"); // Texto fantasma

        btEntrar = new Button("Entrar"); // Cria o botão de entrar

        btSair = new Button("Sair"); // Cria o botão de sair

        pane.getChildren().addAll(txLogin, txSenha, btEntrar, btSair); // Adiciona todos os componentes ao painel
    }

    private void initLayout() {
        // Configuração do layout dos componentes
        txLogin.setLayoutX((pane.getWidth() - txLogin.getWidth()) / 2);
        txLogin.setLayoutY(50);
        txSenha.setLayoutX((pane.getWidth() - txSenha.getWidth()) / 2);
        txSenha.setLayoutY(100);
        btEntrar.setLayoutX((pane.getWidth() - btEntrar.getWidth()) / 3);
        btEntrar.setLayoutY(150);
        btSair.setLayoutX((pane.getWidth() - btSair.getWidth()) / 1.5);
        btSair.setLayoutY(150);

        // Estilo do painel e botão
        pane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, white 0%, silver 100%);");
    }

    private void initListeners() {
        // Configura os eventos dos botões
        btSair.setOnAction(e -> fecharAplicacao()); // Evento para fechar a aplicação
        btEntrar.setOnAction(e -> logar()); // Evento para tentar logar
    }

    private void fecharAplicacao() {
        System.exit(0); // Fecha a aplicação
    }

    private void logar() {
        if (txLogin.getText().equals("luis") && txSenha.getText().equals("123")) {
            try {
                new VitrineApp().start(new Stage());
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Exibe um alerta caso o login ou senha estejam incorretos
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Login e/ou senha inválidos");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação
    }
}
