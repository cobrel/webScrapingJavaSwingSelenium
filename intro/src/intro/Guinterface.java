package intro;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Guinterface {

	private JFrame frmItemScanner;
	private JTextField inputSearchField;
	private JTable jtableG;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guinterface window = new Guinterface();
					window.frmItemScanner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Guinterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frmItemScanner = new JFrame();
		frmItemScanner.setResizable(false);
		frmItemScanner.setTitle("item scanner");
		frmItemScanner.setBounds(100, 100, 800, 413);
		frmItemScanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmItemScanner.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Lowest Price");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(332, 21, 120, 14);
		frmItemScanner.getContentPane().add(lblNewLabel);
		
		inputSearchField = new JTextField();
		inputSearchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
					model.setRowCount(0);
					String cautare = inputSearchField.getText();
					
						Forit fi = new Forit();
						try {
								fi.foritResults(cautare, jtableG);
			

						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
							Evomag ev = new Evomag();
						try {
							ev.evomagResults(cautare, jtableG);
		

					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
								Celpunctro cpr = new Celpunctro();	
						try {
							cpr.celpunctroResults(cautare, jtableG);
		

					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						Altex al = new Altex();
						try {
							al.altexResults(cautare, jtableG);
		

					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						Emag em = new Emag();
						try {
							em.emagResults(cautare, jtableG);
		

					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						

					
				}
			}
		});
		inputSearchField.setBounds(59, 53, 561, 22);
		frmItemScanner.getContentPane().add(inputSearchField);
		inputSearchField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)jtableG.getModel();
				model.setRowCount(0);
				String cautare = inputSearchField.getText();
				
					Forit fi = new Forit();
					try {
							fi.foritResults(cautare, jtableG);
		

					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Evomag ev = new Evomag();
					try {
						ev.evomagResults(cautare, jtableG);
	

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
						Celpunctro cpr = new Celpunctro();	
					try {
						cpr.celpunctroResults(cautare, jtableG);
	

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					Altex al = new Altex();
					try {
						al.altexResults(cautare, jtableG);
	

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					Emag em = new Emag();
					try {
						em.emagResults(cautare, jtableG);
	

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					

			}
		});
		searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
		searchButton.setBounds(642, 53, 89, 23);
		frmItemScanner.getContentPane().add(searchButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 86, 764, 255);
		frmItemScanner.getContentPane().add(scrollPane);
		
		jtableG = new JTable();
		jtableG.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nume", "Pret", "Link"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(jtableG);

		JMenuBar menuBar = new JMenuBar();
		frmItemScanner.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem saveButton = new JMenuItem("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int option = fc.showSaveDialog(saveButton);
				if(option == JFileChooser.APPROVE_OPTION) {
					File fs = fc.getSelectedFile();
					
					try {
						FileWriter fw = new FileWriter(fs);
						BufferedWriter bw = new BufferedWriter(fw);
						for (int i = 0; i < jtableG.getRowCount(); i++) {
							for (int j = 0; j < jtableG.getColumnCount(); j++) {
								bw.write(jtableG.getValueAt(i, j).toString() +",");	
							}
							bw.newLine();	
						}
						JOptionPane.showInternalMessageDialog(saveButton, "Succesfully Saved","Information",JOptionPane.INFORMATION_MESSAGE);
						bw.close();
						fw.close();
					}
					catch(IOException ex) {
						JOptionPane.showInternalMessageDialog(saveButton, "ERROR","ERROR MESSAGE",JOptionPane.ERROR_MESSAGE);
					}

					
					
					
				}
			}
		});
		mnNewMenu.add(saveButton);
		
		JMenuItem exitButton = new JMenuItem("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(exitButton);
		
		JMenu mnNewMenu_1 = new JMenu("About");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem ContactButton = new JMenuItem("Contact");
		ContactButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ContactButton, " Item Scanner\n Marius Cristian Neagu \n private_cristian@protonmail.com ", "Contact", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnNewMenu_1.add(ContactButton);
	}
}
