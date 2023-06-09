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

public class FenetreGestionPension extends JFrame {

	private JPanel contentPane;
	private JTable table_1;

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
		contentPane.setLayout(new BorderLayout(0,10));
		
		JPanel bigTitle = new JPanel();
		bigTitle.setBackground(new Color(255, 255, 255));
		contentPane.add(bigTitle);
		
		JPanel navigation = new JPanel();
		navigation.setBackground(new Color(255, 255, 255));
		contentPane.add(navigation);
		navigation.setLayout(null);
		
		JPanel toolBar = new JPanel();
		toolBar.setBackground(new Color(255, 255, 255));
		contentPane.add(toolBar);
		toolBar.setLayout(null);
		
		JButton btnAddButton = new JButton("Ajouter");
		
		btnAddButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnAddButton.setForeground(new Color(0, 0, 0));
		btnAddButton.setBackground(new Color(192, 192, 192));
		btnAddButton.setBounds(10, 11, 89, 23);
		toolBar.add(btnAddButton);
		
		JButton btnFindButton = new JButton("Chercher");
		
		btnFindButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnFindButton.setForeground(new Color(0, 0, 0));
		btnFindButton.setBackground(new Color(192, 192, 192));
		btnFindButton.setBounds(164, 11, 89, 23);
		toolBar.add(btnFindButton);
		
		JPanel ListPanel = new JPanel();
		contentPane.add(ListPanel);
		
		JLabel ListIm = new JLabel("Immatriculation");
		
		JLabel ListNom = new JLabel("Nom");
		
		JLabel ListPrenoms = new JLabel("Prénoms");
		
		JLabel ListDatenais = new JLabel("Date de naissance");
		
		table_1 = new JTable();
		table_1.setToolTipText("Nom");
		table_1.setBorder(null);
		GroupLayout gl_ListPanel = new GroupLayout(ListPanel);
		gl_ListPanel.setHorizontalGroup(
			gl_ListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ListPanel.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_ListPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(table_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_ListPanel.createSequentialGroup()
							.addComponent(ListIm)
							.addGap(18)
							.addComponent(ListNom)
							.addGap(18)
							.addComponent(ListPrenoms)
							.addGap(18)
							.addComponent(ListDatenais)))
					.addGap(255))
		);
		gl_ListPanel.setVerticalGroup(
			gl_ListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ListPanel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_ListPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(ListIm)
						.addComponent(ListNom)
						.addComponent(ListPrenoms)
						.addComponent(ListDatenais))
					.addGap(29)
					.addComponent(table_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(110, Short.MAX_VALUE))
		);
		ListPanel.setLayout(gl_ListPanel);
		
		/*-----------------------------------Events----------------------------*/
		
		
		//btnAdd clicked
		btnAddButton.addMouseListener(new MouseAdapter() {
			private boolean btnAddClicked=false;
			//Add event on the pension button
			public void mouseClicked(MouseEvent e) {
				if(!btnAddClicked) {
					btnAddClicked=true;
				}else {
					btnAddClicked=false;
				}
				btnAddButton.setBackground(new Color(0, 64, 128));
				btnFindButton.setBackground(new Color(192, 192, 192));
				
				DatabaseConnect postgresDB=new DatabaseConnect();
				Connection connect=postgresDB.connect();
				Object[][] dataSet=new Object [][]{{}};
				int x=0;
				
				try {
					Statement query=connect.createStatement();
					ResultSet data= query.executeQuery("SELECT * FROM personne");
			        String[] columns = new String[] {
			                "Id", "Nom", "Adresse", "Taux horaire", "A temps partiel"
			            };
			             
			            //données pour JTable dans un tableau 2D
			            Object[][] dataFetched = new Object[][] {
			                {1, "Thomas", "Paris", 20.0, true },
			                {2, "Jean", "Marseille", 50.0, false },
			                {3, "Yohan", "Lyon", 30.0, true },
			                {4, "Emily", "Toulouse", 60.0, false },
			                {5, "Alex", "Nice", 10.0, false },
			            };
			            JTable table=new JTable(dataFetched,columns);
			            ListPanel.add(table);
					while(data.next()) {
						
						JLabel nom=new JLabel("Hey");
						nom.setBackground(Color.yellow);
						nom.setBounds(330, 23, 93, 14);
						nom.setForeground(Color.BLUE);
						ListPanel.add(nom);
						System.out.println("Immatriculation: "+data.getString("IM")+"\n"+data.getString("datenais"));
					}
				}catch(Exception error) {
					System.out.println(error.getMessage());
				}
				
				
			}
			
			
		});
		
		//btnFind clicked
		btnFindButton.addMouseListener(new MouseAdapter() {
			private boolean btnFindClicked=false; 
			public void mouseClicked(MouseEvent e) {
				if(!btnFindClicked) {
					btnFindClicked=true;
				}else{
					btnFindClicked=false;
				}
				btnFindButton.setBackground(new Color(0, 64, 128));
				btnAddButton.setBackground(new Color(192, 192, 192));
			}
		});
	}
}
