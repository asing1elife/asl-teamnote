package com.asing1elife.teamnote.module.dictionary.controller;

import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.model.DictionaryModel;
import com.asing1elife.teamnote.module.dictionary.service.DictionaryServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryController extends BaseController<DictionaryModel, DictionaryServiceImpl> {
}
