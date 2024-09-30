/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificaciondecosecha;

/**
 *
 * @author Javier
 */
public class TransformarString {
    public static int[] retornarHuerto(String sCadena){
        String [] Arreglo = sCadena.split(",");
        int [] DD= new int [Arreglo.length];
        for(int i=0; i< Arreglo.length; i++){
            Arreglo[i] = Arreglo[i].trim();
            DD[i] = Integer.parseInt(Arreglo[i].trim());
        }
        return(DD);
    }
    public static int[][] retornarHuertoCuartel(String sCadena, int huerto, int [] cuartelesPorHuerto){
        String [] Arreglo = sCadena.split(",");
        int [][] DD = new int[huerto][];
        int k=0;
        for(int i=0; i<huerto; i++){
            DD[i] = new int[cuartelesPorHuerto[i]];
            for(int j=0; j<cuartelesPorHuerto[i]; j++){
                DD[i][j] = Integer.parseInt(Arreglo[k].trim());
                k++;
            }
        }
        return DD;
    }
    public static int[][][] retornarHuertoCuartelPasada(String sCadena, int huerto, int[] cuartelesPorHuerto, int[][] pasadasPorCuartel){
        String [] arreglo = sCadena.split(",");
        int [][][] DD = new int [huerto][][];
        int l=0;
        for(int i=0; i<huerto; i++){
            DD[i] = new int[cuartelesPorHuerto[i]][];
            for(int j=0; j<cuartelesPorHuerto[i]; j++){
                DD[i][j] = new int[pasadasPorCuartel[i][j]];
                for(int k=0; k< pasadasPorCuartel[i][j]; k++){
                    DD[i][j][k] = Integer.parseInt(arreglo[l].trim());
                    l++;
                }
            }
        }
        return DD;
    }
    public static int[][][][] retornarHuertoCuartelPasadaPeriodo(String sCadena, int huerto, int[] cuartelesPorHuerto, int [][] pasadasPorCuartel, int periodo){
        String [] arreglo = sCadena.split(",");
        int [][][][] DD = new int [huerto][][][];
        int l=0;
        for(int i=0; i<huerto; i++){
            DD[i] = new int[cuartelesPorHuerto[i]][][];
            for(int j=0; j<cuartelesPorHuerto[i]; j++){
                DD[i][j] = new int[pasadasPorCuartel[i][j]][];
                for(int k=0; k< pasadasPorCuartel[i][j]; k++){
                    DD[i][j][k] = new int[periodo];
                    for(int t=0; t<periodo; t++){
                        DD[i][j][k][t] = Integer.parseInt(arreglo[l].trim());
                        l++;
                    }
                }
            }
        }
        return DD;        
    }
    public static int[][][][] retornarHuertoCuartelPasadaCosecha(String sCadena, int huerto, int[] cuartelesPorHuerto, int [][] pasadasPorCuartel){
        String [] arreglo = sCadena.split(",");
        int [][][][] DD = new int [huerto][][][];
        int l=0;
        for(int i=0; i<huerto; i++){
            DD[i] = new int[cuartelesPorHuerto[i]][][];
            for(int j=0; j<cuartelesPorHuerto[i]; j++){
                DD[i][j] = new int[pasadasPorCuartel[i][j]][];
                for(int k=0; k< pasadasPorCuartel[i][j]; k++){
                    DD[i][j][k] = new int[2];
                    for(int t=0; t<2; t++){
                        DD[i][j][k][t] = Integer.parseInt(arreglo[l].trim());
                        l++;
                    }
                }
            }
        }
        return DD;        
    }
    public static double[][] retornarPlantaPeriodo(String sCadena, int periodo){
        String [] arreglo = sCadena.split(",");
        double [][] DD = new double[2][periodo];
        int l=0;
        for(int i=0; i<2; i++){
            for(int j=0; j<periodo; j++){
                DD[i][j] = Double.parseDouble(arreglo[l].trim());
                l++;
            }
        }
        return DD;
    }
    public static int[][] retornarHuertoPeriodo(String sCadena, int huerto, int periodo){
        String[] arreglo = sCadena.split(",");
        int [][] DD = new int [huerto][periodo];
        int l=0;
        for(int i=0; i<huerto; i++){
            for(int j=0; j<periodo; j++){
                DD[i][j] = Integer.parseInt(arreglo[l].trim());
                l++;
            }
        }
        return DD;
    }
    public static double[] retornarTipoDeTrabajador(String sCadena){
        String[] arreglo = sCadena.split(",");
        double [] DD = new double [2];
        int l=0;
        for(int i=0; i<2; i++){
            DD[i] = Double.parseDouble(arreglo[l].trim());
            l++;
        }
        return DD;
    }
    public static double[][][][] retornarHuertoCuartelPasadaCosechaD(String sCadena, int huerto, int[] cuartelesPorHuerto, int [][] pasadasPorCuartel){
        String [] arreglo = sCadena.split(",");
        double [][][][] DD = new double [huerto][][][];
        int l=0;
        for(int i=0; i<huerto; i++){
            DD[i] = new double[cuartelesPorHuerto[i]][][];
            for(int j=0; j<cuartelesPorHuerto[i]; j++){
                DD[i][j] = new double[pasadasPorCuartel[i][j]][];
                for(int k=0; k< pasadasPorCuartel[i][j]; k++){
                    DD[i][j][k] = new double[2];
                    for(int t=0; t<2; t++){
                        DD[i][j][k][t] = Double.parseDouble(arreglo[l].trim());
                        l++;
                    }
                }
            }
        }
        return DD;        
    }
    public static double[][][][] retornarHuertoCuartelPasadaPeriodoD(String sCadena, int huerto, int[] cuartelesPorHuerto, int [][] pasadasPorCuartel, int periodo){
        String [] arreglo = sCadena.split(",");
        double [][][][] DD = new double [huerto][][][];
        int l=0;
        for(int i=0; i<huerto; i++){
            DD[i] = new double[cuartelesPorHuerto[i]][][];
            for(int j=0; j<cuartelesPorHuerto[i]; j++){
                DD[i][j] = new double[pasadasPorCuartel[i][j]][];
                for(int k=0; k< pasadasPorCuartel[i][j]; k++){
                    DD[i][j][k] = new double[periodo];
                    for(int t=0; t<periodo; t++){
                        DD[i][j][k][t] = Double.parseDouble(arreglo[l].trim());
                        l++;
                    }
                }
            }
        }
        return DD;        
    }
}
