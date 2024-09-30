/*********************************************
 * OPL 12.5 Model
 * Author: David
 * Creation Date: 31-07-2016 at 12:04:31
 *********************************************/
//Definición de índices y conjuntos

int ETAPAS=...; 
int PALLETS=...;
int BANDAS=...;
int NIVELES=...;
int PROFUNDIDAD=...;

range S=0..ETAPAS;
range I=1..PALLETS;
range B=1..BANDAS;
range N=1..NIVELES;
range P=1..PROFUNDIDAD;

//Creación de tuplas
tuple periodo
{
   int s;
   int i;
} 
tuple periodoij
{
   int s;
   int j;
   int i;
}
 //Definición de parámetros
 int A[I][S]=...;//1 si el pallet i se almacena en la etapa s.
 int D[I][S]=...;//1 si el pallet i se recupera en la etapa s
 int Q[I][S]=...;//1 si el pallet i se encuentra en la cámara.
 
//Creación de conjuntos
setof (periodo) periodos = {<s,i > | i in I, s in S : Q[i][s]==1 };
setof (periodoij) periodoijs = {<s,j,i > | i in I, s in S, j in I:  D[j][s]==1 && Q[i][s]==1 && i!=j };
 
 //Definición de variables
 dvar boolean x[periodos][B][N][P];
 dvar boolean u[periodoijs];
 dvar boolean v[periodoijs];
 dvar boolean r[periodoijs];
 dvar float+ l[periodoijs];
 dvar boolean y[periodoijs];
 
 //Definición de expresión: Restricción (11) 
dexpr float z[<s,j,i> in periodoijs] = u[<s,j,i>]+v[<s,j,i>]-1;

 //(1)Función objetivo: minimizar el número de reubicaciones
minimize sum(<s,j,i> in periodoijs) (y[<s,j,i>]);
 
subject to{

//(2-3)Cada pallet debe permanecer entre su etapa de llegada y salida en solo una ranura del rack.
forall(<s,i> in periodos){        
       sum(b in B,n in N,p in P) (x[<s,i>][b][n][p])==1;    
}

//(4)Una ubicacion solo puede ser utilizada por un pallet
forall(s in S,b in B,n in N,p in P){        
       sum(<s,i> in periodos) (x[<s,i>][b][n][p])<=1;   
}

//(5) No debe haber espacio entre pallets almacenados
forall(s in S,b in B,n in N,p in P: p>=2 ){        
    (sum(<s,i> in periodos)(x[<s,i>][b][n][p]))<=(sum(<s,i> in periodos)(x[<s,i>][b][n][p-1]));
}

//(6): EL ALMACENAMIENTO EN UNA UBICACIÓN DEBE SER PERMITIDA POR NIVELES INFERIORES
forall(s in S,b in B,n in N,np in N: n<np ){        
    (sum(<s,i> in periodos,p in P)(x[<s,i>][b][n][p]))<=(sum(<s,i> in periodos,p in P)(x[<s,i>][b][np][p]))+1;
}

//(7)Integralidad de u
forall(<s,j,i> in periodoijs){        
      (BANDAS*u[<s,j,i>])>=(((sum(b in B,n in N,p in P) (b*x[<s,i>][b][n][p]))-(sum(b in B,n in N,p in P) (b*x[<s,j>][b][n][p])))+1);
}

//(8)Integralidad de u
forall(<s,j,i> in periodoijs){        
      ((BANDAS*u[<s,j,i>])-BANDAS)<=((sum(b in B,n in N,p in P) (b*x[<s,i>][b][n][p]))-(sum(b in B,n in N,p in P) (b*x[<s,j>][b][n][p])));
}

//(9)Integralidad de v
forall(<s,j,i> in periodoijs){        
      (BANDAS*v[<s,j,i>])>=(((sum(b in B,n in N,p in P) (b*x[<s,j>][b][n][p]))-(sum(b in B,n in N,p in P) (b*x[<s,i>][b][n][p])))+1);
}

//(10)Integralidad de v
forall(<s,j,i> in periodoijs){        
      ((BANDAS*v[<s,j,i>])-BANDAS)<=((sum(b in B,n in N,p in P) (b*x[<s,j>][b][n][p]))-(sum(b in B,n in N,p in P) (b*x[<s,i>][b][n][p])));
}

//(12)Integralidad de r
forall(<s,j,i> in periodoijs){        
     (NIVELES*r[<s,j,i>])>=((sum(b in B,n in N,p in P)(n*x[<s,j>][b][n][p])-sum(b in B,n in N,p in P)(n*x[<s,i>][b][n][p]))+1) ;
}

//(13)Integralidad de r
forall(<s,j,i> in periodoijs){        
     (NIVELES*r[<s,j,i>]-NIVELES)<=(sum(b in B,n in N,p in P)(n*x[<s,j>][b][n][p])-sum(b in B,n in N,p in P)(n*x[<s,i>][b][n][p])) ;
}

forall(<s,j,i> in periodoijs){        
   l[<s,j,i>]-z[<s,j,i>]<=0;             //(14)Integralidad de l
   l[<s,j,i>]-r[<s,j,i>]<=0;             //(15)Integralidad de l
   l[<s,j,i>]-z[<s,j,i>]-r[<s,j,i>]>=-1; //(16)Integralidad de l
}

//(17)Integralidad de y
forall(<s,j,i> in periodoijs){        
     y[<s,j,i>]<=l[<s,j,i>];
}

//(18)Integralidad de y
forall(<s,j,i>  in periodoijs){        
     y[<s,j,i>]>=(l[<s,j,i>]-1+(((sum(b in B,n in N,p in P) (p*x[<s,i>][b][n][p]))-(sum(b in B,n in N,p in P) (p*x[<s,j>][b][n][p])))/PROFUNDIDAD));
}

//(19)Integralidad de y
forall(<s,j,i>  in periodoijs){        
    (((sum(b in B,n in N,p in P) (p*x[<s,j>][b][n][p]))-(sum(b in B,n in N,p in P) (p*x[<s,i>][b][n][p]))+1)/PROFUNDIDAD)<=1-y[<s,j,i>];
}

//(20)Reorganizar pallet
forall(<s,i> in periodos,b in B,n in N,p in P: s>0 && s<ETAPAS && Q[i][s+1]==1 && Q[i][s]==1 ){        
     (x[<s+1,i>][b][n][p]-x[<s,i>][b][n][p])>=sum(<s,j,i> in periodoijs)(-y[<s,j,i>]);
}

//(21)Reorganizar pallet 
forall(<s,i> in periodos,b in B,n in N,p in P: s>0 && s<ETAPAS && Q[i][s+1]==1 && Q[i][s]==1 ){        
     (x[<s,i>][b][n][p]-x[<s+1,i>][b][n][p])>=sum(<s,j,i> in periodoijs)(-y[<s,j,i>]);
}

}



