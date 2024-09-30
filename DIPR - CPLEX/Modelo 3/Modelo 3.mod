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
int M[N][P][N][P];
execute
{
    for(var n in N)
    {
        for(var p in P)
        {
        	for(var np in N)
        	{
        		for(var pp in P)
        		{
        		   if(np <= n && pp > p )
        		   {
        		      M[n][p][np][pp]=1;       		   
        		   }
        		   else
        		   {
        		      M[n][p][np][pp]=0;       		   
        		   }
        		        		
        		}
                		        	
        	}
        	        
        }    
    }
}
tuple npnp
{
   int n;
   int p;
   int np;
   int pp;
}
setof(npnp) NPNP = {<n,p,np,pp > | n in N, p in P, np in N, pp in P : M[n][p][np][pp]==1};

 //Definición de parámetros
 int A[I][S]=...;//1 si el pallet i se almacena en la etapa s.
 int D[I][S]=...;//1 si el pallet i se recupera en la etapa s
 int Q[I][S]=...;//1 si el pallet i se encuentra en la cámara.
 
//Creación de conjuntos
setof (periodo) periodos = {<s,i > | i in I, s in S : Q[i][s]==1 };
setof (periodoij) periodoijs = {<s,j,i > | i in I, s in S, j in I:  (D[j][s]==1 || A[j][s]==1) && Q[i][s]==1 && i!=j };
setof (periodoij) periodoijs2 = {<s,i,k > | i in I, s in S, k in I:  Q[k][s]==1 && Q[i][s]==1 && i!=k };

 //Definición de variables
 dvar boolean x[periodos][B][N][P];
 dvar float+ y[periodoijs];
 dvar float+ yp[periodoijs2];
 
//(1)Función objetivo: minimizar el número de reubicaciones
minimize sum(<s,j,i> in periodoijs) (y[<s,j,i>]);
 
subject to{

//(25)
forall(<s,j,i> in periodoijs, <n,p,np,pp > in NPNP,b in B){ 
     y[<s,j,i>] - (x[<s,j>][b][n][p] + x[<s,i>][b][np][pp])>= -1;        
}

//(26)
forall( <s,i,k> in periodoijs2, <n,p,np,pp > in NPNP,b in B)
  	   yp[<s,i,k>] - (x[<s,i>][b][n][p] + x[<s,k>][b][np][pp])>= -2+sum(<s,j,i> in periodoijs)y[<s,j,i>];

//(27)  	   
forall(<s,j,k> in periodoijs, <s,i,k> in periodoijs2 )
       yp[<s,i,k>] <= y[<s,j,k>];


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


//(20)Reorganizar pallet
forall(<s,i> in periodos,b in B,n in N,p in P: s>0 && s<ETAPAS && Q[i][s+1]==1 && Q[i][s]==1 ){        
     (x[<s+1,i>][b][n][p]-x[<s,i>][b][n][p])>=sum(<s,j,i> in periodoijs)(-y[<s,j,i>]);
}

//(21)Reorganizar pallet 
forall(<s,i> in periodos,b in B,n in N,p in P: s>0 && s<ETAPAS && Q[i][s+1]==1 && Q[i][s]==1 ){        
     (x[<s,i>][b][n][p]-x[<s+1,i>][b][n][p])>=sum(<s,j,i> in periodoijs)(-y[<s,j,i>]);
}

}



