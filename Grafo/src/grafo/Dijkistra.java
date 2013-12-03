package grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dijkistra extends Grafo.Graph{
    
    private boolean nodosVisitados[]; 
    private int distancias[]; 
    private int pai[]; 
    private List filaDePrioridade = new ArrayList<Integer>(); 
    private int origem; 
    private Integer[][]matrizDePesos;
    //int OO = 2147483647;//insere infinito na inicialização do vetor de distância
    //int NUMERO_DE_NODOS;//numero de nodos do grafo

    public static void main(String[] args) {
        // TODO code application logic here
	String delims = "[ ]+";
	String[] inputs = null;
	int vert_A  = 0;
	int vert_B  = 0;		
	int peso    = 0;
	int tam     = 0;
	int menor_A = 0;
	int menor_B = 0;
        //Declara�oes de variaveis.
		
	Scanner lerDado = new Scanner(System.in);
	String dadoLido = "";
	String funcaoLida = "";
		
        //enquanto dado lido da interface nao conter <SAIR>
	while (!funcaoLida.contains("SAIR")) {
		System.out.println("Digite a opcao: <VERTICES> <ARQUIVO> <MENOR_CAMINHO> <SAIR>");
		funcaoLida = lerDado.nextLine();

		//funcionalidade adicional para informar o número de vertices que o grafo tera.
                if (funcaoLida.contains("VERTICES")) {
			System.out.println("Digite a quantidade de vertices do Grafo:");
			dadoLido = lerDado.nextLine();
			//numero de vertices do grafo informado pelo usu�rio
                        tam = Integer.parseInt(dadoLido);
			if (tam < 1){
				System.out.println("Quantidade de Vertices inválida, digite novamente.");
			} else {				
			    try {    
                                Dijkistra dij = new Dijkistra(tam);                               
			    } catch (Exception ex) {
                                Logger.getLogger(Dijkistra.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}                        
                //solicita informação para buscar o menor caminho
                } else if (funcaoLida.contains("MENOR_CAMINHO")) {
			System.out.println("Menor caminho entre o Vertice:");
			dadoLido = lerDado.nextLine();
			//vertice inicial para busca do menor caminho
                        menor_A = Integer.parseInt(dadoLido);
                        System.out.println("e o Vertice:");
			dadoLido = lerDado.nextLine();
			//vertice final para busca do menor caminho
                        menor_B = Integer.parseInt(dadoLido);
                        System.out.println("percorrer os vertices:" + dij.dijkstra(menor_A, menor_B));
			//apresenta vertices que devem ser percorridos				
                //solicita caminho e nome do arquivo
		} else if (funcaoLida.contains("ARQUIVO")) {
			if (tam < 1){
				System.out.println("Quantidade de Vertices inv�lida.");
			} else {
 			    System.out.println("Informe do caminho e o nome do arquivo");
			    dadoLido = lerDado.nextLine();
			    try {
			        //leitura dos vertices, pesos do grafo e armazenando os dados
			        BufferedReader in = new BufferedReader(new FileReader(dadoLido));
				String str;
				while (in.ready()) {
		                    str = in.readLine();
				    inputs = str.split(delims);
				    vert_A = Integer.parseInt(inputs[0]);
				    vert_B = Integer.parseInt(inputs[1]);
				    peso   = Integer.parseInt(inputs[2]);
				    dij.insertArc(vert_A, vert_B, peso); 
				}
				in.close();
			     } catch (IOException e) {
			     }
		        }
	        } else {
	               System.out.println("Comando não previsto.");
                }        
         }
    }
    
    //Construtor: recebe o numero de nodos do grafo (variavel tam) 
    public Dijkistra(int numeroDeNodos)throws Exception{ 
        //vetor de booleanos para marcar já visitados
        nodosVisitados = new boolean[numeroDeNodos]; 
        //distancias entre os vertices
        distancias = new int[numeroDeNodos]; 
        //antecesor do vertice consultado/visitado        
        pai = new int[numeroDeNodos]; 
    } 
     
    public int dijkistra(int origem, int destino)throws Exception{ 
 
        if(origem < 0 || origem > getNUMERO_DE_NODOS()) 
            throw new Exception("Origem é menor que zero ou destino não existe."); 
                 
        try{ 
            iniciaMenorCaminho(origem); 
        }catch(Exception ex){ 
            System.out.println("Erro no menor caminho."); 
        }        
        
        //enquanto fila de prioridade nao e vazia
        while(!filaDePrioridade.isEmpty()){             
            Integer verticeMenorPeso = extraiMenor();     
             
            for(int i = 0 ; i < getNUMERO_DE_NODOS() ; i++){ 
                //ate finalizar o grafo, busca o menor peso na aresta 
                if(getMatrizDePesos()[verticeMenorPeso][i] > 0)                     
                    altdistancias(verticeMenorPeso,i);                 
            } 
        } 
        printDistancias(); 
        return distancias[destino];                           
    }
    
    //Inicializacao dos dados do algoritmo 
    private void iniciaMenorCaminho(int origem){ 
        for(int i = 0 ; i < getNUMERO_DE_NODOS(); i++){ 
            //vetor de distancia com infinito
            distancias[i] = OO; 
            //vetor de visitados com false
            nodosVisitados[i] = false;     
            //vetor pai (antecessor) com -1
            pai[i] = -1; 
            filaDePrioridade.add(new Integer(i));
            //adiciona a aresta na fila 
        } 
        //inicia o vetor de distancias 
        distancias[origem] = 0;

    } 
     
    //Altera vetor de distancias com a menor distância 
    private void altdistancias(int u, int v){ 
         
            if (distancias[v] > distancias[u]+getMatrizDePesos()[u][v]){                 
                distancias[v] = distancias[u]+getMatrizDePesos()[u][v]; 
                pai[v] = u;                         
            } 
    } 
     
    //remor o menor da fila de prioridades
    private int extraiMenor(){ 
        int menorValor = OO;    
        int verticeDeMenorPeso = 0; 
         
        Iterator<Integer>it = filaDePrioridade.iterator(); 
        while(it.hasNext()){ 
            int verticeAtual = it.next();             
            if(distancias[verticeAtual] < menorValor){                 
                menorValor = distancias[verticeAtual]; 
                verticeDeMenorPeso = verticeAtual; 
            } 
        } 
         
        //remove o vertice com menor distancia da fila com os pesos.
        filaDePrioridade.remove(new Integer(verticeDeMenorPeso));         
        return verticeDeMenorPeso; 
    } 
     
    //Busca origem.
    public int getOrigem() { 
        return origem; 
    } 
 
    //Escreve origem.
    public void setOrigem(int origem) { 
        this.origem = origem;
    } 
     
    //Busca fila de prioridades.
    public List getFilaDePrioridade() { 
        return filaDePrioridade; 
    } 
 
    //insere na fila de prioridades
    public void setFilaDePrioridade(List filaDePrioridade) { 
        this.filaDePrioridade = filaDePrioridade; 
    } 
     
    //imprime as distâncias
    private void printDistancias(){ 
        for(int i = 0 ; i < getNUMERO_DE_NODOS(); i++) 
            System.out.print("["+distancias[i]+"] "); 
        System.out.println(); 
    } 
        
}
