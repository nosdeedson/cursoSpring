package br.com.edson.cursoSpring.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.edson.cursoSpring.exceptions.AmazonException;
import br.com.edson.cursoSpring.exceptions.FileException;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3;

	@Value("${s3.bucket}")
	private String bucket;

	public URI upLoadFile(MultipartFile file) throws IOException, URISyntaxException {

		try {
			String fileName = file.getName() + "-" + LocalDateTime.now().getDayOfYear();
			String tipoArquivo = file.getContentType();
			InputStream is = file.getInputStream();
			return uploadFile(fileName, tipoArquivo, is);
		} catch (FileException e) {
			throw new FileException("Falha ao converter o arquivo.");
		}

	}

	public URI uploadFile(String fileName, String tipoArquivo, InputStream is) throws URISyntaxException {

		try {
			ObjectMetadata om = new ObjectMetadata();
			om.setContentType(tipoArquivo);
			s3.putObject(bucket, fileName, is, om);
			return s3.getUrl(bucket, fileName).toURI();
		} catch (AmazonServiceException e) {
			throw new AmazonException("Falha ao salvar o arquivo na Amazon", HttpStatus.valueOf(e.getErrorCode()));
		} catch (AmazonClientException e) {
			throw new AmazonException("Falha ao acessar os serviços da Amazon.");
		}
	}

}
