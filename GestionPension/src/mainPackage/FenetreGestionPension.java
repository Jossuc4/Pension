package mainPackage;

import java.awt.EventQueue;
import java.sql.*;
import Backend.DatabaseConnect;
import Backend.idGenerator;

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
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Cursor;
import javax.swing.JEditorPane;

public class FenetreGestionPension extends JFrame {

	private JPanel contentPane;
	private JTextField createCateg;
	private JTextField createMontant;
	private JTextField tarifMontantModif;
	private JTextField tarifCatModif;
	private JTable listeTarifs;

	
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
		tabbedPane.setBackground(new Color(25, 164, 90));
		
		contentPane.add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel Tarif = new JPanel();
		Tarif.setToolTipText("");
		Tarif.setName("");
		Tarif.setPreferredSize(new Dimension(1000, 500));
		Tarif.setMinimumSize(new Dimension(20, 10));
		Tarif.setBackground(Color.WHITE);
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
		createCateg.setBounds(34, 131, 300, 20);
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
		createMontant.setBounds(34, 175, 300, 20);
		panel.add(createMontant);
		
		JButton btnValidTarif = new JButton("Valider");
		btnValidTarif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		
		btnValidTarif.setBounds(245, 229, 89, 23);
		panel.add(btnValidTarif);
		
		JComboBox comboCreate = new JComboBox();
		
		comboCreate.setModel(new DefaultComboBoxModel(new String[] {"CEPE", "BEPC", "BACCALAUREAT", "LICENCE", "MASTER", "DOCTORAT"}));
		comboCreate.setBounds(140, 84, 194, 22);
		panel.add(comboCreate);
		
		JLabel comboDiplTarif = new JLabel("Diplôme: ");
		comboDiplTarif.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboDiplTarif.setBounds(34, 84, 83, 22);
		panel.add(comboDiplTarif);
		
		JButton btnModiftarif = new JButton("Modifier");
		btnModiftarif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnModiftarif.setBounds(245, 479, 89, 23);
		panel.add(btnModiftarif);
		
		tarifMontantModif = new JTextField();
		tarifMontantModif.setText("Montant");
		tarifMontantModif.setColumns(10);
		tarifMontantModif.setBounds(34, 448, 300, 20);
		panel.add(tarifMontantModif);
		
		tarifCatModif = new JTextField();
		tarifCatModif.setText("Catégorie");
		tarifCatModif.setColumns(10);
		tarifCatModif.setBounds(34, 401, 300, 20);
		panel.add(tarifCatModif);
		
		JComboBox comboModif = new JComboBox();
		comboModif.setModel(new DefaultComboBoxModel(new String[] {"CEPE", "BEPC", "BACCALAUREAT", "LICENCE", "MASTER", "DOCTORAT"}));
		comboModif.setBounds(118, 354, 216, 22);
		panel.add(comboModif);
		
		JLabel comboDiplTarif_1 = new JLabel("Diplôme: ");
		comboDiplTarif_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboDiplTarif_1.setBounds(34, 354, 83, 22);
		panel.add(comboDiplTarif_1);
		
		JLabel lblModification = new JLabel("MODIFICATION");
		lblModification.setFont(new Font("Segoe UI Symbol", Font.BOLD | Font.ITALIC, 25));
		lblModification.setBounds(86, 279, 204, 41);
		panel.add(lblModification);
		
		JLabel labelNumTarif = new JLabel("Tarif n° : ");
		labelNumTarif.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelNumTarif.setBounds(34, 319, 83, 22);
		panel.add(labelNumTarif);
		
		JLabel numTarif = new JLabel("");
		numTarif.setFont(new Font("Tahoma", Font.BOLD, 15));
		numTarif.setBounds(133, 319, 83, 22);
		panel.add(numTarif);
		
