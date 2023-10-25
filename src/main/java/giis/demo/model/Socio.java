package giis.demo.model;

import java.time.LocalDate;

import giis.demo.util.Database;

public class Socio {
	
	private final static String SQL_CARGAR_SOCIO="select id,name,surname,cuota_type,iban,height,weight,birth_date,gender,directive from socios where id = ?" ;
	private final static String SQL_MODIFICAR_SOCIO= "update socios set name=?, surname=?, gender=?, birth_date=? where id = ?";
	
	private Database db;
	
	private int id;
	private String tipoCuota;
	private String numeroIban;
	private String nombre;
	private String apellidos;
	private Generos genero;
	private LocalDate fechaNacimiento;
	private String altura;
	private int peso;
	private boolean esDirectivo;
	
	public Socio(Database db,int id) {
		this.db = db;
		cargarDatosSocio(id);
		
	}
	
	public Socio(int id, String nombre, String apellidos, String tipoCuota, Generos genero, LocalDate fecha) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.tipoCuota = tipoCuota;
		this.fechaNacimiento = fecha;
	}

	private void cargarDatosSocio(int id) {
		Object[] result = db.executeQueryArray(SQL_CARGAR_SOCIO, id).get(0);
		
		this.id = (int) result[0];
		nombre = (String) result[1];
		apellidos = (String) result[2];
		tipoCuota = (String) result[3];
		numeroIban = (String) result[4];
		altura = (String) result[5];
		peso = (int) result[6];
		String edad = (String) result[7];
		String genero = (String)result[8];
		if(genero.equals("HOMBRE")) {
			this.genero = Generos.HOMBRE;
		}else if(genero.equals("MUJER")) {
			this.genero = Generos.MUJER;
		}else {
			this.genero = Generos.OTRO;
		}
		esDirectivo = (int)result[9] == 1;
		
		String[] str = edad.split("-");
		int año = Integer.parseInt(str[0]);
		int mes = Integer.parseInt(str[1]);
		int dia = Integer.parseInt(str[2]);
		fechaNacimiento = LocalDate.of(año, mes, dia);
		//fechaNacimiento.set(año, mes, dia);
	}
	
	public void modificarDatos(String nombre, String apellido, Generos genero, LocalDate fecha) {
		this.nombre = nombre;
		this.apellidos = apellido;
		this.genero = genero;
		fechaNacimiento = fecha;
	}
	
	public void guardarDatos() {
		String fecha = ""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		db.executeUpdate(SQL_MODIFICAR_SOCIO,nombre,apellidos,genero,fecha,id);
		System.out.println("Datos Socio modificados:\n"+toString());
	}
	
	public int getId() {
		return id;
	}

	public String getTipoCuota() {
		return tipoCuota;
	}

	public String getNumeroIban() {
		return numeroIban;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public Generos getGenero() {
		return genero;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getAltura() {
		return altura;
	}

	public int getPeso() {
		return peso;
	}

	public boolean isEsDirectivo() {
		return esDirectivo;
	}

	@Override
	public String toString() {
		String fecha = ""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		return "Nombre: "+nombre+", Apellidos: "+apellidos+", Fecha de nacimiento: "+fecha+", cuota tipo: "+tipoCuota+", numero iban: "+numeroIban+
				", altura: "+altura+", peso: "+peso+", genero: "+genero+", es directivo :"+esDirectivo;
		
	}
	
	public String toStringList() {
		String fecha = ""+fechaNacimiento.getYear()+"-"+fechaNacimiento.getMonthValue()+"-"+fechaNacimiento.getDayOfMonth();
		return id + " - " + nombre +" - " + apellidos + " - " + fecha + " - " + tipoCuota + " - " + genero;
		
	}

}
