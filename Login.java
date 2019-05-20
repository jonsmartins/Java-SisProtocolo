package br.com.uva;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
	public static void main(String args[]) {
		// come�a a preparar a janela Home
		JFrame frame = new JFrame("HOME");

		// Cria label e textField do Login
		JLabel labelLogin = new JLabel("Login");
		labelLogin.setBounds(25, 50, 100, 30);
		JTextField textFieldLogin = new JTextField();
		textFieldLogin.setBounds(25, 80, 170, 40);

		// Cria label e textField da Senha
		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setBounds(25, 130, 100, 30);
		JPasswordField textFieldSenha = new JPasswordField();
		textFieldSenha.setBounds(25, 160, 170, 40);

		//Logouva
		ImageIcon imglogo = new ImageIcon("C:\\Projetos\\JavaProj\\src\\br\\com\\uva\\uvalogo.png");
		JLabel logo = new JLabel(imglogo);
		logo.setBounds(200, 40, 180, 100);
		logo.setVisible(true);


		// Cria bot�o entrar
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(250, 160, 100, 50);;

		// Adiciona components para a tela
		frame.add(labelLogin);
		frame.add(textFieldLogin);
		frame.add(labelSenha);
		frame.add(textFieldSenha);
		frame.add(logo);
		frame.add(btnEntrar);
		
		// Adiciona estilos para a tela
		frame.setSize(400, 300);
		frame.setLayout(null);
		frame.setVisible(true);
	}

}