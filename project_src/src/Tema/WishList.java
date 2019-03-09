package Tema;

public class WishList extends ItemList<Item> {
	
	Strategy strat;
	
	public WishList(String strategy) {
		super();
		switch(strategy) {
			case "A":
				strat = (Strategy) new StrategyA();
				break;
			case "B":
				strat = (Strategy) new StrategyB();
				break;
			case "C":
				strat = (Strategy) new StrategyC();
				break;
			default:
				strat = null;
		}
	}
}
