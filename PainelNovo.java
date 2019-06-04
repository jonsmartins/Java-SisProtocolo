package br.com.uva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PainelNovo {
	Usuarios user;
	PainelNovo(Usuarios usuario) {
		user = usuario;
	}

	JTable table;
	static DefaultTableModel model = new DefaultTableModel() {
//		valida se o dado a ser alterado pertence ao seu tipo primitivo como String, Double, Integer
//		Caso nao seja é feita uma validação que altera a cor da borda do campo para vermelho
		public Class getColumnClass(int column) {
			Class returnValue = null;
			if (column == 0) {
				returnValue = Integer.class;
			} else if (column == 1) {
				returnValue = String.class;
			} else if (column == 2) {
				returnValue = Double.class;
			} else if (column == 3) {
//				vai ser mudado para LocalDate
				returnValue = String.class;
			} else {
				return Object.class;
			}
			return returnValue;
		}
	};
	JScrollPane scrollPane = new JScrollPane();
	Integer id = model.getRowCount() + 1;
	JFrame frame = new JFrame();
	JTextField textFieldBuscar = new JTextField();
	List<RowSorter.SortKey> sortKeys = new ArrayList<>();
	TableRowSorter<TableModel> sorter;

	public void montaTela() throws IOException {
		getColunas();
		getLinhas();

		table = new JTable(model);
		table.setRowSelectionAllowed(true);

		alinharColunasParaEsquerda();
		ordenaColunaEscolhida();

		montaComponentsDeFiltro();
		frame.getContentPane().add(montaBtnAdicionar());
		frame.getContentPane().add(montaBtnRemover());
		frame.getContentPane().add(montaBtnMostrarValores());

		frame.setBounds(800, 400, 650, 400);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		scrollPane.setBounds(20, 100, 300, 190);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);

		encerraPrograma();
	}

	public void encerraPrograma() {
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frameopt = new JFrame();
				
				String[] options = { "Sair e Salvar", "Sair sem salvar", "Cancelar"};
				// 0=Sair e salvar, 1=Sair sem salvar, 2=Cancelar
				int resposta = JOptionPane.showOptionDialog(frameopt.getContentPane(),"Deseja realmente sair ?!","Saindo...", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
				if(resposta==0){
					salvarDadosQueEstavamEmMemoria();
					frame.setVisible(false);
					frame.dispose();
				}
				else if(resposta==1){
					System.exit(0);
				}
				else if(resposta==2){

				}
			}
		});
	}

	public void salvarDadosQueEstavamEmMemoria() {
		String lines = "";
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(user.getNome()+".txt", true));
			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < model.getColumnCount(); j++) {
					if (model.getColumnCount() == j + 1) {
						lines += model.getValueAt(i, j).toString() + String.format("%n", "");
					} else {
						lines += model.getValueAt(i, j).toString() + ",";
					}
				}
			}
			pw.append(lines);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void alinharColunasParaEsquerda() {
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		for (int i = 0; i < model.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
		}
	}

	public void ordenaColunaEscolhida() {
		sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	public JButton montaBtnRemover() {
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(350, 150, 95, 25);

		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (table.getSelectedRow() != -1) {
					model.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
				}
			}
		});
		return btnRemover;
	}

	public Integer getProximoId() throws IOException {
		if (model.getDataVector().size() == 0) {
			return 1;
		}
		Integer max = 0;
		for (int i = 0; i < model.getDataVector().size(); i++) {
			if (max < Integer.parseInt(model.getValueAt(i, 0).toString())) {
				max = Integer.parseInt(model.getValueAt(i, 0).toString());
			}
		}
		max++;
		return max;
	}

	public void montaComponentsDeFiltro() {
		JLabel labelBuscar = new JLabel("Buscar");
		labelBuscar.setBounds(20, 20, 100, 30);
		textFieldBuscar.setBounds(20, 50, 240, 25);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(350, 50, 95, 25);
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(450, 50, 95, 25);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					filtrarDados(textFieldBuscar.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textFieldBuscar.setText("");
					filtrarDados(textFieldBuscar.getText());
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

	public JButton montaBtnMostrarValores() throws IOException {
		JButton btnMostraValores = new JButton("Valores");
		btnMostraValores.setBounds(350, 200, 95, 25);

		btnMostraValores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println(model.getDataVector());
			}
		});
		return btnMostraValores;
	}

	public void getColunas() {
		model.addColumn("ID");
		model.addColumn("NOME");
		model.addColumn("SALARIO");
		model.addColumn("DATA");
	}

	public void getLinhas() throws IOException {
		Scanner sc = new Scanner(new FileReader(user.getNome()+".txt"));
		HashMap<Integer, Object[]> lines = new HashMap<>();
		Integer i = 0;
		while (sc.hasNextLine()) {
			String[] arr = sc.nextLine().split(",");
			int id = Integer.parseInt(arr[0]);
			double salario = Double.parseDouble(arr[2]);
			Object[] obj = { id, arr[1], salario, arr[3] };
			lines.put(i, obj);
			i++;
		}

		sc.close();
		for (int j = 0; j < lines.size(); j++) {
			model.addRow(lines.get(j));
		}
	}

	public void filtrarDados(String dadoBuscado) throws IOException {
		if (dadoBuscado.length() == 0) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + dadoBuscado));
		}
	}

	public JButton montaBtnAdicionar() {
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(350, 100, 95, 25);

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frameAddItem = new JFrame("Salvar");
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

				JButton btnAdicionarNovo = new JButton("Adicionar");
				btnAdicionarNovo.setBounds(50, 220, 95, 25);

				frameAddItem.getContentPane().add(btnAdicionarNovo);
				frameAddItem.getContentPane().add(labelNome);
				frameAddItem.getContentPane().add(textFieldNome);
				frameAddItem.getContentPane().add(labelValor);
				frameAddItem.getContentPane().add(textFieldValor);
				frameAddItem.getContentPane().add(labelData);
				frameAddItem.getContentPane().add(textFieldData);

				btnAdicionarNovo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frameAddItem.setVisible(false);

						try {
							Object[] rowValues = { getProximoId(), textFieldNome.getText(),
									Double.parseDouble(textFieldValor.getText()), textFieldData.getText() };
							model.addRow(rowValues);
							textFieldBuscar.setText("");
							filtrarDados("");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		return btnAdicionar;
	}

	//public static void main(String args[]) throws IOException {
	//	PainelNovo painel = new PainelNovo();
	//	painel.montaTela();
	//}
}
