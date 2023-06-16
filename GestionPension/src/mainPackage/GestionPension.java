package mainPackage;
import javax.swing.*;
import java.awt.BorderLayout;

public class GestionPension extends JFrame {
	public GestionPension() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 5, 642, 310);
		panel.add(tabbedPane_1);
		
		JTabbedPane AccueilOnglet = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addTab("Accueil", null, AccueilOnglet, null);
		
		JPanel CreateOnglet = new JPanel();
		tabbedPane_1.addTab("Ajouter", null, CreateOnglet, null);
		
		JPanel ListeOnglet = new JPanel();
		tabbedPane_1.addTab("Liste", null, ListeOnglet, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_3, null);
	}
	
	
	public static void main(String[] args) {
		JFrame mainWindow=new JFrame("Pension pour tous");
		mainWindow.setVisible(true);
		// TODO Auto-generated method stub

	}
	
}
