package com.cretin.web;

import com.cretin.domain.Product;
import com.cretin.factory.BasicFactory;
import com.cretin.service.ProductService;
import com.cretin.util.IOUtils;
import com.cretin.util.PicUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name = "AddProductServlet", urlPatterns = "/AddProductServlet" )
public class AddProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = BasicFactory.getFactory().getInstance(ProductService.class);
        try {
            String encode = getServletContext().getInitParameter("encode");
            Map<String, String> paramMap = new HashMap<String, String>();
            //1.上传图片
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024);
            factory.setRepository(new File(this.getServletContext().getRealPath("WEB-INF/temp")));

            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding(encode);
            fileUpload.setFileSizeMax(1014 * 1024 * 10);
            fileUpload.setSizeMax(1024 * 1024 * 10);

            if ( !fileUpload.isMultipartContent(request) ) {
                throw new RuntimeException("请使用正确的表单进行上传!");
            }

            List<FileItem> list = fileUpload.parseRequest(request);
            for ( FileItem fileItem :
                    list ) {
                if ( fileItem.isFormField() ) {
                    //普通字段
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString(encode);
                    paramMap.put(name, value);
                } else {
                    //文件上传项
                    String realName = fileItem.getName();
                    String uuidName = UUID.randomUUID().toString() + "_" + realName;

                    String hash = Integer.toHexString(uuidName.hashCode());
                    String upload = getServletContext().getRealPath("WEB-INF/upload");
                    String imgurl = "/WEB-INF/upload";
                    for ( char c :
                            hash.toCharArray() ) {
                        upload += "/" + c;
                        imgurl += "/" + c;
                    }
                    imgurl += "/" + uuidName;
                    paramMap.put("imgurl", imgurl);

                    File uploadFile = new File(upload);
                    if ( !uploadFile.exists() ) {
                        uploadFile.mkdirs();
                    }

                    InputStream in = fileItem.getInputStream();
                    OutputStream out = new FileOutputStream(new File(upload, uuidName));

                    IOUtils.In2Out(in, out);
                    IOUtils.close(in, out);

                    //删除临时文件
                    fileItem.delete();

                    //生成缩略图
                    PicUtils picUtils = new PicUtils(this.getServletContext().getRealPath(imgurl));
                    picUtils.resizeByHeight(140);
                }
            }
            //2.调用Service中提供的方法,在数据库中添加商品
            Product prod = new Product();
            BeanUtils.populate(prod, paramMap);
            service.addProduct(prod);

            //3.提示成功,回到主页
            response.getWriter().write("商品添加成功,3s后回到主页");
            response.setHeader("Refresh","3;url=index.jsp");
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
