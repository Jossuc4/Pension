package Backend;

import java.io.*;


public class idGenerator {
	private static int idTarif=0,idPersonne=0,idConjoint=0;
	
	private String readFile(String filename) {
		String line="";
		BufferedReader br=null;
		File fichier=new File(filename);
		FileReader filereader;
		try {
			filereader = new FileReader(fichier);
			br= new BufferedReader(filereader);
			line=br.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return line;
		
	}
	private void editFile(String filename,int newValue) {
		try (FileWriter fw = new FileWriter(filename, false);
	             BufferedWriter bw = new BufferedWriter(fw)) {
	            bw.write(String.valueOf(newValue));

	           // System.out.println("Le fichier a été mis à jour avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public String newTarifId() {
		String idFile=readFile("fichierTarif.txt");
		int numero=Integer.parseInt(idFile);
		numero=numero+1;
		
		editFile("fichierTarif.txt",numero);
		
		return "T"+numero/100+""+numero/10+""+numero;
	}
	
}
