package springboot.controller.home;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.constant.WebConst;
import springboot.controller.AbstractController;
import springboot.dao.MovieMapper;
import springboot.modal.vo.Movie;
import springboot.service.IMovieService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

}
