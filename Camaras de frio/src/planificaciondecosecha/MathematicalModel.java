/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificaciondecosecha;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Javier
 */
public class MathematicalModel {
    protected IloCplex cplex;
    protected IloLinearNumExpr fo1;
    protected IloLinearNumExpr fo2;
    protected IloLinearNumExpr fo;
    private int I; //Listo
    private int B; //Listo
    private int T; //Listo
    private int P; //Listo
    private int S; //Listo
    private int [] ID;
    private int K; //Listo
    private int [][][][] PsiL; //Listo
    private int [][][][] PsiR; //Listo
    private int [][][][] RL; //Listo
    private int [][][][] RR; //Listo
    private int [][] varFi; //Listo
    private int [][] Omega; //Listo
    private int [] A; //Listo
    private int [] D; //Listo
    private int [][][][] X;
    
    private IloNumVar [][][][][] x;
    private IloNumVar [][][][] yL;
    private IloNumVar [][][][] yR;
    private IloNumVar [][][][][] FiL;
    private IloNumVar [][][][][] FiR;
    private IloNumVar [][][] wL;
    private IloNumVar [][][] wR;
    private IloNumVar [][][][] zL;
    private IloNumVar [][][][] zR;
    private IloNumVar [][][] UL;
    private IloNumVar [][][] UR;
    private IloNumVar [] F;
    private IloNumVar [][] GL;
    private IloNumVar [][][] CL;
    private IloNumVar [][][] CR;
    
