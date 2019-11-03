package com.asing1elife.teamnote.core.service;

import com.asing1elife.teamnote.core.exception.CustomException;
import com.asing1elife.teamnote.core.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseService<T, Repository extends BaseRepository<T, Long>> implements DefaultService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Repository repository;

    public T save(T t) {
        logger.info("save entity -> {}", t);
        return repository.save(t);
    }

    public void delete(long id) {
        logger.info("remove entity id -> {}", id);
        repository.deleteById(id);
    }

    public Page<T> page(HttpServletRequest request) {
        throw new CustomException("this method must be override");
    }

    public List<T> findAll(Sort... sort) {
        List<T> all = sort.length == 0 ? repository.findAll() : repository.findAll(sort[0]);

        logger.info("findAll size -> {}", all.size());
        return all;
    }

    public T getOne(long id) {
        T t = repository.getOne(id);

        logger.info("getOne entity -> {}", t);
        return t;
    }

}
