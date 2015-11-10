package ch.old.GGITRechner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Eine grafische Schnittstelle f�r den Rechner. Hier wird keine
 * Berechnung durchgef�hrt. Diese Klasse ist ausschlie�lich f�r
 * die Anzeige auf dem Bildschirm zust�ndig. Sie l�sst die
 * Recheneinheit alle Berechnungen ausf�hren.
 * 
 * @author David J. Barnes, Michael K�lling & Davina Golomingi
 * @version 20140318
 */
public class GrafischeSchnittstelle
    implements ActionListener
{
    private Recheneinheit rechner;

    private JFrame fenster;
    private JTextField anzeige;
    //private JLabel status;

    /**
     * Erzeuge eine grafische Benutzungsschnittstelle f�r
     * eine Recheneinheit.
     * @param recheneinheit die Recheneinheit
     */
    public GrafischeSchnittstelle(Recheneinheit recheneinheit)
    {
        rechner = recheneinheit;
        fensterAufbauen();
        fenster.setVisible(true);
    }

    /**
     * Setze die Sichtbarkeit dieser grafischen Schnittstelle.
     * @param sichtbar
     *        true, wenn die Schnittstelle sichtbar sein soll, false sonst.
     * 
     */
    public void setzeSichtbarkeit(boolean sichtbar)
    {
        fenster.setVisible(sichtbar);
    }

    /**
     * Baue das Fenster f�r diese grafische Oberfl�che auf.
     */
    private void fensterAufbauen()
    {
        fenster = new JFrame(rechner.gibTitel());
        
        JPanel fensterflaeche = (JPanel)fenster.getContentPane();
        fensterflaeche.setLayout(new BorderLayout(8, 8));
        fensterflaeche.setBorder(new EmptyBorder( 10, 10, 10, 10));

        anzeige = new JTextField();
        fensterflaeche.add(anzeige, BorderLayout.NORTH);

        JPanel tastenfeld = new JPanel(new GridLayout(4, 4));
            //Mit '/' und 'X' "Buttons" ergaenzt und '?'-"Button" entfernt
            tasteHinzufuegen(tastenfeld, "7");
            tasteHinzufuegen(tastenfeld, "8");
            tasteHinzufuegen(tastenfeld, "9");
            tasteHinzufuegen(tastenfeld, "+");

            
            tasteHinzufuegen(tastenfeld, "4");
            tasteHinzufuegen(tastenfeld, "5");
            tasteHinzufuegen(tastenfeld, "6");
            tasteHinzufuegen(tastenfeld, "-");
            
            tasteHinzufuegen(tastenfeld, "1");
            tasteHinzufuegen(tastenfeld, "2");
            tasteHinzufuegen(tastenfeld, "3");
            tasteHinzufuegen(tastenfeld, "=");

            tasteHinzufuegen(tastenfeld, " ");
            tasteHinzufuegen(tastenfeld, "0");
            tasteHinzufuegen(tastenfeld, "<-");
            tasteHinzufuegen(tastenfeld, "C");
            
        fensterflaeche.add(tastenfeld, BorderLayout.CENTER);

        fenster.pack();
    }

    /**
     * F�ge dem Tastenfeld eine Taste hinzu.
     * @param panel der Panel, der die Taste aufnehmen soll
     * @param tastentext der Text f�r die Taste
     */
    private void tasteHinzufuegen(Container panel, String tastentext)
    {
        JButton taste = new JButton(tastentext);
        taste.addActionListener(this);
        panel.add(taste);
    }

    /**
     * Eine Aktion an der Schnittstelle wurde ausgef�hrt.
     * Finde heraus, welche es war und behandle sie.
     * @param event die Beschreibung der Aktion
     */
    public void actionPerformed(ActionEvent event)
    {
        String taste = event.getActionCommand();
        //Faelle geteilt durch und mal wurden addiert
        if(taste.equals("0") ||
           taste.equals("1") ||
           taste.equals("2") ||
           taste.equals("3") ||
           taste.equals("4") ||
           taste.equals("5") ||
           taste.equals("6") ||
           taste.equals("7") ||
           taste.equals("8") ||
           taste.equals("9")) {
            int number = Integer.parseInt(taste);
            rechner.zifferGetippt(number);
        }
        else if(taste.equals("+")) {
            rechner.plus();
        }
        else if(taste.equals("-")) {
            rechner.minus();
        }
        else if(taste.equals("=")) {
            rechner.gleich();
        }
        else if(taste.equals("C")) {
            rechner.clear();
        }
        else if(taste.equals("<-")) {
            rechner.zurueck();
        }

        auffrischen();
        }

/**
 * Frische die grafische Anzeige mit dem aktuellen Wert der
 * Recheneinheit auf.
 */
private void auffrischen()
        {
        anzeige.setText("" + rechner.gibAnzeigewert());
        }

        }
