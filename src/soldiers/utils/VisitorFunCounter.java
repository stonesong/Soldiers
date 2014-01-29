package soldiers.utils;
import soldiers.entity.ArmedUnit;
import soldiers.entity.ArmedUnitSquad;


public class VisitorFunCounter implements VisitorFunForArmedUnit<Integer>{

	public Integer visit(ArmedUnit f) {
		return 1;
	}

	public Integer visit(ArmedUnitSquad a) {
		return 0;
	}

	public Integer compos(Integer i1, Integer i2) {
		return i1 + i2;
	}
}
