package br.com.uva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Test {

	public static void main(String ar[]) throws IOException {
		Test test = new Test();
		// test.setInformacao();
		// test.getInformacao();
		// test.updateInformacao();
		// test.getInformacao();
		// test.tTT();
		 System.out.println(test.contaLinhas());
	}

	public String[][] getLinhas() throws IOException {
		Scanner sc = new Scanner(new File("database.txt"));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}

		String[] arr = lines.toArray(new String[0]);
		String result[][] = new String[arr.length][getColunas().length];

		for (int i = 0; i < arr.length; i++) {
			String[] values = arr[i].split("\\s*,\\s*");
			for (int j = 0; j < values.length; j++) {
				result[i][j] = values[j];
			}
		}
		return result;
	}

	public String[] getColunas() {
		String columns[] = { "ID", "NOME", "IDADE" };
		return columns;
	}

	public int contaLinhas() throws IOException {
		int qtdLinha = 0;
		try {
			File arquivoLeitura = new File("database.txt");
			LineNumberReader linhaLeitura;
			linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));
			linhaLeitura.skip(arquivoLeitura.length());
			qtdLinha = linhaLeitura.getLineNumber();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qtdLinha;
	}

	public void getInformacao() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("database.txt"));
			String string = "";
			while ((string = br.readLine()) != null) {
				String data[] = new String[contaLinhas()];
				data = string.split(",");
				for (int i = 0; i < contaLinhas(); i++) {
					System.out.print(data[i] + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {

		}
	}

	public void setInformacao() {
		try {
			File myObj = new File("database.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);
		System.out.println("Entre com o id:");
		String id = sc.nextLine();
		System.out.println("Entre com o seu nome:");
		String nome = sc.nextLine();
		System.out.println("Entre com a sua idade:");
		String idade = sc.nextLine();

		try {
			File f = new File("database.txt");
			PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
			pw.append(id + "," + nome + "," + idade + String.format("%n", ""));
			pw.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public void updateInformacao() {
		////////// FASE 1
		System.out.println("Qual id?");
		Scanner sc = new Scanner(System.in);
		int id_to_change = sc.nextInt();
		String to_change_column_names[] = { "Nome", "Idade" };
		int yes_no[] = new int[2];
		String to_update[] = new String[5];

		System.out.println("Quais colunas devo atualizar? (0:NÃO / 1:SIM");
		for (int i = 0; i < 2; i++) {
			System.out.println(to_change_column_names[i] + "");
			int temp = sc.nextInt();
			yes_no[i] = temp;
		}

		////////// FASE 2
		System.out.println("===============================");
		System.out.println("Adicionar novos valores para as colunas");
		for (int i = 0; i < 2; i++) {
			System.out.println(to_change_column_names[i] + "");
			if (yes_no[i] == 1) {
				Scanner sc1 = new Scanner(System.in);
				String temp_val = sc1.nextLine();
				to_update[i] = temp_val;
				System.out.println();
			} else {
				System.out.println("nao pode ser mudado");
			}
		}

		////////// FASE 3
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader("database.txt"));
			String string = "";
			while ((string = br.readLine()) != null) {
				String data[] = new String[5];
				data = string.split(",");
				if (id_to_change == Integer.parseInt(data[0])) {
					String row = data[0] + ",";
					for (int i = 0; i < 2; i++) {
						if (yes_no[i] == 1) {
							System.out.printf("row " + row + String.format("%n", ""));
							System.out.printf("to_update[i] " + to_update[i] + String.format("%n", ""));
							row = row + to_update[i] + ",";
						} else {
							System.out.printf("ELSE " + row, String.format("%n", ""));
							row = row + data[i] + ",";
						}
					}
					System.out.printf("fora do FOR " + row + String.format("%n", ""));
					sb.append(row);
					sb.append(String.format("%n", ""));
				} else {
					sb.append(string);
					sb.append(String.format("%n", ""));
				}

				////////// FASE 4
				File f = new File("database.txt");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
				pw.print(sb.toString());
				pw.close();
			}
		} catch (Exception e) {

		}
	}
}