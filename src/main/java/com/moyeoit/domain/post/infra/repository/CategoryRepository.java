package com.moyeoit.domain.post.infra.repository;

import com.moyeoit.domain.post.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
