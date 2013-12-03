package Grafo; 
import java.util.List; 
import java.util.ArrayList; 

public class Graph { 
    public int NUMERO_DE_NODOS; //numero de nodos do grafo 
    public int OO = 2147483647; //valor/simbolo do infinito para inicializar vetor de distancia
    private Integer[][]matrizDePesos; 
         
    public Graph(int numeroDeNodos)throws Exception{ 
         
        if(numeroDeNodos <= 0) 
            throw new Exception("A quantidade de Vertices deve ser maior que ZERO."); 
        NUMERO_DE_NODOS = numeroDeNodos;     
        try{ 
            setMatrizDePesos(criaMatrizInt(numeroDeNodos,0));                 
        }catch(Exception ex){ 
            System.out.println("Ocorreu um erro."); 
        } 
    }     
     
    public Integer[][] criaMatrizInt(int tamanho, Integer valorPadrao)throws Exception{         
 
        if(tamanho <=1 ) 
            throw new Exception("O tamanho da matriz deve ser maior que 1."); 
        //a matriz precisa ser maior que 1
        Integer matriz[][] = new Integer[tamanho+1][]; 
        try{ 
            for(int i = 0 ; i < tamanho ; i++){ 
                matriz[i] = new Integer[tamanho+1];             
                for(int j = 0 ; j < tamanho ; j++){ 
                    //insere zero como valor padrao na matriz.
                    matriz[i][j] = valorPadrao; 
                } 
            } 
        }catch(Exception ex){ 
            System.out.println("Erro ao criar a matriz."); 
        } 
        return matriz; 
    } 
          
    //Insere Vertice A, B e o respectivo peso
    public void insertArc(int A, int B, int peso)throws Exception{ 
        try{ 
            if(A < 0 || B < 0  
                     || A > getNUMERO_DE_NODOS()             
                     || B > getNUMERO_DE_NODOS()) 
                throw new Exception("Um ou os dois vertices são inválidos."); 
            if(peso == 0) 
                throw new Exception("O peso da aresta não pode ser zero."); 
            //atribui peso das arestas (entre os vertices)
            matrizDePesos[A][B] = peso;     
        }catch(Exception ex){ 
            System.out.println("Ocorreu um erro."); 
        } 
    } 
     
    //Retorna a matriz de pesos das arestas.
    public Integer[][] getMatrizDePesos() {         
        return this.matrizDePesos;  
    } 
 
    //Seta o peso de uma aresta (entre dois Vertices).
    public void setMatrizDePesos(Integer[][] pesos) {         
        this.matrizDePesos = pesos;         
     } 
 
   //Retorna o número de nodos 
   public int getNUMERO_DE_NODOS() { 
        return NUMERO_DE_NODOS; 
    } 
 
} 