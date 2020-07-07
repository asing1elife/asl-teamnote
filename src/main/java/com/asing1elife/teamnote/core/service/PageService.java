/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.core.service;

import com.asing1elife.teamnote.core.exception.CustomException;
import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.core.util.DateUtil;
import com.asing1elife.teamnote.model.simple.PageParam;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class PageService<T, Repository extends BaseRepository<T, Long>> extends DefaultService {

    @Autowired
    protected Repository repository;

    /**
     * 获取分页数据
     */
    protected Page<T> getPageData(Pageable pageable) {
        return repository.findAll((Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate[] predicates = generatePredicates(root, criteriaBuilder);

            return criteriaBuilder.and(predicates);
        }, pageable);
    }

    /**
     * 生成分页条件
     */
    private Predicate[] generatePredicates(Root root, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();

        List<PageParam> pageParams = filterPageParameters();
        pageParams.forEach(pageParam -> {
            // 获取参数名称
            Path paramName = getParameterName(pageParam, root);
            // 获取参数值
            String paramValue = pageParam.getValue();

            if (pageParam.getType().equals(PageParam.ConditionType.like)) {
                paramValue = super.wrapLikeConditionValue(pageParam.getValue());

                predicates.add(cb.like(paramName, paramValue));
            } else if (pageParam.getType().equals(PageParam.ConditionType.time)) {
                // 转换日期参数
                transformDateParam(pageParam, paramName, predicates, cb);
            } else {
                predicates.add(cb.equal(paramName, paramValue));
            }
        });

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    /**
     * 转换日期参数
     */
    private void transformDateParam(PageParam pageParam, Path paramName, List<Predicate> predicates, CriteriaBuilder cb) {
        String startTime = pageParam.getStartTime();
        String endTime = pageParam.getEndTime();

        if (StringUtils.isNotBlank(startTime)) {
            // 大于或等于传入时间
            predicates.add(cb.greaterThanOrEqualTo(paramName, DateUtil.stringToDate(startTime)));
        }
        if (StringUtils.isNotBlank(endTime)) {
            // 小于或等于传入时间
            predicates.add(cb.lessThanOrEqualTo(paramName, DateUtil.stringToDate(endTime)));
        }
    }

    /**
     * 获取参数名称
     */
    private Path getParameterName(PageParam pageParam, Root root) {
        Path paramName;

        // 判断参数是否为对象
        if (pageParam.getObject()) {
            // 对象参数需要按照 _ 划线将对象和属性分开
            String[] objParams = pageParam.getName().split("_");

            // 参数格式判断
            if (ArrayUtils.isEmpty(objParams)) {
                throw new CustomException(String.format("参数 %s 不符合对象属性规则", pageParam.getName()));
            }

            // 第一个参数是对象，第二个参数是对象的属性
            paramName = root.join(objParams[0]).get(objParams[1]);
        } else {
            paramName = root.get(pageParam.getName());
        }

        return paramName;
    }

    /**
     * 如果需要整理分页条件，可以重写此方法
     */
    protected List<PageParam> filterPageParameters() {
        List<PageParam> pageParams = Lists.newArrayList();

        // 获取所有参数
        Map<String, String[]> parameters = request.getParameterMap();
        parameters.forEach((key, value) -> {
            // 跳过分页条件
            if (("page").equals(key) || ("size").equals(key)) {
                return;
            }

            // 过滤无效值
            String paramValue = value[0];
            if (StringUtils.isEmpty(paramValue)) {
                return;
            }

            // 获取有效条件
            pageParams.add(new PageParam(key, paramValue));
        });

        return pageParams;
    }

}
