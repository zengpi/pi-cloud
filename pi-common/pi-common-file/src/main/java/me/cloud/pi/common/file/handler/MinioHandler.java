/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.cloud.pi.common.file.handler;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.common.file.config.MinioProperties;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZnPi
 * @date 2022-12-05 14:39
 */
@Slf4j
@RequiredArgsConstructor
public class MinioHandler {
    private final MinioProperties minioProperties;
    private volatile MinioClient minioClient;

    public void init() {
        log.info("初始化 Minio 客户端...");
        // Create a minioClient with the MinIO server, its access key and secret key.
        this.minioClient =
                MinioClient.builder()
                        .endpoint(minioProperties.getEndpoint())
                        .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                        .build();
    }

    /**
     * Uploads data from a stream to an object and return its urls.
     *
     * @param obj object's name
     * @return url of data
     */
    @SneakyThrows
    public String putObject(MultipartFile obj) {
        if (this.minioClient == null) {
            synchronized (this) {
                if (this.minioClient == null) {
                    init();
                }
            }
        }

        makeBucket();

        String extName = FileUtil.extName(obj.getOriginalFilename());
        String uuid = IdUtil.simpleUUID();
        String fileName = uuid + "." + extName;

        // Uploads data from a stream to an object.
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                        .stream(obj.getInputStream(), obj.getSize(), -1)
                        .contentType(obj.getContentType())
                        .build());

        String url =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(minioProperties.getBucket())
                                .object(fileName)
                                .build());
        return url.substring(0, url.indexOf("?"));
    }

    /**
     * Removes an object.
     *
     * @param objName object's name
     */
    @SneakyThrows
    public void removeObject(String objName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucket()).object(objName).build());
    }

    @SneakyThrows
    private void makeBucket() {
        // Make 'pi-cloud' bucket if not exist.
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
        if (!found) {
            // Make a new bucket called 'pi-cloud'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());

            String policyJson = "{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": \"*\",\n" +
                    "            \"Action\": [\n" +
                    "                \"s3:GetObject\"\n" +
                    "            ],\n" +
                    "            \"Resource\": \"arn:aws:s3:::" + minioProperties.getBucket() + "/*\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder().bucket(minioProperties.getBucket()).config(policyJson).build());
        } else {
            log.info("Bucket '{}' already exists.", minioProperties.getBucket());
        }
    }
}
