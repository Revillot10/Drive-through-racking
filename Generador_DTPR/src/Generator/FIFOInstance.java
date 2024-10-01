package Generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FIFOInstance {
    
      int Inv=0;  
      int Trucks=2;
      int palletsTrucks=6;        
      int I=Trucks*palletsTrucks;  
      int B=1;
      int T=6;
      int P=5;
      int S=2*Trucks+1;  
      int K=1; 
      ArrayList availableStages= new ArrayList();
      ArrayList<Integer> A= new ArrayList();
      ArrayList<Integer> D= new ArrayList();  
      int[][] varFi = new int[I][S];
      int[][] Omega = new int[I][S]; 
      int [][][][] RL = new int[T][P][T][P];
      int [][][][] RR = new int[T][P][T][P];
      int [][][][] PsiL = new int[T][P][T][P];;
      int [][][][] PsiR = new int[T][P][T][P];;     

    public FIFOInstance() {
       
        
      for(int i=1;i<S;i++){
          availableStages.add(i);
      }
      System.out.println("Available Stages:"+availableStages);      
      
      for(int i=0;i<Inv;i++){
           A.add(0);
       }
      for(int i=0;i<Trucks;i++){
          for(int j=0;j<palletsTrucks;j++){
                A.add(i+1);
           }
      }
      for(int i=Trucks;i<Inv+2*Trucks;i++){
          for(int j=0;j<palletsTrucks;j++){
              D.add(i+1);
           }
      }
      
      System.out.println();
      System.out.println("Llegada Instancia FIFO"+A);
      System.out.println("Salida Instancia FIFO"+D); 
      
      for (int i=0; i<S;i++){
            for(int j=0; j<I;j++){
                if(A.get(j) == i){ 
                    varFi[j][i]= 1 ;
                    System.out.println("El pallet " +(j+1)+ " llega en la etapa "+A.get(j)); 
                } 
                if(D.get(j) == i){
                    Omega[j][i]= 1 ;
                    System.out.println("El pallet " +(j+1)+ " sale en la etapa "+D.get(j)); 
                }
            }
        }
        
        for(int i=0 ; i<I; i++){
            System.out.println("varFi"+Arrays.toString(varFi[i]));
        }
        for(int i=0 ; i<I; i++){
            System.out.println("Omega"+Arrays.toString(Omega[i]));
        }
 
        //MATRIX RL         
        for(int t=0; t<T; t++){
            for(int p=0; p<P; p++){
                for(int tp =0 ; tp<T; tp++){
                    for(int pp=0; pp<P; pp++){
                        if(tp<=t && pp>p){
                            RL[t][p][tp][pp]=1;
                        }
                        else {
                            RL[t][p][tp][pp]=0;
                        }
                    }
                }
            }
        }
                        
        //MATRIX RR 
        for(int t=0; t<T; t++){
            for(int p=0; p<P; p++){
                for(int tp =0 ; tp<T; tp++){
                    for(int pp=0; pp<P; pp++){
                        if(tp<=t && pp<p){
                            RR[t][p][tp][pp]=1;
                        }
                        else {
                            RR[t][p][tp][pp]=0;
                        }
                    }
                }
            }
        }

        // Create a StringBuilder to store the RL-RR array
        StringBuilder rlBuilder = new StringBuilder("RL=[\n");
        StringBuilder rrBuilder = new StringBuilder("RR=[\n");
        
        //Writing the RL matrix in 4D format
        for (int t = 0; t < T; t++) {
          rlBuilder.append("  [\n"); 
          for (int p = 0; p < P; p++) {
              rlBuilder.append("    ["); 
              for (int tp = 0; tp < T; tp++) {
                  rlBuilder.append("["); 
                  for (int pp = 0; pp < P; pp++) {
                      if (pp > 0) {
                          rlBuilder.append(", "); 
                      }
                      rlBuilder.append(RL[t][p][tp][pp]); 
                  }
                  rlBuilder.append("]"); 
                  if (tp < T - 1) {
                      rlBuilder.append(", "); 
                  }
              }
              rlBuilder.append("]"); 
              if (p < P - 1) {
                  rlBuilder.append(",\n"); 
              } else {
                  rlBuilder.append("\n"); 
              }
          }
          rlBuilder.append("  ]"); 
          if (t < T - 1) {
              rlBuilder.append(",\n"); 
          }
      }
      rlBuilder.append("\n];"); 
        
        
        //Writing the RR matrix in 4D format
        for (int t = 0; t < T; t++) {
           rrBuilder.append("  [\n"); 
           for (int p = 0; p < P; p++) {
               rrBuilder.append("    ["); 
               for (int tp = 0; tp < T; tp++) {
                   rrBuilder.append("["); 
                   for (int pp = 0; pp < P; pp++) {
                       if (pp > 0) {
                           rrBuilder.append(", "); 
                       }
                       rrBuilder.append(RR[t][p][tp][pp]); 
                   }
                   rrBuilder.append("]"); 
                   if (tp < T - 1) {
                       rrBuilder.append(", "); 
                   }
               }
               rrBuilder.append("]"); 
               if (p < P - 1) {
                   rrBuilder.append(",\n"); 
               } else {
                   rrBuilder.append("\n"); 
               }
           }
           rrBuilder.append("  ]"); 
           if (t < T - 1) {
               rrBuilder.append(",\n"); 
           }
       }
       rrBuilder.append("\n];");
       
        //MATRIX PsiL         
        for(int t=0; t<T; t++){
            for(int p=0; p<P; p++){
                for(int tp =0 ; tp<T; tp++){
                    for(int pp=0; pp<P; pp++){
                        if(tp>=t && pp<p){
                            PsiL[t][p][tp][pp]=1;
                        }
                        else {
                            PsiL[t][p][tp][pp]=0;
                        }
                    }
                }
            }
        }
        
        //MATRIX PsiR
        for(int t=0; t<T; t++){
            for(int p=0; p<P; p++){
                for(int tp =0 ; tp<T; tp++){
                    for(int pp=0; pp<P; pp++){
                        if(tp>=t && pp>p){
                            PsiR[t][p][tp][pp]=1;
                        }
                        else {
                            PsiR[t][p][tp][pp]=0;
                        }
                    }
                }
            }
        }
        
        // Create a StringBuilder to store the RL-RR array
        StringBuilder PsiLBuilder = new StringBuilder("PsiL=[\n");
        StringBuilder PsiRBuilder = new StringBuilder("PsiR=[\n");    
        
        //Writing the PsiL matrix in 4D format
        for (int t = 0; t < T; t++) {
          PsiLBuilder.append("  [\n"); 
          for (int p = 0; p < P; p++) {
              PsiLBuilder.append("    ["); 
              for (int tp = 0; tp < T; tp++) {
                  PsiLBuilder.append("["); 
                  for (int pp = 0; pp < P; pp++) {
                      if (pp > 0) {
                          PsiLBuilder.append(", "); 
                      }
                      PsiLBuilder.append(PsiL[t][p][tp][pp]); 
                  }
                  PsiLBuilder.append("]"); 
                  if (tp < T - 1) {
                      PsiLBuilder.append(", "); 
                  }
              }
              PsiLBuilder.append("]"); 
              if (p < P - 1) {
                  PsiLBuilder.append(",\n"); 
              } else {
                  PsiLBuilder.append("\n"); 
              }
          }
          PsiLBuilder.append("  ]"); 
          if (t < T - 1) {
              PsiLBuilder.append(",\n"); 
          }
      }
      PsiLBuilder.append("\n];"); 
        
        
        //Writing the PsiR matrix in 4D format
        for (int t = 0; t < T; t++) {
          PsiRBuilder.append("  [\n"); 
          for (int p = 0; p < P; p++) {
              PsiRBuilder.append("    ["); 
              for (int tp = 0; tp < T; tp++) {
                  PsiRBuilder.append("["); 
                  for (int pp = 0; pp < P; pp++) {
                      if (pp > 0) {
                          PsiRBuilder.append(", "); 
                      }
                      PsiRBuilder.append(PsiR[t][p][tp][pp]); 
                  }
                  PsiRBuilder.append("]"); 
                  if (tp < T - 1) {
                      PsiRBuilder.append(", "); 
                  }
              }
              PsiRBuilder.append("]"); 
              if (p < P - 1) {
                  PsiRBuilder.append(",\n"); 
              } else {
                  PsiRBuilder.append("\n"); 
              }
          }
          PsiRBuilder.append("  ]"); 
          if (t < T - 1) {
              PsiRBuilder.append(",\n"); 
          }
      }
      PsiRBuilder.append("\n];");
      
      
        
        try{   
            ////EXPORTAR LLEGADA SALIDA Y ESTANCIA  
            BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter ("Instancias\\DTPR_FIFO.txt"));
            PrintWriter printWriter= new PrintWriter(bufferedWriter);   
            printWriter.println("I="+I+";");
            printWriter.println("B="+B+";"); 
            printWriter.println("T="+T+";"); 
            printWriter.println("P="+P+";"); 
            printWriter.println("S="+(S-1)+";");  
            printWriter.println("A="+A+";");
            printWriter.println("D="+D+";");
            printWriter.println("K="+K+";");
            
            printWriter.print("varFi=[");
            for(int i=0 ; i<I; i++){
                printWriter.print(Arrays.toString(varFi[i]));
                if(i!=I-1){
                    printWriter.print(",");
                }
            }
            printWriter.print("];");
            printWriter.println();  
            
            printWriter.print("Omega=[");
            for(int i=0 ; i<I; i++){
                printWriter.print(Arrays.toString(Omega[i]));
                if(i!=I-1){
                    printWriter.print(",");
                }
            }
            printWriter.print("];");
            printWriter.println();            
            printWriter.write(rlBuilder.toString());
            printWriter.println();
            printWriter.write(rrBuilder.toString()); 
            
            printWriter.println();            
            printWriter.write(PsiLBuilder.toString());
            printWriter.println();
            printWriter.write(PsiRBuilder.toString()); 
            
            bufferedWriter.close();            
                        
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
}
