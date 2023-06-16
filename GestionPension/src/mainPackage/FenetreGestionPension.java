package mainPackage;

import java.awt.EventQueue;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import Backend.DatabaseConnect;
import Backend.idGenerator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Cursor;
import javax.swing.JCheckBox;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FenetreGestionPension extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField createCateg;
	private JTextField createMontant;
	private JTextField tarifMontantModif;
	private JTextField tarifCatModif;
	private JTextField newDipl;

	
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
	
	/**
	 * 
	 * @param valeurs
	 * @param comp
	 * @return count
	 */
	public int occurence(String[]valeurs,String comp) {
		int count=0;
		for (String valeur : valeurs) {
            if (comp.equals(valeur)) {
                count++;
            }
        }
		return count;
	}
	

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
	 * Return true  if the value is fund in the table
	 * @param table
	 * @param value
	 * @return found
	 */
	public boolean includedValue(String[]table,String value) {
		boolean found=false;
		if(table.length>0) {
			for (String i : table) {
				if(!(i==null) && i.equals(value)) {
					found=true;
				}
			}
		}
		return found;
	}

	/**
	 * Vérification de valeur pour éviter les tarifs de même diplome après modifcation
	 * @param model
	 * @param selectedRow
	 * @param newComboValue
	 * @return boolean found
	 */
	public boolean sameRowValue(DefaultTableModel model,int selectedRow,Object newComboValue) {
		boolean found=false;
		String cible=(String)newComboValue;
		
		for(int i=0;i<model.getRowCount();i++) {
			
			if(i!=selectedRow) {
				print("test n°"+i);
				String valeur=(String)model.getValueAt(i, 1);
				if(cible.equals(valeur)) {
					print(cible +" and "+valeur);
					found=true;
				}
			}
			
			
		}
		return found;
	}
	/**
	 * obtenir toutes les diplomes
	 * @return ArrayList<String>
	 * @throws SQLException
	 */
	public ArrayList<String> getAllDiplome() throws SQLException {
		Connection connection=connectDB();
		Statement query=connection.createStatement();
		ResultSet rs=query.executeQuery("SELECT diplome FROM public.tarif");
		ArrayList<String> res=new ArrayList<String>();
		while(rs.next()) {
			if(!res.contains(rs.getString("diplome"))) {
				res.add(rs.getString("diplome"));
			}
		}
		print("----all diplomes from db:"+res.toString());
		return res;
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public FenetreGestionPension() throws SQLException {
		setTitle("Pension pour tous");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 30, 1020, 696);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(25, 164, 90));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFocusCycleRoot(true);
		tabbedPane.setBackground(new Color(25, 164, 90));
		
		contentPane.add(tabbedPane);
		
		JPanel HomePanel = new JPanel();
		tabbedPane.addTab("Accueil", null, HomePanel, null);
		
		JPanel Tarif = new JPanel();
		Tarif.setToolTipText("");
		Tarif.setName("");
		Tarif.setPreferredSize(new Dimension(1000, 500));
		Tarif.setMinimumSize(new Dimension(20, 10));
		Tarif.setBackground(new Color(25, 164, 90));
		tabbedPane.addTab("Tarif", null, Tarif, null);
		Tarif.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setBounds(25, 59, 386, 538);
		Tarif.add(panel);
		panel.setLayout(null);
		
		JLabel formTitleLabel = new JLabel("CREATION");
		formTitleLabel.setFont(new Font("Dialog", Font.PLAIN, 25));
		formTitleLabel.setBounds(115, 0, 140, 37);
		panel.add(formTitleLabel);
		
		createCateg = new JTextField();
		createCateg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(createCateg.getText().equals("Catégorie"))
					createCateg.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(createCateg.getText().isEmpty()) {
					createCateg.setText("Catégorie");
				}
			}
		});
		createCateg.setText("Catégorie");
		createCateg.setColumns(10);
		createCateg.setBounds(34, 143, 300, 20);
		panel.add(createCateg);
		
		createMontant = new JTextField();
		createMontant.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(createMontant.getText().equals("Montant"))
					createMontant.setText("");
			}
			public void focusLost(FocusEvent e) {
				if(createMontant.getText().isEmpty()) {
					createMontant.setText("Montant");
				}
			}
		});
		createMontant.setText("Montant");
		createMontant.setColumns(10);
		createMontant.setBounds(34, 187, 300, 20);
		panel.add(createMontant);
		
		final JButton btnValidTarif = new JButton("Valider");
		btnValidTarif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		
		btnValidTarif.setBounds(245, 229, 89, 23);
		panel.add(btnValidTarif);
		
		JPanel newDiplForm = new JPanel();
		newDiplForm.setBounds(34, 41, 342, 22);
		panel.add(newDiplForm);
		newDiplForm.setLayout(null);
		
		
		JLabel comboDiplTarif_2 = new JLabel("Nouveau Diplôme: ");
		comboDiplTarif_2.setBounds(0, 0, 138, 22);
		newDiplForm.add(comboDiplTarif_2);
		comboDiplTarif_2.setFont(new Font("Dialog", Font.PLAIN, 15));
		
		newDipl = new JTextField();
		newDipl.setBounds(156, 2, 186, 20);
		newDiplForm.add(newDipl);
		newDipl.setColumns(10);
		newDipl.setEnabled(rootPaneCheckingEnabled);
		
		JPanel oldDiplForm = new JPanel();
		oldDiplForm.setBounds(34, 75, 300, 22);
		panel.add(oldDiplForm);
		oldDiplForm.setLayout(null);
		
		final JComboBox<String> comboCreate = new JComboBox<String>();
		comboCreate.setBounds(106, 0, 194, 22);
		oldDiplForm.add(comboCreate);
		final ArrayList<String> defaultDipls=new ArrayList<String>(Arrays.asList("CEPE", "BEPC", "BACCALAUREAT", "LICENCE", "MASTER", "DOCTORAT"));
		ArrayList<String> diplFromDB=getAllDiplome();
		for(int i=0;i<diplFromDB.size();i++) {
			if(!defaultDipls.contains(diplFromDB.get(i))) {
				defaultDipls.add(diplFromDB.get(i));
			}
		}
		comboCreate.setModel(new DefaultComboBoxModel(defaultDipls.toArray()));
		
		JLabel comboDiplTarif = new JLabel("Diplôme: ");
		comboDiplTarif.setBounds(0, 0, 83, 22);
		oldDiplForm.add(comboDiplTarif);
		comboDiplTarif.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		final JButton btnModiftarif = new JButton("Modifier");
		btnModiftarif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnModiftarif.setBounds(213, 479, 121, 23);
		panel.add(btnModiftarif);
		
		final JPanel modif = new JPanel();
		modif.setBounds(34, 279, 300, 189);
		panel.add(modif);
		modif.setLayout(null);
		modif.setVisible(false);
		
		tarifMontantModif = new JTextField();
		tarifMontantModif.setBounds(0, 169, 300, 20);
		modif.add(tarifMontantModif);
		tarifMontantModif.setText("Montant");
		tarifMontantModif.setColumns(10);
		
		tarifCatModif = new JTextField();
		tarifCatModif.setBounds(0, 122, 300, 20);
		modif.add(tarifCatModif);
		tarifCatModif.setText("Catégorie");
		tarifCatModif.setColumns(10);
		
		final JComboBox<String> comboModif = new JComboBox<String>();
		comboModif.setBounds(84, 75, 216, 22);
		modif.add(comboModif);
		comboModif.setModel(new DefaultComboBoxModel(defaultDipls.toArray()));
		
		JLabel comboDiplTarif_1 = new JLabel("Diplôme: ");
		comboDiplTarif_1.setBounds(0, 75, 83, 22);
		modif.add(comboDiplTarif_1);
		comboDiplTarif_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblModification = new JLabel("MODIFICATION");
		lblModification.setBounds(52, 0, 204, 41);
		modif.add(lblModification);
		lblModification.setFont(new Font("Dialog", Font.PLAIN, 25));
		
		JLabel labelNumTarif = new JLabel("Tarif n° : ");
		labelNumTarif.setBounds(0, 40, 83, 22);
		modif.add(labelNumTarif);
		labelNumTarif.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		final JLabel numTarif = new JLabel("");
		numTarif.setBounds(79, 40, 83, 22);
		modif.add(numTarif);
		numTarif.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		final JButton btnDeleteTarif = new JButton("Supprimer");
		btnDeleteTarif.setForeground(new Color(255, 255, 255));
		btnDeleteTarif.setBackground(new Color(246, 97, 81));
		btnDeleteTarif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnDeleteTarif.setBounds(34, 479, 127, 23);
		panel.add(btnDeleteTarif);
		
		final JCheckBox chckNewDipl = new JCheckBox("Ajouter un nouveu diplome");
		chckNewDipl.setBounds(34, 112, 321, 23);
		panel.add(chckNewDipl);
		
		
		JLabel tarifLabel = new JLabel("GESTION DES TARIFS");
		tarifLabel.setFont(new Font("Dialog", Font.PLAIN, 25));
		tarifLabel.setForeground(new Color(0, 0, 0));
		tarifLabel.setBounds(433, 11, 280, 41);
		Tarif.add(tarifLabel);
		
		final JPanel ListPanel = new JPanel();
		ListPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		ListPanel.setBackground(new Color(255, 255, 255));
		ListPanel.setBounds(464, 59, 495, 538);
		Tarif.add(ListPanel);
		ListPanel.setLayout(new BorderLayout(0, 0));
		
		
		print("ligne 376: ajout de le fenêtre GestionPersonnes");
		GestionPersonnes gprs=new GestionPersonnes();
		tabbedPane.addTab("Personnes", null, gprs, null);
		
		//-----------------Events
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			private int comboModifChanged=0;
			private boolean firstChange=false;
			public void stateChanged(ChangeEvent e) {
				
				if(!firstChange) {
					firstChange=true;
					
					if(tabbedPane.getSelectedIndex()==1) {
						print("Voilà");
						try {
							print("ligne 306\nJ'ai commenté la collecte des diplomes déjà crées");
							
							Connection connection=connectDB();
							Statement query=connection.createStatement();
							ResultSet rs=query.executeQuery("SELECT * FROM public.tarif");
							ResultSetMetaData tarifs=rs.getMetaData();
							
							final int columns=tarifs.getColumnCount();
							
							String [] tableHeader=new String[columns];
							
							final DefaultTableModel model=new DefaultTableModel();
							
							int lignes=model.getRowCount();
							for(int i=0;i<columns;i++) {
								tableHeader[i]=tarifs.getColumnName(i+1);
							}
							model.setColumnIdentifiers(tableHeader);
							
							/**
							 * Collecte des diplomes déjà crées
							 * 
							 */
						
							while (rs.next()) {
				                Object[] rowData = new Object[columns];
				                for (int i = 1; i <= columns; i++) {
				                    rowData[i - 1] = rs.getObject(i);
				                }
				                model.addRow(rowData);
				            }
							
							final JTable ListTable=new JTable(model);
							ListTable.setIntercellSpacing(new Dimension(0,0));
							ListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							ListTable.getTableHeader().setReorderingAllowed(false);
							ListTable.setDefaultEditor(Object.class, null);
							Cursor pointerCursor = new Cursor(Cursor.HAND_CURSOR);
							ListTable.setCursor(pointerCursor);
							
				print("new code line 357");
				print("creation createdDipl");
						
							int rowCount = model.getRowCount();
							
							final String[] createdDipl=new String[rowCount+1];
							for (int row = 0; row < rowCount; row++) {
						        Object value = model.getValueAt(row, 1);
						        createdDipl[row]=(String)value;
						        System.out.println("Valeur à la position : " + value);
							}
							
							/**
							 * Selectionner une ligne
							 */
							ListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								
								public void valueChanged(ListSelectionEvent e) {
									
					                if (!e.getValueIsAdjusting()) {
					                	
					                    int selectedRow = ListTable.getSelectedRow();
					                    
					                    if (selectedRow != -1) {

					                    	String[] selectedValue=new String[columns];

					                    	for(int i=0;i<columns;i++) {
					                    		
												selectedValue[i]=ListTable.getValueAt(selectedRow, i).toString();

											}
					                    	
					                        tarifMontantModif.setText(selectedValue[3]);
					                        tarifCatModif.setText(selectedValue[2]);
					                        comboModif.setSelectedItem(selectedValue[1]);
					                        numTarif.setText(selectedValue[0]);
					                        modif.setVisible(false);
											btnModiftarif.setText("modifier");
					                      
					                    }
					                }
					            }
					        });
							
							JScrollPane scrollpane=new JScrollPane(ListTable);
							scrollpane.setBackground(Color.white);
							scrollpane.setBorder(new EmptyBorder(0,0,0,0));
							ListPanel.add(scrollpane);
							
							/**
							 * Création d'un tarif
							 */
							
							btnValidTarif.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									String diplome=(String)comboCreate.getSelectedItem(),categ=createCateg.getText(),montant = createMontant.getText();
									if(chckNewDipl.isSelected()) {
										diplome=newDipl.getText().toUpperCase();
									}
									
									if((!categ.equals("Catégorie") && !categ.isEmpty()) && (!montant.equals("Catégorie") && !montant.isEmpty())) {
													
											try {
												
												Connection c=connectDB();
												PreparedStatement query=c.prepareStatement("INSERT INTO  public.tarif VALUES(?,?,?,?)");
												
												idGenerator id=new idGenerator();
												String newTarifId=id.newTarifId();
												
												query.setString(1,newTarifId);
												query.setString(2, diplome);
												query.setString(3, categ);
												query.setInt(4,Integer.parseInt(montant));
		
												
												int rowsAffected=query.executeUpdate();
												
												if(rowsAffected==1) {
													model.addRow(new Object[]{newTarifId, diplome, categ,Integer.parseInt(montant)});
													JOptionPane.showMessageDialog(new JLabel("label"),"Insertion réussie");
													createCateg.setText("Catégorie");
													createMontant.setText("Montant");
													
													modif.setVisible(false);
													btnModiftarif.setText("modifier");
													newDipl.setText("");
													
													if(chckNewDipl.isSelected()) {
														if(!defaultDipls.contains(diplome))defaultDipls.add(diplome);
														
														comboCreate.setModel(new DefaultComboBoxModel(defaultDipls.toArray()));
														comboModif.setModel(new DefaultComboBoxModel(defaultDipls.toArray()));
													}
									
												}
											}catch(SQLException error) {
												print(error.getMessage());
											}
										
									}
									
								}
							});
							/**
							 * Modification d'un tarif
							 */
						
							
							btnModiftarif.addMouseListener(new MouseAdapter() {
								
								public void mouseClicked(MouseEvent e) {
									
								if(btnModiftarif.getText().equalsIgnoreCase("confirmer")) {
									UIManager.put("OptionPane.yesButtonText", "Oui");
							        UIManager.put("OptionPane.noButtonText", "Non");
							        
							        Object modifDipl=comboModif.getSelectedItem();
							  
							       		int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous modifier ce tarif ?", "Modification d'un tarif", JOptionPane.YES_NO_OPTION);
										
								        if (choice == JOptionPane.YES_OPTION) {
											String diplModif="",tarifModif="",numTarModif="";
											int montantModif=0;
											if(!tarifCatModif.getText().equals("Catégorie") && !tarifMontantModif.getText().equals("Montant")) {
												 diplModif=(String)comboModif.getSelectedItem();
												 tarifModif=tarifCatModif.getText();
												 montantModif=Integer.parseInt(tarifMontantModif.getText());
												 numTarModif=numTarif.getText();
											}else {
												JOptionPane.showMessageDialog(ListPanel,"Vous devez choisir le tarif sur la table");
											}
												
											try {
												Connection c=connectDB();
												PreparedStatement query=c.prepareStatement("UPDATE public.tarif SET diplome=?, categorie=?, montant=? WHERE num_tarif=?");
												
												query.setString(1, diplModif);
												query.setString(2, tarifModif);
												query.setInt(3,montantModif);
												query.setString(4, numTarModif);
												
												int rowsAffected=query.executeUpdate();
												
												if(rowsAffected==1) {
													
													model.setValueAt(diplModif,ListTable.getSelectedRow(),1);
													model.setValueAt(tarifModif,ListTable.getSelectedRow(),2);
													model.setValueAt(montantModif,ListTable.getSelectedRow(),3);
													
													JOptionPane.showMessageDialog(ListPanel,"Modification réussie");
													
													tarifCatModif.setText("Catégorie");
													tarifMontantModif.setText("Montant");
													numTarif.setText("");
													ListTable.clearSelection();
													
													modif.setVisible(false);
													btnModiftarif.setText("modifier");
												}
											}catch(SQLException error) {
												print(error.getMessage());
											}
											
										}
							        
								}else if(btnModiftarif.getText().equalsIgnoreCase("modifier")){
									modif.setVisible(true);
									btnModiftarif.setText("confirmer");
								}else {
									print("error on line 546");
								}
									
							    }
							});
							
							/**
							 * Suppression d'un tarif
							*/
							btnDeleteTarif.addMouseListener(new MouseAdapter() {
								
								public void mouseClicked(MouseEvent e) {
									
									UIManager.put("OptionPane.yesButtonText", "Oui");
							        UIManager.put("OptionPane.noButtonText", "Non");
							        
							        int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer ce tarif ?", "Suppression d'un tarif", JOptionPane.YES_NO_OPTION);
									
							        if (choice == JOptionPane.YES_OPTION) {
							        	
										String numTarModif="";
										
										if(!tarifCatModif.getText().equals("Catégorie") && !tarifMontantModif.getText().equals("Montant")) {
											 numTarModif=numTarif.getText();
											 
										}else {
											
											JOptionPane.showMessageDialog(new JLabel("label"),"Vous devez choisir le tarif sur la table");
										
										}
										try {
											Connection cnx=connectDB();
											PreparedStatement query= cnx.prepareStatement("DELETE FROM public.tarif WHERE num_tarif=?");
											
											query.setString(1, numTarModif);
											
											int rowsAffected=query.executeUpdate();
											
											if(rowsAffected==1) {
												model.removeRow(ListTable.getSelectedRow());
												JOptionPane.showMessageDialog(ListPanel, "Suppression réussie");
												
												tarifCatModif.setText("Catégorie");
												tarifMontantModif.setText("Montant");
												numTarif.setText("");
												
												modif.setVisible(false);
												btnModiftarif.setText("modifier");
											}
											
											
										}catch(Exception error) {
											print(error.getMessage());
										}
										
							        }

									
								}
												
							});
						
							/**
							 * CheckBox new dipl
							 */
							chckNewDipl.addChangeListener(new ChangeListener() {
								public void stateChanged(ChangeEvent e) {
									if(chckNewDipl.isSelected()) {
										newDipl.setEnabled(true);
										comboCreate.setEnabled(false);
									}else {
										newDipl.setEnabled(false);
										comboCreate.setEnabled(true);
									}
								}
							});
						
						}catch(Exception error) {
							print(error.getMessage());
							for (StackTraceElement element : error.getStackTrace()) {
						        print(element.toString() + "\n");
						    }
							
						}
					}
				}
				
			}
			
		});
		
	}
}
