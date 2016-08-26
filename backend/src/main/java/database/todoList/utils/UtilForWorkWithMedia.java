package database.todoList.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@Component
public class UtilForWorkWithMedia implements Serializable {
	public static BufferedImage getBufferedImage(File file) throws IOException {
		return ImageIO.read(file);
	}
	/**
	 * Encode image to Base64 string
	 * @param image The image to Base64 encode
	 * @param type jpeg, bmp, ...
	 * @return Base64 encoded string
	 */
	public static String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();

			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);

			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageString;
	}
	public static String getBASE64StringFromImage(String pathToImage) throws IOException {
		File fileOfIMG = new File(pathToImage);
		String extensionOfFile = UtilForWorkWithFileAndDirectories.getFileExtension(fileOfIMG);

		BufferedImage bufferedImage = UtilForWorkWithMedia.getBufferedImage(fileOfIMG);
		String encodingString = UtilForWorkWithMedia.encodeToString(bufferedImage, extensionOfFile);

		return "data:image/" + extensionOfFile + ";base64," + encodingString;
	}

}
