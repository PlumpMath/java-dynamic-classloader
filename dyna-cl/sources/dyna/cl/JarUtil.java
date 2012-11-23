package dyna.cl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class JarUtil {
	public static byte[] readFile(String jarPath, String fileName) throws Exception {
		ZipFile zf = new ZipFile(new File(jarPath));
		Enumeration<? extends ZipEntry> en =  zf.entries();
		while (en.hasMoreElements()) {
			ZipEntry entry = en.nextElement();
			if (entry.getName().equals(fileName)) {
				InputStream is = zf.getInputStream(entry);
				byte[] content = new byte[(int) entry.getSize()];
				is.read(content);
				is.close();
				zf.close();
				return content;
			}
		}
		zf.close();
		System.out.println("File "+fileName+" non trovato nel jar "+jarPath);
		return null;
	}
	public static List<String> listFiles(String jarPath) throws Exception {
		ZipFile zf = new ZipFile(new File(jarPath));
		Enumeration<? extends ZipEntry> en =  zf.entries();
		List<String> files = new ArrayList<>();
		while (en.hasMoreElements())
			files.add(en.nextElement().getName());
		zf.close();
		return files;
	}
	
	public static JarInfo getInfo(String filePath) throws Exception {
		JarInfo jarInfo = new JarInfo();
		jarInfo.fileName = filePath;
		jarInfo.fileList = listFiles(filePath);
		byte[] defContent = readFile(filePath, "def");
		if (defContent == null) {
			System.err.println(filePath+" does not contain a def file: skipped");
			return null;
		}
		jarInfo.def = Def.load(new ByteArrayInputStream(defContent));
		return jarInfo;
		
	}
}
