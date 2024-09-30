/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificaciondecosecha;

import ilog.concert.IloIntVar;
import java.util.Scanner;

/**
 *
 * @author Javier
 */
public class Solution {

    
    private int THF;
    private int[][][][] Y;
    private int[][][][]W;
    private double[][][][]X;
    private double[][][][] INI;
    private double[][][]S;
    private int[] BF;
    private int[] TFV;
    private int[] THV;
    private int[] TV;
    private int[][][][] TVC;
    private int[][][][] NB;
    private int[][][][] TFC;
    private int[][][] FI;
    private int[][][] FT;
    public Solution(int huertos, int[] cuartelesPorHuerto, int[][] pasadasPorCuartel, int periodo, int THF, int[][][][]Y, int[][][][]W, double[][][][]X, double[][][][] INI, double[][][] S, int[]BF, int[] TFV, int[] THV, int[] TV, int[][][][]TVC, int[][][][]NB, int[][][][]TFC, int[][][]FI, int[][][]FT){

        this.THF = THF;
        this.Y = new int[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.Y[h]= new int[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.Y[h][c] = new int[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.Y[h][c][f] = new int[periodo];
                    for(int t=0; t<periodo; t++){
                        this.Y[h][c][f][t] = Y[h][c][f][t];
                    }
                }
            }
        }
        this.W = new int[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.W[h]= new int[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.W[h][c] = new int[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.W[h][c][f] = new int[periodo];
                    for(int t=0; t<periodo; t++){
                        this.W[h][c][f][t] = W[h][c][f][t];
                    }
                }
            }
        }
        this.X = new double[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.X[h]= new double[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.X[h][c] = new double[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.X[h][c][f] = new double[periodo];
                    for(int t=0; t<periodo; t++){
                        this.X[h][c][f][t] = X[h][c][f][t];
                    }
                }
            }
        }
        this.INI = new double[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.INI[h]= new double[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.INI[h][c] = new double[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.INI[h][c][f] = new double[periodo];
                    for(int t=0; t<periodo; t++){
                        this.INI[h][c][f][t] = INI[h][c][f][t];
                    }
                }
            }
        }   
        this.S = new double[huertos][][];
        for(int h=0; h<huertos; h++){
            this.S[h]= new double[cuartelesPorHuerto[h]][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.S[h][c] = new double[pasadasPorCuartel[h][c]];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.S[h][c][f] = S[h][c][f];
                }
            }
        }
        this.BF = new int[periodo];
        for(int t=0; t<periodo;t++){
            this.BF[t] = BF[t];
        }
        this.TFV = new int[periodo];
        for(int t=0; t<periodo;t++){
            this.TFV[t] = TFV[t];
        }
        this.THV = new int[periodo];
        for(int t=0; t<periodo;t++){
            this.THV[t] = THV[t];
        }
        this.TV = new int[periodo];
        for(int t=0; t<periodo;t++){
            this.TV[t] = TV[t];
        }
        this.TVC = new int[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.TVC[h]= new int[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.TVC[h][c] = new int[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.TVC[h][c][f] = new int[periodo];
                    for(int t=0; t<periodo; t++){
                        this.TVC[h][c][f][t] = TVC[h][c][f][t];
                    }
                }
            }
        }
        this.NB = new int[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.NB[h]= new int[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.NB[h][c] = new int[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.NB[h][c][f] = new int[periodo];
                    for(int t=0; t<periodo; t++){
                        this.NB[h][c][f][t] = NB[h][c][f][t];
                    }
                }
            }
        }
        this.TFC = new int[huertos][][][];
        for(int h=0; h<huertos; h++){
            this.TFC[h]= new int[cuartelesPorHuerto[h]][][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.TFC[h][c] = new int[pasadasPorCuartel[h][c]][];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.TFC[h][c][f] = new int[periodo];
                    for(int t=0; t<periodo; t++){
                        this.TFC[h][c][f][t] = TFC[h][c][f][t];
                    }
                }
            }
        } 
        this.FI = new int[huertos][][];
        for(int h=0; h<huertos; h++){
            this.FI[h]= new int[cuartelesPorHuerto[h]][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.FI[h][c] = new int[pasadasPorCuartel[h][c]];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.FI[h][c][f] = FI[h][c][f];
                }
            }
        }
        this.FT = new int[huertos][][];
        for(int h=0; h<huertos; h++){
            this.FT[h]= new int[cuartelesPorHuerto[h]][];
            for(int c=0; c<cuartelesPorHuerto[h]; c++){
                this.FT[h][c] = new int[pasadasPorCuartel[h][c]];
                for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                    this.FT[h][c][f] = FT[h][c][f];
                }
            }
        } 
    }       

    public int obtenerTHF(){
        return THF;
    }
    public int[][][][] obtenerY(){
        return Y;
    }
    public int[][][][] obtenerW(){
        return W;
    }
    public double[][][][] obtenerX(){
        return X;
    }
    public double[][][][] obtenerINI(){
        return INI;
    }
    public double[][][] obtenerS(){
        return S;
    }
    public int[] obtenerBF(){
        return BF;
    }
    public int[] obtenerTFV(){
        return TFV;
    }
    public int[] obtenerTHV(){
        return THV;
    }
    public int[] obtenerTV(){
        return TV;
    }
    public int[][][][] obtenerTVC(){
        return TVC;
    }
    public int[][][][] obtenerNB(){
        return NB;
    }
    public int[][][][] obtenerTFC(){
        return TFC;
    }
    public int[][][] obtenerFI(){
        return FI;
    }
    public int[][][] obtenerFT(){
        return FT;
    }
}
    