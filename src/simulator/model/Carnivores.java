package simulator.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;
import simulator.model.MapInfo.RegionData;

public class Carnivores implements EcoSysObserver {
	protected Map<RegionData, Integer> mapa_regiones;

	public Carnivores(Controller ctrl) {
		mapa_regiones = new HashMap<>();
		ctrl.addObserver(this);
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		Iterator<RegionData> it = map.iterator();
		while (it.hasNext()) {
			RegionData r = it.next();
			mapa_regiones.put(r, 0);
		}
		update(map);
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		mapa_regiones.clear();
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		;
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		update(map);
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		update(map);
	}

	// actualiza la tabla con los valores del mapa
	private void update(MapInfo map) {
		Iterator<RegionData> iterator = map.iterator();
		while (iterator.hasNext()) {
			RegionData r = iterator.next();
			List<AnimalInfo> animals = r.r().getAnimalsInfo();
			int aux = (int) animals.stream().filter((a) -> a.get_diet() == Animal.Diet.CARNIVORE).count();
			if (aux > 3) {
				int coso = this.mapa_regiones.get(r);
				this.mapa_regiones.put(r, coso + 1);
			}
		}
	}

	public void printInfo() {
		for(RegionData r: this.mapa_regiones.keySet()) {
			System.out.print("Region: (" + r.row() + "," + r.col()+ ") tiene: "+ this.mapa_regiones.get(r)+ "\n");
		}
	}

}
