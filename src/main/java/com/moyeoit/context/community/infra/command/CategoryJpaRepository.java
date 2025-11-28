package com.moyeoit.context.community.infra.command;

import com.moyeoit.context.community.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category,Long>{
}
