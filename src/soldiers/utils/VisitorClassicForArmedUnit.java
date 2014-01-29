package soldiers.utils;

import soldiers.entity.*;

public interface VisitorClassicForArmedUnit {
	void visit(ArmedUnit s);
	void visit(ArmedUnitSquad a);
}
 