package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {
	
	public static void main(String [] args) throws ParseException{
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Product> list = new ArrayList<>();
		
		System.out.println("Entre com o diretorio: ");
		String sourceFileStr = sc.nextLine();
		
		File sourceFile = new File(sourceFileStr);
		String sourceFolderStr = sourceFile.getParent();
		
		boolean success = new File(sourceFolderStr + "\\out").mkdir();
		
		String targetFileStr = sourceFolderStr + "\\out\\summary.csv";
		
		try(BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))){
			
			String itemCsv = br.readLine();
			while (itemCsv != null) {
				String[] fields = itemCsv.split(",");
				String nome = fields[0];
				Double valor = Double.parseDouble(fields[1]);
				int quantidade = Integer.parseInt(fields[2]);
				
				list.add(new Product(nome, valor, quantidade));
				
				itemCsv = br.readLine();
			}
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
				
				for(Product item : list) {
					bw.write(item.getNome() + "," + String.format("%.2f" , item.total()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " Created");
				
			}
			catch(IOException e) {
				System.out.println(" Erro escrevendo o arquivo: " + e.getMessage());
			}
			
		}
		catch(IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
		
		sc.close();
	}

}
