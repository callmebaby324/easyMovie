package springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import springboot.dao.MovieMapper;
import springboot.modal.vo.Movie;
import springboot.service.IMovieService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService {

    @Resource
    private MovieMapper movieMapper;

    @Override
    public PageInfo<Movie> getMovies(int p, int limit) {
        PageHelper.startPage(p, limit);
        List<Movie> movies = movieMapper.selectAll();
        for(Movie movie :movies){
            if(movie.getContent().length()>100){
                movie.setContent(movie.getContent().substring(0,100));
            }
        }
        PageInfo<Movie> pageInfo = new PageInfo<>(movies);
        return pageInfo;
    }

    @Override
    public void addMovie(Movie movie) {
        movieMapper.insert(movie);
    }
}
