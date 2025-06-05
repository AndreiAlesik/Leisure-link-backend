package work.service.util;

import org.springframework.web.multipart.MultipartFile;

public interface UtilService {
  byte[] compressImage(MultipartFile file, float compressionQuality);

  byte[] decompressImage(byte[] compressedImageData);
}
