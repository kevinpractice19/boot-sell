package com.imooc.bootsell.service.impl;

import com.imooc.bootsell.dto.CartDTO;
import com.imooc.bootsell.entity.ProductInfo;
import com.imooc.bootsell.enums.ProductStatusEnum;
import com.imooc.bootsell.enums.ResultEnum;
import com.imooc.bootsell.exception.SellException;
import com.imooc.bootsell.repository.ProductInfoRepository;
import com.imooc.bootsell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return this.repository.save(productInfo);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return this.repository.findOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(org.springframework.data.domain.Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return this.repository.findProductInfoByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 商品上架
     *
     * @param productInfoId
     * @return
     */
    @Override
    public ProductInfo onSale(String productInfoId) {
        ProductInfo productInfo = this.repository.findOne(productInfoId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.UP)) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        //更新商品状态
        return this.repository.save(productInfo);
    }

    /**
     * 商品下架
     *
     * @param productInfoId
     * @return
     */
    @Override
    public ProductInfo offSale(String productInfoId) {
        ProductInfo productInfo = this.repository.findOne(productInfoId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN)) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        //更新商品状态
        return this.repository.save(productInfo);
    }

    /**
     * 减库存
     *
     * @param dtoList
     */
    @Override
    public void decreaseStock(List<CartDTO> dtoList) {
        for (CartDTO cartDTO : dtoList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductInfoId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            //更新库存
            this.repository.save(productInfo);
        }
    }

    /**
     * 加库存
     *
     * @param dtoList
     */

    @Override
    public void increaseStock(List<CartDTO> dtoList) {

        for (CartDTO cartDTO : dtoList) {
            ProductInfo productInfo = this.repository.findOne(cartDTO.getProductInfoId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            //更新库存
            this.repository.save(productInfo);
        }
    }

}
