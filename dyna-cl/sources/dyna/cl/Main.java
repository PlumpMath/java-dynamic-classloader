package dyna.cl;

import java.io.File;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("watching directory: "+ModuleWatcher.instance.watchedDirectory);
		System.out.println("current working directory: "+new File(".").getCanonicalPath());
		System.out.flush();
		while (true) {
			Thread.sleep(Long.MAX_VALUE);
		}
	}
}
