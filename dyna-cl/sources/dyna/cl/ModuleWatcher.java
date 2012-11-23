package dyna.cl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleWatcher implements Runnable {
	
	//singleton
	public static ModuleWatcher instance = new ModuleWatcher();
	static {
		new Thread(instance).start();
	}
	
	public List<JarInfo> watched = new ArrayList<JarInfo>();
	public String watchedDirectory = "modules";
	@Override
	public void run() {
		while (true) {
			try {
				List<JarInfo> watched = new ArrayList<JarInfo>();
				String[] files = new File(watchedDirectory).list();
				for (String file: files) {
					System.out.println("Scanning file: "+file);
					if (!file.endsWith(".jar")) continue;
					JarInfo jarInfo = JarUtil.getInfo(watchedDirectory+File.separator+file);
					if (jarInfo == null) continue;
					boolean found = false;
					boolean updated = false;
					for (JarInfo ji : this.watched) {
						if (ji.def.modulename.equals(jarInfo.def.modulename)) {
							found = true;
							if (!ji.def.version.equals(jarInfo.def.version)) {
								System.out.println(jarInfo.fileName+": version changed (" + ji.def.version + " to "+jarInfo.def.version+")");
								updated = true;
								break;
							}
						}
					}
					System.out.println("found: "+found+" updated: "+updated);
					if (!found || updated) {
						EntryPoint entryPoint =
								(EntryPoint)
									new DynamicJarClassLoader(
										jarInfo, ClassLoader.getSystemClassLoader())
									.loadClass(jarInfo.def.entrypoint)
									.newInstance();
						entryPoint.execute();
					}
					watched.add(jarInfo);
				}
				this.watched = watched;
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace(System.out);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				return;
			}
		}
	}
}