main {
  var ofile = new IloOplOutputFile("TesisResultados.txt");
  ofile.write("F.O.");
  ofile.write(" Instancia"); 
  ofile.write(" Tiempo");
  ofile.write(" GAP");
  ofile.writeln();
  for(var i=1; i<=10; i++)
  {
    
    var source = new IloOplModelSource("ModeloTesis.mod");
  	var def = new IloOplModelDefinition(source);
  	var produce = new IloOplModel(def,cplex); 

     if(i==1)
     {
         var data = new IloOplDataSource("ModeloTesis.dat");
         produce.addDataSource(data);   
     }
     if(i==2)
     {
         var data = new IloOplDataSource("ModeloTesis2.dat");
         produce.addDataSource(data);   
     }
     if(i==3)
     {
         var data = new IloOplDataSource("ModeloTesis3.dat");
         produce.addDataSource(data);   
     }
     if(i==4)
     {
         var data = new IloOplDataSource("ModeloTesis4.dat");
         produce.addDataSource(data);   
     }
     if(i==5)
     {
         var data = new IloOplDataSource("ModeloTesis5.dat");
         produce.addDataSource(data);   
     }
     if(i==6)
     {
         var data = new IloOplDataSource("ModeloTesis6.dat");
         produce.addDataSource(data);   
     }
     if(i==7)
     {
         var data = new IloOplDataSource("ModeloTesis7.dat");
         produce.addDataSource(data);   
     }
          if(i==8)
     {
         var data = new IloOplDataSource("ModeloTesis8.dat");
         produce.addDataSource(data);   
     }
          if(i==9)
     {
         var data = new IloOplDataSource("ModeloTesis9.dat");
         produce.addDataSource(data);   
     }
          if(i==10)
     {
         var data = new IloOplDataSource("ModeloTesis10.dat");
         produce.addDataSource(data);   
     }
          
      produce.generate();
      var TI = new Date();
  	  var TIP = TI.getTime();
      if (cplex.solve()) 
      {
      	var TF = new Date();
      	ofile.write(cplex.getObjValue());
      	ofile.write(" ", i);
      	ofile.write(" ", (TF.getTime() - TIP)/1000);
      	ofile.write(" ", cplex.getMIPRelativeGap());
      	ofile.writeln();   
      	produce.postProcess();        		      		          
      }
      else
      {
      	writeln("No solution!");
      } 
  }  	  
  ofile.close(); 

}
 











 

