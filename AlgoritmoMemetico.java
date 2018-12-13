/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmomemetico;

/**
 *
 * @author 2017.1.08.008
 */
public class AlgoritmoMemetico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CarregarArquivo c = new CarregarArquivo("instancia.csv");
        Globals.maxX();
        Globals.maxY();
        Executar ex = new Executar();
    }
    
}
