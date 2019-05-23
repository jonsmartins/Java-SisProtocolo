package br.com.uva;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
	public static void main(String args[]) throws IOException {
		Painel painel = new Painel();
		JFrame telaPainel = painel.montaTela();

		//carregando meu arraylist de usuarios
        ArrayList<Usuarios> usuarios = new ArrayList<Usuarios>();
        try{
		    File bduser = new File("user.txt");
		    BufferedReader bufferedReader = new BufferedReader(new FileReader(bduser));
		    String line;
 		    while((line = bufferedReader.readLine()) != null) {
                String usuario = line.split(":")[0];
                String senha = line.split(":")[1];
        	    Usuarios us = new Usuarios(usuario, senha);
				usuarios.add(us);
				System.out.println(usuario+"\n"+senha);
            }
            bufferedReader.close();  
        }catch(IOException e){
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

		//Logouva
		ImageIcon imglogo = new ImageIcon("uvalogo.png");
		JLabel logo = new JLabel(imglogo);
		logo.setBounds(200, 40, 180, 100);
		logo.setVisible(true);

		//criando janela de erro
		JFrame erroframe = new JFrame("Erro");
		erroframe.setBounds(800,400,290,170);
		erroframe.setLayout(null);
		JLabel errolb = new JLabel("Login ou senha invalidos");	
		errolb.setBounds(50, 25, 200, 70);
		JButton btnErro = new JButton("Ok");
		btnErro.setBounds(100, 80, 50, 30);
		btnErro.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			erroframe.setVisible(false);
		}
		});

		// Cria botao entrar
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(250, 160, 100, 50);;
		btnEntrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				//validando informacoes
				boolean validacao = false;
				for(int i=0; i<usuarios.size();i++){
					String user1 = usuarios.get(i).getNome();
					String user2 = textFieldLogin.getText();
					String pass1 = usuarios.get(i).getSenha();
					String pass2 = textFieldSenha.getText();
					if(user1.equals(user2) && pass1.equals(pass2)){
						validacao = true;
					}							
				}
				if(validacao==true){
					frame.setVisible(false);
					telaPainel.setVisible(true);
				}else{
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
		//frame.setSize(400, 300);
		frame.setBounds(800, 400, 400, 300);
		frame.setLayout(null);
		frame.setVisible(true);
	}

}