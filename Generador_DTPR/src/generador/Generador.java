package generador;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Generador {

    public static void main(String[] args) {        
      
 /*Case study 3PL (Third Party Logistics): storage requests result from receiving an inbound truckload and the    
 outbound freight request can be specified by the customer or determined by the distribution center using machine learning. */
  
/*In other cases, storage requests result from receiving a production batch and retrieval request 
 results from customer demand*/

/*
A Y D OK-> MODIFICAR EL .DAT
*/

//Dedicated storage vs shared storage
        
      int Inv=0;  
      int Transportes=2;
      int Pallet_transporte=12;        
      int Pallet=Transportes*Pallet_transporte;  
      
      //Generar etapas-Se calcula el total de etapas (almacenamiento+recuperacion)
      int Etapas=2*Transportes;    
      
      ArrayList vector= new ArrayList();
      ArrayList Allegada= new ArrayList();
      ArrayList ASalida= new ArrayList(); 
      
      //RANDOM GENERAR PARÁMETROS
      for(int i=0;i<Etapas;i++){
           vector.add(i+1);
      }     
      
      System.out.println("Etapas disponibles:"+vector);
      
      Random r = new Random();     
      
      //CALCULAR LLEGADA/SALIDA ALEATORIA DE CADA PALLET                      
      for (int i=0; i<Transportes;i++){
            int aleatoriosalida,aleatoriollegada,llegadapallet,salidapallet;             
            //calcular llegada
            do{                 
                llegadapallet=(r.nextInt(vector.size())); 
                aleatoriollegada=(int) vector.get(llegadapallet);
            } while(llegadapallet>=vector.size()-1); 
                  for(int j=0;j<Pallet_transporte;j++){
                      Allegada.add(aleatoriollegada);
                  }
                  vector.remove(llegadapallet); 
                  System.out.println("El camion "+(i+1)+ " llega en la etapa "+aleatoriollegada+" "+Allegada );
                  System.out.println("Etapas disponibles:"+vector);
            //calcular salida             
             do{
                 salidapallet=(r.nextInt(vector.size()));
                 aleatoriosalida=(int) vector.get(salidapallet);
             } while( (aleatoriosalida==1) || (aleatoriosalida<=aleatoriollegada)); 
                  for(int j=0;j<Pallet_transporte;j++){
                      ASalida.add(aleatoriosalida); 
             }
             vector.remove(salidapallet);  
             System.out.println("El camion "+(i+1)+ " sale en la etapa "+aleatoriosalida+" "+ASalida);
             System.out.println("Etapas disponibles:"+vector);
        } 
        System.out.println("Llegada Instancia RANDOM"+Allegada);
        System.out.println("Salida Instancia RANDOM"+ASalida);  
        
        try{   
            ////EXPORTAR LLEGADA SALIDA Y ESTANCIA GENERADOR DE INSTANCIAS  
            BufferedWriter bsa= new BufferedWriter(new FileWriter ("Instancias\\Generador_DTPR_RANDOM.dat"));
            PrintWriter bsaa= new PrintWriter(bsa);                        
                       
            bsaa.println("ETAPAS="+Etapas+";");
            bsaa.println("PALLETS="+Pallet+";");            
            bsaa.println();
            
            bsaa.print("A=[");
            //Construir matriz de llegada
            for(int i=0;i<Inv+Pallet;i++){  
                for(int j=0;j<=Etapas;j++){
                    if(Integer.parseInt(Allegada.get(i).toString())==j){                        
                        if(j==0){
                           bsaa.print("[1,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("1],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("1,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("1]]");
                        }
                    }
                    if(Integer.parseInt(Allegada.get(i).toString())!=j){                        
                        if(j==0){
                           bsaa.print("[0,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("0],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("0,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("0]]");
                        }
                    }                    
                }
            }
            bsaa.print(";");
            bsaa.println();            

            //Construir matriz de salida
            bsaa.print("D=[");
            for(int i=0;i<Inv+Pallet;i++){
                for(int j=0;j<=Etapas;j++){
                    if(Integer.parseInt(ASalida.get(i).toString())==j){                        
                        if(j==0){
                           bsaa.print("[1,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("1],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("1,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("1]]");
                        }
                    }
                    if(Integer.parseInt(ASalida.get(i).toString())!=j){                        
                        if(j==0){
                           bsaa.print("[0,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("0],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("0,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("0]]");
                        }
                    }                    
                }             
            }
            bsaa.print(";");
            bsaa.println();
            
            //Construir matriz de estancia           
            bsaa.print("Q=[");
            for(int i=0;i<Inv+Pallet;i++){
                for(int j=0;j<=Etapas;j++){
                    if((j>=Integer.parseInt(Allegada.get(i).toString()))&&(j<=Integer.parseInt(ASalida.get(i).toString()))){                        
                        if(j==0){
                           bsaa.print("[1,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("1],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("1,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("1]]");
                        }
                    }
                    else{
                        if(j==0){
                           bsaa.print("[0,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("0],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("0,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("0]]");
                        }                        
                    }                    
                }             
            }
            bsaa.print(";"); 
            bsa.close();            
                        
        }catch(Exception e){
            e.printStackTrace();
        }     
      

      //FIFO - GENERAR PARÁMETROS 
      ArrayList Fllegada= new ArrayList();
      ArrayList FSalida= new ArrayList();
      
      for(int i=0;i<Inv;i++){//agregar llegada en etapa 0 de pallets en inventario
           Fllegada.add(0);
       }
      for(int i=0;i<Transportes;i++){//agregar etapa de llegada
          for(int j=0;j<Pallet_transporte;j++){
                Fllegada.add(i+1);
           }
      }
      for(int i=Transportes;i<Inv+2*Transportes;i++){//agregar etapa de salida
          for(int j=0;j<Pallet_transporte;j++){
              FSalida.add(i+1);
           }
      }
      
      System.out.println();
      System.out.println("Llegada Instancia FIFO"+Fllegada);
      System.out.println("Salida Instancia FIFO"+FSalida); 
      
     
        try{   
            ////EXPORTAR LLEGADA SALIDA Y ESTANCIA GENERADOR DE INSTANCIAS  
            BufferedWriter bsa= new BufferedWriter(new FileWriter ("Instancias\\Generador_DTPR_FIFO.dat"));
            PrintWriter bsaa= new PrintWriter(bsa);                        
                       
            bsaa.println("ETAPAS="+Etapas+";");
            bsaa.println("PALLETS="+Pallet+";");            
            bsaa.println();
            
            bsaa.print("A=[");
            //Construir matriz de llegada
            for(int i=0;i<Inv+Pallet;i++){  
                for(int j=0;j<=Etapas;j++){
                    if(Integer.parseInt(Fllegada.get(i).toString())==j){                        
                        if(j==0){
                           bsaa.print("[1,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("1],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("1,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("1]]");
                        }
                    }
                    if(Integer.parseInt(Fllegada.get(i).toString())!=j){                        
                        if(j==0){
                           bsaa.print("[0,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("0],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("0,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("0]]");
                        }
                    }                    
                }
            }
            bsaa.print(";");
            bsaa.println();            

            //Construir matriz de salida
            bsaa.print("D=[");
            for(int i=0;i<Inv+Pallet;i++){
                for(int j=0;j<=Etapas;j++){
                    if(Integer.parseInt(FSalida.get(i).toString())==j){                        
                        if(j==0){
                           bsaa.print("[1,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("1],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("1,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("1]]");
                        }
                    }
                    if(Integer.parseInt(FSalida.get(i).toString())!=j){                        
                        if(j==0){
                           bsaa.print("[0,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("0],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("0,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("0]]");
                        }
                    }                    
                }             
            }
            bsaa.print(";");
            bsaa.println();
            
            //Construir matriz de estancia           
            bsaa.print("Q=[");
            for(int i=0;i<Inv+Pallet;i++){
                for(int j=0;j<=Etapas;j++){
                    if((j>=Integer.parseInt(Fllegada.get(i).toString()))&&(j<=Integer.parseInt(FSalida.get(i).toString()))){                        
                        if(j==0){
                           bsaa.print("[1,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("1],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("1,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("1]]");
                        }
                    }
                    else{
                        if(j==0){
                           bsaa.print("[0,");
                        }
                        if(j==Etapas && (i<Inv+Pallet-1)){
                           bsaa.print("0],");
                        }
                        if(j!=Etapas && j!=0 ){
                           bsaa.print("0,");
                        }
                        if(j==Etapas && (i==Inv+Pallet-1)){
                           bsaa.print("0]]");
                        }                        
                    }                    
                }             
            }
            bsaa.print(";"); 
            bsa.close();            
                        
        }catch(Exception e){
            e.printStackTrace();
        }        
    }    
}
