package com.vortexlab.bigdata.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class HdfsClient {

    private final FileSystem fs;

    public HdfsClient()
            throws Exception {
        Configuration conf = new Configuration();

        conf.set("fs.defaultFS", "hdfs://localhost:9000");

        fs =
                FileSystem.get(conf);
    }


    /**
     * 上传文件
     */
    public void upload(String local, String target) throws Exception {
        fs.copyFromLocalFile(
                new Path(local),
                new Path(target)
        );
    }

    /**
     * 读取文件
     */
    public InputStream read(String path)
            throws Exception {
        return fs.open(new Path(path));
    }
}
