package br.com.pjc.minio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.common.io.ByteStreams;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.messages.Bucket;

@Service
public class MinioAdapter {

    @Autowired
    MinioClient minioClient;

    @Value("${minio.buckek.name}")
    String defaultBucketName;
    
    public String getDefaultBucketName() {
		return defaultBucketName;
	}
    
    public MinioClient getMinioClient() {
		return minioClient;
	}

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    
    
	public void uploadImagem(String diretorioImagem, String contentType, byte[] content) {
        try {
        	InputStream imageStream = new ByteArrayInputStream(content);
        	 boolean existeBucket =  getMinioClient().bucketExists(BucketExistsArgs.builder().bucket(defaultBucketName).build());
        	 if (!existeBucket) {
        		 getMinioClient().makeBucket(MakeBucketArgs.builder().bucket(defaultBucketName).build());
         	 } 
           	getMinioClient().putObject(    PutObjectArgs.builder().bucket(getDefaultBucketName() ).object(diretorioImagem).stream(
			            	    	  imageStream, -1, 10485760)
			            	          .contentType(contentType)
			            	          .build());
        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }

    }
            
    
    public byte[] getImage(String diretorioImagem) {
        try {
        	InputStream stream = getMinioClient().getObject(GetObjectArgs.builder().bucket(getDefaultBucketName() ).object(diretorioImagem).build());
        	byte[] targetArray = ByteStreams.toByteArray(stream);
        	stream.close();
            return targetArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}