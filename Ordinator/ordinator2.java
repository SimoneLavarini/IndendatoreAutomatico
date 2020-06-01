/*

    CREDITS: Simone Lavarini

*/

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ordinator2 {

    public static void main(String[] args) throws IOException {

        System.out.print("Inserisci il nome del file che vuoi sistemare (estensione compresa): ");
        InputStreamReader reader1 = new InputStreamReader (System.in);
		BufferedReader myInput = new BufferedReader (reader1);
        String nome1= new String();
        
        try {
			
			nome1 = myInput.readLine();
		
		} catch (IOException e) {
		
			System.out.println ("Si è verificato un errore: " + e);
			System.exit(-1); 
        }

        System.out.print("Inserisci il nome del file di output (estensione compresa): ");
        InputStreamReader reader2 = new InputStreamReader (System.in);
		BufferedReader myInput2 = new BufferedReader (reader2);
        String nome2= new String();
        
        try {
			
			nome2 = myInput2.readLine();
		
		} catch (IOException e) {
		
			System.out.println ("Si è verificato un errore: " + e);
			System.exit(-1); 
        }

        String path = "file/" + nome2; //file di output, se si vuole cambiare nome all'output basta farlo da qui
        String path2 = "file/" + nome1;  //file di input
        String apostrofo = "'";
        File file = new File(path);

        try { // funzione che controlla se il file di output esiste gia oppure deve essere creato
            if (file.exists())
            System.out.println("Il file " + path + " esiste");
            else if (file.createNewFile())
            System.out.println("Il file " + path + " è stato creato");
            else
            System.out.println("Il file " + path + " non può essere creato");
            } catch (IOException e) {
            e.printStackTrace();
        }

        String testfinal = ""; //corpo del file finale che verrà dato in input
        BufferedReader reader = new BufferedReader(new FileReader(path2)); //serve per leggere le rige del file 
        String line = reader.readLine(); //ogni riga viene estratte e salvata in questa variabile

        char[] tipi = {'=', '+', '-', '!', '/', '*', ' ', '<', '>'}; //caratteri da distanziare all'occorrenza

        while(line!= null){ //continua finchè non finisce il file
        
            String testo = line + "  "; //contenuto della riga che stiamo analizzando con uno spazio alla fine per eventuali errori di "index out of range"
            boolean str = false; //booleano che indica la condizione del puntatore (true = ci troviamo in una stringa e non bisognerà aggiungere spazi)
            boolean carr = false; //booleano che indica la condizione del puntatore (true = ci troviamo in un char e non bisognerà aggiungere spazi)


            for (int i = 0; i<testo.length(); i++){ //controlliamo ogni carattere della stringa uno alla volta

                if(testo.charAt(i) == '"' && str == false){ //se il carattere è " e non siamo in una stringa significa che è l'inizio di una stringa
                    str = true; //imposta lo stato di stringa a true
                }

                else if(testo.charAt(i) == '"' && str == true){ //se il carattere è " e siamo in una stringa significa che è la fina della stringa
                    str = false; //imposta lo stato di stringa a false
                    testfinal += testo.charAt(i); //aggiunge " alla stringa senza spazi
                    i++; //passa subito a controllare il carattere successivo
                }

                if(testo.charAt(i) == apostrofo.charAt(0) && carr == false){ //se il carattere è ' e non siamo in un char significa che è l'inizio di un char
                    carr = true; //imposta lo stato di carattere a true
                }

                else if(testo.charAt(1) == apostrofo.charAt(0)){ //se il carattere è ' e siamo in un char significa che è la fine del char
                    carr = false; //imposto lo stato di carattere a false
                    testfinal += testo.charAt(i); //aggiungo ' alla stringa senza spazi
                    i++; //passa subito a controllare il carattere successivo
                }

                if(str == true || carr == true){ //se siamo in una stringa o in un char bisognera copiare il carattere e basta
                    testfinal += testo.charAt(i); //aggiunge " alla stringa senza spazi
                }

                else if(str == false && carr == false){ // se non siamo in una stringa o in un char bisogna controllare l'indendazione
                    char carattere = ' '; // crea un carattere vuoto in cui verrà inserito il carattere che abbiamo in questo momento
                    
                    for (char c : tipi){
                        if(testo.charAt(i) == c){
                            carattere = c; //se trova che il carattere è uguale a uno degli elementi che devo controllare lo salva
                            break;
                        } 
                    }

                    if(carattere == ' '){ //se il carattere non è uno di quelli da controllare allora procede
                        testfinal += testo.charAt(i); //aggiunge il carattere alla stringa senza spazi
                    }

                    else{

                        char prec = testo.charAt(i-1); //salva il carattere precedente
                        char succ = testo.charAt(i+1); //salva il carattere successivo
                        boolean precin = false; //indica se il carattere è uno di quelli da controllare
                        boolean succin = false; //indica se il carattere è uno di quelli da controllare

                        for (char c : tipi){
                            if(c == prec) precin = true; //se il carattere precendente è uno di quelli da controllare mette precin a true
                            if(c == succ) succin = true; //se il carattere precendente è uno di quelli da controllare mette succin a true
                        }

                        if(succin == true && precin == true){ //se sono entrambi caratteri speciali
                            testfinal += testo.charAt(i); //aggiunge semplicemente il carattere
                        }

                        else if(precin == true){ //se solo il precedente è speciale
                            testfinal += testo.charAt(i) + " "; //aggiunge lo spazio dopo
                        }

                        else if(succin == true){ //se solo il successivo è speciale
                            testfinal += " " + testo.charAt(i); //aggiunge lo spazio prima
                        }

                        else {
                            testfinal += " " + testo.charAt(i) + " ";
                        }

                    }
                }

            }

            testfinal += "\n";            

            line = reader.readLine();

        }

        reader.close();

        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(testfinal);
            bw.flush();
            bw.close();
            System.out.println("Il file ordinato.java è stato creato, si prega di prestare attenzione a rinominarlo con il nome della classe");
        }catch(IOException e){
            e.printStackTrace();
        }
        

    }

}