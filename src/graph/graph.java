package graph;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



public class graph
{
public String[] node=new String[1000];
public int [][] Matrix=new int [1000][1000];
public int vertexnum;
public graph createDirectedGraph()
{
	FileReader fis=null;
	try {
		fis=new FileReader("E:/PPAP.txt");
		char[]b=new char[4096];
		int n=fis.read(b);
		String s=new String(b,0,n);
		fis.close();
		String a[]=s.split("[^a-zA-Z]+");
	    for(int i=0;i<a.length;i++)
	    {
		   a[i]=a[i].toLowerCase();
	    }
		for(int i=0;i<a.length;i++)
		{
			System.out.println(a[i]+" ");
		}
		String c[]=new String[a.length];
		int flag,num=0;
		for(int i=0;i<a.length;i++)
		{
			flag=0;
		    for(int j=0;j<c.length;j++)
		    {
		    	if(a[i].equals(c[j]))
		    	{
		    		flag=1;
		    	}
		    }
		    if(flag==0)
		    {
		    	c[num]=a[i];
		    	num++;
		    }
		}
		String node1[]=new String[num];
		for(int i=0;i<num;i++)
		{
			node1[i]=c[i];
		}
		int graph1[][]=new int[num][num];
		for(int i=0;i<a.length-1;i++)
		{
			for(int j=0;j<num;j++)
			{
				if(a[i].equals(node1[j]))
				{	
					
					for(int k=0;k<num;k++)
					{
						if(a[i+1].equals(node1[k]))
						{   
							graph1[j][k]++;
						}
					}
				}
			}
		}
		for(int i=0;i<node1.length;i++)
		{
			this.node[i]=node1[i];
		}
		for(int i=0;i<node1.length;i++)
		{
			for(int j=0;j<node1.length;j++)
			{
				if(graph1[i][j]==0)
				{
					this.Matrix[i][j]=100000;
				}
				else
				{
					this.Matrix[i][j]=graph1[i][j];
				}

			}
		}
       this.vertexnum=num;
       System.out.println( this.vertexnum);
	}
	
	catch(IOException e){
		e.printStackTrace();
	}
	finally{
		try{
			fis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	return this;

}



public void showDirectedGraph(graph G){
	    GraphViz gViz=new GraphViz("C:\\Users\\11969\\Desktop\\1111","D:\\dot\\bin\\dot.exe" );
	    gViz.start_graph();
	    for(int i=0;i<G.vertexnum;i++)
	    {
	    	for(int j=0;j<G.vertexnum;j++)
	    	{
	    		if(G.Matrix[i][j]<100000)
	    		{
	    			gViz.addln(G.node[i]+"->"+G.node[j]+";");
	    		}
	    	}
	    }
	    gViz.end_graph();
	    try {
	        gViz.run();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
}






public String queryBridgeWords(graph G,String word1,String word2)
{ 
	int w1=-1,w2=-1;
	word1=word1.toLowerCase();
	word2=word2.toLowerCase();
	String bridge[]=new String[G.vertexnum];
	for(int i=0;i<G.vertexnum;i++)
	{
		if(G.node[i].equals(word1))
		{
			w1=i;
		}
		if(G.node[i].equals(word2))
		{
			w2=i;
		}
	}
	for(int i=0;i<G.vertexnum;i++)
	{
		if(G.Matrix[w1][i]<100000&&i!=w2)
		{
			if(G.Matrix[i][w2]<100000)
			{
				bridge[i]=G.node[i];
			}
		}
	}
	for(int i=0;i<bridge.length;i++){
		System.out.print(bridge[i]+"   ");
	}
	/*for(int i=0;i<G.vertexnum;i++)
	{
		for(int j=0;j<G.vertexnum;j++)
		{
			System.out.println(G.Matrix[i][j]);
		}
	}*/
	int count=0;
	String out[]=new String[G.vertexnum];
	for(int i=0;i<G.vertexnum;i++)
	{
		if(bridge[i]!=null)
		{
			out[count]=bridge[i];
		    count++;
		}
	}
	if(w1==-1&&w2!=-1)
	{
		System.out.println("No\""+word1+"\"exist in the graph");
	}
	else if(w1!=-1&&w2==-1)
	{
		System.out.println("No\""+word2+"\" exist in the graph");
	}
	else if(w1==-1&&w2==-1)
	{
		System.out.println("No\""+word2+" and "+"\"exist in the graph");
	}
	else
	{
		if(count==0)
		{
			System.out.println("No bridge words from \""+word1+"\"to \""+word2+"!");
		}
		else if(count==1)
		{
			System.out.println("The bridge words from \""+word1+"\"to \""+word2+"\"is: "+out[0]);
		}
		else 
		{
			System.out.print("The bridge words from \""+word1+"\"to \""+word2+"\"is: ");
			for(int i=0;i<count-1;i++)
			{
				System.out.print(out[i]+",");
			}
			System.out.print("and "+out[count]);
		}
	}
	return "1";
}






public String generateNewText(graph G,String inputText)
{
	String a[]=inputText.split("[^a-zA-Z]+");
    for(int i=0;i<a.length;i++)
    {
	   a[i]=a[i].toLowerCase();
    }
    for(int j=0;j<a.length-1;j++)
    {   
    	String word1=a[j];
    	String word2=a[j+1];
    	int w1=-1,w2=-1;
    	word1=word1.toLowerCase();
    	word2=word2.toLowerCase();
    	String bridge[]=new String[G.vertexnum];
    	for(int i=0;i<G.vertexnum;i++)
    	{
    		if(G.node[i].equals(word1))
    		{
    			w1=i;
    		}
    		if(G.node[i].equals(word2))
    		{
    			w2=i;
    		}
    	}
    	for(int i=0;i<G.vertexnum;i++)
    	{
    		if(G.Matrix[w1][i]<100000&&i!=w2)
    		{
    			if(G.Matrix[i][w2]<100000)
    			{
    				bridge[i]=G.node[i];
    			}
    		}
    	}
    	int count=0;
    	String out[]=new String[G.vertexnum];
    	for(int i=0;i<G.vertexnum;i++)
    	{
    		if(bridge[i]!=null)
    		{
    			out[count]=bridge[i];
    		    count++;
    		}
    	}
    }
	return "1";
}
public String calsShortestPath(graph G,String word1,String word2)
{
	int d[][]=new int[G.vertexnum][G.vertexnum] ;
	String s=new String();
	boolean[][][] p = new boolean[G.vertexnum][G.vertexnum][G.vertexnum];
	int w1=-1,w2=-1;
	word1=word1.toLowerCase();
	word2=word2.toLowerCase();
	for(int i=0;i<G.vertexnum;i++)
	{
		if(G.node[i].equals(word1))
		{
			w1=i;
		}
		if(G.node[i].equals(word2))
		{
			w2=i;
		}
	}
	for(int v=0;v<G.vertexnum;v++)
	{
	  for(int w=0;w<G.vertexnum;w++)
	  {
		  d[v][w]=G.Matrix[v][w];
		  if(d[v][w]<100000)
		  {
			  p[v][w][v]=true;
			  p[v][w][w]=true;
		  }
	  }
	}
	for(int i=0;i<G.vertexnum;i++)
	{
		d[i][i]=0;
	}
	for (int u=0;u<G.vertexnum;u++)
	{
		for(int v=0;v<G.vertexnum;v++)
		{
			for(int w=0;w<G.vertexnum;w++)
			{
				if(d[v][u]+d[u][w]<d[v][w])
				{
					d[v][w]=d[v][u]+d[u][w];
				
				for(int i=0;i<G.vertexnum;i++)
				{
					p[v][w][i]=p[v][u][i]||p[u][w][i];
				}
				}
			}
		}
	}

//	for(int i=0;i<G.vertexnum;i++)
//	{
//		for(int j=0;j<G.vertexnum;j++)
//		{
//			System.out.print(d[i][j]+"     ");
//		}
//			
//		System.out.println();
//	}
	
//	for(int i=0;i<G.vertexnum;i++)
//	{
//		for(int j=0;j<G.vertexnum;j++)
//		{
//			System.out.print("[");
//			for(int k=0;k<G.vertexnum;k++)
//			{
//				System.out.print(p[i][j][k]+",");
//			}
//			System.out.print("]");
//		}
//			
//		System.out.println();
//	}	
	if(w1==-1&&w2!=-1)
	{
		System.out.println("No\""+word1+"\"exist in the graph");
	}
	else if(w2==-1&&w1!=-1)
	{
		System.out.println("No\""+word2+"\" exist in the graph");
	}
	else if(w2==-1&&w2==-1)
	{
		System.out.println("No\""+word2+" and "+"\"exist in the graph");
	}
	else if(w1==w2)
	{
		System.out.println("Two words are same!");
	}
	else
	{
		if(d[w1][w2]<100000)
		{
			System.out.println("The length of the path is:"+d[w1][w2]);
		}
		System.out.print("The  path is: ");
		for(int i=0;i<G.vertexnum;i++)
		{
			if(p[w1][w2][i]==true)
			{
				System.out.print(G.node[i]+"->");
			}
		}
		System.out.print("END");
	
	}
	
     s = String.valueOf(d[w1][w2]);
     return "s";
}

public static void main(String[] args) {
	graph G=new graph();
	String s=new String();
	String length=new String();
	String word3=new String();
	G.createDirectedGraph();
	System.out.println("图已经构建完成！！！");
	s=G.queryBridgeWords(G, "explore","new");
	//length=G.calsShortestPath(G, "I","pineapplepen");
	//System.out.println(length);
    G.showDirectedGraph(G);
}
}
