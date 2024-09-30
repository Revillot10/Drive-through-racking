/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificaciondecosecha;

import ilog.concert.IloException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class AchievementScalarizingFunctionApproach extends MathematicalModel {
    private String link;
    private ArrayList<ArrayList<String>>solucion;
    private double UFO1;
    private double NFO1;
    private double UFO2;
    private double NFO2;
    private double UFO3;
    private double NFO3; 
    public AchievementScalarizingFunctionApproach(int K, int EO,int[][][] inicio, int[][][] fin, int[] cuartelesPorHuerto, int[][] pasadasPorCuartel, int[][] modoDeCosecha, int P, int PH, double[][][][] A, double[] H, double[] F, double[][][][] J, double Q, int[][][] Pi, int[][][] Ri, int[][][] Si, int[][][] D, double[][] G, int[][][][] N, int[] L, int[][] I, int Ni, int W, int E, int[][][][] PL, double Po, int M1, int M2) throws IloException, IOException {
        super(K, EO, inicio, fin,cuartelesPorHuerto, pasadasPorCuartel, modoDeCosecha, P, PH, A, H, F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2);
        link = "C:\\Users\\Administrador\\Desktop\\Cosecha Multiobjetivo\\15 Cuarteles\\AchievementScalarizingFunctionApproach\\";
        solucion = new ArrayList<>();
        UFO1 = 35261155.2;
        NFO1 = 51480877.2;
        UFO2 = 497910;
        NFO2 = 669099.1;
        UFO3 = 1305;
        NFO3 = 1422;  
        metodo();
    }
    private void metodo() throws IloException, IOException{
        int l=0;
        for(double i=UFO1; i<=NFO1; i+= (NFO1-UFO1)/7){
            for(double j=UFO2; j<NFO2; j+= (NFO2 - UFO2)/7){
                for(double k=UFO3; j<NFO3; j+= (NFO3 - UFO3)/7){
                    double foCalendario =-1;
                    double foCostos =-1;
                    double foPerdida =-1;
                    double gap =-1;
                    double tiempo =-1;
                    agregarRestricciones(7200);
                    AchievementScalarizingFunctionApproach(UFO1, UFO2,UFO3, NFO1, NFO2, NFO3, i,j,k);
                    if(cplex.solve()){
                        foCostos = foCostos();
                        foPerdida = foPerdida();                        
                        foCalendario =foCalendario();
                        gap =cplex.getMIPRelativeGap();  
                        tiempo = cplex.getCplexTime();
                        solucion.add(new ArrayList<>());
                        solucion.get(l).add(String.valueOf(i));
                        solucion.get(l).add(String.valueOf(j));
                        solucion.get(l).add(String.valueOf(k));
                        solucion.get(l).add(String.valueOf(foCostos));
                        solucion.get(l).add(String.valueOf(foPerdida));
                        solucion.get(l).add(String.valueOf(foCalendario));
                        solucion.get(l).add(String.valueOf(gap));
                        solucion.get(l).add(String.valueOf(tiempo));
                        l++;
                        exportarSolucion(link+"S"+l+".txt");
                    }
                    else System.out.println("lala");
                }
            }
        }
        for(int m=0; m<solucion.size(); m++){
            System.out.println(solucion.get(m));           
        }
    }
    
}
