/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmomemetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2017.1.08.008
 */
public class Executar {
    
    private ArrayList<Individuo> populacao = new ArrayList(Globals.tamPopulacao);
    private Individuo melhorObtido = null;
    
    public Executar(){
        //Inicializando população
        this.inicializaPopulação();
        melhorObtido = this.melhorIndividuo();
        
        //Exibir show = new Exibir(this.melhorIndividuo().getCromossomo());
        for(int i = 0; i < Globals.numGeracoes; i++){
            //Pegando subpopulação
            Random gen = new Random();
            
            int min = gen.nextInt(Globals.numPontos);
            int max = gen.nextInt(Globals.numPontos);
            
            //Limite superar e inferior da subpopulação
            if(min > max){
                int aux = min;
                min = max;
                max = aux;
            }
            //Número de cruzamentos será igual a metade do tamanho da subpopulação
            int subPopulacao = max - min;
            
            
            for(int j = 0; j < subPopulacao; j++){
                if(Math.random() < Globals.taxaCruzamento){
                Individuo pai1 = null;
                    Individuo pai2 = null;
                    int indexpai1;
                    int indexpai2;

                    //Seleciona os pais para cruzamento de forma aleatória
                    do{
                        indexpai1 = Globals.randomInterval(min, max);
                        pai1 = this.populacao.get(indexpai1);
                        indexpai2 = Globals.randomInterval(min, max);
                        pai2 = this.populacao.get(indexpai2);
                    }while(pai1 == pai2);

                    Individuo filho = this.cruzamento(pai1, pai2);
                    Individuo aux = null;
                    int indexaux;

                    //Verifica qual dos pais tem menor nota
                    if(pai1.getNotaAvaliacao() < pai2.getNotaAvaliacao()){
                        aux = pai1;
                        indexaux = indexpai1;
                    }else{
                        aux = pai2;
                        indexaux = indexpai2;
                    }

                    //Se o pai tiver nota menor que o filho, ele é substituido
                    if(aux.getNotaAvaliacao() < filho.getNotaAvaliacao()){
                        filho.HillClimb();
                        this.populacao.set(indexaux, filho);
                    }     
                }
            }
            
            for(int j = min; j <= max; j++){
                this.populacao.get(j).mutacao(Globals.taxaMutacao);
            }
            
            //show.setSolucao(this.melhorIndividuo().getCromossomo());
//            try {
//                Thread.sleep(400);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Executar.class.getName()).log(Level.SEVERE, null, ex);
//            }

            if(this.melhorObtido.getNotaAvaliacao() < this.melhorIndividuo().getNotaAvaliacao()){
                this.melhorObtido = this.melhorIndividuo();
            }
            
        }
        
        this.HillClimb();
        
        System.out.println(this.melhorObtido.distancia());
        Exibir ex = new Exibir(this.melhorObtido.getCromossomo());
    
    }
    
    private Individuo cruzamento(Individuo pai1, Individuo pai2){
        
        int corte = Globals.randomInterval(1, Globals.numPontos);
        
        ArrayList<Ponto> pontosFilho = new ArrayList(Globals.numPontos);
        pontosFilho.addAll(pai1.getCromossomo().subList(0, corte));
        pontosFilho.addAll(pai2.getCromossomo().subList(corte, Globals.numPontos));
        
        Individuo filho = new Individuo(pontosFilho);
        filho.avaliacao();
        
        return filho;
        
    }
    
    private void inicializaPopulação(){
        ArrayList<Ponto> pontos = new ArrayList(Globals.numPontos);
        pontos.addAll(Globals.pontosIniciais);
        Random gen = new Random();
        for(int i = 0; i < Globals.tamPopulacao; i++){
            Collections.shuffle(pontos);
            ArrayList<Ponto> novosPontos = new ArrayList(Globals.numPontos);
            for(Ponto p : pontos){
                double px = 0;
                double py = 0;
                Ponto np = null;
                do{
                    px = gen.nextDouble() * (p.px + p.pai.raio);
                    py = gen.nextDouble() * (p.py + p.pai.raio);
                    
                    np = new Ponto(px, py, p);
                }while(Globals.calcDistancia(np, p) > p.pai.raio);
                novosPontos.add(np);
            }
            Individuo novo = new Individuo(novosPontos);
            novo.avaliacao();
            this.populacao.add(novo);
        }
    }
    
    public void HillClimb(){
        Random gen = new Random();
        for(int i = 0; i < 100000; i++){
            int index = gen.nextInt(this.melhorObtido.getCromossomo().size());
            Ponto p1 = this.melhorObtido.getCromossomo().get(index);      

            double x = 0;
            double y = 0;
            Ponto np = null;

            do{
                x = gen.nextDouble() * (p1.pai.px + p1.pai.raio);
                y = gen.nextDouble() * (p1.pai.py + p1.pai.raio); 
                np = new Ponto(x, y, p1.pai);
            }while(Globals.calcDistancia(p1, np) > p1.pai.raio);
            
            double antiDist = this.melhorObtido.distancia();
            this.melhorObtido.getCromossomo().set(index, np);
            
            
            if(this.melhorObtido.distancia() > antiDist || !this.melhorObtido.todosVisitados()){
                this.melhorObtido.getCromossomo().set(index, p1);
            }         
        }
        this.melhorObtido.avaliacao();
    }
    
    private Individuo melhorIndividuo(){
        
        Individuo melhor = null;
        double maiorNota = 0;
        
        for(Individuo i : this.populacao){
            if(i.getNotaAvaliacao() > maiorNota){
                maiorNota = i.getNotaAvaliacao();
                melhor = i;
            }
        }
        
        return melhor;
    }
    
}