		JButton btnDeleteTarif = new JButton("Supprimer");
		btnDeleteTarif.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnDeleteTarif.setBounds(34, 479, 101, 23);
		panel.add(btnDeleteTarif);
		
		
		JLabel tarifLabel = new JLabel("GESTION DE TARIFS");
		tarifLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD | Font.ITALIC, 25));
		tarifLabel.setForeground(new Color(0, 0, 0));
		tarifLabel.setBounds(433, 11, 250, 41);
		Tarif.add(tarifLabel);
		
		JPanel ListPanel = new JPanel();
		ListPanel.setBackground(new Color(255, 255, 255));
		ListPanel.setBounds(464, 59, 495, 538);
		Tarif.add(ListPanel);
		ListPanel.setLayout(new BorderLayout(0, 0));
		
		//-----------------Events
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			private int comboModifChanged=0;
			private boolean firstChange=false;
			public void stateChanged(ChangeEvent e) {
				
				if(!firstChange) {
					
					firstChange=true;
					
					if(tabbedPane.getSelectedIndex()==1) {
						
						try {
							print("ligne 306\nJ'ai commenté la collecte des diplomes déjà crées");
							
							Connection connection=connectDB();
							Statement query=connection.createStatement();
							ResultSet rs=query.executeQuery("SELECT * FROM public.tarif");
							ResultSetMetaData tarifs=rs.getMetaData();
							
							int columns=tarifs.getColumnCount();
							
							String [] tableHeader=new String[columns];
							
							DefaultTableModel model=new DefaultTableModel();
							
							int lignes=model.getRowCount();
							print(String.valueOf(lignes));
							
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
							
							JTable ListTable=new JTable(model);
							ListTable.setIntercellSpacing(new Dimension(0,0));
							ListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							ListTable.setDefaultEditor(Object.class, null);
							Cursor pointerCursor = new Cursor(Cursor.HAND_CURSOR);
							ListTable.setCursor(pointerCursor);
							
				print("new code line 357");
				print("creation createdDipl");
						
							int rowCount = model.getRowCount();
							
							String[] createdDipl=new String[rowCount+1];
							
							print("Nbre de lignes: "+rowCount);
							for (int row = 0; row < rowCount; row++) {
						        Object value = model.getValueAt(row, 1);
						        createdDipl[row]=(String)value;
						        System.out.println("Valeur à la position : " + value);
							}
							
							ListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								
								public void valueChanged(ListSelectionEvent e) {
									print("ligne 367");
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
									
									if((!categ.equals("Catégorie") && !categ.isEmpty()) && (!montant.equals("Catégorie") && !montant.isEmpty())) {
										
								if(!includedValue(createdDipl,diplome)) {
											
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
												}
											}catch(SQLException error) {
												print(error.getMessage());
											}
										}else {
											JOptionPane.showMessageDialog(ListPanel,"Le tarif pour le diplome "+diplome+" existe déjà\nVous pouvez le modifier en le cliquant");
										}
									}
									
								}
							});
							/**
							 * Modification d'un tarif
							 */
						
							
							btnModiftarif.addMouseListener(new MouseAdapter() {
								
								public void mouseClicked(MouseEvent e) {
									
									UIManager.put("OptionPane.yesButtonText", "Oui");
							        UIManager.put("OptionPane.noButtonText", "Non");
							        
							        Object modifDipl=comboModif.getSelectedItem();
							        
							        if(!sameRowValue(model,ListTable.getSelectedRow(),modifDipl)) {
							        	
							        
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
													
												}
											}catch(SQLException error) {
												print(error.getMessage());
											}
											
										}
							        }else {
							        	JOptionPane.showMessageDialog(ListPanel, "Le tarif pour le diplome "+modifDipl+" existe déjà");
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
							        
							        int choice = JOptionPane.showConfirmDialog(null, "Voulez-vous modifier ce tarif ?", "Modification d'un tarif", JOptionPane.YES_NO_OPTION);
									
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
												
											}
										}catch(Exception error) {
											print(error.getMessage());
										}
										
							        }

									
								}
								
							});
						}catch(Exception error) {
							print("C'est ici que ça se passe");
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
