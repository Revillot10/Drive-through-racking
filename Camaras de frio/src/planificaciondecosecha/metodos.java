/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificaciondecosecha;

import ilog.concert.IloException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class metodos {
    private int huertos; //Listo
    private int W;
    private int E;
    private int [][][] Ri;
    private int [][][] Si;
    private int Ni;
    private int tiposDeCosecha; //Listo
    private int [][] modoDeCosecha; //Listo
    private int plantas; //Listo
    private int[] cuartelesPorHuerto; //Listo
    private int[] L;
    private int[][] pasadasPorCuartel; //Listo
    private int[][][][] PL;
    private int [][][][] N;
    private int [][][] D;
    private double [][] G;
    private int periodo;
    private double Q;
    private double[] H;
    private double[] F;
    private double[][][][] J;
    private double Po;
    private double[][][][] A; //Listo
    private int Pi[][][];
    private int I[][];
    private int Inicio[][][];
    private int Fin[][][];
    private int M1;
    private int M2;
    public metodos() throws IOException{
        String link = "C:\\Users\\javie\\Dropbox\\Universidad\\Doctorado\\Trabajo de Doctorado\\Planificacion de Cosecha MultiObjetivo\\servidor 2\\Cosecha Multiobjetivo\\InstanciaReal\\";
        String huertosA = IngresoArchivoExterno.leer(link+"huertos.txt");
        String WA = IngresoArchivoExterno.leer(link+"W.txt");
        String EA = IngresoArchivoExterno.leer(link+"E.txt");
        String RiA = IngresoArchivoExterno.leer(link+"Ri.txt");
        String SiA = IngresoArchivoExterno.leer(link+"Si.txt");
        String NiA = IngresoArchivoExterno.leer(link+"Ni.txt");
        String tiposDeCosechaA = IngresoArchivoExterno.leer(link+"tiposDeCosecha.txt");
        String modoDeCosechaA = IngresoArchivoExterno.leer(link+"ModosDeCosecha.txt");
        String plantasA = IngresoArchivoExterno.leer(link+"Plantas.txt");
        String cuartelesPorHuertoA = IngresoArchivoExterno.leer(link+"CuartelesPorHuerto.txt");
        String LA = IngresoArchivoExterno.leer(link+"L.txt");
        String pasadasPorCuartelA = IngresoArchivoExterno.leer(link+"PasadasPorCuartel.txt");
        String PLA = IngresoArchivoExterno.leer(link+"PL.txt");
        String NA = IngresoArchivoExterno.leer(link+"N.txt");
        String DA = IngresoArchivoExterno.leer(link+"D.txt");
        String GA = IngresoArchivoExterno.leer(link+"G.txt");
        String periodoA = IngresoArchivoExterno.leer(link+"Periodo.txt");
        String QA = IngresoArchivoExterno.leer(link+"Q.txt");
        String HA = IngresoArchivoExterno.leer(link+"H.txt");
        String FA = IngresoArchivoExterno.leer(link+"F.txt");
        String JA = IngresoArchivoExterno.leer(link+"J.txt");
        String PoA = IngresoArchivoExterno.leer(link+"Po.txt");
        String AA = IngresoArchivoExterno.leer(link+"A.txt");
        String PiA = IngresoArchivoExterno.leer(link+"Pi.txt");
        String IA = IngresoArchivoExterno.leer(link+"I.txt");
        String InicioA  =  IngresoArchivoExterno.leer(link+"Inicio.txt");
        String FinA  =  IngresoArchivoExterno.leer(link+"Fin.txt");
        String M1A = "3000000";//IngresoArchivoExterno.leer("");
        String M2A = "100";//IngresoArchivoExterno.leer("");
        huertos = Integer.parseInt(huertosA); //Listo
        W = Integer.parseInt(WA);
        E = Integer.parseInt(EA);
        periodo = Integer.parseInt(periodoA);
        cuartelesPorHuerto = TransformarString.retornarHuerto(cuartelesPorHuertoA);
        pasadasPorCuartel = TransformarString.retornarHuertoCuartel(pasadasPorCuartelA, huertos, cuartelesPorHuerto);
        Ri = TransformarString.retornarHuertoCuartelPasada(RiA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        Si = TransformarString.retornarHuertoCuartelPasada(SiA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        Pi = TransformarString.retornarHuertoCuartelPasada(PiA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        D = TransformarString.retornarHuertoCuartelPasada(DA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        Inicio = TransformarString.retornarHuertoCuartelPasada(InicioA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        Fin = TransformarString.retornarHuertoCuartelPasada(FinA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        Ni = Integer.parseInt(NiA);
        tiposDeCosecha = Integer.parseInt(tiposDeCosechaA);
        //s on 2 mecánico y manual
        modoDeCosecha = TransformarString.retornarHuertoCuartel(modoDeCosechaA, huertos, cuartelesPorHuerto);
        // por que modo se cosecha cada cuartel, 1 mecánico, 2 manual
        plantas = Integer.parseInt(plantasA);
        L = TransformarString.retornarHuerto(LA);
        PL = TransformarString.retornarHuertoCuartelPasadaCosecha(PLA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        N = TransformarString.retornarHuertoCuartelPasadaPeriodo(NA, huertos, cuartelesPorHuerto, pasadasPorCuartel, periodo);
        G = TransformarString.retornarPlantaPeriodo(GA, periodo);
        Q = Double.parseDouble(QA);
        Po =Double.parseDouble(PoA);
        M1 = Integer.parseInt(M1A);
        M2 = Integer.parseInt(M2A);
        H = TransformarString.retornarTipoDeTrabajador(HA);
        F = TransformarString.retornarTipoDeTrabajador(FA);
        J = TransformarString.retornarHuertoCuartelPasadaCosechaD(JA, huertos, cuartelesPorHuerto, pasadasPorCuartel);
        A = TransformarString.retornarHuertoCuartelPasadaPeriodoD(AA, huertos, cuartelesPorHuerto, pasadasPorCuartel, periodo);
        I = TransformarString.retornarHuertoPeriodo(IA, huertos, periodo);
        try {
            System.out.println("");//modeloMatematico m = new Lexicografico(tiposDeCosecha, huertos, Inicio, Fin, cuartelesPorHuerto, pasadasPorCuartel,   modoDeCosecha, plantas, periodo, A, H,F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2 );
            //modeloMatematico m = new metodoDeLosPesos(tiposDeCosecha, huertos, Inicio, Fin, cuartelesPorHuerto, pasadasPorCuartel,   modoDeCosecha, plantas, periodo, A, H,F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2 );
            //modeloMatematico m = new metodoDeLosPesosPonderado(tiposDeCosecha, huertos,Inicio, Fin,  cuartelesPorHuerto, pasadasPorCuartel,   modoDeCosecha, plantas, periodo, A, H,F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2 );
            //modeloMatematico m = new metodoLPMetric(tiposDeCosecha, huertos, Inicio, Fin, cuartelesPorHuerto, pasadasPorCuartel,   modoDeCosecha, plantas, periodo, A, H,F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2 );
            //modeloMatematico m = new WeightedMetric(tiposDeCosecha, huertos,Inicio, Fin,  cuartelesPorHuerto, pasadasPorCuartel,   modoDeCosecha, plantas, periodo, A, H,F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2 );
            //modeloMatematico m = new eConstraint(tiposDeCosecha, huertos,Inicio, Fin,  cuartelesPorHuerto, pasadasPorCuartel,   modoDeCosecha, plantas, periodo, A, H,F, J, Q, Pi, Ri, Si, D, G, N, L, I, Ni, W, E, PL, Po, M1, M2 );

        } catch (Exception ex) {
            Logger.getLogger(metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
