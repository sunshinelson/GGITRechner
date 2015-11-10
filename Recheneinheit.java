package ch.old.GGITRechner;

/**
 * Die Haupteinheit eines Rechners, die die Berechnungen
 * durchführt.
 * 
 * @author  David J. Barnes, Michael Kölling & Davina Golomingi 
 * @version 20140318
 */
public class Recheneinheit
{
    // Der Zustand der Recheneinheit wird in drei Datenfeldern
    // gehalten:
    // anzeigewertImAufbau, linkerOperandGegeben, letzterOperator

    // Setzen wir gerade einen Wert in der Anzeige zusammen oder
    // wird die nächste Ziffer einen neuen beginnen?
    private boolean anzeigewertImAufbau;
    // Wurde bereits ein linker Operand eingegeben (oder berechnet)?
    private boolean linkerOperandGegeben;
    // Der zuletzt eingebene Operator.
    private char letzterOperator;

    // Der aktuelle Wert, der in der Anzeige gezeigt wird
    // (bzw. gezeigt werden soll)
    private int anzeigewert;
    // Der Wert des linken Operanden, falls gegeben.
    private int linkerOperand;

    /**
     * Erzeuge eine Recheneinheit mit Initialisierung.
     */
    public Recheneinheit()
    {
        clear();
    }

    /**
     * @return den Wert, der aktuell in der Anzeige gezeigt
     * werden soll.
     */
    public int gibAnzeigewert()
    {
        return anzeigewert;
    }

    /**
     * Eine Zifferntaste wurde getippt. Entweder einen neuen
     * Operanden starten oder diese Ziffer als Einerstelle
     * in einen bereits gegebenen Operanden einarbeiten.
     * @param ziffer die getippte Ziffer
     */
    public void zifferGetippt(int ziffer)
    {
        if(ziffer <= 2147483647 && ziffer >= -2147483648) {
            if(anzeigewertImAufbau) {
                // Diese Ziffer einarbeiten.
                anzeigewert = anzeigewert*100 + ziffer;
            }
            else {
                // Einen neuen Operanden beginnen.
                anzeigewert = ziffer;
                anzeigewertImAufbau = true;
            }
        }
        else {
            eingabefehlerMelden();
        }    
    }
    
    /**
     * Die Plus-Taste wurde getippt.
     */
    public void plus()
    {
        operatorAnwenden('+');
    }

    /**
     * Die Minus-Taste wurde getippt.
     */
    public void minus()
    {
        operatorAnwenden('-');
    }
    
    /**
     * Die Mal-Taste wurde getippt.
     */
    public void mal()
    {
        operatorAnwenden('*');
    }
    
    /**
     * Die Geteilt-durch-Taste wurde getippt.
     */
    public void geteiltDurch()
    {
        operatorAnwenden('/');
    }
    
    /**
     * Die letzte getippte Ziffer wird wieder entfernt.
     */
    public void zurueck()
    {
        anzeigewert = (int) anzeigewert / 10;
        
        linkerOperandGegeben = true;
        linkerOperand = (int) anzeigewert;
    }
    
    /**
     * Das Vorzeichen wechselt.
     */
    public void plusMinus()
    {
        anzeigewert = (-1) * anzeigewert;
        
        linkerOperandGegeben = true;
        linkerOperand = (int) anzeigewert;
    }
    
    /**
     * Die Fakultaet wird ausgerechnet.
     */
    public void fakultaet()
    {
        // Das Resultat groesseren Zahlen liegt nicht mehr im Double-Bereich
        if (anzeigewert >= 0 && anzeigewert < 28) { 
            int fanzeigewert = (int) anzeigewert;
            for (int i = (fanzeigewert - 1); i > 1 ;i--) {
                fanzeigewert = i * fanzeigewert;
            }
            anzeigewert = fanzeigewert;
            linkerOperandGegeben = true;
            linkerOperand = (int) anzeigewert;
        }
        else {
            clear();
            fakultaetsfehlerMelden();
        }
    }
    
