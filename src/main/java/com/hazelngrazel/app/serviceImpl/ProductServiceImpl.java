package com.hazelngrazel.app.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hazelngrazel.app.dto.ProductRequest;
import com.hazelngrazel.app.dto.ProductResponse;
import com.hazelngrazel.app.dto.ProductVariantRequest;
import com.hazelngrazel.app.entity.CategoryEntity;
import com.hazelngrazel.app.entity.ProductEntity;
import com.hazelngrazel.app.entity.ProductVariantEntity;
import com.hazelngrazel.app.entity.ProductImageEntity;
import com.hazelngrazel.app.repository.ProductImageRepository;
import com.hazelngrazel.app.helper.FileUploadHelper;
import com.hazelngrazel.app.repository.CategoryRepository;
import com.hazelngrazel.app.repository.ProductRepository;
import com.hazelngrazel.app.repository.ProductVariantRepository;
import com.hazelngrazel.app.serviceI.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductServiceI {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductVariantRepository productVariantRepository;
	
	@Autowired
	ProductImageRepository productImageRepository;
	
	@Autowired
	FileUploadHelper fileUploadHelper;
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	@Transactional
	public void saveProduct(ProductRequest request) {

	    CategoryEntity category = categoryRepository
	            .findById(request.getCategoryId())
	            .orElseThrow(() ->
	                    new RuntimeException("Category Not Found"));

	    ProductEntity product = new ProductEntity();

	    product.setCategory(category);
	    product.setProductName(request.getProductName());
	    product.setDescription(request.getDescription());
	    product.setIsDisabled(request.getIsDisabled());
	    product.setIsFeature(request.getIsFeature() != null ? request.getIsFeature() : false);
	    
	    product = productRepository.save(product);
	    
	    String mainImageUrl = null;
	    int minActivePos = Integer.MAX_VALUE;
	    
	    List<Map<String, Object>> metaList = new ArrayList<>();
	    if (request.getImagesMetadata() != null && !request.getImagesMetadata().trim().isEmpty()) {
	        try {
	            metaList = objectMapper.readValue(request.getImagesMetadata(), new TypeReference<List<Map<String, Object>>>() {});
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to parse images metadata: " + e.getMessage());
	        }
	    }
	    
	    if (!metaList.isEmpty() && request.getFiles() != null) {
	        for (Map<String, Object> meta : metaList) {
	            String type = (String) meta.get("type");
	            int position = ((Number) meta.get("position")).intValue();
	            boolean isActive = meta.get("isActive") != null ? (Boolean) meta.get("isActive") : true;
	            
	            if ("new".equals(type)) {
	                Number tempIndexNum = (Number) meta.get("tempIndex");
	                int tempIndex = tempIndexNum.intValue();
	                if (tempIndex >= 0 && tempIndex < request.getFiles().size()) {
	                    MultipartFile file = request.getFiles().get(tempIndex);
	                    if (file != null && !file.isEmpty()) {
	                        Map uploadResult = fileUploadHelper.uploadFile(file);
	                        String secureUrl = (String) uploadResult.get("secure_url");
	                        
	                        ProductImageEntity productImage = new ProductImageEntity();
	                        productImage.setProductId(product.getProductId());
	                        productImage.setImageUrl(secureUrl);
	                        productImage.setPosition(position);
	                        productImage.setIsActive(isActive);
	                        productImageRepository.save(productImage);
	                        
	                        if (isActive && position < minActivePos) {
	                            minActivePos = position;
	                            mainImageUrl = secureUrl;
	                        }
	                    }
	                }
	            }
	        }
	        if (mainImageUrl == null) {
	            List<ProductImageEntity> allImgs = productImageRepository.findByProductIdOrderByPositionAsc(product.getProductId());
	            if (!allImgs.isEmpty()) {
	                for (ProductImageEntity pi : allImgs) {
	                    if (pi.getIsActive()) {
	                        mainImageUrl = pi.getImageUrl();
	                        break;
	                    }
	                }
	                if (mainImageUrl == null) {
	                    mainImageUrl = allImgs.get(0).getImageUrl();
	                }
	            }
	        }
	    } else if (request.getFile() != null && !request.getFile().isEmpty()) {
	        Map data = fileUploadHelper.uploadFile(request.getFile());
	        String secureUrl = (String) data.get("secure_url");
	        
	        ProductImageEntity productImage = new ProductImageEntity();
	        productImage.setProductId(product.getProductId());
	        productImage.setImageUrl(secureUrl);
	        productImage.setPosition(0);
	        productImage.setIsActive(true);
	        productImageRepository.save(productImage);
	    }
	    
	    for (ProductVariantRequest variantRequest : request.getVariants()) {
	        ProductVariantEntity variant = new ProductVariantEntity();
	        variant.setProductId(product.getProductId());
	        variant.setType(variantRequest.getType());
	        variant.setValue(variantRequest.getValue());
	        variant.setPrice(variantRequest.getPrice());
	        productVariantRepository.save(variant);
	    }
	}

//	@Override
//	public Page<ProductResponse> getAllProducts(int page, int size, String status, String name) {
//	    Pageable pageable = PageRequest.of(page, size);
//
//	    boolean hasName = name != null && !name.trim().isEmpty();
//	    boolean hasStatus = status != null && !status.trim().isEmpty();
//
//	    Page<ProductEntity> productEntities;
//
//	    if (hasName && hasStatus) {
//	        Boolean isDisabled = status.equalsIgnoreCase("DISABLED");
//	        productEntities = productRepository.findByProductNameContainingIgnoreCaseAndIsDisabled(name, isDisabled, pageable);
//	    } else if (hasName) {
//	        productEntities = productRepository.findByProductNameContainingIgnoreCase(name, pageable);
//	    } else if (hasStatus) {
//	        Boolean isDisabled = status.equalsIgnoreCase("DISABLED");
//	        productEntities = productRepository.findByIsDisabled(isDisabled, pageable);
//	    } else {
//	        productEntities = productRepository.findAll(pageable);
//	    }
//
//	    return productEntities.map(entity -> {
//	        ProductResponse response = new ProductResponse();
//	        response.setProductId(entity.getProductId());
//	        response.setCategory(entity.getCategory());
//	        response.setProductName(entity.getProductName());
//	        response.setDescription(entity.getDescription());
//	        response.setIsDisabled(entity.getIsDisabled());
//	        response.setIsFeature(entity.getIsFeature());
//	        response.setCreatedTm(entity.getCreatedTm());
//	        response.setUpdatedTm(entity.getUpdatedTm());
//
//	        List<ProductVariantEntity> variants = productVariantRepository.findByProductId(entity.getProductId());
//	        response.setVariants(variants);
//
//	        List<ProductImageEntity> images = productImageRepository.findByProductIdOrderByPositionAsc(entity.getProductId());
//	        response.setImages(images);
//
//	        String primaryUrl = null;
//	        if (images != null) {
//	            for (ProductImageEntity img : images) {
//	                if (img.getIsActive()) {
//	                    primaryUrl = img.getImageUrl();
//	                    break;
//	                }
//	            }
//	            if (primaryUrl == null && !images.isEmpty()) {
//	                primaryUrl = images.get(0).getImageUrl();
//	            }
//	        }
//	        response.setImageUrl(primaryUrl);
//
//	        return response;
//	    });
//	}
	
	@Override
	public Page<ProductResponse> getAllProducts(
	        int page,
	        int size,
	        String status,
	        String name) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

	    boolean hasName =
	            name != null && !name.trim().isEmpty();

	    boolean hasStatus =
	            status != null && !status.trim().isEmpty();

	    Page<ProductEntity> productPage;

	    if (hasName && hasStatus) {
	        Boolean isDisabled =
	                status.equalsIgnoreCase("DISABLED");

	        productPage =
	                productRepository
	                        .findByProductNameContainingIgnoreCaseAndIsDisabled(
	                                name,
	                                isDisabled,
	                                pageable);

	    } else if (hasName) {

	        productPage =
	                productRepository
	                        .findByProductNameContainingIgnoreCase(
	                                name,
	                                pageable);

	    } else if (hasStatus) {

	        Boolean isDisabled =
	                status.equalsIgnoreCase("DISABLED");

	        productPage =
	                productRepository
	                        .findByIsDisabled(
	                                isDisabled,
	                                pageable);

	    } else {

	        productPage =
	                productRepository.findAll(pageable);
	    }

	    List<ProductEntity> products =
	            productPage.getContent();

	    if (products.isEmpty()) {
	        return Page.empty(pageable);
	    }

	    List<Long> productIds =
	            products.stream()
	                    .map(ProductEntity::getProductId)
	                    .collect(Collectors.toList());

	    /*
	     * Query 2
	     */
	    List<ProductVariantEntity> allVariants =
	            productVariantRepository
	                    .findByProductIdIn(productIds);

	    /*
	     * Query 3
	     */
	    List<ProductImageEntity> allImages =
	            productImageRepository
	                    .findByProductIdInOrderByPositionAsc(
	                            productIds);

	    Map<Long, List<ProductVariantEntity>> variantMap =
	            allVariants.stream()
	                    .collect(Collectors.groupingBy(
	                            ProductVariantEntity::getProductId));

	    Map<Long, List<ProductImageEntity>> imageMap =
	            allImages.stream()
	                    .collect(Collectors.groupingBy(
	                            ProductImageEntity::getProductId));

	    List<ProductResponse> responses =
	            products.stream()
	                    .map(product -> {

	                        ProductResponse response =
	                                new ProductResponse();

	                        response.setProductId(
	                                product.getProductId());

	                        response.setCategory(
	                                product.getCategory());

	                        response.setProductName(
	                                product.getProductName());

	                        response.setDescription(
	                                product.getDescription());

	                        response.setIsDisabled(
	                                product.getIsDisabled());

	                        response.setIsFeature(
	                                product.getIsFeature());

	                        response.setCreatedTm(
	                                product.getCreatedTm());

	                        response.setUpdatedTm(
	                                product.getUpdatedTm());

	                        List<ProductVariantEntity> variants =
	                                variantMap.getOrDefault(
	                                        product.getProductId(),
	                                        Collections.emptyList());

	                        List<ProductImageEntity> images =
	                                imageMap.getOrDefault(
	                                        product.getProductId(),
	                                        Collections.emptyList());

	                        response.setVariants(variants);
	                        response.setImages(images);

	                        String primaryImage = images.stream()
	                                .filter(ProductImageEntity::getIsActive)
	                                .map(ProductImageEntity::getImageUrl)
	                                .findFirst()
	                                .orElse(
	                                        images.isEmpty()
	                                                ? null
	                                                : images.get(0)
	                                                        .getImageUrl());

	                        response.setImageUrl(primaryImage);

	                        return response;
	                    })
	                    .collect(Collectors.toList());

	    return new PageImpl<>(
	            responses,
	            pageable,
	            productPage.getTotalElements());
	}

	@Override
	@Transactional
	public void deleteProduct(Long productId, Boolean isDisabled) {
	    ProductEntity product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	    product.setIsDisabled(isDisabled);
	    productRepository.save(product);
	}

	@Override
	@Transactional
	public void updateProduct(Long productId, ProductRequest request) {
	    ProductEntity product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    CategoryEntity category = categoryRepository.findById(request.getCategoryId())
	            .orElseThrow(() -> new RuntimeException("Category not found"));

	    product.setCategory(category);
	    product.setProductName(request.getProductName());
	    product.setDescription(request.getDescription());
	    
	    if (request.getIsDisabled() != null) {
	        product.setIsDisabled(request.getIsDisabled());
	    }
	    if (request.getIsFeature() != null) {
	        product.setIsFeature(request.getIsFeature());
	    }

	    List<Map<String, Object>> metaList = new ArrayList<>();
	    if (request.getImagesMetadata() != null && !request.getImagesMetadata().trim().isEmpty()) {
	        try {
	            metaList = objectMapper.readValue(request.getImagesMetadata(), new TypeReference<List<Map<String, Object>>>() {});
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to parse images metadata: " + e.getMessage());
	        }
	    }

	    String mainImageUrl = null;
	    int minActivePos = Integer.MAX_VALUE;

	    if (!metaList.isEmpty()) {
	        List<Long> keptImageIds = new ArrayList<>();
	        for (Map<String, Object> meta : metaList) {
	            String type = (String) meta.get("type");
	            if ("existing".equals(type)) {
	                Number idNum = (Number) meta.get("id");
	                if (idNum != null) {
	                    keptImageIds.add(idNum.longValue());
	                }
	            }
	        }

	        List<ProductImageEntity> existingImages = productImageRepository.findByProductIdOrderByPositionAsc(productId);
	        for (ProductImageEntity ex : existingImages) {
	            if (!keptImageIds.contains(ex.getId())) {
	                productImageRepository.delete(ex);
	            }
	        }

	        for (Map<String, Object> meta : metaList) {
	            String type = (String) meta.get("type");
	            int position = ((Number) meta.get("position")).intValue();
	            boolean isActive = meta.get("isActive") != null ? (Boolean) meta.get("isActive") : true;

	            if ("existing".equals(type)) {
	                Number idNum = (Number) meta.get("id");
	                if (idNum != null) {
	                    ProductImageEntity pi = productImageRepository.findById(idNum.longValue()).orElse(null);
	                    if (pi != null) {
	                        pi.setPosition(position);
	                        pi.setIsActive(isActive);
	                        productImageRepository.save(pi);

	                        if (isActive && position < minActivePos) {
	                            minActivePos = position;
	                            mainImageUrl = pi.getImageUrl();
	                        }
	                    }
	                }
	            } else if ("new".equals(type)) {
	                Number tempIndexNum = (Number) meta.get("tempIndex");
	                int tempIndex = tempIndexNum.intValue();
	                if (request.getFiles() != null && tempIndex >= 0 && tempIndex < request.getFiles().size()) {
	                    MultipartFile file = request.getFiles().get(tempIndex);
	                    if (file != null && !file.isEmpty()) {
	                        Map uploadResult = fileUploadHelper.uploadFile(file);
	                        String secureUrl = (String) uploadResult.get("secure_url");

	                        ProductImageEntity pi = new ProductImageEntity();
	                        pi.setProductId(productId);
	                        pi.setImageUrl(secureUrl);
	                        pi.setPosition(position);
	                        pi.setIsActive(isActive);
	                        productImageRepository.save(pi);

	                        if (isActive && position < minActivePos) {
	                            minActivePos = position;
	                            mainImageUrl = secureUrl;
	                        }
	                    }
	                }
	            }
	        }

	        if (mainImageUrl == null) {
	            List<ProductImageEntity> allImgs = productImageRepository.findByProductIdOrderByPositionAsc(productId);
	            if (!allImgs.isEmpty()) {
	                for (ProductImageEntity pi : allImgs) {
	                    if (pi.getIsActive()) {
	                        mainImageUrl = pi.getImageUrl();
	                        break;
	                    }
	                }
	                if (mainImageUrl == null) {
	                    mainImageUrl = allImgs.get(0).getImageUrl();
	                }
	            }
	        }
	    } else if (request.getFile() != null && !request.getFile().isEmpty()) {
	        Map data = fileUploadHelper.uploadFile(request.getFile());
	        String secureUrl = (String) data.get("secure_url");

	        productImageRepository.deleteByProductId(productId);
	        ProductImageEntity pi = new ProductImageEntity();
	        pi.setProductId(productId);
	        pi.setImageUrl(secureUrl);
	        pi.setPosition(0);
	        pi.setIsActive(true);
	        productImageRepository.save(pi);
	    }

	    productRepository.save(product);

	    productVariantRepository.deleteByProductId(productId);

	    if (request.getVariants() != null) {
	        for (ProductVariantRequest variantRequest : request.getVariants()) {
	            ProductVariantEntity variant = new ProductVariantEntity();
	            variant.setProductId(productId);
	            variant.setType(variantRequest.getType());
	            variant.setValue(variantRequest.getValue());
	            variant.setPrice(variantRequest.getPrice());
	            productVariantRepository.save(variant);
	        }
	    }
	}

	@Override
	public ProductResponse getProductById(Long productId) {
	    ProductEntity entity = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    ProductResponse response = new ProductResponse();
	    response.setProductId(entity.getProductId());
	    response.setCategory(entity.getCategory());
	    response.setProductName(entity.getProductName());
	    response.setDescription(entity.getDescription());
	    response.setIsDisabled(entity.getIsDisabled());
	    response.setIsFeature(entity.getIsFeature());
	    response.setCreatedTm(entity.getCreatedTm());
	    response.setUpdatedTm(entity.getUpdatedTm());

	    List<ProductVariantEntity> variants = productVariantRepository.findByProductId(entity.getProductId());
	    response.setVariants(variants);

	    List<ProductImageEntity> images = productImageRepository.findByProductIdOrderByPositionAsc(entity.getProductId());
	    response.setImages(images);

	    String primaryUrl = null;
	    if (images != null) {
	        for (ProductImageEntity img : images) {
	            if (img.getIsActive()) {
	                primaryUrl = img.getImageUrl();
	                break;
	            }
	        }
	        if (primaryUrl == null && !images.isEmpty()) {
	            primaryUrl = images.get(0).getImageUrl();
	        }
	    }
	    response.setImageUrl(primaryUrl);

	    return response;
	}

	@Override
	@Transactional
	public void hardDeleteProduct(Long productId) {
	    ProductEntity product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	    productVariantRepository.deleteByProductId(productId);
	    productImageRepository.deleteByProductId(productId);
	    productRepository.delete(product);
	}

	@Override
	public List<ProductResponse> getFeaturedProducts() {
	    List<ProductEntity> entities = productRepository.findTop5ByIsFeatureTrueAndIsDisabledFalseOrderByUpdatedTmDesc();
	    List<ProductResponse> responseList = new ArrayList<>();
	    for (ProductEntity entity : entities) {
	        ProductResponse response = new ProductResponse();
	        response.setProductId(entity.getProductId());
	        response.setCategory(entity.getCategory());
	        response.setProductName(entity.getProductName());
	        response.setDescription(entity.getDescription());
	        response.setIsDisabled(entity.getIsDisabled());
	        response.setIsFeature(entity.getIsFeature());
	        response.setCreatedTm(entity.getCreatedTm());
	        response.setUpdatedTm(entity.getUpdatedTm());

	        List<ProductVariantEntity> variants = productVariantRepository.findByProductId(entity.getProductId());
	        response.setVariants(variants);

	        List<ProductImageEntity> images = productImageRepository.findByProductIdOrderByPositionAsc(entity.getProductId());
	        response.setImages(images);

	        String primaryUrl = null;
	        if (images != null) {
	            for (ProductImageEntity img : images) {
	                if (img.getIsActive()) {
	                    primaryUrl = img.getImageUrl();
	                    break;
	                }
	            }
	            if (primaryUrl == null && !images.isEmpty()) {
	                primaryUrl = images.get(0).getImageUrl();
	            }
	        }
	        response.setImageUrl(primaryUrl);

	        responseList.add(response);
	    }
	    return responseList;
	}
}
