package mainPackage;

import java.awt.EventQueue;
import java.sql.*;
import Backend.DatabaseConnect;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;

public class FenetreGestionPension extends JFrame {

	private JPanel contentPane;
	private JTextField txtCatgorie;
	private JTextField txtMontant;
	private JTextField textField;
	private JTextField textField_1;
	private JTable listeTarifs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreGestionPension frame = new FenetreGestionPension();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreGestionPension() {
		setTitle("Pension pour tous");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 30, 1020, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(25, 164, 90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		contentPane.add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel Tarif = new JPanel();
		Tarif.setToolTipText("");
		Tarif.setName("");
		Tarif.setPreferredSize(new Dimension(1000, 500));
		Tarif.setMinimumSize(new Dimension(20, 10));
		Tarif.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Tarif", null, Tarif, null);
		Tarif.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setBounds(25, 59, 386, 538);
		Tarif.add(panel);
		panel.setLayout(null);
		
		JLabel formTitleLabel = new JLabel("CREATION");
		formTitleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 25));
		formTitleLabel.setBounds(118, 11, 140, 41);
		panel.add(formTitleLabel);
		
		txtCatgorie = new JTextField();
		txtCatgorie.setText("Catégorie");
		txtCatgorie.setColumns(10);
		txtCatgorie.setBounds(34, 131, 300, 20);
		panel.add(txtCatgorie);
		
		txtMontant = new JTextField();
		txtMontant.setText("Montant");
		txtMontant.setColumns(10);
		txtMontant.setBounds(34, 175, 300, 20);
		panel.add(txtMontant);
		
		JButton btnValidTarif = new JButton("Valider");
		btnValidTarif.setBounds(245, 229, 89, 23);
		panel.add(btnValidTarif);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"M", "N", "O", "P", "I", "J", "K", "L"}));
		comboBox.setBounds(140, 84, 194, 22);
		panel.add(comboBox);
		
		JLabel comboDiplTarif = new JLabel("Diplôme: ");
		comboDiplTarif.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboDiplTarif.setBounds(34, 84, 83, 22);
		panel.add(comboDiplTarif);
		
		JButton btnValidTarif_1 = new JButton("Valider");
		btnValidTarif_1.setBounds(245, 479, 89, 23);
		panel.add(btnValidTarif_1);
		
		textField = new JTextField();
		textField.setText("Montant");
		textField.setColumns(10);
		textField.setBounds(34, 425, 300, 20);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setText("Catégorie");
		textField_1.setColumns(10);
		textField_1.setBounds(34, 381, 300, 20);
		panel.add(textField_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(140, 334, 194, 22);
		panel.add(comboBox_1);
		
		JLabel comboDiplTarif_1 = new JLabel("Diplôme: ");
		comboDiplTarif_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboDiplTarif_1.setBounds(34, 334, 83, 22);
		panel.add(comboDiplTarif_1);
		
		JLabel lblModification = new JLabel("MODIFICATION");
		lblModification.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 25));
		lblModification.setBounds(86, 279, 204, 41);
		panel.add(lblModification);
		
		JLabel tarifLabel = new JLabel("GESTION DE TARIFS");
		tarifLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 25));
		tarifLabel.setForeground(new Color(0, 0, 0));
		tarifLabel.setBounds(433, 11, 250, 41);
		Tarif.add(tarifLabel);
		
		JPanel ListPanel = new JPanel();
		ListPanel.setBounds(464, 59, 495, 538);
		Tarif.add(ListPanel);
		
		//-----------------Events
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==1) {
					System.out.println("Tab changed\n");
					DatabaseConnect cnx=new DatabaseConnect();
					
					try {
						Connection connection=cnx.connect();
						Statement query=connection.createStatement();
						ResultSet rs=query.executeQuery("SELECT * FROM public.tarif");
						ResultSetMetaData tarifs=rs.getMetaData();
						
						int columns=tarifs.getColumnCount();
						String [] tableHeader=new String[columns];
						
						DefaultTableModel model=new DefaultTableModel();
						
						for(int i=0;i<columns;i++) {
							tableHeader[i]=tarifs.getColumnName(i+1);
							System.out.println(tableHeader[i]);
						}
						model.setColumnIdentifiers(tableHeader);
					
						
						while (rs.next()) {
			                Object[] rowData = new Object[columns];
			                for (int i = 1; i <= columns; i++) {
			                    rowData[i - 1] = rs.getObject(i);
			                }
			                model.addRow(rowData);
			            }
						JTable ListTable=new JTable(model);
						JScrollPane scrollpane=new JScrollPane(ListTable);
						
						ListPanel.add(scrollpane);
					}catch(Exception error) {
						System.out.println(error);
					}
				}
				
			}
			
		});
	}
}
