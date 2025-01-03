package com.client.login;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.client.util.VoiceRecorder;

public class MainLauncher extends Application {

    private static Stage primaryStageObj;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/LoginView.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Socket聊天: 客户端版本 0.3");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
        Scene mainScene = new Scene(root, 350, 420);
        mainScene.setRoot(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    @Override
    public void stop() {
        // 关闭语音处理线程池
        VoiceRecorder.shutdown();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }
}