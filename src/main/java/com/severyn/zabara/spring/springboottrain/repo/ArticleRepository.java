package com.severyn.zabara.spring.springboottrain.repo;

import com.severyn.zabara.spring.springboottrain.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,Long> {
}
