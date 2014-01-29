package soldiers.rule;

import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameEntity;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Vector;

import soldiers.entity.ArmedUnitSoldier;
import soldiers.entity.ArmedUnitSquad;
import soldiers.entity.Health;
import soldiers.entity.Horseman;
import soldiers.entity.InfantryMan;
import soldiers.entity.Shield;
import soldiers.entity.SoldierAbstract;
import soldiers.entity.Sword;
import soldiers.entity.TeleportPairOfPoints;
import soldiers.utils.MiddleAgeFactory;
import soldiers.weapon.SoldierArmed;
import soldiers.weapon.SoldierWithShield;
import soldiers.weapon.SoldierWithSword;

@SuppressWarnings("rawtypes")
public class SoldiersOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Vector<SoldierAbstract> vSoldiers = new Vector<SoldierAbstract>();
	protected Vector<ArmedUnitSoldier> vSquad = new Vector<ArmedUnitSoldier>();
	protected Vector<ArmedUnitSquad> vSquads = new Vector<ArmedUnitSquad>();
	protected Vector<SoldierArmed> vArmed = new Vector<SoldierArmed>();
	protected Vector<Health> vHealth = new Vector<Health>();
	


	private final ObservableValue<Boolean> endOfGame;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	
	private int totalNbSoldiers = 8;
	private int nbKilledSoldiers = 0;
	private int nbDefeatedArmies = 0;
	private int totalNbArmies = 7;
	private int nbLives = 3;

	Canvas defaultCanvas;


	public SoldiersOverlapRules(Point pacPos, Point gPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame, Canvas defaultCanvas) {
		this.endOfGame = endOfGame;
		this.defaultCanvas = defaultCanvas;
		this.life = life;
		this.score = score;
	}

	public void setLife(int i){
		this.life.setValue(i);
	}
	
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}


	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		super.applyOverlapRules(overlappables);
	}

	
	
	public void overlapRule(InfantryMan im, TeleportPairOfPoints teleport) {
		im.setPosition(teleport.getDestination());
	}

	public void overlapRule(ArmedUnitSquad squad, TeleportPairOfPoints teleport) {
		squad.setPosition(teleport.getDestination());
	}
	
	public void overlapRule(SoldierAbstract s1, SoldierAbstract s2) {
		System.out.println("soldiers overlapping");
		s2.parry(s1.strike());
		s1.parry(s2.strike());
		if(s1.getHealthPoints() <= 0){
			universe.removeGameEntity(s1);
			soldierKilledHandler();
		}
		if(s2.getHealthPoints() <= 0){
			universe.removeGameEntity(s2);
			soldierKilledHandler();
		}
	}
	
	public void overlapRule(InfantryMan s1, InfantryMan s2) {
		System.out.println("soldiers overlapping");
		s2.parry(s1.strike());
		s1.parry(s2.strike());
		if(s1.getHealthPoints() <= 0){
			universe.removeGameEntity(s1);
			soldierKilledHandler();
		}
		if(s2.getHealthPoints() <= 0){
			universe.removeGameEntity(s2);
			soldierKilledHandler();
		}
	}
	
	private void soldierKilledHandler() {
		System.out.println("soldier killed");
		nbKilledSoldiers++;
		if (nbKilledSoldiers >= totalNbSoldiers - 1) {
			endOfGame.setValue(true);
		}
	}
	
	private void armyKilledHandler(ArmedUnitSquad a) {
		System.out.println(a.getName() + " defeated");
		nbDefeatedArmies++;
		if (nbDefeatedArmies >= totalNbArmies - 1) {
			System.out.println("CONGRATULATIONS, YOU WON!");
			endOfGame.setValue(true);
		}
		if(a.getName().equals("My Team "))
			endOfGame.setValue(true);
			
	}
	
	public void overlapRule(ArmedUnitSquad a, InfantryMan s) {
		if(a.getName().equals("My Team "))
			score.setValue(score.getValue() + 1);
		System.out.println(a.getName() + "Recruiting Soldiers");
		if(s.belongToArmy == false){
			ArmedUnitSoldier recruit = new ArmedUnitSoldier(new MiddleAgeFactory(), "Simple", "rookie", this.defaultCanvas);
			a.addUnit(recruit);
			universe.removeGameEntity(s);
		}
	}
	
	public void overlapRule(ArmedUnitSquad a, Horseman h) {
		System.out.println(a.getName() + "Recruiting Soldiers");
		if(h.belongToArmy == false){
			ArmedUnitSoldier recruit = new ArmedUnitSoldier(new MiddleAgeFactory(), "Complex", "rookie", this.defaultCanvas);
			a.addUnit(recruit);
			universe.removeGameEntity(h);
		}
	}
	
	public void overlapRule(ArmedUnitSquad a, SoldierWithSword s) {
		System.out.println("Recruiting Soldiers");
		if(s.belongToArmy == false){
			ArmedUnitSoldier recruit = new ArmedUnitSoldier(new MiddleAgeFactory(), "Complex", "rookie", this.defaultCanvas);
			a.addUnit(recruit);
			universe.removeGameEntity((GameEntity) s);
		}
	}
	
	public void overlapRule(ArmedUnitSquad a, SoldierWithShield s) {
		if(a.getName().equals("My Team "))
			score.setValue(score.getValue() + 1);
		System.out.println("Recruiting Soldiers");
		if(s.belongToArmy == false){
			ArmedUnitSoldier recruit = new ArmedUnitSoldier(new MiddleAgeFactory(), "Complex", "rookie", this.defaultCanvas);
			a.addUnit(recruit);
			universe.removeGameEntity((GameEntity) s);
		}
	}
	
	public void overlapRule(ArmedUnitSquad a1, ArmedUnitSquad a2) {
		
		if(a1.getName().equals("My Team ") || a2.getName().equals("My Team ")){
			
			score.setValue(score.getValue() + 2);

			System.out.println("Squad War");
			System.out.println(a1.getName() + "strike: " + a1.strike());
			System.out.println(a2.getName() + "strike: " + a2.strike());
			System.out.println(a1.getName() + "health: " + a1.getHealthPoints());
			System.out.println(a2.getName() + "health: " + a2.getHealthPoints());

			float strikeTmp = a2.strike();
			a2.parry(a1.strike());
			a1.parry(strikeTmp);

			if(a1.getHealthPoints() <= 0){
				if(a1.getName().equals("My Team ")){
					setLife(life.getValue() - 1);
					if(--nbLives>0)
						a1.heal();
					else{
						universe.removeGameEntity(a1);
						armyKilledHandler(a1);
					}
				}
				else{
					universe.removeGameEntity(a1);
					armyKilledHandler(a1);
				}
			}

			if(a2.getHealthPoints() <= 0){
				if(a2.getName().equals("My Team ")){
					setLife(life.getValue() - 1);
					if(--nbLives>0)
						a2.heal();
					else{
						universe.removeGameEntity(a2);
						armyKilledHandler(a2);
					}
				}
				else{
					universe.removeGameEntity(a2);
					armyKilledHandler(a2);
				}
			}
		}

		else{
			System.out.println("Friendly Encounter");
		}
	}
	
	public void overlapRule(ArmedUnitSquad a1, Health h){
		a1.heal();
		universe.removeGameEntity(h);
		System.out.println("Healing " + a1.getName() + " " + a1.getHealthPoints());
	}
	
	public void overlapRule(ArmedUnitSquad a1, Sword s){
		a1.addEquipment("Offensive", defaultCanvas);
		universe.removeGameEntity(s);
		System.out.println(a1.getName() + " added sword");
	}
	
	public void overlapRule(ArmedUnitSquad a1, Shield s){
		a1.addEquipment("Defensive", defaultCanvas);
		universe.removeGameEntity(s);
		System.out.println(a1.getName() + " added shield");
	}

	public void addSoldier(SoldierAbstract mySoldier) {
		vSoldiers.addElement(mySoldier);		
	}
	
	public void addSoldierArmed(SoldierArmed mySoldier) {
		vArmed.addElement(mySoldier);		
	}

	public void addSoldier(ArmedUnitSoldier myUnit) {
		vSquad.addElement(myUnit);		
	}

	public void addSoldier(ArmedUnitSquad team1) {
		vSquads.addElement(team1);		
	}

	public void addHealth(Health health) {
		vHealth.addElement(health);	
	}

	
		
	
}
