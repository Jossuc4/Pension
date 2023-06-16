package mainPackage;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Backend.DatabaseConnect;

import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GestionPersonnes extends JPanel {
	private JTextField nameField;
	private JTextField prenomsField;
	private JTextField diplomeField;
	private JTextField nomConjoint;
	private JTextField prenomsConjoint;
	private JTextField txtNumroDeTarif;
	private String datenais;
	private ButtonGroup buttonGroup;
	/**
	 * @wbp.nonvisual location=525,89
	 */
	
	public void print(String param) {
		System.out.println(param);
	}
	/**
	 * 
	 * @return Connection
	 */
	public Connection connectDB() {
		Connection cnx=null;
		DatabaseConnect dbcnx=new DatabaseConnect();
		try {
			cnx=dbcnx.connect();
		}catch(Exception error) {
			print(error.getMessage());
		}
		return cnx;
	}
	
	public GestionPersonnes() {
		setBackground(new Color(25, 164, 90));
		setLayout(null);
		
		JPanel FormPanel = new JPanel();
		FormPanel.setBackground(new Color(255, 255, 255));
		FormPanel.setBounds(29, 58, 375, 216);
		add(FormPanel);
		FormPanel.setLayout(null);
		
		nameField = new JTextField();
		nameField.setText("Votre nom");
		nameField.setBounds(44, 7, 281, 27);
		FormPanel.add(nameField);
		nameField.setColumns(10);
		
		prenomsField = new JTextField();
		prenomsField.setText("Votre prénom");
		prenomsField.setColumns(10);
		prenomsField.setBounds(44, 56, 281, 27);
		FormPanel.add(prenomsField);
		
		diplomeField = new JTextField();
		diplomeField.setText("Diplome");
		diplomeField.setColumns(10);
		diplomeField.setBounds(193, 180, 132, 25);
		FormPanel.add(diplomeField);
		diplomeField.setVisible(false);
		
		JDateChooser datenaisField = new JDateChooser();
		datenaisField.setDateFormatString("dd/MM/yyyy");
		datenais=null;
		datenaisField.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent evt) {
				if(datenaisField.getDate()!=null) {
					SimpleDateFormat spfOut=new SimpleDateFormat("yyyy-MM-dd");
					try {
			            datenais=spfOut.format(datenaisField.getDate());
			            System.out.println("Date formatée : " + datenais);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				}
				
			}
		});
		datenaisField.setBounds(193, 109, 132, 20);
		FormPanel.add(datenaisField);
		
		JLabel lblNewLabel = new JLabel("Date de naissance");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(44, 109, 160, 20);
		FormPanel.add(lblNewLabel);
		
		JCheckBox checkOtherDipl = new JCheckBox("Autre diplome");
		checkOtherDipl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diplomeField.setVisible(true);
			}
		});
		checkOtherDipl.setBackground(Color.WHITE);
		checkOtherDipl.setBounds(44, 159, 97, 23);
		FormPanel.add(checkOtherDipl);
		
		JComboBox comboDiplome = new JComboBox();
		comboDiplome.setBounds(193, 147, 132, 22);
		FormPanel.add(comboDiplome);
		
		JPanel conjointPanel = new JPanel();
		conjointPanel.setBackground(new Color(255, 255, 255));
		conjointPanel.setBounds(443, 58, 394, 216);
		add(conjointPanel);
		conjointPanel.setLayout(null);
		
		nomConjoint = new JTextField();
		nomConjoint.setText("Nom du conjoint");
		nomConjoint.setColumns(10);
		nomConjoint.setBounds(50, 56, 281, 27);
		conjointPanel.add(nomConjoint);
		nomConjoint.setEnabled(false);
		
		prenomsConjoint = new JTextField();
		prenomsConjoint.setText("Prénoms du conjoint");
		prenomsConjoint.setColumns(10);
		prenomsConjoint.setBounds(50, 105, 281, 27);
		conjointPanel.add(prenomsConjoint);
		prenomsConjoint.setEnabled(false);
		
		txtNumroDeTarif = new JTextField();
		txtNumroDeTarif.setText("Numéro de tarif");
		txtNumroDeTarif.setColumns(10);
		txtNumroDeTarif.setBounds(50, 159, 281, 27);
		conjointPanel.add(txtNumroDeTarif);
		
		JRadioButton rdbtnMarried = new JRadioButton("Marié(e)");
		rdbtnMarried.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomConjoint.setEnabled(true);
				prenomsConjoint.setEnabled(true);
			}
		});
		rdbtnMarried.setBackground(new Color(255, 255, 255));
		rdbtnMarried.setBounds(50, 7, 92, 23);
		conjointPanel.add(rdbtnMarried);
		
		JRadioButton rdbtnDivorced = new JRadioButton("Divorcé(e)");
		rdbtnDivorced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomConjoint.setEnabled(false);
				prenomsConjoint.setEnabled(false);
			}
		});
		rdbtnDivorced.setBackground(new Color(255, 255, 255));
		rdbtnDivorced.setBounds(144, 7, 114, 23);
		conjointPanel.add(rdbtnDivorced);
		
		JRadioButton rdbtnWidow = new JRadioButton("Veuf(ve)");
		rdbtnWidow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomConjoint.setEnabled(false);
				prenomsConjoint.setEnabled(false);
			}
		});
		rdbtnWidow.setBackground(new Color(255, 255, 255));
		rdbtnWidow.setBounds(260, 7, 85, 23);
		conjointPanel.add(rdbtnWidow);
		
		buttonGroup=new ButtonGroup();
		buttonGroup.add(rdbtnWidow);
		buttonGroup.add(rdbtnMarried);
		buttonGroup.add(rdbtnDivorced);
		
		JPanel ListPannel = new JPanel();
		ListPannel.setBackground(new Color(255, 255, 255));
		ListPannel.setBounds(29, 366, 944, 230);
		add(ListPannel);
		
		JButton btnNewPers = new JButton("Ajouter");
		btnNewPers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewPers.setBounds(865, 73, 108, 48);
		add(btnNewPers);
		
		JButton btnModifPers = new JButton("Modifier");
		btnModifPers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModifPers.setBounds(865, 151, 108, 48);
		add(btnModifPers);
		
		JButton btnDeletePers = new JButton("Supprimer");
		btnDeletePers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeletePers.setBounds(865, 226, 108, 48);
		add(btnDeletePers);
		
		JLabel Titre = new JLabel("LISTE DES PERSONNES BENEFICIAIRES");
		Titre.setForeground(Color.WHITE);
		Titre.setFont(new Font("Times New Roman", Font.BOLD, 25));
		Titre.setBounds(276, 306, 524, 36);
		add(Titre);
		
		JPanel Separator = new JPanel();
		Separator.setBackground(new Color(255, 255, 255));
		Separator.setBounds(0, 292, 1000, 3);
		add(Separator);
		
		try {
			Connection cnx= connectDB();
			Statement query=cnx.createStatement();
			ResultSet rs=query.executeQuery("SELECT * FROM public.personne");
			ResultSetMetaData personnes=rs.getMetaData();
			
			//Préparation de JTable
			int columnCount=personnes.getColumnCount();
			
			String [] tableHeader=new String[columnCount];
			
			final DefaultTableModel model=new DefaultTableModel();
			
			int lignes=model.getRowCount();
			for(int i=0;i<columnCount;i++) {
				tableHeader[i]=personnes.getColumnName(i+1);
			}
			model.setColumnIdentifiers(tableHeader);
			
			while(rs.next()) {
				print(rs.getString("prenoms"));
				Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
			}
			final JTable ListPersonnes=new JTable(model);
			ListPersonnes.setIntercellSpacing(new Dimension(0,0));
			ListPersonnes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ListPersonnes.getTableHeader().setReorderingAllowed(false);
			ListPersonnes.setDefaultEditor(Object.class, null);
			Cursor pointerCursor = new Cursor(Cursor.HAND_CURSOR);
			ListPannel.setLayout(null);
			ListPersonnes.setCursor(pointerCursor);
			
			JScrollPane scrollpane=new JScrollPane(ListPersonnes);
			scrollpane.setBounds(10, 5, 924, 214);
			scrollpane.setBackground(Color.white);
			scrollpane.setBorder(new EmptyBorder(0,0,0,0));
			ListPannel.add(scrollpane);
			
		}catch(Exception error) {
			System.out.println(error.getMessage());
		}
		
	}
}