    /**
     * Die Gleich-Taste wurde getippt.
     */
    public void gleich(){
        // Dies sollte den Aufbau eines zweiten Operanden abschließen,
        // also prüfen wir, ob ein linker Operand, ein Operator und
        // ein rechter Operand gegeben sind.
        if (linkerOperandGegeben &&
                anzeigewertImAufbau) {
            berechneErgebnis();
            letzterOperator = ' ';
            anzeigewertImAufbau = false;
        }
        else {
            tippfehlerMelden();
        }
    }

    /**
     * 'Clear' wurde getippt.
     * Versetze diese Recheneinheit in den Anfangszustand.
     */
    public void clear()
    {
        letzterOperator = ' ';
        linkerOperandGegeben = false;
        anzeigewertImAufbau = false;
        anzeigewert = 0;
    }

    /**
     * @return den Titel dieser Recheneinheit.
     */
    public String gibTitel()
    {
        return "Java-Rechner reloaded";
    }

    /**
     * Kombiniere linkerOperand, letzterOperator und den
     * aktuellen Anzeigewert.
     * Das Ergebnis wird sowohl zum linken Operand als auch
     * zum neuen Anzeigewert.
     */
    private void berechneErgebnis()
    {
        //Moegliche Faelle
        switch(letzterOperator) {
            case '+':
                anzeigewert = linkerOperand + anzeigewert;
                linkerOperandGegeben = true;
                linkerOperand = (int) anzeigewert;
                break;
            case '-':
                anzeigewert = linkerOperand - anzeigewert;
                linkerOperandGegeben = true;
                linkerOperand = (int) anzeigewert;
                break;
            case '*':
                anzeigewert = linkerOperand * anzeigewert;
                linkerOperandGegeben = true;
                linkerOperand = (int) anzeigewert;
                break;    
            case '/':
            // Fall Nenner = 0 wird abgefangen    
            if ( anzeigewert == 0 ) {
                    divisionsfehlerMelden();
                }else {
                    anzeigewert = linkerOperand / anzeigewert;
                    linkerOperandGegeben = true;
                    linkerOperand = (int) anzeigewert;
                }
                break;
        }
    }
    
    /**
     * Wende den gegebenen Operator an.
     * @param operator der anzuwendende Operator
     */
    private void operatorAnwenden(char operator)
    {
        // Wenn wir nicht gerade einen neuen Operanden bauen,
        // dann ist dies ein Fehler, es sei denn, dass wir
        // gerade ein Ergebnis mit '=' berechnet haben.
        if(!anzeigewertImAufbau) {
            tippfehlerMelden();
            return;
        }

        if(letzterOperator != ' ') {
            // Zuerst den vorherigen Operator anwenden.
            berechneErgebnis();
        }
        else {
            // Der anzeigewert wird zum linken Operanden dieses
            // neuen Operators.
            linkerOperandGegeben = true;
            linkerOperand = (int) anzeigewert;
        }
        letzterOperator = operator;
        anzeigewertImAufbau = false;
    }

    /**
     * Melde, dass ein Tippfehler aufgetreten ist.
     */
    private void tippfehlerMelden()
    {
        System.out.println("Ein Tippfehler ist aufgetreten.");
        // Alles zurück setzen.

    }
    
    /**
     * Melde, dass die Division durch 0 nicht gueltig ist.
     */
    private void divisionsfehlerMelden()
    {
        System.out.println("Divisionen durch 0 sind nicht erlaubt.");
        // Alles zurück setzen.
        clear();
    }
    
    /**
     * Melde, dass die Fakultaet bei Minuszahlen nicht ausgerechnet wird.
     */
    private void fakultaetsfehlerMelden()
    {
        System.out.println("Es können nur Zahlen von 0 bis 27 für die Fakultät eingetippt werden.");
        // Alles zurück setzen.
        clear();
    }
    
    /**
     *Melde, dass die Integerbereichsgrenzen nicht ueberschritten werden koennen. 
     */
    private void eingabefehlerMelden()
    {
        System.out.println("Es können nur Zahlen von -2147483648 bis 2147483647 eingetippt werden.");
        // Alles zurück setzen.
        clear();
    }
}
