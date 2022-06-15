package org.foi.nwtis.tskobic.aplikacija_3.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

public class UlogeDAO {

	public boolean autorizacijaKorisnika(String korisnik, String grupa, PostavkeBazaPodataka pbp) {
		String url = pbp.getServerDatabase() + pbp.getUserDatabase();
		String bpkorisnik = pbp.getUserUsername();
		String bplozinka = pbp.getUserPassword();
		String upit = "SELECT * FROM uloge WHERE korisnik = ? AND grupa = ?;";
		
		try {
			Class.forName(pbp.getDriverDatabase(url));

			try (Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
					PreparedStatement s = con.prepareStatement(upit)) {

				s.setString(1, korisnik);
				s.setString(2, grupa);

				ResultSet rs = s.executeQuery();
				
				if(rs.next()) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException ex) {
				Logger.getLogger(KorisniciDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(KorisniciDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return false;
	}
	
}
