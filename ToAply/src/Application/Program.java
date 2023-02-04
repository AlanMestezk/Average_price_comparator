package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import Entities.Products;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Products> list = new ArrayList<>();
			
			String line = br.readLine();
			
			while(line != null) {
				String[] fields = line.split(" - "); 
				
				/*
				 fields na posição 0 será o nome, fields na posição 0 sera o preço
				 e estou convertendo a posição 0 para double com .parse 
				 */
				list.add(new Products(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}
			
			// pipeline da soma dos preços para mostrar os preços medio
			double avg = list.stream()
						.map(p-> p.getPrice())
						//aqui eu acho o preço medio de todos os produtos
						.reduce(0.0, (x, y) -> x + y / list.size() );
			
			System.out.println();
			System.out.println("Preço medio dos produtos da lista: "+ String.format("%.2f", avg));
			
			//aqui estou procurando os nomes dos produtos que estam abaixo da media
			
			Comparator<String> comp = (s1, s2)-> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> names = list.stream()
										.filter(p -> p.getPrice() < avg)
										.map(p -> p.getName())
										.sorted(comp.reversed())
										.collect(Collectors.toList());
			System.out.println();
			
			System.out.println("Produtos abaixo da media de preços: ");
			names.forEach(System.out::println);
			
		} catch (Exception e) {
			System.out.println("Derro erro: "+ e.getMessage());
		}
	}

}
