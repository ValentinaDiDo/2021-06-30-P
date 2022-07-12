package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
public List<String> getLocalizations(){
	String sql = "SELECT DISTINCT Localization as l "
			+ "FROM classification";
	List<String> result = new ArrayList<>();
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		ResultSet res = st.executeQuery();
		while (res.next()) {
			result.add(res.getString("l"));
		}
		res.close();
		st.close();
		conn.close();
		return result;
		
	} catch (SQLException e) {
		throw new RuntimeException("Database error", e) ;
	}
	
}
	public List<Adiacenza> getAdiacenze(){
		String sql = "SELECT c1.Localization as l1, c2.Localization as l2, count(distinct i.`Type`) as peso "
				+ "FROM interactions i, classification c1, classification c2 "
				+ "WHERE  c1.Localization> c2.Localization "
				+ "	AND ((c1.GeneID = i.GeneID1 AND c2.GeneID = i.GeneID2)  "
				+ "	OR (c1.GeneID = i.GeneID2 AND c2.GeneID = i.GeneID1)) "
				+ " GROUP BY c1.Localization, c2.Localization";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				String l1 = res.getString("l1");
				String l2 = res.getString("l2");
				int peso = res.getInt("peso");
				Adiacenza a = new Adiacenza(l1,l2,peso);
				result.add(a);
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
	}
	
}