    private IloNumVar f1;
    private IloNumVar f2;
    private IloNumVar f3;
    public MathematicalModel(int I, int B, int T, int P, int S, int[] ID, int K, int[][][][] PsiL, int[][][][] PsiR, int[][][][] RL, int [][][][] RR, int [][] varFi, int[][] Omega, int [] A, int [] D, int [][][][] X) throws IloException{
        this.I = I;
        this.B = B;
        this.T = T;
        this.P = P;
        this.S = S;
        this.ID = new int[I];
        for(int i =0 ; i<I; i++){
            this.ID[i] = ID[i];
        }
        this.K = K;
        this.PsiL = new int[T][][][];
        for(int t=0; t<T; t++){
            this.PsiL[t] = new int[P][][];
            for(int p=0; p<P; p++){
                this.PsiL[t][p] = new int[T][];
                for(int tp =0 ; tp<T; tp++){
                    this.PsiL[t][p][tp] = new int [P];
                    for(int pp=0; pp<P; pp++){
                        this.PsiL[t][p][tp][pp] = PsiL[t][p][tp][pp];
                    }
                }
            }
        }
        this.PsiR = new int[T][][][];
        for(int t=0; t<T; t++){
            this.PsiR[t] = new int[P][][];
            for(int p=0; p<P; p++){
                this.PsiR[t][p] = new int[T][];
                for(int tp =0 ; tp<T; tp++){
                    this.PsiR[t][p][tp] = new int [P];
                    for(int pp=0; pp<P; pp++){
                        this.PsiR[t][p][tp][pp] = PsiR[t][p][tp][pp];
                    }
                }
            }
        }
        this.RL = new int[T][][][];
        for(int t=0; t<T; t++){
            this.RL[t] = new int[P][][];
            for(int p=0; p<P; p++){
                this.RL[t][p] = new int[T][];
                for(int tp =0 ; tp<T; tp++){
                    this.RL[t][p][tp] = new int [P];
                    for(int pp=0; pp<P; pp++){
                        this.RL[t][p][tp][pp] = RL[t][p][tp][pp];
                    }
                }
            }
        }
        this.RR = new int[T][][][];
        for(int t=0; t<T; t++){
            this.RR[t] = new int[P][][];
            for(int p=0; p<P; p++){
                this.RR[t][p] = new int[T][];
                for(int tp =0 ; tp<T; tp++){
                    this.RR[t][p][tp] = new int [P];
                    for(int pp=0; pp<P; pp++){
                        this.RR[t][p][tp][pp] = RR[t][p][tp][pp];
                    }
                }
            }
        }
        this.varFi = new int[I][];
        for(int i=0 ; i<I; i++){
            this.varFi[i] = new int[S];
            for(int s=0; s<S; s++){
                this.varFi[i][s] = varFi[i][s];
            }
        }
        this.Omega = new int[I][];
        for(int i=0 ; i<I; i++){
            this.Omega[i] = new int[S];
            for(int s=0; s<S; s++){
                this.Omega[i][s] = Omega[i][s];
            }
        }
        this.A = new int[I];
        for(int i=0; i<I; i++){
            this.A[i] = A[i];
        }
        this.D = new int[I];
        for(int i=0; i<I; i++){
            this.D[i] = D[i];
        }
        this.X = new int[B][][][];
        for(int b =0 ; b<B ; b++){
            this.X[b] = new int[T][][];
            for(int t=0; t<T; t++){
                this.X[b][t] = new int[P][];
                for(int p =0; p<P; p++){
                    this.X[b][t][p] = new int[I];
                    for(int i=0; i<I; i++){
                        this.X[b][t][p][i] = X[b][t][p][i];
                    }
                }
            }
        } 
        cplex = new IloCplex();
        f1 = cplex.numVar(0, Double.MAX_VALUE);
        f2 = cplex.numVar(0, Double.MAX_VALUE);
        x = new IloNumVar[I][S][B][T][P];
        for(int i =0 ; i<I; i++){
            for(int s =0; s<S; s++){
                for(int b =0; b<B;b++){
                    for(int t=0; t<T; t++){
                        for(int p=0; p<P; p++){
                            if(s >= A[i] && s<=D[i]){
                                x[i][s][b][t][p] = cplex.boolVar();
                            }
                        }
                    }
                }
            }
        }
        yL = new IloNumVar[I][S][B][P];
        yR = new IloNumVar[I][S][B][P];
        for(int i=0; i<I; i++){
            for(int s=0; s<S;s++){
                for(int b=0 ; b<B; b++){
                    for(int p =0; p<P; p++){
                        if(varFi[i][s] == 1 && Omega[i][s] == 1){
                            yL[i][s][b][p] = cplex.boolVar();
                            yR[i][s][b][p] = cplex.boolVar();
                        }
                    }
                }
            }
        }
        FiL = new IloNumVar[I][I][S][B][P]; 
        FiR = new IloNumVar[I][I][S][B][P];
        for(int j =0 ; j<I; j++){
            for(int k=0; k<I; k++){
                for(int s=0; s<S ;s++){
                    for(int b =0 ; b<B ; b++){
                        for(int p =0; p<P; p++){
                            if(varFi[j][s] == 1 && Omega[j][s] == 1){
                                if(varFi[k][s] == 1 && Omega[k][s] == 1){
                                    FiL[j][k][s][b][p] = cplex.boolVar();
                                    FiR[j][k][s][b][p] = cplex.boolVar();
                                }
                            }
                        }
                    }
                }
            }
        }
        wL = new IloNumVar[S][K][B];
        wR = new IloNumVar[S][K][B];
        for(int s =S; s <S ; s++){
            for(int k=0; k<K;k++){
                for(int b=0; b<B;b++){
                    wL[s][k][b] = cplex.boolVar();
                    wR[s][k][b] = cplex.boolVar();
                }
            }
        }
        zL = new IloNumVar[I][S][B][P];
        zR = new IloNumVar[I][S][B][P];
        for(int j = 0; j<I; j++){
            for(int s=0; s <S ; s++){
                for(int b=0; b<B ; b++){
                    for(int p=0; p <P; p++){
                        if(varFi[j][s] == 1 && Omega[j][s] == 1){
                            zL[j][s][b][p] = cplex.boolVar();
                            zR[j][s][b][p] = cplex.boolVar();
                        }
                    }
                }
            }
        }
        UL = new IloNumVar[S][K][B];
        UR = new IloNumVar[S][K][B];
        for(int s =0; s<S; s++){
            for(int k =0; k<K;k++){
                for(int b=0; b<B; b++){
                    UR[s][k][b] = cplex.intVar(0, Integer.MAX_VALUE);
                    UL[s][k][b] = cplex.intVar(0, Integer.MAX_VALUE);
                }
            }
        }
        F = new IloNumVar[S];
        for(int s =0 ; s<S; s++){
            F[s] = cplex.intVar(0, Integer.MAX_VALUE);
        }
        GL = new IloNumVar[I][S];
        for(int i=0; i<I; i++){
            for(int s=0; s<S; s++){
                if(varFi[i][s] == 1 && Omega[i][s] == 1){
                    GL[i][s] = cplex.boolVar();
                }
            }
        }
        CL = new IloNumVar[I][B][P];
        CR = new IloNumVar[I][B][P];
        for(int i=0; i<I; i++){
            for(int b=0; b<B; b++){
                for(int p =0; p<P; p++){
                    if(ID[i]==1){
                        CL[i][b][p] = cplex.boolVar();
                        CR[i][b][p] = cplex.boolVar();
                    }
                }
            }
        }

    }
    protected void objectiveFunction1() throws IloException{
        fo1 = cplex.linearNumExpr();
        for(int b=0; b<B; b++){
            for(int p=0; p<P;p++){
                for(int s=0; s<S;s++){
                    for(int i=0; i<I; i++){
                        if(varFi[i][s] == 1 && Omega[i][s] == 1){
                            fo1.addTerm(1, yL[i][s][b][p]);
                            fo1.addTerm(1, yR[i][s][b][p]);
                        }
                    }
                }
            }
        }

    }
    protected void objectiveFunction2() throws IloException{
        fo2 = cplex.linearNumExpr();
        for(int s=0; s<S; s++){
            fo2.addTerm(1, F[s]);
        }
    }
    protected void agregarRestricciones(int tiempo) throws IloException{
        cplex.clearModel();       
        cplex.setParam(IloCplex.Param.TimeLimit, tiempo);
        //cplex.setParam(IloCplex.Param.Threads, 30);
        for(int i=0; i<I; i++){
            for(int s = A[i]; s<=D[i]; s++){
                IloLinearNumExpr c3 = cplex.linearNumExpr();
                for(int b=0; b<B; b++){
                    for(int t=0; t<T; t++){
                        for(int p=0; p<P;p++){
                            c3.addTerm(1, x[i][s][b][t][p]);
                        }
                    }
                }
                cplex.addEq(c3, 1);
                
            }
        }
        for(int s=0; s<S; s++){
            for(int b=0; b<B; b++){
                for(int t=0; t<T; t++){
                    for(int p=0; p<P; p++){
                        IloLinearNumExpr c4 = cplex.linearNumExpr();
                        for(int i =0; i<I; i++){
                            if(varFi[i][s]==1){
                                c4.addTerm(1,x[i][s][b][t][p]);
                            }
                        }
                        cplex.addLe(c4,1);
                    }
                }
            }
        }
        for(int i=0; i<I; i++){
            for(int b=0; b<B; b++){
                for(int t=0; t<T; t++){
                    for(int p=0; p<P; p++){
                        cplex.addEq(x[i][0][b][t][p], X[b][t][p][i]);
                    }
                }
            }
        }
        for(int s=0; s<S; s++){
            for(int b=0; b<B;b++){
                for(int t=0; t<T; t++){
                    for(int p=0; p<P; p++){
                        for(int tau=0; tau<T; tau++){
                            for(int ro=0; ro<P; ro++){
                                for(int varTau=0; varTau<T; varTau++){
                                    for(int varRo=0; varRo<P; varRo++){
                                        if(PsiL[t][p][tau][ro]==1){
                                            if(RR[tau][ro][varTau][varRo]==1){
                                                IloLinearNumExpr c6 = cplex.linearNumExpr();
                                                for(int i =0; i<I;i++){
                                                    if(varFi[i][s]==1){
                                                        c6.addTerm(1,x[i][s][b][varTau][varRo]);
                                                        c6.addTerm(1, x[i][s][b][t][p]);
                                                        c6.addTerm(-1,x[i][s][b][tau][ro]);
                                                    }
                                                }
                                                cplex.addLe(c6,1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int s=0; s<S; s++){
            for(int b=0; b<B;b++){
                for(int t=0; t<T; t++){
                    for(int p=0; p<P; p++){
                        for(int tau=0; tau<T; tau++){
                            for(int ro=0; ro<P; ro++){
                                for(int varTau=0; varTau<T; varTau++){
                                    for(int varRo=0; varRo<P; varRo++){
                                        if(PsiR[t][p][tau][ro]==1){
                                            if(RL[tau][ro][varTau][varRo]==1){
                                                IloLinearNumExpr c7 = cplex.linearNumExpr();
                                                for(int i =0; i<I;i++){
                                                    if(varFi[i][s]==1){
                                                        c7.addTerm(1,x[i][s][b][varTau][varRo]);
                                                        c7.addTerm(1, x[i][s][b][t][p]);
                                                        c7.addTerm(-1,x[i][s][b][tau][ro]);
                                                    }
                                                }
                                                cplex.addLe(c7,1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int b =0; b<B; b++){
            for(int p=0; p<P; p++){
                for(int i=0; i<I;i++){
                    if(ID[i]==1){
                        IloLinearNumExpr c8 = cplex.linearNumExpr();
                        for(int t=0; t<T; t++){
                            c8.addTerm(-1, x[i][D[i]][b][t][p]);
                        }
                        c8.addTerm(1, CL[i][b][p]);
                        c8.addTerm(1, CR[i][b][p]);
                        cplex.addGe(c8,0);
                    }
                }
            }
        }
        
    }

    public void exportarSolucion(String sCadena, ArrayList<ArrayList<String>>solucion) throws IOException{
        FileWriter fichero = null;
        PrintWriter pw = null;
        fichero = new FileWriter(sCadena);
        pw = new PrintWriter(fichero);
        for(int i=0; i<solucion.size();i++){
            for(int j=0; j<solucion.get(i).size();j++){
                pw.print(solucion.get(i).get(j) +" ");
            }
            pw.println();
        }
        pw.close();
    }
    protected void exportarSolucion(String sCadena) throws IloException, IOException{
        FileWriter fichero = null;
        PrintWriter pw = null;
            fichero = new FileWriter(sCadena);
            pw = new PrintWriter(fichero);
            pw.println("THF");
            THFS = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(THF)).setScale(0,BigDecimal.ROUND_HALF_UP)));
            pw.println(THFS);
            pw.println("Y");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        for(int t=0; t<periodo; t++){
                            try{
                                YS[h][c][f][t] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(Y[h][c][f][t])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                            }
                            catch(IloException e){
                                YS[h][c][f][t]=0;
                            }
                            
                            pw.print(YS[h][c][f][t]+" " );
                        }
                        pw.println();
                    }
                }
            }
            pw.println();
            pw.println("WI");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        for(int t=0; t<periodo; t++){
                            try{
                                WIS[h][c][f][t] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(WI[h][c][f][t])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                            }
                            catch(IloException e){
                                WIS[h][c][f][t]=0;
                            }
                            pw.print(WIS[h][c][f][t] + " ");
                        }
                        pw.println();
                    }
                }
            }
            pw.println();
            pw.println("X");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        for(int t=0; t<periodo; t++){
                            try{
                               XS[h][c][f][t] = cplex.getValue(X[h][c][f][t]); 
                            }
                            catch(IloException e){
                                XS[h][c][f][t]=0;
                            }
                            pw.print(XS[h][c][f][t] + " ");
                        }
                        pw.println();
                    }
                }
            }
            pw.println();
            pw.println("INI");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        for(int t=0; t<periodo; t++){
                            try{
                                INIS[h][c][f][t] =cplex.getValue(INI[h][c][f][t]);
                            }
                            catch(IloException e){
                                INIS[h][c][f][t]=0;
                            }
                            pw.print(INIS[h][c][f][t]+ " ");
                        }
                        pw.println();
                    }
                    
                }
            }        
            pw.println();
            pw.println("S");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        SS[h][c][f] = cplex.getValue(S[h][c][f]);
                        pw.print(SS[h][c][f]+ " ");
                    }
                    pw.println();
                }
            }
            pw.println();
            
            pw.println("BF");
            for(int i=0; i<periodo; i++){
                BFS[i] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(BF[i])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                pw.print(BFS[i] + " ");
            }
            pw.println();
            pw.println("TFV");
            for(int i=0; i<periodo; i++){
                TFVS[i] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(TFV[i])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                pw.print(TFVS[i] + " ");
            }
            pw.println();
            
            pw.println("THV");
            for(int i=0; i<periodo; i++){
                THVS[i] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(THV[i])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                pw.print(THVS[i] + " ");
            }
            pw.println();
            
            pw.println("TV");
            for(int i=0; i<periodo; i++){
                TVS[i] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(TV[i])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                pw.print(TVS[i] + " ");
            }
            pw.println();
            pw.println("TVC");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    if(modoDeCosecha[h][c]==1){
                        for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                            for(int t=0; t<periodo; t++){
                                try{
                                    TVCS[h][c][f][t] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(TVC[h][c][f][t])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                                }
                                catch(IloException e){
                                    TVCS[h][c][f][t]=0;
                                }
                                pw.print(TVCS[h][c][f][t] + " ");
                            }
                            pw.println();
                        }
                    }
                }
            }
            pw.println();
            pw.println("NB");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        for(int t=0; t<periodo; t++){
                            try{
                                NBS[h][c][f][t] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(NB[h][c][f][t])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                            }
                            catch(IloException e){
                                NBS[h][c][f][t]=0;
                            }
                            pw.print(NBS[h][c][f][t] + " ");
                        }
                        pw.println();
                    }
                }
            }
            pw.println();
            pw.println("TFC");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    if(modoDeCosecha[h][c]==1){
                        for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                            for(int t=0; t<periodo; t++){
                                try{
                                    TFCS[h][c][f][t] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(TFC[h][c][f][t])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                                }
                                catch(IloException e){
                                    TFCS[h][c][f][t]=0;
                                }
                                
                                pw.print(TFCS[h][c][f][t] + " ");
                            }
                            pw.println();
                        }
                    }
                }
            } 
            pw.println();
            pw.println("FI");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        FIS[h][c][f] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(FI[h][c][f])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                        pw.print(FIS[h][c][f] + " ");
                    }
                    pw.println();
                }
            }
            pw.println();
            pw.println("FT");
            for(int h=0; h<huertos; h++){
                for(int c=0; c<cuartelesPorHuerto[h]; c++){
                    for(int f=0; f< pasadasPorCuartel[h][c]; f++){
                        FTS[h][c][f] = Integer.parseInt(String.valueOf(new BigDecimal(cplex.getValue(FT[h][c][f])).setScale(0,BigDecimal.ROUND_HALF_UP)));
                        pw.print(FTS[h][c][f]+ " ");
                    }
                    pw.println();
                }
            }  
            pw.println();
            pw.close();
        } 
        final int[][][][] obtenerY(){
            return YS;
        }
        final int obtenerTHF(){
            return THFS;
        }
        final int [][][][] obtenerWI(){
            return WIS;
        }
        final double[][][][]obtenerX(){
            return XS;
        }
        final double [][][][] obtenerINI(){
            return INIS;
        }
        final double [][][] obtenerS(){
            return SS;
        }
        final int [] obtenerBF(){
            return BFS;
        }
        final int[] obtenerTFV(){
            return TFVS;
        }
        final int[] obtenerTHV(){
            return THVS;
        }
        final int[] obtenerTV(){
            return TVS;
        }
        final int[][][][] obtenerTVC(){
            return TVCS;
        }
        final int[][][][] obtenerNB(){
            return NBS;
        }
        final int[][][][] obtenerTFC(){
            return TFCS;
        }
        final int[][][] obtenerFI(){
            return FIS;
        }
        final int[][][] obtenerFT(){
            return FTS;
        }

    }
