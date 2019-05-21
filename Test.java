package br.com.uva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Test {

	public static void main(String ar[]) throws IOException {
		Test test = new Test();
// 		test.setInformacao();
//		test.updateData();
//		test.getNextId();
//		System.out.println(test.contaLinhas());
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

	public void removeData(int lineToRemove) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("database.txt"));
			StringBuffer sb = new StringBuffer("");
			int linenumber = 1;
			String line;

			while ((line = br.readLine()) != null) {
				if (linenumber < lineToRemove || linenumber >= lineToRemove + 1) {
					sb.append(line + String.format("%n", ""));
				}
				linenumber++;
			}
			if (lineToRemove + 1 > linenumber)
				System.out.println("Fim do arquivo");
			br.close();

			FileWriter fw = new FileWriter(new File("database.txt"));
			fw.write(sb.toString());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setData(String nome, String idade) {
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

		try {
			File f = new File("database.txt");
			PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
			pw.append(getNextId() + "," + nome + "," + idade + String.format("%n", ""));
			pw.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public int getNextId() throws IOException {
		if (contaLinhas() == 0) {
			return 1;
		}
		int max = 0;
		for (int i = 0; i < contaLinhas(); i++) {
			max = Integer.parseInt(getLinhas()[i][0]);
			if (max < Integer.parseInt(getLinhas()[i][0])) {
				max = Integer.parseInt(getLinhas()[i][0]);
			}
		}
		return max + 1;
	}

	public void updateData(String[] strings) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("database.txt"));
		StringBuffer sb = new StringBuffer("");
		int linenumber = 1;
		String line;

		while ((line = br.readLine()) != null) {
			if (Integer.parseInt(strings[0]) == linenumber) {
				sb.append(strings[0] + "," + strings[1] + "," + strings[2] + String.format("%n", ""));
			} else {
				sb.append(line + String.format("%n", ""));
			}
			linenumber++;
		}
		br.close();
		File f = new File("database.txt");
		PrintWriter pw = new PrintWriter(new FileOutputStream(f, false));
		pw.print(sb.toString());
		pw.close();
	}
}