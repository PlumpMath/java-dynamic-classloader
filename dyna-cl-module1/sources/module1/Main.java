package module1;

import dyna.cl.Def;
import dyna.cl.EntryPoint;

public class Main implements EntryPoint {
	public String getVersion() throws Exception {
		return Def.load(Main.class.getResourceAsStream("/def")).version;
	}
	public void execute() throws Exception {
		System.out.println("VERSION: "+getVersion());
	}
}
