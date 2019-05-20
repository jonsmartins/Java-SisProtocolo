package br.com.uva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Painel {
	JFrame frame = new JFrame();
	JTable table = new JTable();

	public static void main(String[] args) throws IOException {
		new Painel().montaTela();
	}

	public void montaTela() throws IOException {
		Test ts = new Test();

		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(258, 41, 97, 25);

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameAddItem = new JFrame();
				frameAddItem.setBounds(100, 100, 450, 300);
				frameAddItem.getContentPane().setLayout(null);
				frameAddItem.setVisible(true);
				
				JButton btnAdicionar2 = new JButton("Adicionar");
				frameAddItem.getContentPane().add(btnAdicionar2);
				btnAdicionar2.setBounds(258, 41, 97, 25);
				frame.setEnabled(false);
				btnAdicionar2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frameAddItem.setVisible(false);
						try {
							File f = new File("database.txt");
							PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
							pw.append(ts.contaLinhas() + "," + "tedd" + "," + "19" + String.format("%n", ""));
							pw.close();
							frame.setVisible(false);
							new Painel().montaTela();
						} catch (Exception exep) {
							System.out.println("exep: " + exep);
						}
					}
				});
			}
		});

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(258, 84, 97, 25);

		JButton btnExcluir = new JButton("excluir");
		btnExcluir.setBounds(258, 131, 97, 25);

		frame.getContentPane().add(btnAdicionar);
		frame.getContentPane().add(btnEditar);
		frame.getContentPane().add(btnExcluir);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 37, 157, 188);
		frame.getContentPane().add(scrollPane);

		table.setModel(new DefaultTableModel(ts.getLinhas(), ts.getColunas()));
		scrollPane.setViewportView(table);
	}

	public void addItem() {

	}
}