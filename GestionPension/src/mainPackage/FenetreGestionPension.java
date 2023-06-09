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
import javax.swing.UIManager;
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

public class FenetreGestionPension extends JFrame {

	private JPanel contentPane;
	private JTextField createCateg;
	private JTextField createMontant;
	private JTextField tarifMontantModif;
	private JTextField tarifCatModif;
	private JTable listeTarifs;
	private int num_tarif=3;
	
	
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
		
		createCateg = new JTextField();
		createCateg.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
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
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==1) {
					
					try {
						Connection connection=connectDB();
						Statement query=connection.createStatement();
						ResultSet rs=query.executeQuery("SELECT * FROM public.tarif");
						ResultSetMetaData tarifs=rs.getMetaData();
						
						int columns=tarifs.getColumnCount();
						String [] tableHeader=new String[columns];
						
						DefaultTableModel model=new DefaultTableModel();
						
						for(int i=0;i<columns;i++) {
							tableHeader[i]=tarifs.getColumnName(i+1);
							//print(tableHeader[i]);
						}
						model.setColumnIdentifiers(tableHeader);
					
						//String[]comboModifArray=(String [])comboModif.getSelectedObjects();
						//String[]comboCreateArray=(String [])comboCreate.getSelectedObjects();
						
						while (rs.next()) {
							//if(occurence(comboModifArray,rs.getString("diplome"))!=0) {
								comboModif.addItem(rs.getString("diplome"));
							//}if(occurence(comboCreateArray,rs.getString("diplome"))!=0) {
								comboCreate.addItem(rs.getString("diplome"));
							//}
							
							
			                Object[] rowData = new Object[columns];
			                for (int i = 1; i <= columns; i++) {
			                    rowData[i - 1] = rs.getObject(i);
			                }
			                model.addRow(rowData);
			            }
						
						
						JTable ListTable=new JTable(model);
						ListTable.setIntercellSpacing(new Dimension(0,0));
						
						ListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							
							public void valueChanged(ListSelectionEvent e) {
				                if (!e.getValueIsAdjusting()) {
				                    int selectedRow = ListTable.getSelectedRow();
				                    if (selectedRow != -1) {
				                        // Do something with the selected row data
				                    	String[] selectedValue=new String[columns];
				                    	//print("Selected Row Data: ");
				                    	for(int i=0;i<columns;i++) {
											selectedValue[i]=ListTable.getValueAt(selectedRow, i).toString();
											//print(selectedValue[i]);
										}
				                        tarifMontantModif.setText(selectedValue[3]);
				                        tarifCatModif.setText(selectedValue[2]);
				                        comboModif.setSelectedItem(selectedValue[1]);
				                        numTarif.setText(selectedValue[0]);
				                        
				                        Icon icon = UIManager.getIcon("OptionPane.deleteIcon");
				                        JButton btnDeleteTarif = new JButton(icon);
				                        btnDeleteTarif.setFocusPainted(false);
				                        btnDeleteTarif.setBackground(Color.white);
				                        btnDeleteTarif.setBorder(new EmptyBorder(0,0,0,0));
				                        
				                		btnDeleteTarif.setBounds(34, 479, 89, 30);
				                		panel.add(btnDeleteTarif);
				                    }
				                }
				            }
				        });
						
						JScrollPane scrollpane=new JScrollPane(ListTable);
						scrollpane.setBackground(Color.white);
						scrollpane.setBorder(new EmptyBorder(0,0,0,0));
						
						ListPanel.add(scrollpane);
					}catch(Exception error) {
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
				try {
					Connection c=connectDB();
					PreparedStatement query=c.prepareStatement("UPDATE public.tarif SET diplome=?, categorie=?, montant=? WHERE num_tarif=?");
					
					query.setString(1, (String)comboModif.getSelectedItem());
					query.setString(2, tarifCatModif.getText());
					query.setInt(3,Integer.parseInt(tarifMontantModif.getText()));
					query.setString(4, numTarif.getText());
					
					int rowsAffected=query.executeUpdate();
					
					if(rowsAffected==1) {
						print("Modification réussie");
						tabbedPane.repaint();
						
					}
				}catch(SQLException error) {
					print(error.getMessage());
				}
				
			}
		});
		
		
		/**
		 * Création d'un tarif
		 */
		
		btnValidTarif.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String diplome=(String)comboCreate.getSelectedItem(),categ=createCateg.getText(),montant = createMontant.getText();
				
				if((!categ.equals("Catégorie") && !categ.isEmpty()) && (!montant.equals("Catégorie") && !montant.isEmpty())) {
					try {
						Connection c=connectDB();
						PreparedStatement query=c.prepareStatement("INSERT INTO  public.tarif VALUES(?,?,?,?)");
						
						
						query.setString(1,"T"+num_tarif);
						query.setString(2, diplome);
						query.setString(3, categ);
						query.setInt(4,Integer.parseInt(montant));
						
						num_tarif++;
						
						int rowsAffected=query.executeUpdate();
						
						if(rowsAffected==1) {
							print("Insertion réussie");
						}
					}catch(SQLException error) {
						print(error.getMessage());
					}
				}
				
			}
		});
	}
}
