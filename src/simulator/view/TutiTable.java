package simulator.view;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

public class TutiTable extends AbstractTableModel implements EcoSysObserver {

	private static final long serialVersionUID = 1L;
	private String[] _cols_name = { "Pasos", "Danger", "Hunger" };
	private int pasitoApasitoSuaveSuavecito = 0;
	private List<Info> cosas;

	class Info {
		Integer paso;
		Integer danger;
		Integer hunger;

		Info(List<AnimalInfo> animals, int pasos) {
			paso = pasos;
			hunger = (int) animals.stream().filter((a) -> a.get_state() == Animal.State.HUNGER).count();
			danger = (int) animals.stream().filter((a) -> a.get_state() == Animal.State.DANGER).count();
		}
	}

	public TutiTable(Controller ctrl) {
		pasitoApasitoSuaveSuavecito = 0;
		cosas = new LinkedList<Info>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this.cosas == null ? 0 : this.cosas.size();
	}

	@Override
	public int getColumnCount() {
		return _cols_name.length;
	}

	public String getColumnName(int col) {
		return _cols_name[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.cosas.get(rowIndex).paso;
		} else if (columnIndex == 1) {
			return this.cosas.get(rowIndex).danger;
		} else {
			return this.cosas.get(rowIndex).hunger;
		}
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		;
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		this.cosas.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		;
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		;
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		update(animals);
	}

	// actualiza la tabla con los valores del mapa
	private void update(List<AnimalInfo> animals) {
		pasitoApasitoSuaveSuavecito++;
		this.cosas.add(new Info(animals, pasitoApasitoSuaveSuavecito));
		if (this.cosas.size() > 10) {
			this.cosas.remove(0);
		}
		// avisamos de que la tabla ha cambiado
		fireTableStructureChanged();
	}

}
