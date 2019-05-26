package br.com.uva;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class EscolheData {
	Calendar calendar = Calendar.getInstance();
	int mes = calendar.get(Calendar.MONTH);
	int ano = calendar.get(Calendar.YEAR);;
	JLabel jLabel = new JLabel("", JLabel.CENTER);
	String dia = "";
	JDialog jDialog;
	JButton[] jBtnDataEscolhida = new JButton[49];

	public static void main(String[] args) {
		JLabel jLabel = new JLabel("Selecione uma data:");
		final JTextField jTextField = new JTextField(20);
		JButton jButton = new JButton("...");
		JPanel jPanel = new JPanel();
		jPanel.add(jLabel);
		jPanel.add(jTextField);
		jPanel.add(jButton);
		final JFrame Jframe = new JFrame();
		Jframe.getContentPane().add(jPanel);
		Jframe.pack();
		Jframe.setVisible(true);
		jButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jTextField.setText(new EscolheData(Jframe).setDataEscolhida());
			}
		});
	}
	
	public EscolheData(JFrame JframePai) {
		jDialog = new JDialog();
		jDialog.setModal(true);
		String[] header = { "Dom", "Seg", "Ter", "Quar", "Quin", "Sex", "Sab" };
		JPanel jPanelCalendario = new JPanel(new GridLayout(7, 7));
		jPanelCalendario.setPreferredSize(new Dimension(430, 120));

		for (int i = 0; i < jBtnDataEscolhida.length; i++) {
			final int escolha = i;
			jBtnDataEscolhida[i] = new JButton();
			jBtnDataEscolhida[i].setFocusPainted(false);
			jBtnDataEscolhida[i].setBackground(Color.white);
			if (i > 6)
				jBtnDataEscolhida[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						dia = jBtnDataEscolhida[escolha].getActionCommand();
						jDialog.dispose();
					}
				});
			if (i < 7) {
				jBtnDataEscolhida[i].setText(header[i]);
				jBtnDataEscolhida[i].setForeground(Color.red);
			}
			jPanelCalendario.add(jBtnDataEscolhida[i]);
		}
		JPanel jPainelBtnsCalendario = new JPanel(new GridLayout(1, 3));
		JButton mesAnteriror = new JButton("<<");
		mesAnteriror.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mes--;
				mostraData();
			}
		});
		jPainelBtnsCalendario.add(mesAnteriror);
		jPainelBtnsCalendario.add(jLabel);
		JButton mesSeguinte = new JButton(">>");
		mesSeguinte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mes++;
				mostraData();
			}
		});
		jPainelBtnsCalendario.add(mesSeguinte);
		jDialog.add(jPanelCalendario, BorderLayout.CENTER);
		jDialog.add(jPainelBtnsCalendario, BorderLayout.SOUTH);
		jDialog.pack();
		jDialog.setLocationRelativeTo(JframePai);
		mostraData();
		jDialog.setVisible(true);
	}

	public void mostraData() {
		for (int i = 7; i < jBtnDataEscolhida.length; i++) {
			jBtnDataEscolhida[i].setText("");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mes, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 6 + dayOfWeek, day = 1; day <= daysInMonth; i++, day++) {
			jBtnDataEscolhida[i].setText("" + day);
		}
		jLabel.setText(sdf.format(cal.getTime()));
		jDialog.setTitle("Selecione uma data");
	}

	public String setDataEscolhida() {
		if (dia.equals("")) {			
			return dia;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mes, Integer.parseInt(dia));
		return sdf.format(cal.getTime());
	}
}