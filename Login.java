package br.com.uva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
	public static void main(String args[]) throws IOException {
		Test ts = new Test();
		// carregando meu HashMap de usuarios
		HashMap<String, String> usermap = new HashMap<String, String>();
		try {
			File bduser = new File("user.txt");
			BufferedReader bufferedReader = new BufferedReader(new FileReader(bduser));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String usuario = line.split(":")[0];
				String senha = line.split(":")[1];
				usermap.put(usuario, senha);
				System.out.println(usuario + "\n" + senha);
			}
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("Ocorreu um erro");
			e.printStackTrace();
		}

		// começa a preparar a janela Home
		JFrame frame = new JFrame("Protocolos");

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

		// Logouva
		ImageIcon imglogo = new ImageIcon("uvalogo.png");
		JLabel logo = new JLabel(imglogo);
		logo.setBounds(200, 40, 180, 100);
		logo.setVisible(true);

		// criando janela de erro
		JFrame erroframe = new JFrame("Erro");
		erroframe.setBounds(800, 400, 290, 170);
		erroframe.setLayout(null);
		JLabel errolb = new JLabel("Login ou senha invalidos");
		errolb.setBounds(50, 25, 200, 70);
		JButton btnErro = new JButton("Ok");
		btnErro.setBounds(100, 80, 50, 30);
		btnErro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erroframe.setVisible(false);
			}
		});

		// Cria botao entrar
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(250, 160, 100, 50);
		;
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// validando informacoes
				boolean validacao = false;
				try{
					String user = textFieldLogin.getText();
					String pass = textFieldSenha.getText();
					if (usermap.get(user)!=null) {
						String value = usermap.get(user);
						if(value.equals(pass)){
							validacao = true;
						}
					}
				}catch(Exception exc){
					exc.printStackTrace();
				}
				if (validacao == true) {
					String login = textFieldLogin.getText();
					String pass = textFieldSenha.getText();
					Usuarios usuario = new Usuarios(login, pass);
					usuario.createFile();
					//String fl = usuario.getNome() + ".txt";
					//File file = new File(fl);
					frame.setVisible(false);
					try {
						PainelNovo pn = new PainelNovo(usuario);
						pn.montaTela();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					erroframe.setVisible(true);
					erroframe.add(errolb);
					erroframe.add(btnErro);
				}

			}
		});

		// Adiciona components para a tela
		frame.add(labelLogin);
		frame.add(textFieldLogin);
		frame.add(labelSenha);
		frame.add(textFieldSenha);
		frame.add(logo);
		frame.add(btnEntrar);

		// Adiciona estilos para a tela
		// frame.setSize(400, 300);
		frame.setBounds(800, 400, 400, 300);
		frame.setLayout(null);
		frame.setVisible(true);
	}

}
