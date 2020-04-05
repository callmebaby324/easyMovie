package springboot.controller.home;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springboot.constant.WebConst;
import springboot.controller.AbstractController;
import springboot.dao.MovieMapper;
import springboot.modal.vo.Movie;
import springboot.service.IMovieService;
import springboot.util.ZipUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 首页控制
 *
 * @author tangj
 * @date 2018/2/17 9:47
 */
@Controller
@Slf4j
public class IndexController extends AbstractController {

    @Autowired
    private IMovieService movieService;
    @Resource
    private MovieMapper movieMapper;

    /**
     * 首页
     *
     * @param request
     * @param limit
     * @return
     */
    @GetMapping(value = "/")
    private String index(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return this.index2(request, 1, limit);
    }

    @GetMapping(value = "pages/{p}")
    public String index2(HttpServletRequest request, @PathVariable int p, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        // 开启thymeleaf缓存，加快访问速度
        p = p < 0 || p > WebConst.MAX_PAGE ? 1 : p;
        PageInfo<Movie> movies = movieService.getMovies(p, limit);
        request.setAttribute("movies", movies);
        if (p > 1) {
            this.title(request, "第" + p + "页");
        }
        return this.render("index2");
    }

    @GetMapping(value = {"movie/{id}", "article/{cid}.html"})
    public String getMovie(HttpServletRequest request, @PathVariable String id) {
        Movie movie = movieMapper.selectByPrimaryKey(Integer.parseInt(id));
        if (null == movie) {
            return this.render_404();
        }
        request.setAttribute("movie", movie);
        return this.render("page_movie");
    }

    @PostMapping(value = "/add")
    private String add(Movie movie) {
        try {
            movieService.addMovie(movie);
        } catch (Exception e) {
            log.error("insert fail:",e);
            return "FAIL";
        }
        return "SUCCESS";
    }

    @PostMapping(value = "/uploadZip")
    private String uploadZip(@RequestParam MultipartFile file) {
        try {
            String path = ZipUtils.getDic();
            String fileName = file.getOriginalFilename();
            File file1 = new File(path);
            File targetFile = new File(file1,fileName);
//            file.transferTo(targetFile);
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
            System.out.println(targetFile+"-----------"+path);
            ZipUtils.unZip(targetFile,path);

            //调用接口
            String url = "";
            PostMethod post = new PostMethod(url);
            /*
             * 可以添加多个类型，携带多个参数
             * StringPart
             * FilePart
             */
            Part[] parts = {
                    new FilePart("file", targetFile)
            };
            MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
            /*
             * entity类型
             * ByteArrayRequestEntity
             * InputStreamRequestEntity
             * MultipartRequestEntity
             * StringRequestEntity
             */
            post.setRequestEntity(entity);

        } catch (Exception e) {
            log.error("uploadZip fail:",e);
            return "FAIL";
        }
        return "index2";
    }



}
