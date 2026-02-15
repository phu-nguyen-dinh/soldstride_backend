package com.solestride.service;

import com.solestride.dto.ProductDto;
import com.solestride.entity.Product;
import com.solestride.entity.ProductVariant;
import com.solestride.exception.ResourceNotFoundException;
import com.solestride.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public List<ProductDto.ProductResponse> getAllProducts(String brand, String category, Double minPrice, Double maxPrice) {
        Specification<Product> spec = Specification.where(null);
        
        if (brand != null && !brand.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        }
        
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));
        }
        
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        
        return productRepository.findAll(spec).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public ProductDto.ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToResponse(product);
    }
    
    @Transactional
    public ProductDto.ProductResponse createProduct(ProductDto.ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());
        product.setFeatured(request.getFeatured() != null ? request.getFeatured() : false);
        
        Product savedProduct = productRepository.save(product);
        
        if (request.getVariants() != null && !request.getVariants().isEmpty()) {
            for (ProductDto.ProductVariantDto variantDto : request.getVariants()) {
                ProductVariant variant = new ProductVariant();
                variant.setSize(variantDto.getSize());
                variant.setColor(variantDto.getColor());
                variant.setStock(variantDto.getStock());
                savedProduct.addVariant(variant);
            }
            savedProduct = productRepository.save(savedProduct);
        }
        
        return convertToResponse(savedProduct);
    }
    
    @Transactional
    public ProductDto.ProductResponse updateProduct(Long id, ProductDto.ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(request.getCategory());
        product.setFeatured(request.getFeatured() != null ? request.getFeatured() : false);
        
        // Update variants
        if (request.getVariants() != null) {
            product.getVariants().clear();
            for (ProductDto.ProductVariantDto variantDto : request.getVariants()) {
                ProductVariant variant = new ProductVariant();
                variant.setSize(variantDto.getSize());
                variant.setColor(variantDto.getColor());
                variant.setStock(variantDto.getStock());
                product.addVariant(variant);
            }
        }
        
        Product updatedProduct = productRepository.save(product);
        return convertToResponse(updatedProduct);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    
    private ProductDto.ProductResponse convertToResponse(Product product) {
        List<ProductDto.ProductVariantDto> variantDtos = product.getVariants().stream()
                .map(v -> new ProductDto.ProductVariantDto(v.getSize(), v.getColor(), v.getStock()))
                .collect(Collectors.toList());
        
        return new ProductDto.ProductResponse(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCategory(),
                product.getFeatured(),
                variantDtos
        );
    }
}
