package com.hfut.book.component;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * FastDFS组件
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@Component
public class FastdfsComponent {

    @Resource
    FastFileStorageClient storageClient;

    /**
     * 上传文件
     *
     * @param filePath 文件路径
     * @return 文件在FastDFS中的路径
     */
    public String uploadFile(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(fileInputStream,
                    file.length(), "png", null);
            return "http://47.100.139.173:8888/" + storePath.getFullPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 上传图片文件
     *
     * @param multipartFile 图片文件
     * @return 文件在FastDFS中的路径
     */
    public String uploadMultipartFile(MultipartFile multipartFile) {
        try {
            String originalFilename = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(
                    multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
            StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                    multipartFile.getInputStream(), multipartFile.getSize(), originalFilename, null);
            // 文件在FastDFS中的路径
            return "http://47.100.139.173:8888/" + storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件完整路径
     * @return 是否删除成功
     */
    public Boolean deleteFile(String filePath) {
        storageClient.deleteFile(filePath);
        return true;
    }
}
