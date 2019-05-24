package br.com.uva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Painel {
	JFrame frame = new JFrame("Painel");
	JScrollPane scrollPane = new JScrollPane();
	JTable table = new JTable();
	JTextField textFieldBuscar = new JTextField();
	static Test ts = new Test();

	String[][] linhas_tabela;
	String valorDeBusca;

	int selectedElement = -1;
	String[] rowSelected = new String[3];

	public static void main(String[] args) throws IOException {
		Painel pn = new Painel(ts.getLinhas(), "");
		pn.montaTela();
	}

	Painel(String[][] linhas, String textFieldBuscar) {
		linhas_tabela = linhas;
		valorDeBusca = textFieldBuscar;
	}

	public void montaTela() throws IOException {
		montaComponentsDeFiltro();
		frame.getContentPane().add(montaBtnAdicionar());
		frame.getContentPane().add(montaBtnEditar());
		frame.getContentPane().add(montaBtnRemover());

		frame.setBounds(100, 100, 550, 400);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		scrollPane.setBounds(20, 100, 240, 190);
		frame.getContentPane().add(scrollPane);

		if (linhas_tabela.length > 0) {
			montaTabela();
		}
	}

	public void montaTabela() throws IOException {
		table.setModel(new DefaultTableModel(linhas_tabela, ts.getColunas()));
		table.setRowSelectionAllowed(true);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				selectedElement = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				for (int i = 0; i < rowSelected.length; i++) {
					rowSelected[i] = table.getValueAt(table.getSelectedRow(), i).toString();
				}
			}
		});
		scrollPane.setViewportView(table);
	}

	public String[][] filtrarDados(String dadoBuscado) throws IOException {
		int qtdLinhas = 0;
		HashMap<Integer, String[]> lista = new HashMap<Integer, String[]>();
		for (int i = 0; i < ts.contaLinhas(); i++) {
			for (int j = 0; j < ts.getColunas().length; j++) {
				if (i < ts.contaLinhas() && ts.getLinhas()[i][j].contains(dadoBuscado)) {
					lista.put(qtdLinhas, ts.getLinhas()[i]);
					qtdLinhas++;
					j = 0;
					i++;
				}
			}
		}

		String[][] linhasTabela = new String[qtdLinhas][ts.getColunas().length];
		for (int i = 0; i < qtdLinhas; i++) {
			linhasTabela[i] = lista.get(i);
		}

		return linhasTabela;
	}

	public void montaComponentsDeFiltro() {
		JLabel labelBuscar = new JLabel("Buscar");
		labelBuscar.setBounds(20, 20, 100, 30);
		textFieldBuscar.setText(valorDeBusca);
		textFieldBuscar.setBounds(20, 50, 240, 25);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(300, 50, 95, 25);
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(400, 50, 95, 25);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					new Painel(filtrarDados(textFieldBuscar.getText()), textFieldBuscar.getText()).montaTela();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					new Painel(ts.getLinhas(), "").montaTela();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		frame.getContentPane().add(labelBuscar);
		frame.getContentPane().add(textFieldBuscar);
		frame.getContentPane().add(btnBuscar);
		frame.getContentPane().add(btnLimpar);
	}

	public JButton montaBtnEditar() {
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(300, 140, 95, 25);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedElement > 0) {
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

					textFieldNome.setText(rowSelected[1]);
					textFieldIdade.setText(rowSelected[2]);

					JButton btnEditar = new JButton("Editar");
					btnEditar.setBounds(50, 180, 95, 25);
					btnEditar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String valueTochange[] = new String[3];
							valueTochange[0] = rowSelected[0];
							valueTochange[1] = textFieldNome.getText();
							valueTochange[2] = textFieldIdade.getText();
							frameEditItem.setVisible(false);
							try {
								ts.updateData(valueTochange);
								frame.setVisible(false);
								new Painel(filtrarDados(textFieldBuscar.getText()), textFieldBuscar.getText()).montaTela();
							} catch (IOException e1) {
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
		btnRemover.setBounds(300, 180, 95, 25);

		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedElement >= 0) {
					try {
						ts.removeData(rowSelected[0]);
						frame.setVisible(false);
						new Painel(filtrarDados(textFieldBuscar.getText()), textFieldBuscar.getText()).montaTela();
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
		btnAdicionar.setBounds(300, 100, 95, 25);

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
							new Painel(ts.getLinhas(), "").montaTela();
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