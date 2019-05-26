package br.com.uva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	Usuarios user;

	int selectedElement = -1;
	String[] rowSelected = new String[ts.getColunas().length];

	Painel(String[][] linhas, String textFieldBuscar, Usuarios usuario) {
		this.linhas_tabela = linhas;
		this.valorDeBusca = textFieldBuscar;
		this.user = usuario;
	}
	// user.createFile();
	// String fl = user.getNome()+".txt";
	// File file = new File(fl);

	public void montaTela() throws IOException {
		montaComponentsDeFiltro();
		frame.getContentPane().add(montaBtnAdicionar());
		frame.getContentPane().add(montaBtnEditar());
		frame.getContentPane().add(montaBtnRemover());

		frame.setBounds(800, 400, 650, 400);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		scrollPane.setBounds(20, 100, 300, 190);
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

	public String[][] filtrarDados(String dadoBuscado, File file) throws IOException {
		int qtdLinhas = 0;
		HashMap<Integer, String[]> lista = new HashMap<Integer, String[]>();
		// String fl = user.getNome()+".txt";
		// File file = new File(fl);
		for (int i = 0; i < ts.contaLinhas(file); i++) {
			for (int j = 0; j < ts.getColunas().length; j++) {
				if (i < ts.contaLinhas(file) && ts.getLinhas(file)[i][j].contains(dadoBuscado)) {
					lista.put(qtdLinhas, ts.getLinhas(file)[i]);
					qtdLinhas++;
					j = -1;
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
		btnBuscar.setBounds(350, 50, 95, 25);
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(450, 50, 95, 25);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					String fl = user.getNome() + ".txt";
					File file = new File(fl);
					new Painel(filtrarDados(textFieldBuscar.getText(), file), textFieldBuscar.getText(), user)
							.montaTela();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					String fl = user.getNome() + ".txt";
					File file = new File(fl);
					new Painel(ts.getLinhas(file), "", user).montaTela();
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
		btnEditar.setBounds(350, 140, 95, 25);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedElement > 0) {
					JFrame frameEditItem = new JFrame("Editar");
					frameEditItem.setBounds(800, 400, 500, 350);
					frameEditItem.getContentPane().setLayout(null);
					frameEditItem.setVisible(true);

					// Cria label e textField do Nome
					JLabel labelNome = new JLabel("Nome");
					labelNome.setBounds(50, 20, 100, 30);
					JTextField textFieldNome = new JTextField();
					textFieldNome.setBounds(50, 50, 150, 30);

					// Cria label e textField da Valor
					JLabel labelValor = new JLabel("Valor");
					labelValor.setBounds(50, 80, 100, 30);
					JTextField textFieldValor = new JTextField();
					textFieldValor.setBounds(50, 110, 150, 30);
					
					// Cria label e textField da Data
					JLabel labelData = new JLabel("Data");
					labelData.setBounds(50, 140, 100, 30);
					JTextField textFieldData = new JTextField();
					textFieldData.setBounds(50, 170, 150, 30);

					textFieldNome.setText(rowSelected[1]);
					textFieldValor.setText(rowSelected[2]);
					textFieldData.setText(rowSelected[3]);
					
					JButton jBtnEscolheData = new JButton("...");
					jBtnEscolheData.setBounds(205, 170, 60, 30);
					jBtnEscolheData.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							textFieldData.setText(new EscolheData(frame).setDataEscolhida());
						}
					});

					JButton btnEditar = new JButton("Editar");
					btnEditar.setBounds(50, 220, 95, 25);
					btnEditar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String valueTochange[] = new String[ts.getColunas().length];
							valueTochange[0] = rowSelected[0];
							valueTochange[1] = textFieldNome.getText();
							valueTochange[2] = textFieldValor.getText();
							valueTochange[3] = textFieldData.getText();
							frameEditItem.setVisible(false);
							try {
								String fl = user.getNome() + ".txt";
								File file = new File(fl);
								ts.updateData(valueTochange, file);
								frame.setVisible(false);
								new Painel(filtrarDados(textFieldBuscar.getText(), file), textFieldBuscar.getText(), user).montaTela();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});

					frameEditItem.getContentPane().add(btnEditar);
					frameEditItem.getContentPane().add(labelNome);
					frameEditItem.getContentPane().add(textFieldNome);
					frameEditItem.getContentPane().add(labelValor);
					frameEditItem.getContentPane().add(textFieldValor);
					frameEditItem.getContentPane().add(labelData);
					frameEditItem.getContentPane().add(textFieldData);
					frameEditItem.getContentPane().add(jBtnEscolheData);
				}
			}
		});
		return btnEditar;
	}

	public JButton montaBtnRemover() {
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(350, 180, 95, 25);

		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedElement >= 0) {
					try {
						String fl = user.getNome() + ".txt";
						File file = new File(fl);
						ts.removeData(rowSelected[0], file);
						frame.setVisible(false);
						new Painel(filtrarDados(textFieldBuscar.getText(), file), textFieldBuscar.getText(), user)
								.montaTela();
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
		btnAdicionar.setBounds(350, 100, 95, 25);

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameAddItem = new JFrame("Adicionar novo");
				frameAddItem.setBounds(800, 400, 500, 350);
				frameAddItem.getContentPane().setLayout(null);
				frameAddItem.setVisible(true);

				// Cria label e textField do Nome
				JLabel labelNome = new JLabel("Nome");
				labelNome.setBounds(50, 20, 100, 30);
				JTextField textFieldNome = new JTextField();
				textFieldNome.setBounds(50, 50, 150, 30);

				// Cria label e textField do Valor
				JLabel labelValor = new JLabel("Valor");
				labelValor.setBounds(50, 80, 100, 30);
				JTextField textFieldValor = new JTextField();
				textFieldValor.setBounds(50, 110, 150, 30);

				// Cria label e textField da Data
				JLabel labelData = new JLabel("Data");
				labelData.setBounds(50, 140, 100, 30);
				JTextField textFieldData = new JTextField();
				textFieldData.setBounds(50, 170, 150, 30);

				JButton jBtnEscolheData = new JButton("...");
				jBtnEscolheData.setBounds(205, 170, 60, 30);
				jBtnEscolheData.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						textFieldData.setText(new EscolheData(frame).setDataEscolhida());
					}
				});

				JButton btnAdicionarNovo = new JButton("Adicionar");
				btnAdicionarNovo.setBounds(50, 220, 95, 25);

				frameAddItem.getContentPane().add(btnAdicionarNovo);
				frameAddItem.getContentPane().add(labelNome);
				frameAddItem.getContentPane().add(textFieldNome);
				frameAddItem.getContentPane().add(labelValor);
				frameAddItem.getContentPane().add(textFieldValor);
				frameAddItem.getContentPane().add(labelData);
				frameAddItem.getContentPane().add(textFieldData);
				frameAddItem.getContentPane().add(jBtnEscolheData);

				btnAdicionarNovo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frameAddItem.setVisible(false);
						try {
							String fl = user.getNome() + ".txt";
							File file = new File(fl);
							ts.setData(textFieldNome.getText(), textFieldValor.getText(), textFieldData.getText(),
									file);
							frame.setVisible(false);
							new Painel(ts.getLinhas(file), "", user).montaTela();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		return btnAdicionar;
	}
}