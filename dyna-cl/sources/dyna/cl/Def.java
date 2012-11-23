package dyna.cl;

import java.io.InputStream;
import java.util.Properties;

public class Def {
	public String modulename;
	public String version;
	public String entrypoint;
	
	public static Def load(InputStream is) throws Exception {
		Properties p = new Properties();
		p.load(is);
		is.close();
		Def def = new Def();
		def.modulename = p.getProperty("modulename");
		def.version = p.getProperty("version");
		def.entrypoint = p.getProperty("entrypoint");
		return def;
	}
	
}
