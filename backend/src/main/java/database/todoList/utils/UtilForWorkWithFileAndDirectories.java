package database.todoList.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

@Component
public class UtilForWorkWithFileAndDirectories implements Serializable {
	//метод определения расширения файла
	public static String getFileExtension(String fileName) {
		// если в имени файла есть точка и она не является первым символом в названии файла
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			// то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
			return fileName.substring(fileName.lastIndexOf(".")+1);
			// в противном случае возвращаем заглушку, то есть расширение не найдено
		else return "";
	}

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		return getFileExtension(fileName);
	}

	public static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	public static boolean deleteFile(String pathToFile) {
		File file = new File(pathToFile);
		return deleteFile(file);
	}
	public static boolean deleteFile(File file) {
		return file.isFile() && file.delete();
	}

	/**
	 * Deletes directory with subdirs and subfolders
	 * @author Cloud
	 * @param dir Directory to delete
	 */
	public static void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				File f = new File(dir, children[i]);
				deleteDirectory(f);
			}
			dir.delete();
		} else dir.delete();
	}
}