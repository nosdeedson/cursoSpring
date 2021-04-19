package br.com.edson.cursoSpring.helper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.edson.cursoSpring.exceptions.FileException;

@Service
public class ImageHelper {
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public BufferedImage toBufferedImageFromMultPartFile( MultipartFile file) {
		String extensao = FilenameUtils.getExtension(file.getOriginalFilename());
		if( !extensao.equals("png") && !extensao.equals("jpg") && !extensao.equals("jpeg")) {
			throw new FileException("A imagem deve ter a extensão PNG, JPG pu JPEG.");
		}
		try {
			BufferedImage img = ImageIO.read(file.getInputStream());
			if ("png".equals(extensao)) {
				img = this.pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Não foi possível ler a imagem.");
		}
	}
	
	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	public InputStream toInputStreamFromBufferedImage( BufferedImage img) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "jpg", outStream);
			return new ByteArrayInputStream(outStream.toByteArray()) ;
		} catch (IOException e) {
			throw new FileException("Não foi possível ler a imagem.");
		}
	}
	
	public BufferedImage cropSquare( BufferedImage img) {
		int min = ( img.getHeight() <= img.getWidth()) ? img.getHeight() : img.getWidth();
		return Scalr.crop(img, 
				(img.getWidth() / 2) - (min/2),
				(img.getHeight() / 2 ) - (min/2),
				min , min
				);
	}
	
	public BufferedImage resize( BufferedImage img, Integer size) {
		 return Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, size);
	}
	
}
