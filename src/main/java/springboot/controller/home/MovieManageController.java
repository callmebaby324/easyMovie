package springboot.controller.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import springboot.dao.MovieMapper;
import springboot.modal.vo.Movie;
import springboot.service.IMovieService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 电影管理
 *
 * @author tangj
 * @date 2018/2/17 9:47
 */
@Controller
@RequestMapping("/movieManage")
@Slf4j
public class MovieManageController {

    @Autowired
    private IMovieService movieService;
    @Resource
    private MovieMapper movieMapper;

    /**
     * 1.上传图片
     */
    @ResponseBody
    @PostMapping(value = "/upload")
    private String uploadImg(HttpServletRequest request,@RequestParam MultipartFile file) {
        try {
            //图片访问路径
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/img/";
            //图片上传位置
            String path = MovieManageController.class.getClassLoader().getResource("static/img").getPath();
            //获取文件名加后缀
            System.out.println(request);
            System.out.println(path);
            String fileName = file.getOriginalFilename();
            File file1 = new File(path);
            File targetFile = new File(file1,fileName);

            File file2 = new File("./");
            System.out.println("files2:"+file2.getPath());

            //上传
            file.transferTo(targetFile);
            String url=returnUrl+fileName;
            Integer[] nums = {1,2};

            ArrayList<Integer> list = new ArrayList<>();
            List<Integer> ints = new ArrayList<>(Arrays.asList(nums));
            return url;
        } catch (Exception e) {
            log.error("upload fail:",e);
            return "FAIL";
        }
    }

    /**
     * 2.添加电影
     */
    @PostMapping(value = "/add")
    @ResponseBody
    private String add(Movie movie) {
        try {
            movieService.addMovie(movie);
        } catch (Exception e) {
            log.error("insert fail:",e);
            return "FAIL";
        }
        return "SUCCESS";
    }


}
