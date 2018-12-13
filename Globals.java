/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmomemetico;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Gustavo
 */
//MELHORES RESULTADOS:
//15000 gerações
//400 indivíduos
//0.05 de mutação

public class Globals {
    
    public static ArrayList<Ponto> pontosIniciais = new ArrayList();
    
    public static double raio = 10;
    public static double maiorx = 0.0;
    public static double maiory = 0.0;
    public static double taxaCruzamento = 0.8;
    public static double taxaMutacao = 0.3;
    public static int numGeracoes = 10000;
    public static int tamPopulacao = 500;
    public static int numHill = 1000;
    public static int numPontos = 29;
    
    public static double calcDistancia(Ponto p1, Ponto p2){
        return Math.abs(Math.sqrt(Math.pow((p2.px - p1.px), 2) + Math.pow((p2.py - p1.py), 2)));
    }
    
    public static boolean verificaPonto(Ponto p1){
        
        for(Ponto p2 : pontosIniciais){
            if(calcDistancia(p1,p2) <= raio){
                return true;
            }
        }
        return false;
    }
    
    public static void maxX(){
        double maior = 0;
        for(Ponto p : pontosIniciais){
            if(p.px > maior){
                maior = p.px;
            }
        }
        maiorx = maior;
    }
    
    public static void maxY(){
        double maior = 0;
        for(Ponto p : pontosIniciais){
            if(p.py > maior){
                maior = p.py;
            }
        }
        maiory = maior;
    }
    
    public static int randomInterval(int i, int s) {
        Random rd = new Random();
        return rd.nextInt(s - i + 1) + i;
    }
    
}
