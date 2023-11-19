package giis.demo.logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import giis.demo.ui.VentanaGestionCursosSocios;

public class GestionCursosSocios {
	
	// TODO RELLENAR CON LAS COLUMNAS Y CON EL ID DEL USUARIO
	private static final String CARGADATOS = "SELECT  from cursos c, cursante r "
			+ "where c.id = r.id_curso and r.id_socio = ?"; 
	private static final String ESTA_INSCRITO = "select * from inscritos where id_inscrito = ?";
	// TODO RELLENAR EL ID DEL USUARIO Y CAMBIAR CURSO POR CURSANTE?
	private static final String BORRADECURSO = "delete from cursos where id = ? "; 
	private static final String ACTUALIZAPRECIO = "update from cuotas set price = price - ?";

	private VentanaGestionCursosSocios vgcs;
	
	public GestionCursosSocios(VentanaGestionCursosSocios vgcs) {
		this.vgcs = vgcs;
	}
	
	public void borrar() {
		Date fecha = vgcs.getVp().getDcFecha().getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate date = LocalDate.of(year, month, day);
		if (day <= 15) {
			int[] row = vgcs.getTable().getSelectedRows();
			// TODO CAMBIAR EL 0 POR LA COLUMNA DEL ID DEL CURSO
			for(int i = 0; i < row.length; i++) {
				int id = Integer.parseInt(vgcs.getDatos()[row[i]][0].toString());
				System.out.println("ID:" + id);
//				vp.getDb().executeUpdate(BORRADECURSO, id);
			// TODO COMPROBAR QUE FUE BORRADO
			// TODO CAMBIAR EL 0 POR LA COLUMNA DEL PRECIO DEL CURSO
				int precio = Integer.parseInt(vgcs.getDatos()[row[i]][0].toString());
//				vp.getDb().executeUpdate(ACTUALIZAPRECIO, precio);
			}
		}
		else {
			//TODO COMPROBAR, PERO CREO QUE SÓLO BORRA DEL CURSO
		}
	}
	
	public Object[][] cargaFilas() {
		int id;
		if (vgcs.getVp().getTramitarLicencia().esDirectivo())
			id = vgcs.getVp().getTramitarLicencia().getDirectivo().getId();
		else
			id = vgcs.getVp().getTramitarLicencia().getSocio().getId();
		List<Object[]> cursos = vgcs.getVp().getDb().executeQueryArray(CARGADATOS, id);
		Object[][] datos = new Object[cursos.size()][cursos.get(0).length + 1];
		for (int i = 0; i < cursos.size(); i++) {
			Object[] columnas = cursos.get(i);
			for (int j = 0; j < columnas.length - 1; j++) {
				datos[i][j] = columnas[j];
			}
			if(vgcs.getVp().getDb().executeQueryArray(ESTA_INSCRITO, id).isEmpty())
				datos[i][columnas.length] = creaBotonBorrarse();
			else
				datos[i][columnas.length] = creaBotonInscribirse();
		}
		return datos;
	}
	
	private JButton creaBotonInscribirse() {
		// TODO Auto-generated method stub
		JButton btInscribirse = new JButton("Inscribirse");
		btInscribirse.setEnabled(true);
		btInscribirse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					inscribirse();
				}
			});
		return null;
	}

	protected void inscribirse() {
		// TODO Auto-generated method stub
		
	}

	private JButton creaBotonBorrarse() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] cargaNomColumnas() {
		return new String[]{"ID CURSO","NOMBRE CURSO","DEPORTE"}; 					//TODO CAMBIAR
	}
	
}
