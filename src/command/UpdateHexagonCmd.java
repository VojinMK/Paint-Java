package command;

import adapter.HexagonAdapter;
import hexagon.Hexagon;

public class UpdateHexagonCmd implements Command {

	HexagonAdapter newState;
	HexagonAdapter hexagon;
	HexagonAdapter original = new HexagonAdapter(new Hexagon(0, 0, 0));
	public String toString;

	public UpdateHexagonCmd(HexagonAdapter hexagon, HexagonAdapter newState) {
		this.hexagon = hexagon;
		this.newState = newState;
		toString = hexagon.toString() + ":to" + newState.toString();
	}

	@Override
	public void execute() {

		hexagon.clone(original);

		newState.clone(hexagon);

	}

	@Override
	public void unexecute() {

		original.clone(hexagon);
	}

	public String getToString() {
		return toString;
	}

}
