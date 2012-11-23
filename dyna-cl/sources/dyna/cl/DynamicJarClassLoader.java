package dyna.cl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class DynamicJarClassLoader extends ClassLoader {
	private JarInfo jarInfo;
	public DynamicJarClassLoader(JarInfo jarInfo, ClassLoader parentClassLoader) {
		super(parentClassLoader);
		this.jarInfo = jarInfo;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			return super.loadClass(name);	
		} catch (ClassNotFoundException e) {}
		System.out.println("Requested Class "+name+" from jar "+jarInfo.fileName);
		try {
			String fileName = name.replace('.', '/') + ".class";
			System.out.println("fileList: "+jarInfo.fileList);
			System.out.println("fileName: "+fileName);
			if (jarInfo.fileList.contains(fileName)) {
				byte[] data = JarUtil.readFile(jarInfo.fileName, fileName);
				return defineClass(name, data, 0, data.length);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		throw new ClassNotFoundException("Class "+name+" not found in jar "+jarInfo.fileName);
	}
	
	@Override
	public InputStream getResourceAsStream(String resourceName) {
		try {
			return new ByteArrayInputStream(JarUtil.readFile(jarInfo.fileName, resourceName));
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new RuntimeException(e);
		}
	}
}
