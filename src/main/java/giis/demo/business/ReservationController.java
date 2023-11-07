package giis.demo.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import giis.demo.model.Instalacion;
import giis.demo.model.Reserva;
import giis.demo.util.Database;

public class ReservationController {
	
	private Database db;
	
	private final static String SQL_CARGAR_RESERVA = "SELECT id, fecha, instalation_code, extra FROM reservas";
	private final static String SQL_CREAR_RESERVA = "INSERT INTO reservas(fecha, instalation_code, extra) VALUES (?, ?, ?)";
	private final static String SQL_ID_RESERVA = "SELECT seq FROM sqlite_sequence where name='reservas'";
	private final static String SQL_CARGAR_PARTICIPANTES = "SELECT * FROM participante_reserva";
	private final static String SQL_CREAR_PARTICIPANTE = "INSERT INTO participante_reserva (reserva_id, dni) VALUES (?, ?)";
	private final static String SQL_CARGAR_FECHAS_RESERVA = "SELECT DISTINCT fecha FROM reservas, participante_reserva WHERE participante_reserva.dni='?' and reservas.instalation_code='?'";
	
	private List<Object[]> resQuery;
	
	private List<Reserva> listaReservas = new ArrayList<Reserva>();
	
	public ReservationController(Database db) {
		this.db = db;
	}
	
	public void reservar(LocalDateTime dia, String reserva, Instalacion instalacion, List<String> listaParticipantes, boolean extra) {
		checkParticipantsAvailability(dia, instalacion, listaParticipantes);
		createReservation(reserva, instalacion.getCode(), extra);
		createQueryParticipants(listaParticipantes);
		getReservas();
		getParticipantes();
	}
	
	private void checkParticipantsAvailability(LocalDateTime dia, Instalacion instalacion, List<String> participantes) {
		List<Object[]> queryRes = new ArrayList<>();
		for (String dni: participantes) {
			
		}
	}

	private void createQueryParticipants(List<String> listaParticipantes) {
		int id_reserva = (int) db.executeQueryArray(SQL_ID_RESERVA).get(0)[0];
		
		System.out.println("loco aver: " + id_reserva);
		
		for (String dni: listaParticipantes) {
			System.out.println(id_reserva + " - " + dni);
			db.executeUpdate(SQL_CREAR_PARTICIPANTE, id_reserva, dni);
		}
	}

	public List<Reserva> getReservaL(){
		return listaReservas;
	}
	
	public List<Reserva> getReservas() {
		listaReservas.removeAll(listaReservas);
		resQuery = db.executeQueryArray(SQL_CARGAR_RESERVA);
		
		int id = 0;
		String reserva = "";
		String instalacionId = "";
		String fecha = "";
		String hora = "";
		boolean extra = false;
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			reserva = (String) objects[1];
			instalacionId = (String) objects[2];
			extra = ((int) objects[3]) == 1;			
			
			fecha = reserva.split(" ")[0];
			hora = reserva.split(" ")[1];
			
			System.out.println(fecha + " || " + hora);
			
			listaReservas.add(new Reserva(id, fecha, hora, instalacionId, extra));
		}
		
		for (Reserva reservaTP : listaReservas) {
			System.out.println(reservaTP.getId());
		}
		
		return listaReservas;		
	}
	
	
	public void getParticipantes() {
		List<Object[]> resQuery = db.executeQueryArray(SQL_CARGAR_PARTICIPANTES);
		int id = 0;
		String dni = "";
		
		for (Object[] objects : resQuery) {
			id = (int) objects[0];
			dni = (String) objects[2];
			
			System.out.println(id + " |hola| " + dni);
		}
	}
	
	private void createReservation(String reserva, String instalacionId, boolean extra) {
		db.executeUpdate(SQL_CREAR_RESERVA, reserva, instalacionId, extra);
	}

}
