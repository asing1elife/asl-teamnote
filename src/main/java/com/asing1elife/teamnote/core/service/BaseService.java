package com.asing1elife.teamnote.core.service;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class BaseService<T, Repository extends BaseRepository<T, Long>> extends PageService<T, Repository> {

    public T save(T t) {
        logger.info("save entity -> {}", t);
        return repository.save(t);
    }

    public void delete(long id) {
        logger.info("remove entity id -> {}", id);
        repository.deleteById(id);
    }

    public Page<T> page(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createTime");

        return getPageData(pageable);
    }

    public List<T> findAll(Sort... sort) {
        List<T> all = sort.length == 0 ? repository.findAll() : repository.findAll(sort[0]);

        logger.info("findAll size -> {}", all.size());
        return all;
    }

    public T get(long id) {
        T t = repository.getOne(id);

        logger.info("get entity -> {}", t);
        return t;
    }

}
