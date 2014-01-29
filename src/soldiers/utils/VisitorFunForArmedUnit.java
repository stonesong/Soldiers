package soldiers.utils;
import soldiers.entity.ArmedUnit;
import soldiers.entity.ArmedUnitSquad;


public interface VisitorFunForArmedUnit<T> {
	T visit(ArmedUnit s);
	T visit(ArmedUnitSquad a);
	T compos(T x1, T x2);
}
