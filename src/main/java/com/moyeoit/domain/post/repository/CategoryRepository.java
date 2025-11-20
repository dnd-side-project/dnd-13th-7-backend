package com.moyeoit.domain.post.repository;

import com.moyeoit.domain.post.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
