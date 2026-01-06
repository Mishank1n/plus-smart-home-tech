package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.StoreProduct;
import ru.yandex.practicum.store.dto.ProductCategory;

import java.util.List;

public interface StoreProductRepository extends JpaRepository<StoreProduct, String> {

    List<StoreProduct> findAllByProductCategory(ProductCategory category);

    List<StoreProduct> findAllByProductCategory(ProductCategory category, Sort sort);

    Page<StoreProduct> findAllByProductCategory(ProductCategory category, Pageable pageable);
}