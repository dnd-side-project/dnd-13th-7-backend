package com.moyeoit.context.community.domain;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(Long id);
}
