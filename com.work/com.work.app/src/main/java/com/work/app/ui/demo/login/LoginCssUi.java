package com.work.app.ui.demo.login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginCssUi extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));                
		grid.add(scenetitle, 0, 0, 2, 1);
		
		//创建Label对象，放到第0列，第1行
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
		
		//创建文本输入框，放到第1列，第1行
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		
		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);//将按钮控件作为子节点
		final Text actiontarget=new Text();//增加用于显示信息的文本
		btn.setOnAction(new EventHandler<ActionEvent>() {//注册事件handler
			@Override
			public void handle(ActionEvent e) {
				actiontarget.setFill(Color.FIREBRICK);//将文字颜色变成 firebrick red
				actiontarget.setText("Sign in button pressed");
			}
		});
		
		grid.add(hbBtn, 1, 4);//将HBox pane放到grid中的第1列，第4行
		grid.add(actiontarget, 1, 6);
		
		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		
		primaryStage.setTitle("JavaFX Welcome");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
