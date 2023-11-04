import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static ArrayList<String> segmenti = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Inserisci una funzione di X e proverò a trovarti la Primitiva!");
        Scanner sc = new Scanner(System.in);
        String equazione = sc.nextLine();
        String risultato = "";

        segmenti = segmenta(equazione);

        System.out.println("Segmentato: " + Arrays.toString(segmenti.toArray()));

        for (int i = 0; i < segmenti.size(); i++) {
            String segmento = riscrivi(segmenti.get(i));
            risultato += risolvi(segmento);
        }

        risultato += "+c";

        System.out.println();
        System.out.println("Risultato finale " + risultato);

    }

    static String risolvi(String equazione) {
        if (equazione.equals("+") || equazione.equals("-")) {
            return equazione;
        } else if(equazione.contains("x^(") && equazione.contains("/")){
            return risolviPotenzaFrazione(equazione);
        } else if (equazione.contains("x") && equazione.contains("/")) {
            return risolviFrazione(equazione);
        } else if (equazione.contains("x^")) {
            return risolviPotenza(equazione);
        } else if(!equazione.contains("x")) {
            return equazione + "x";
        }
        return "?";
    }

    static String risolviPotenza(String equazione) {
        System.out.println("C'è una potenza in " + equazione);
        String espressione = "";
        String riscrivi = "";
        int index = 0;
        boolean eq = false;
        while (index < equazione.length()) {
            if (equazione.charAt(index) == 'x' || eq) {
                espressione += equazione.charAt(index);
                eq = true;
            } else {
                riscrivi += equazione.charAt(index);
            }
            index++;
        }
        System.out.println("riscrivo: " + riscrivi + " risolvo " + espressione);
        System.out.println("alfa: " + espressione.substring(2));
        espressione = formulaPotenza(espressione, espressione.substring(2));
        System.out.println("ritorno " + riscrivi + espressione);
        return "(" + riscrivi + espressione + ")";
    }

    static String risolviFrazione(String equazione) {
        System.out.println("Ci sta una frazione");
        int index = 0;
        String espressione = "";
        String riscrivi = "";
        while (index < equazione.length()) {
            if (equazione.charAt(index) == 'x') {
                while (index < equazione.length() && equazione.charAt(index) != '/') {
                    espressione += equazione.charAt(index);
                    index++;
                }
                riscrivi += equazione.charAt(index);
            } else {
                riscrivi += equazione.charAt(index);
            }
            index++;
        }
        System.out.println("espressione: " + espressione + " riscrivi " + riscrivi);

        if (espressione.equals("x")) {
            espressione = formulaPotenza(espressione, "1");
        } else {
            espressione = formulaPotenza(espressione, espressione.substring(2));
        }
        System.out.println("ho risolto con " + riscrivi + "*" + espressione);

        return "(" + riscrivi + "*" + espressione + ")";
    }

    static String formulaPotenza(String eq, String alfa) {
        if(Integer.parseInt(alfa)!=-1){
            return "(x^" + String.valueOf((int) Integer.parseInt(alfa) + 1) + ")/" + String.valueOf((int) Integer.parseInt(alfa) + 1);
        } else {
            return "ln(x)";
        }
    }

    static String risolviPotenzaFrazione(String equazione){
        String numeratore = "";
        String denominatore = "";
        String riscrivi = "";

        for (int i = 0; i < equazione.length(); i++) {
            if(equazione.charAt(i) == '('){
                i++;
                while(equazione.charAt(i) != '/'){
                    numeratore += equazione.charAt(i);
                    i++;
                }
                i++;
                while(equazione.charAt(i) != ')'){
                    denominatore += equazione.charAt(i);
                    i++;
                }
                i++;
                while(i < equazione.length()){
                    riscrivi+=equazione.charAt(i);
                    i++;
                }
            }
        }
        System.out.println("risolvo: x^("+
                String.valueOf(Integer.parseInt(numeratore)+Integer.parseInt(denominatore))+"/"+denominatore+")"+
                "*"+
                denominatore+"/"+String.valueOf(Integer.parseInt(numeratore)+Integer.parseInt(denominatore))+
                riscrivi);
        return "(x^("+
                String.valueOf(Integer.parseInt(numeratore)+Integer.parseInt(denominatore))+"/"+denominatore+")"+
                "*"+
                denominatore+"/"+String.valueOf(Integer.parseInt(numeratore)+Integer.parseInt(denominatore))+
                riscrivi + ")";
    }
    static String riscrivi(String equazione){
        String riscritta = "";
        for (int i = 0; i < equazione.length(); i++) {
            if(equazione.charAt(i) == 'x' && i<equazione.length()-1 && equazione.charAt(i+1)!='^' || equazione.charAt(i) == 'x' && i==equazione.length()-1){
                riscritta=equazione.substring(0,i+1)+"^1"+equazione.substring(i+1);
                equazione = riscritta;
                System.out.println("ho riscritto " + riscritta);
            }
        }
        if(equazione.contains("sqrt(")){
            String radicando = "";
            String n = "";
            String r = "";
            String riporta = "";
            for (int i = 0; i < equazione.length(); i++) {
                if(equazione.charAt(i) == '('){
                    i++;
                    while(equazione.charAt(i) != '^'){
                        radicando += equazione.charAt(i);
                        i++;
                    }
                    i++;
                    while(equazione.charAt(i) != ','){
                        n += equazione.charAt(i);
                        i++;
                    }
                    i++;
                    while(equazione.charAt(i) != ')'){
                        r += equazione.charAt(i);
                        i++;
                    }
                    i++;
                    while(i < equazione.length()){
                        riporta+=equazione.charAt(i);
                        i++;
                    }
                }
            }
            System.out.println("radicando: "+ radicando + " n: " + n + " r: " + r);
            equazione = radicando + "^(" + n + "/" + r + ")" + riporta;
            System.out.println("Ho riscritto " + equazione);
         }
        return equazione;
    }
    static ArrayList<String> segmenta(String equazione) {
        int index = 0;
        String buffer = "";

        while (index < equazione.length()) {
            if (equazione.charAt(index) != '+' && equazione.charAt(index) != '-') {
                buffer += equazione.charAt(index);
            } else {
                segmenti.add(buffer);
                buffer = "";
                segmenti.add(String.valueOf(equazione.charAt(index)));
            }
            index++;
        }

        segmenti.add(buffer.toString());
        return segmenti;
    }
}