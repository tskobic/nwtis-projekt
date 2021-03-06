package org.foi.nwtis.tskobic.aplikacija_2.podaci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.foi.nwtis.rest.podaci.AvionLeti;
import org.foi.nwtis.tskobic.vjezba_06.konfiguracije.bazaPodataka.PostavkeBazaPodataka;

/**
 * Klasa AerodromiPolasciDAO za pristup bazi.
 */
public class AerodromiPolasciDAO {

	/**
	 * Dohvaća sve polaske aviona.
	 *
	 * @param pbp pristup bazi podataka
	 * @return the lista polazaka
	 */
	public List<AvionLeti> dohvatiSvePolaske(PostavkeBazaPodataka pbp) {
		String url = pbp.getServerDatabase() + pbp.getUserDatabase();
		String bpkorisnik = pbp.getUserUsername();
		String bplozinka = pbp.getUserPassword();
		String upit = "SELECT id, icao24, firstSeen, estDepartureAirport, lastSeen, estArrivalAirport, "
				+ "callsign, estDepartureAirportHorizDistance, "
				+ "estDepartureAirportVertDistance, estArrivalAirportHorizDistance, "
				+ "estArrivalAirportVertDistance, departureAirportCandidatesCount, arrivalAirportCandidatesCount, `stored` "
				+ "FROM AERODROMI_POLASCI;";

		try {
			Class.forName(pbp.getDriverDatabase(url));

			List<AvionLeti> aerodromPolasci = new ArrayList<>();

            try (
                    Connection con = DriverManager.getConnection(url, bpkorisnik, bplozinka);
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(upit)) {

				while (rs.next()) {
					String icao24 = rs.getString("icao24");
					int firstSeen = rs.getInt("firstSeen");
					String estDepartureAirport = rs.getString("estDepartureAirport");
					int lastSeen = rs.getInt("lastSeen");
					String estArrivalAirport = rs.getString("estArrivalAirport");
					String callsign = rs.getString("callsign");
					int estDepartureAirportHorizDistance = rs.getInt("estDepartureAirportHorizDistance");
					int estDepartureAirportVertDistance = rs.getInt("estDepartureAirportVertDistance");
					int estArrivalAirportHorizDistance = rs.getInt("estArrivalAirportHorizDistance");
					int estArrivalAirportVertDistance = rs.getInt("estArrivalAirportVertDistance");
					int departureAirportCandidatesCount = rs.getInt("departureAirportCandidatesCount");
					int arrivalAirportCandidatesCount = rs.getInt("arrivalAirportCandidatesCount");

					AvionLeti avionLeti = new AvionLeti(icao24, firstSeen, estDepartureAirport, lastSeen,
							estArrivalAirport, callsign, estDepartureAirportHorizDistance,
							estDepartureAirportVertDistance, estArrivalAirportHorizDistance,
							estArrivalAirportVertDistance, departureAirportCandidatesCount,
							arrivalAirportCandidatesCount);

					aerodromPolasci.add(avionLeti);
				}
				return aerodromPolasci;

			} catch (Exception ex) {
				Logger.getLogger(AerodromiProblemiDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(AerodromiProblemiDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	/**
	 * Dodaje avion koji je poletio sa polazišta.
	 *
	 * @param al klasa koja predstavlja avion u letu
	 * @param pbp postavke baze podataka
	 * @param con veza na bazu podataka
	 * @return true, ako je uspješno dodavanje
	 */
	public boolean dodajAerodromPolasci(AvionLeti al, PostavkeBazaPodataka pbp, Connection con) {
		String url = pbp.getServerDatabase() + pbp.getUserDatabase();
		String upit = "INSERT IGNORE INTO AERODROMI_POLASCI "
				+ "(icao24, firstSeen, estDepartureAirport, lastSeen, estArrivalAirport, "
				+ "callsign, estDepartureAirportHorizDistance, estDepartureAirportVertDistance, estArrivalAirportHorizDistance, "
				+ "estArrivalAirportVertDistance, departureAirportCandidatesCount, arrivalAirportCandidatesCount, `stored`) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			Class.forName(pbp.getDriverDatabase(url));

			try (PreparedStatement s = con.prepareStatement(upit)) {

				Date datum = new Date();

				s.setString(1, al.getIcao24());
				s.setInt(2, al.getFirstSeen());
				s.setString(3, al.getEstDepartureAirport());
				s.setInt(4, al.getLastSeen());
				s.setString(5, al.getEstArrivalAirport());
				s.setString(6, al.getCallsign());
				s.setInt(7, al.getEstDepartureAirportHorizDistance());
				s.setInt(8, al.getEstDepartureAirportVertDistance());
				s.setInt(9, al.getEstArrivalAirportHorizDistance());
				s.setInt(10, al.getEstArrivalAirportVertDistance());
				s.setInt(11, al.getDepartureAirportCandidatesCount());
				s.setInt(12, al.getArrivalAirportCandidatesCount());
				s.setTimestamp(13, new Timestamp(datum.getTime()));

				int brojAzuriranja = s.executeUpdate();

				return brojAzuriranja == 1;

			} catch (Exception ex) {
				Logger.getLogger(AerodromiPolasciDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(AerodromiPolasciDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}
}
