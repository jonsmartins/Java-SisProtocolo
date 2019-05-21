package br.com.uva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Painel {
	JFrame frame = new JFrame("Painel");
	JTable table = new JTable() {
		public boolean isCellEditable(int rowIndex, int vColIndex) {
			return false;
		}
	};
	Test ts = new Test();
	int selectedElement = -1;

	public static void main(String[] args) throws IOException {
		new Painel().montaTela();
	}

	public void montaTela() throws IOException {

		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		frame.getContentPane().add(montaBtnAdicionar());
		frame.getContentPane().add(montaBtnEditar());
		frame.getContentPane().add(montaBtnRemover());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 20, 240, 190);
		frame.getContentPane().add(scrollPane);

		table.setModel(new DefaultTableModel(ts.getLinhas(), ts.getColunas()));
		table.setRowSelectionAllowed(true);

		ListSelectionModel select = table.getSelectionModel();
		select.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				for (int i = 0; i < table.getSelectedRows().length; i++) {
					selectedElement = (table.getSelectedRows()[i] + 1);
				}
			}
		});
		scrollPane.setViewportView(table);
	}

	public JButton montaBtnEditar() {
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(300, 80, 95, 25);

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedElement != -1) {
					JFrame frameEditItem = new JFrame("Editar");
					frameEditItem.setBounds(100, 100, 500, 350);
					frameEditItem.getContentPane().setLayout(null);
					frameEditItem.setVisible(true);

					// Cria label e textField do Nome
					JLabel labelNome = new JLabel("Nome");
					labelNome.setBounds(50, 20, 100, 30);
					JTextField textFieldNome = new JTextField();
					textFieldNome.setBounds(50, 50, 150, 30);

					// Cria label e textField da Idade
					JLabel labelIdade = new JLabel("Idade");
					labelIdade.setBounds(50, 80, 100, 30);
					JTextField textFieldIdade = new JTextField();
					textFieldIdade.setBounds(50, 110, 150, 30);

					try {
						textFieldNome.setText(ts.getLinhas()[selectedElement - 1][1]);
						textFieldIdade.setText(ts.getLinhas()[selectedElement - 1][2]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					JButton btnEditar = new JButton("Editar");
					btnEditar.setBounds(50, 180, 95, 25);
					btnEditar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							frameEditItem.setVisible(false);
							String[] valueTochange = new String[3];
							valueTochange[0] = Integer.toString(selectedElement);
							valueTochange[1] = textFieldNome.getText();
							valueTochange[2] = textFieldIdade.getText();
							try {
								ts.updateData(valueTochange);
								frame.setVisible(false);
								new Painel().montaTela();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});

					frameEditItem.getContentPane().add(btnEditar);
					frameEditItem.getContentPane().add(labelNome);
					frameEditItem.getContentPane().add(textFieldNome);
					frameEditItem.getContentPane().add(labelIdade);
					frameEditItem.getContentPane().add(textFieldIdade);
				}
			}
		});
		return btnEditar;
	}

	public JButton montaBtnRemover() {
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(300, 130, 95, 25);

		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedElement >= 0) {
					try {
						System.out.println(selectedElement);
						ts.removeData(selectedElement);
						frame.setVisible(false);
						new Painel().montaTela();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		return btnRemover;
	}

	public JButton montaBtnAdicionar() {
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(300, 30, 95, 25);

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameAddItem = new JFrame("Adicionar novo");
				frameAddItem.setBounds(100, 100, 500, 350);
				frameAddItem.getContentPane().setLayout(null);
				frameAddItem.setVisible(true);

				// Cria label e textField do Nome
				JLabel labelNome = new JLabel("Nome");
				labelNome.setBounds(50, 20, 100, 30);
				JTextField textFieldNome = new JTextField();
				textFieldNome.setBounds(50, 50, 150, 30);

				// Cria label e textField da Idade
				JLabel labelIdade = new JLabel("Idade");
				labelIdade.setBounds(50, 80, 100, 30);
				JTextField textFieldIdade = new JTextField();
				textFieldIdade.setBounds(50, 110, 150, 30);

				JButton btnAdicionarNovo = new JButton("Adicionar");
				btnAdicionarNovo.setBounds(50, 180, 95, 25);

				frameAddItem.getContentPane().add(btnAdicionarNovo);
				frameAddItem.getContentPane().add(labelNome);
				frameAddItem.getContentPane().add(textFieldNome);
				frameAddItem.getContentPane().add(labelIdade);
				frameAddItem.getContentPane().add(textFieldIdade);

				btnAdicionarNovo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frameAddItem.setVisible(false);
						try {
							ts.setData(textFieldNome.getText(), textFieldIdade.getText());
							frame.setVisible(false);
							new Painel().montaTela();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		return btnAdicionar;
	}
}